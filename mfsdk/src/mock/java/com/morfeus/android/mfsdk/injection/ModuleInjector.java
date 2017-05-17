package com.morfeus.android.mfsdk.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.core.MfSdkCore;
import com.morfeus.android.mfsdk.messenger.MfSdkMsg;
import com.morfeus.android.mfsdk.messenger.MfSdkMsgKit;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.ui.MfSdkUI;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Injector to provide module dependency.
 */
public class ModuleInjector {

    private static MfSdkUI sMfSdkUI;

    private static MfSdkCore sMfSdkCore;

    public static MfSdkUI provideMfSdkUIKit(@NonNull Context context) {
        checkNotNull(context);
        return sMfSdkUI;
    }

    public static MfSdkCore provideMfSdkCoreKit(@NonNull Context context) {
        checkNotNull(context);
        return sMfSdkCore;
    }

    public static MfSdkMsg provideMfSdkMsgKit(@NonNull Context context, String appSessionToken,
                                              String customerId, MFSDKProperties sdkProperties) {
        checkNotNull(context);
        return MfSdkMsgKit.createInstance(context, appSessionToken, customerId, sdkProperties);
    }

    public static void setsMfSdkUI(MfSdkUI sMfSdkUI) {
        ModuleInjector.sMfSdkUI = sMfSdkUI;
    }

    public static void setsMfSdkCore(MfSdkCore sMfSdkCore) {
        ModuleInjector.sMfSdkCore = sMfSdkCore;
    }

    public static MFMediaSdk provideMFMediaSdk(@NonNull Context context) {
        checkNotNull(context);
        return MFMediaSdk.createInstance(context);
    }
}
