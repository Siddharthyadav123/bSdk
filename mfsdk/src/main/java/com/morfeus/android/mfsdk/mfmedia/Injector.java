package com.morfeus.android.mfsdk.mfmedia;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.mfmedia.image.cache.BitmapDiskCache;
import com.morfeus.android.mfsdk.volley.RequestQueue;
import com.morfeus.android.mfsdk.volley.toolbox.Volley;

/**
 * Dependency provider
 */
final class Injector {

    /**
     * Provide the instance of {@link BitmapDiskCache}
     *
     * @param context application context
     */
    static BitmapDiskCache provideBitmapDiscCache(@NonNull Context context) {
        return new BitmapDiskCache(context.getApplicationContext());
    }

    private static RequestQueue provideVolleyRequestQueue(@NonNull Context context) {
        return Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Provide instance of {@link MfImageLoader}
     *
     * @param context application context
     */
    static MfImageLoader provideImageLoader(Context context) {

        return new MfImageLoader(
                context,
                provideVolleyRequestQueue(context),
                provideBitmapDiscCache(context)
        );
    }
}
