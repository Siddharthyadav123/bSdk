package com.morfeus.android.mfsdk.mfmedia;

import android.content.Context;

import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.mfmedia.image.cache.BitmapDiskCache;

/**
 * This is main interface to get media files
 */
public final class MFMediaSdk {
    private static MFMediaSdk sMFMediaSdk;
    private final MfImageLoader mImageLoader;
    private final BitmapDiskCache mBitmapDiskCache;

    private MFMediaSdk(Context context) {
        mImageLoader = Injector.provideImageLoader(context);
        mBitmapDiskCache = Injector.provideBitmapDiscCache(context);
    }

    public static MFMediaSdk createInstance(Context context) {
        if (sMFMediaSdk == null)
            sMFMediaSdk = new MFMediaSdk(context);
        return sMFMediaSdk;
    }

    public static MFMediaSdk getInstance() {
        if (sMFMediaSdk == null)
            throw new IllegalStateException("Error: MFMediaSdk isn't initialized!");
        return sMFMediaSdk;
    }

    public MfImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * Clear all cache data on disk.
     */
    public void clearCache() {

    }

    /**
     * Clear cached images specific to bot.
     *
     * @param botId unique bot id
     */
    public void clearImageCache(String botId) {
        mBitmapDiskCache.clearAll(botId);
    }

    /**
     * Clear all cached images.
     */
    public void clearImageCache() {
        mBitmapDiskCache.clearAll();
    }
}
