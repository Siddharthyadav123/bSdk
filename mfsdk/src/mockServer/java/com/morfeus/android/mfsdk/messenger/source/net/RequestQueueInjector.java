package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;
import android.util.Log;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.volley.RequestQueue;


/**
 * Inject fake request queue
 */
public final class RequestQueueInjector {

    private static final String TAG = RequestQueueInjector.class.getSimpleName();

    public static RequestQueue provideRequestQueue(Context context) {
        Log.d(TAG, "provideRequestQueue: using fake request queue...");
        RequestQueue fakeRequestQueue = new FakeRequestQueue(context);
        fakeRequestQueue.start();
        return fakeRequestQueue;
    }
}
