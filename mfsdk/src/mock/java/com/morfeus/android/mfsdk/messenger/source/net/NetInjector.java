package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Provided dependency required by source/net package.
 */
public final class NetInjector {

    /**
     * @param context Application context
     */
    public static RestClient provideRestClient(@NonNull Context context) {
        return RestClient.getInstance(context, null);
    }
}
