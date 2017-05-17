package com.morfeus.android.mfsdk.messenger.session;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.event.NetworkEvent;
import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.messenger.utils.UniqueBlockingQueue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SessionModel used for chat
 */
public abstract class MfSession {

    public static final int SINGLE_USER_SESSION = 1;
    private static final String TAG = MfSession.class.getSimpleName();
    private final NetUtil mNetUtil;
    private Context mContext;
    private SessionModel mSessionModel;
    private UniqueBlockingQueue<Object> mActualMessageQueue;
    private UniqueBlockingQueue<OutgoingMsgModel> mOfflineMessageQueue;
    private MfConnectionManager mConnectionManager;
    private BroadcastReceiver mNetworkChangeListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                if (mNetUtil.isConnected()) {
                    ModuleEventBus.getDefault().post(new NetworkEvent(true));
                    while (mOfflineMessageQueue.size() > 0) {
                        OutgoingMsgModel outgoingMsgModel = mOfflineMessageQueue.pollFirst();
                        sendOfflineMessage(outgoingMsgModel);
                    }
                } else {
                    ModuleEventBus.getDefault().post(new NetworkEvent(false));
                }
            }
        }
    };

    public MfSession(@NonNull Context context,
                     @NonNull SessionModel sessionModel,
                     @NonNull UniqueBlockingQueue<Object> actualMessageQueue,
                     @NonNull UniqueBlockingQueue<OutgoingMsgModel> offlineMessageQueue,
                     @NonNull MfConnectionManager connectionManager,
                     @NonNull NetUtil netUtil) {

        mContext = context;
        mSessionModel = sessionModel;
        mActualMessageQueue = actualMessageQueue;
        mOfflineMessageQueue = offlineMessageQueue;
        mConnectionManager = connectionManager;
        mNetUtil = checkNotNull(netUtil);

        IntentFilter netIntentFilter = new IntentFilter();
        netIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mNetworkChangeListener, netIntentFilter);
    }

    abstract void invalidate();

    public SessionModel getData() {
        return mSessionModel;
    }

    public void addMessage(final OutgoingMsgModel outgoingMsgModel) {
        boolean isMsgAdd = mActualMessageQueue.add(outgoingMsgModel);
        if (isMsgAdd) {
            mConnectionManager.sendMessage(outgoingMsgModel, this, new OnMessageSendListener() {
                @Override
                public void onSuccess(MfMsgModel msgModel) {
                    // Incoming message from server as response
                    mActualMessageQueue.add(msgModel);
                }

                @Override
                public void onFail(String message) {
                    // when fail to send message we assume that app is offline and
                    // keeping those message in offline message queue.
                    mOfflineMessageQueue.add(outgoingMsgModel);
                }
            });
        }
    }

    private void sendOfflineMessage(final OutgoingMsgModel outgoingMsgModel) {
        mConnectionManager.sendMessage(outgoingMsgModel, this, new OnMessageSendListener() {
            @Override
            public void onSuccess(MfMsgModel msgModel) {
                mActualMessageQueue.add(msgModel);
            }

            @Override
            public void onFail(String message) {
                mOfflineMessageQueue.add(outgoingMsgModel);
            }
        });
    }

    public void removeMessage(OutgoingMsgModel testMessage) {
        mActualMessageQueue.remove(testMessage);
    }

    public void clearMessages() {
        mActualMessageQueue.clear();
    }

    public int getMessageCount() {
        return mActualMessageQueue.size();
    }

    public void unregisterNetListener() {
        mContext.unregisterReceiver(mNetworkChangeListener);
    }

    public interface OnMessageSendListener {
        void onSuccess(MfMsgModel msgModel);

        void onFail(String message);
    }
}
