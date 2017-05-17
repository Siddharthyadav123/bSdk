package com.morfeus.android.mfsdk.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.MFSDKProperties;
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
import static com.morfeus.android.mfsdk.core.config.ConfigManagerImpl.getInstance;

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
