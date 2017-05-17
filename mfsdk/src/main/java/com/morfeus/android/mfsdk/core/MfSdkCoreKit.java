package com.morfeus.android.mfsdk.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.core.config.ConfigManager;
import com.morfeus.android.mfsdk.core.network.NetworkManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents the main interface to the core module of the MF sdk.
 */
@SuppressLint("StaticFieldLeak")
public class MfSdkCoreKit implements MfSdkCore {

    private Context mContext;

    private ConfigManager mConfigManager;

    private NetworkManager mNetworkManager;

    private MfSdkCoreKit(Builder builder) {
        mContext = builder.context;
        mConfigManager = builder.configManager;
        mNetworkManager = builder.networkManager;
    }

    @Override
    public void init() {
        //TODO load latter
//        loadConfig();
    }

    private void loadConfig() {
        mConfigManager.loadConfig();
    }

    public static class Builder {

        private Context context;

        private ConfigManager configManager;

        private NetworkManager networkManager;

        public Builder(@NonNull Context context) {
            checkNotNull(context);
            this.context = context;
        }

        public Builder setConfigManager(@NonNull ConfigManager configManager) {
            checkNotNull(configManager);
            this.configManager = configManager;
            return this;
        }

        public Builder setNetworkManager(@NonNull NetworkManager networkManager) {
            checkNotNull(networkManager);
            this.networkManager = networkManager;
            return this;
        }

        public MfSdkCore build() {
            return new MfSdkCoreKit(this);
        }

    }

}
