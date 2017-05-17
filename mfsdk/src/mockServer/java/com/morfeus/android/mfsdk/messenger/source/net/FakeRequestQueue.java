package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;

import com.morfeus.android.mfsdk.volley.RequestQueue;
import com.morfeus.android.mfsdk.volley.toolbox.BasicNetwork;
import com.morfeus.android.mfsdk.volley.toolbox.NoCache;


/**
 * Fake request queue
 */
public class FakeRequestQueue extends RequestQueue {

    public FakeRequestQueue(Context context) {
        super(new NoCache(), new BasicNetwork(new FakeHttpStack(context)));
    }

}
