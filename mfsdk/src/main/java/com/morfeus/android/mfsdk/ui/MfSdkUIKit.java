package com.morfeus.android.mfsdk.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.MessengerConfig;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.entity.Action;
import com.morfeus.android.mfsdk.ui.action.entity.LaunchAction;
import com.morfeus.android.mfsdk.ui.action.exception.UnsupportedActionException;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatScreenModel;
import com.morfeus.android.mfsdk.ui.screen.entity.ChatScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.LocationScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.LoginScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.OTPScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.widget.bubble.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class implements the {@link MfSdkUI}
 */
@SuppressLint("StaticFieldLeak")
public class MfSdkUIKit implements MfSdkUI {

    private static final String TAG = MfSdkUIKit.class.getSimpleName();

    private Context mContext;

    private ConfigManager mConfigManager;

    private ActionManager mActionManager;

    private TemplateFactory mTemplateFactory;

    private TemplateModelFactory mTemplateModelFactory;

    private boolean isConfigLoad;

    public MfSdkUIKit(Builder builder) {
        mContext = builder.context;
        mConfigManager = builder.configManager;
        mActionManager = builder.actionManager;
        mTemplateFactory = builder.templateFactory;
        mTemplateModelFactory = builder.templateModelFactory;
    }

    @Override
    public void init() throws MfSdkUIInitializationException {
        try {
            loadUIConfig();
        } catch (ConfigLoadException e) {
            throw new MfSdkUIInitializationException("Error: Failed to initialize MfUISdk!", e);
        }
    }

    @Override
    public void showScreen(String screenName, Context ctx, String language) throws ScreenNotFoundException {
        if (!isConfigLoad) {
            throw new IllegalStateException("Error: UI config is not loaded, " +
                    "please load UIConfig first.");
        }
        setLanguageProperty(language);
        Screen screen = mConfigManager.getScreen(screenName);
        screen.setParentContext(ctx);
        Action action = new LaunchAction(screen);
        try {
            mActionManager.execute(action);
        } catch (UnsupportedActionException e) {
            LogManager.e(TAG, "showScreen: ", e);
        }
    }

    private void setLanguageProperty(String language) {
        MFSDKProperties sdkProperties = MessengerConfig.getInstance().getSdkProperties();
        boolean isSupportMultiLang = sdkProperties.isSuportMultilanguage();
        setLanguageProperty(isSupportMultiLang, language);
    }

    private void setLanguageProperty(boolean isSupported, String language) {
        LanguageRepository languageRepository = LanguageRepository.getInstance();
        languageRepository.setmMultiLanguageSupportEnabled(isSupported);
        languageRepository.setSelectedLanguage(language);
    }

    @Override
    public void registerTemplate(String templateID, BaseView templateView) {
        mTemplateFactory.registerTemplate(templateID, templateView);
    }

    @Override
    public void registerTemplateModel(String templateID, TemplateModel templateModel) {
        mTemplateModelFactory.registerTemplateModel(templateID, templateModel);
    }

    private void loadUIConfig() throws ConfigLoadException {
        LogManager.i(TAG, "SDK INITIALIZATION: GOING TO LOAD UI CONFIG");
        mConfigManager.loadConfig(new ConfigManager.OnConfigLoad() {

            @Override
            public void onSuccess(@NonNull Object configObject) {
                LogManager.i(TAG, "SDK INITIALIZATION: UI CONFIG LOAD SUCCESS");

                HashMap<String, Screen> mapScreen = (HashMap<String, Screen>) configObject;

                LoginScreen loginScreen = (LoginScreen) mapScreen.get(Screen.LOGIN_SCREEN);
                mConfigManager.registerScreen(loginScreen.getType(), loginScreen);

                OTPScreen otpScreen = (OTPScreen) mapScreen.get(Screen.OTP_SCREEN);
                mConfigManager.registerScreen(otpScreen.getType(), otpScreen);

                // TODO config object will change, currently it's object of ChatScreen
                ChatScreen chatScreen = ((ChatScreen) mapScreen.get(Screen.CHAT_SCREEN));
                mConfigManager.registerScreen(chatScreen.getType(), chatScreen);

                LocationScreen locationScreen = ((LocationScreen) mapScreen.get(Screen.LOCATION_SCREEN));
                mConfigManager.registerScreen(locationScreen.getType(), locationScreen);

                ChatScreenModel chatScreenModel = chatScreen.getChatScreenModel();
                MFEditorViewModel editorViewModel = chatScreenModel.getMfEditorViewModel();

                String id = editorViewModel.getId();
                mConfigManager.registerSubView(id, editorViewModel);

                isConfigLoad = true;
            }

            @Override
            public void onFail(String errMsg) {
                LogManager.i(TAG, "SDK INITIALIZATION: UI CONFIG LOAD FAILED");
                isConfigLoad = false;
                LogManager.e(TAG, "Error: Failed to load UIConfig json! " + ",Message: " + errMsg);
            }
        });
    }

    public static class Builder {
        private Context context;

        private ActionManager actionManager;

        private ConfigManager configManager;

        private TemplateFactory templateFactory;

        private TemplateModelFactory templateModelFactory;

        public Builder(@NonNull Context context) {
            checkNotNull(context);
            this.context = context;
        }

        public Builder setConfigManager(@NonNull ConfigManager configManager) {
            checkNotNull(configManager);
            this.configManager = configManager;
            return this;
        }

        public Builder setActionManager(@NonNull ActionManager actionManager) {
            checkNotNull(actionManager);
            this.actionManager = actionManager;
            return this;
        }

        public Builder setTemplateFactory(@NonNull TemplateFactory templateFactory) {
            checkNotNull(templateFactory);
            this.templateFactory = templateFactory;
            return this;
        }

        public Builder setTemplateModelFactory(@NonNull TemplateModelFactory templateModelFactory) {
            checkNotNull(templateModelFactory);
            this.templateModelFactory = templateModelFactory;
            return this;
        }

        public MfSdkUI build() {
            return new MfSdkUIKit(this);
        }
    }
}
