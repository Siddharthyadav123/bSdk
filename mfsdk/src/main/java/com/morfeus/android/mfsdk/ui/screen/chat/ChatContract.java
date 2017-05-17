package com.morfeus.android.mfsdk.ui.screen.chat;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.screen.BasePresenter;
import com.morfeus.android.mfsdk.ui.screen.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;

import java.util.List;

public interface ChatContract {

    interface View extends BaseView<Presenter> {
        void showHeader(@NonNull ActionbarModel actionbarModel);

        void showMessages(List<TemplateModel> templateModels);

        void showMessage(TemplateModel templateModel);

        void textToSpeechMsg(String text);

        void showMFEditor(BaseModel mfEditorModel);

        void showToast(String msg);

        void showLoginDialog(LoginModel loginModel);

        void showOTPDialog(OTPModel otpModel);

        void configLoadingProgress(@NonNull LoadingModel loadingModel);

        void showProgressIndicator(boolean showLoading);

        void scrollToLast();

        void showInitError();

        void showTyping(boolean show);

        void updateMessageStatus(String msgId, int status);

        void updateNetworkStatus(boolean isNetworkConnected);
    }

    interface Presenter extends BasePresenter {
        void loadInitMessages(Parcelable parcelable);

        void loadMFEditorModel();

        void loadHeaderModel();

        void loadLoadingModel();
    }
}
