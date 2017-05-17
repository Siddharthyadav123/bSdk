package com.morfeus.android.mfsdk.messenger.source.net;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.session.MfTimeoutWorkerThread;
import com.morfeus.android.mfsdk.messenger.source.MfService;
import com.morfeus.android.mfsdk.volley.Request;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents set of api provided to make network call.
 */
// TODO make package private
public final class MfRemoteService implements MfService {

    private static MfRemoteService sInstance;
    private RestClient mRestClient;
    private MfTimeoutWorkerThread mTimeoutWorkerThread;


    private MfRemoteService(@NonNull RestClient restClient,
                            @NonNull MfTimeoutWorkerThread timeoutWorkerThread) {
        mRestClient = checkNotNull(restClient);
        mTimeoutWorkerThread = checkNotNull(timeoutWorkerThread);
    }

    public static MfRemoteService getInstance(RestClient restClient
            , MfTimeoutWorkerThread timeoutWorkerThread) {
        if (sInstance == null)
            sInstance = new MfRemoteService(restClient, timeoutWorkerThread);
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public void destoryTimeoutWorkerThread() {
        mTimeoutWorkerThread.destroyTimeoutThread();
    }

    @Override
    public <T> void init(Request<T> request) {
        mTimeoutWorkerThread.initTimeoutTread();
        mRestClient.addToRequestQueue(request);
    }

    @Override
    public <T> void sendMessage(Request<T> request) {
        mTimeoutWorkerThread.refreshTimeoutTread();
        mRestClient.addToRequestQueue(request);
    }

    @Override
    public <T> void sendLogin(Request<T> request) {
        mTimeoutWorkerThread.refreshTimeoutTread();
        mRestClient.addToRequestQueue(request);
    }

    @Override
    public <T> void sendTimeout(Request<T> request) {
        mRestClient.addToRequestQueue(request);
    }
}
