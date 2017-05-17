package com.morfeus.android.mfsdk.ui.screen.voice;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.event.MessageReceiveEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginDialogEvent;
import com.morfeus.android.mfsdk.ui.action.event.otp.OTPDialogEvent;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.screen.entity.LoginScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.OTPScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.screen.parser.TemplateModelParser;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class VoicePresenter implements VoiceContract.Presenter {

    private static final String TAG = VoicePresenter.class.getSimpleName();


    private final ConfigManager mConfigManager;
    private final VoiceContract.View mVoiceView;
    private final TemplateModelParser mTemplateModelParser;

    public VoicePresenter(@NonNull ConfigManager configManager,
                          @NonNull VoiceContract.View voiceView,
                          @NonNull TemplateModelParser templateModelParser) {
        mConfigManager = checkNotNull(configManager);
        mVoiceView = checkNotNull(voiceView);
        mTemplateModelParser = checkNotNull(templateModelParser);
        mVoiceView.setPresenter(this);
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onMessageEvent(MessageReceiveEvent event) {
        if (event.getMessage() != null) {
            List<TemplateModel> templateModels = null;
            try {
                templateModels = mTemplateModelParser.parse(event.getMessage().getMsgItems());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            if (templateModels != null && templateModels.size() == 1) {
                mVoiceView.showMessage(templateModels.get(0));
            } else {
                mVoiceView.showMessages(templateModels);
            }
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginDialogEvent(LoginDialogEvent event) {
        if (event.isShow())
            try {
                LoginScreen loginScreen
                        = (LoginScreen) mConfigManager.getScreen(Screen.LOGIN_SCREEN);

                if (loginScreen != null) {
                    LoginModel loginModel = loginScreen.getLoginModel();
                    String token = event.getToken();
                    loginModel.setToken(token);
                    mVoiceView.showLoginDialog(loginModel);
                } else {
                    throw new NullPointerException("Error: LoginScreen model can't be null!");
                }

            } catch (ScreenNotFoundException e) {
                LogManager.e(TAG, "onLoginDialogEvent: ", e);
            }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onOTPDialogEvent(OTPDialogEvent event) {
        if (event.isShow())
            try {
                OTPScreen otpScreen
                        = (OTPScreen) mConfigManager.getScreen(Screen.OTP_SCREEN);
                if (otpScreen != null)
                    mVoiceView.showOTPDialog(otpScreen.getmOtpModel());

            } catch (ScreenNotFoundException e) {
                LogManager.e(TAG, "onLoginDialogEvent: ", e);
            }
    }
}
