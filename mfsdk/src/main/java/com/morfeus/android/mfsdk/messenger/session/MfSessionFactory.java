package com.morfeus.android.mfsdk.messenger.session;

import android.content.Context;

import com.morfeus.android.mfsdk.messenger.ManagerFactory;
import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.messenger.utils.UniqueBlockingQueue;

/**
 * Factory to provide session
 */
public class MfSessionFactory {

    private static final String TAG = MfSessionFactory.class.getSimpleName();

    private static MfSessionFactory sInstance;

    private MfSessionFactory() {
        // No-op
    }

    public static MfSessionFactory getInstance() {
        if (sInstance == null)
            sInstance = new MfSessionFactory();
        return sInstance;
    }

    public MfSession createSession(int id, SessionModel session, Context context) {
        switch (id) {
            case MfSession.SINGLE_USER_SESSION: {
                MfConnectionManager connectionManager
                        = (MfConnectionManager) ManagerFactory.getInstance()
                        .provideManager(ManagerFactory.CONNECTION_MANAGER, context);

                return new MfSingleUserSession(
                        context,
                        session,
                        new UniqueBlockingQueue<Object>(),
                        new UniqueBlockingQueue<OutgoingMsgModel>(),
                        connectionManager,
                        NetUtil.getInstance());
            }
            default:
                throw new IllegalArgumentException(TAG + ", Error: No SessionModel type found!");
        }
    }
}
