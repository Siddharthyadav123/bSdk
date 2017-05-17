package com.morfeus.android.mfsdk.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.core.MfSdkCore;
import com.morfeus.android.mfsdk.core.MfSdkCoreKit;
import com.morfeus.android.mfsdk.core.config.ConfigWriter;
import com.morfeus.android.mfsdk.core.network.NetworkManager;
import com.morfeus.android.mfsdk.messenger.MfSdkMsg;
import com.morfeus.android.mfsdk.messenger.MfSdkMsgKit;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.ui.MfSdkUI;
import com.morfeus.android.mfsdk.ui.MfSdkUIKit;
import com.morfeus.android.mfsdk.ui.action.ActionManagerImpl;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManagerImpl;
import com.morfeus.android.mfsdk.ui.config.ConfigUtils;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParserProvider;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Injector to provide module dependency.
 */
public final class ModuleInjector {

    public static MfSdkUI provideMfSdkUIKit(@NonNull Context context) {
        checkNotNull(context);

        ConfigManager configManager = ConfigManagerImpl.createInstance(
                context,
                ConfigParserProvider.getInstance(),
                new ConfigUtils()
        );
        return new MfSdkUIKit.Builder(context)
                .setActionManager(ActionManagerImpl.getInstance())
                .setConfigManager(configManager)
                .setTemplateFactory(TemplateFactory.getInstance())
                .setTemplateModelFactory(TemplateModelFactory.getInstance())
                .build();
    }

    public static MfSdkCore provideMfSdkCoreKit(@NonNull Context context) {
        checkNotNull(context);
        return new MfSdkCoreKit.Builder(context)
                .setConfigManager(
                        com.morfeus.android.mfsdk.core.config.ConfigManagerImpl
                                .getInstance(
                                        new com.morfeus.android.mfsdk.core.config.ConfigReader(),
                                        new ConfigWriter()))
                .setNetworkManager(new NetworkManager())
                .build();
    }

    public static MfSdkMsg provideMfSdkMsgKit(@NonNull Context context, String appSessionToken,
                                              String customerId, MFSDKProperties sdkProperties) {
        checkNotNull(context);
        return MfSdkMsgKit.createInstance(context, appSessionToken, customerId, sdkProperties);
    }

    public static MFMediaSdk provideMFMediaSdk(@NonNull Context context) {
        checkNotNull(context);
        return MFMediaSdk.createInstance(context);
    }
}
