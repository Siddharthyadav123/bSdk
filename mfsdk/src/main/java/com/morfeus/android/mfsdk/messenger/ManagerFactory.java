package com.morfeus.android.mfsdk.messenger;

import android.content.Context;

import com.morfeus.android.mfsdk.messenger.account.AccountManager;
import com.morfeus.android.mfsdk.messenger.message.MessageManager;
import com.morfeus.android.mfsdk.messenger.roster.MfRosterManager;
import com.morfeus.android.mfsdk.messenger.session.MfSessionManager;
import com.morfeus.android.mfsdk.messenger.session.MfTimeoutWorkerThread;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.source.net.MfRemoteService;
import com.morfeus.android.mfsdk.messenger.source.net.RequestQueueInjector;
import com.morfeus.android.mfsdk.messenger.source.net.RestClient;
import com.morfeus.android.mfsdk.messenger.source.net.request.MfURLFactory;
import com.morfeus.android.mfsdk.messenger.source.net.request.RequestFactory;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.volley.RequestQueue;

/**
 * Factory to provide instance of all manager
 */
public class ManagerFactory {

    public static final int ROSTER_MANAGER = 1;
    public static final int ACCOUNT_MANAGER = 2;
    public static final int SESSION_MANAGER = 3;
    public static final int SOURCE_MANAGER = 4;
    public static final int CONNECTION_MANAGER = 5;
    public static final int MESSAGE_MANAGER = 6;

    private static final String TAG = ManagerFactory.class.getSimpleName();
    private static ManagerFactory sInstance;

    private ManagerFactory() {
        // No-op
    }

    public static ManagerFactory getInstance() {
        if (sInstance == null)
            sInstance = new ManagerFactory();
        return sInstance;
    }

    public Object provideManager(int id, Context context) {
        switch (id) {
            case ROSTER_MANAGER:
                return MfRosterManager.getInstance();
            case ACCOUNT_MANAGER:
                return AccountManager.getInstance();
            case SESSION_MANAGER:
                return MfSessionManager.getInstance();
            case SOURCE_MANAGER: {
                RequestQueue requestQueue = RequestQueueInjector.provideRequestQueue(context);
                RestClient restClient = RestClient.getInstance(context, requestQueue);
                MfTimeoutWorkerThread mfTimeoutWorkerThread
                        = MfTimeoutWorkerThread.getInstance(context);
                return MfRemoteService.getInstance(restClient, mfTimeoutWorkerThread);
            }
            case CONNECTION_MANAGER: {
                MfRemoteService remoteService
                        = (MfRemoteService) provideManager(SOURCE_MANAGER, context);
                ManagerMediator managerMediator
                        = ManagerMediatorImpl.getInstance();

                MfURLFactory urlFactory = MfURLFactory.getInstance();
                RequestFactory requestFactory = RequestFactory.getInstance(urlFactory);
                return MfConnectionManager.getInstance(
                        managerMediator, remoteService, requestFactory,
                        NetUtil.getInstance(), MessengerConfig.getInstance());
            }
            case MESSAGE_MANAGER: {
                MfSessionManager sessionManager
                        = (MfSessionManager) provideManager(SESSION_MANAGER, context);
                return MessageManager.getInstance(context, sessionManager);
            }
            default:
                throw new IllegalArgumentException(TAG + ", Error: Manager not found!");
        }
    }
}
