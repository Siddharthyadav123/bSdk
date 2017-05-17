package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.volley.Request;
import com.morfeus.android.mfsdk.volley.RequestQueue;

import static com.morfeus.android.mfsdk.volley.VolleyLog.TAG;

/**
 * REST client to facilitate set of operation for
 * http request.
 */
public final class RestClient {
    private static RestClient sInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private RestClient(Context context, RequestQueue requestQueue) {
        mContext = context;
        mRequestQueue = requestQueue;
    }

    /**
     * @param context Application context
     * @return Instance of {@link RestClient}
     */
    public static synchronized RestClient getInstance(Context context,
                                                      RequestQueue requestQueue) {
        if (sInstance == null)
            sInstance = new RestClient(context, requestQueue);
        return sInstance;
    }

    private RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        LogManager.d(TAG, "Request URL: " + req.getUrl());
        LogManager.d(TAG, "Request: " + req.toString());
        getRequestQueue().add(req);
    }

}
