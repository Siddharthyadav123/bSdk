package com.morfeus.android.mfsdk.push;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.push.model.PushModel;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Helper service for {@link FirebaseServiceImpl}.
 */

public class NotificationServiceHelper extends Service {

    public static final String NOTIFICATION_INTENT_FILTER = "notification_intent_filter";

    private static final String TAG = NotificationServiceHelper.class.getSimpleName();

    private final ServiceBinder mBinder = new ServiceBinder();

    private List<NotificationListener> mNotificationListener = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private final BroadcastReceiver mNotificationBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HashMap<String, String> mapData = (HashMap<String, String>) intent.getSerializableExtra(
                    FirebaseServiceImpl.EXTRA_DATA);
            LogManager.d(TAG, "onReceive: " +
                    "\n mapData > " + mapData.toString());

            PushModel pushModel = createPushModel(mapData);

            Context ctx = NotificationServiceHelper.this.getApplicationContext();
            boolean isBackground = NotificationUtils.isAppIsInBackground(ctx);
            boolean isChatOnTopOfStack = NotificationUtils.isChatOnTopOfStack(ctx);

            LogManager.d(TAG, "onReceive: "
                    + "\n isBackground > " + isBackground
                    + "\n isChatOnTopOfStack > " + isChatOnTopOfStack);

            if (isBackground) {
                NotificationUtils.showHeadsUpNotification(
                        NotificationServiceHelper.this, pushModel);
            }

            if (!isBackground && isChatOnTopOfStack) {
                // show card
                notifyListener(pushModel);
            }

            // TODO below lollipop display notification dialog
            if (!isBackground && !isChatOnTopOfStack) {
                // Open chat activity
                NotificationUtils.showHeadsUpNotification(
                        NotificationServiceHelper.this, pushModel);
            }
        }
    };

    private PushModel createPushModel(HashMap<String, String> mapData) {
        String title = mapData.get("title");
        String description = mapData.get("description");
        String card = mapData.get("card");
        String pushId = mapData.get("pushId");

        return new PushModel(title, description, card, pushId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mNotificationBroadCastReceiver,
                new IntentFilter(NOTIFICATION_INTENT_FILTER));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mNotificationBroadCastReceiver);
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Register to receive notification data.
     */
    public void register(@NonNull NotificationListener listener) {
        if (!mNotificationListener.contains(listener))
            mNotificationListener.add(listener);
    }

    /**
     * Unregister to stop receiving notification data.
     */
    public void unregister(@NonNull NotificationListener listener) {
        if (mNotificationListener.contains(listener))
            mNotificationListener.remove(listener);
    }

    /**
     * Stop {@link NotificationServiceHelper} service.
     */
    public void stopService() {
        stopSelf();
    }

    private void notifyListener(PushModel pushModel) {
        for (NotificationListener listener : mNotificationListener) {
            listener.onMessageReceived(pushModel);
        }
    }

    /**
     * Callback tobe invoked when a notification arrived from server and
     * client application is in background.
     * <p/>
     * <b>Note: if {@link ChatActivity} is on top of the activity stack
     * then this callback will not be invoked.</b>
     */
    public interface NotificationListener {
        void onMessageReceived(PushModel pushModel);
    }

    public class ServiceBinder extends Binder {

        public NotificationServiceHelper getService() {
            return NotificationServiceHelper.this;
        }
    }
}
