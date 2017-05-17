package com.morfeus.android.mfsdk.messenger;

import com.morfeus.android.mfsdk.MFSDKProperties;

/**
 * Provides messenger config, like app session token, customer id
 * and {@link com.morfeus.android.mfsdk.MFSDKProperties}
 * <p>
 * This class is immutable
 */
public class MessengerConfig {
    private static MessengerConfig sInstance;
    private final MFSDKProperties sdkProperties;
    private final String appSessionToken;
    private final String customerId;


    private MessengerConfig(String appSessionToken, String customerId, MFSDKProperties sdkProperties) {
        this.sdkProperties = sdkProperties;
        this.appSessionToken = appSessionToken;
        this.customerId = customerId;
    }

    public static MessengerConfig create(String appSessionToken, String customerId,
                                         MFSDKProperties sdkProperties) {
        if (sInstance == null)
            sInstance = new MessengerConfig(appSessionToken, customerId, sdkProperties);
        else
            throw new IllegalStateException("Error: MessengerConfig is already created!");

        return sInstance;
    }

    public static MessengerConfig getInstance() {
        if (sInstance == null)
            throw new IllegalStateException("Error: MessengerConfig is not created, " +
                    "please create MessengerConfig before use!");
        return sInstance;
    }

    public MFSDKProperties getSdkProperties() {
        return sdkProperties;
    }

    public String getAppSessionToken() {
        return appSessionToken;
    }

    public String getCustomerId() {
        return customerId;
    }
}
