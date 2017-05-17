package com.morfeus.android.mfsdk.messenger.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class to get information about device internet connectivity.
 */
public final class NetUtil {

    private static NetUtil sInstance;

    private final Context mContext;

    private NetUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * @param context <b>Application context</b>
     */
    public static NetUtil createInstance(Context context) {
        if (sInstance == null)
            sInstance = new NetUtil(context);
        return sInstance;
    }

    public static NetUtil getInstance() {
        return sInstance;
    }

    public boolean isConnected() {
        ConnectivityManager cm
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

    }
}
