package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;

import com.morfeus.android.mfsdk.volley.RequestQueue;
import com.morfeus.android.mfsdk.volley.toolbox.Volley;

/**
 * Inject fake request queue
 */
public final class RequestQueueInjector {
    public static RequestQueue provideRequestQueue(Context context) {
        return Volley.newRequestQueue(context.getApplicationContext());
    }
}
