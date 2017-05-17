package com.morfeus.android.mfsdk.ui.screen.voice;

import com.morfeus.android.mfsdk.ui.screen.BasePresenter;
import com.morfeus.android.mfsdk.ui.screen.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;

import java.util.List;

public interface VoiceContract {
    interface View extends BaseView<Presenter> {
        void showMessages(List<TemplateModel> templateModels);

        void showMessage(TemplateModel templateModel);

        void showLoginDialog(LoginModel loginModel);

        void showOTPDialog(OTPModel otpModel);
    }

    interface Presenter extends BasePresenter {

    }
}
