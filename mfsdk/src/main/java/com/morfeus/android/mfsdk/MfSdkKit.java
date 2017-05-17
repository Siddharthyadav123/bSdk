package com.morfeus.android.mfsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.injection.ModuleInjector;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.MfSdkMsg;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.ui.MfSdkUI;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("StaticFieldLeak")
public final class MfSdkKit implements MfSdk {

    private static final String TAG = MfSdkKit.class.getSimpleName();
    private static Context sContext;

    private static String sAppSessionToken;

    private static String sCustomerId;

    private static MfSdkUI sMfSdkUIKit;

    private static MfSdkMsg sMfSdkMsg;

    private static MFMediaSdk sMfMediaSdk;

    private static MFSDKProperties sMfSdkProperties;

    private static KeepAliveCallback sKeepAliveCallback;

    public MfSdkKit(Builder builder) {
        sContext = builder.context;
        sAppSessionToken = builder.appSessionToken;
        sCustomerId = builder.customerId;
        sMfSdkProperties = builder.sdkProperties;
        sKeepAliveCallback = builder.keepAliveCallback;

        // internal framework module initialization
        sMfSdkUIKit = ModuleInjector.provideMfSdkUIKit(sContext);
        sMfSdkMsg = ModuleInjector.provideMfSdkMsgKit(sContext, sAppSessionToken,
                sCustomerId, sMfSdkProperties);
        sMfMediaSdk = ModuleInjector.provideMFMediaSdk(sContext);
    }

    @Override
    public void registerTemplate(@NonNull Template template) {
        checkNotNull(template);
        String templateId = template.getTemplateID();
        TemplateView templateView = template.getTemplateView();
        TemplateModel templateModel = template.getTemplateModel();

        LogManager.i(TAG, "SDK: REGISTERING TEMPLATE");
        sMfSdkUIKit.registerTemplate(templateId, templateView);
        sMfSdkUIKit.registerTemplateModel(templateId, templateModel);
    }

    @Override
    public void initWithAppSessionToken() throws MfSdkInitializationException {
        LogManager.i(TAG, "SDK INITIALIZATION: GOING TO INIT SESSION");
        sMfSdkUIKit.init();
        sMfSdkMsg.init();
    }

    @Override
    public void showConversation(Context ctx, String language) {
        try {
            LogManager.i(TAG, "SDK: SHOW CONVERSION CALLED");
            sMfSdkUIKit.showScreen(Screen.CHAT_SCREEN, ctx, language);
        } catch (ScreenNotFoundException e) {
            LogManager.d(TAG, "Error: fail to open conversation screen ", e);
        }
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private Context context;

        private String appSessionToken;

        private String customerId;

        private MFSDKProperties sdkProperties;

        private KeepAliveCallback keepAliveCallback;

        public Builder(@NonNull Context context,
                       @NonNull String appSessionToken) {
            this.context = checkNotNull(
                    context,
                    new MfSdkInitializationException("Error: Fail to initialize Morfues SDK," +
                            "Context can't be null!"));
            this.appSessionToken = appSessionToken;
        }

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setSdkProperties(@NonNull MFSDKProperties sdkProperties) {
            this.sdkProperties = checkNotNull(
                    sdkProperties,
                    new MfSdkInitializationException(
                            "Error: Fail to initialize Morfues SDK, " +
                                    "MFSDKProperties can't be null!"));
            return this;
        }

        public Builder setKeepAliveListener(@NonNull KeepAliveCallback keepAliveCallback) {
            this.keepAliveCallback = checkNotNull(
                    keepAliveCallback,
                    new MfSdkInitializationException(
                            "Error: Fail to initialize Morfues SDK, " +
                                    "keepAliveCallback can't be null!"));
            return this;
        }

        public MfSdkKit build() {
            return new MfSdkKit(this);
        }
    }

}
