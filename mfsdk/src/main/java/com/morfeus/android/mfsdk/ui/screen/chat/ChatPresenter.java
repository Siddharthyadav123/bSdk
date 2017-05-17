package com.morfeus.android.mfsdk.ui.screen.chat;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.MfSdkMsg;
import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.event.MessageStatusEvent;
import com.morfeus.android.mfsdk.messenger.event.NetworkEvent;
import com.morfeus.android.mfsdk.messenger.event.error.InitErrorEvent;
import com.morfeus.android.mfsdk.messenger.event.error.SendErrorEvent;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.MessageReceiveEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginDialogEvent;
import com.morfeus.android.mfsdk.ui.action.event.otp.OTPDialogEvent;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.entity.ChatScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.LoginScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.OTPScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.screen.parser.TemplateModelParser;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatPresenter implements ChatContract.Presenter {

    private static final String TAG = ChatPresenter.class.getSimpleName();

    private final ActionManager mActionManager;

    private final ConfigManager mConfigManager;

    private final ChatContract.View mChatView;

    private final MfSdkMsg mMFSdkMsg;

    private final TemplateModelParser mTemplateModelParser;

    public ChatPresenter(@NonNull ActionManager actionManager,
                         @NonNull ConfigManager configManager,
                         @NonNull ChatContract.View chatView,
                         @NonNull MfSdkMsg mfSdkMsg,
                         @NonNull TemplateModelParser templateModelParser) {
        mActionManager = checkNotNull(actionManager);
        mConfigManager = checkNotNull(configManager);
        mChatView = checkNotNull(chatView);
        mMFSdkMsg = checkNotNull(mfSdkMsg);
        mTemplateModelParser = checkNotNull(templateModelParser);
        mChatView.setPresenter(this);
    }

    @Override
    public void loadInitMessages(Parcelable parcelable) {
        mChatView.showProgressIndicator(true);
        mMFSdkMsg.loadInitMessage(parcelable);
    }

    @Override
    public void loadMFEditorModel() {
        BaseModel baseModel = mConfigManager.getSubView(ConfigParser.KEY_FOOTER);
        mChatView.showMFEditor(baseModel);
    }

    @Override
    public void loadHeaderModel() {
        Screen chatScreen = null;
        try {
            chatScreen = mConfigManager.getScreen(Screen.CHAT_SCREEN);
            assert ((ChatScreen) chatScreen) != null;
            ChatScreenModel chatScreenModel = ((ChatScreen) chatScreen).getChatScreenModel();
            ActionbarModel actionbarModel = chatScreenModel.getActionbarModel();
            mChatView.showHeader(actionbarModel);
        } catch (ScreenNotFoundException e) {
            LogManager.e(TAG, "loadHeaderModel: ", e);
            mChatView.showToast("Error: fail to show Header");
        }


    }

    @Override
    public void loadLoadingModel() {
        Screen chatScreen = null;
        try {
            chatScreen = mConfigManager.getScreen(Screen.CHAT_SCREEN);
            assert ((ChatScreen) chatScreen) != null;
            ChatScreenModel chatScreenModel = ((ChatScreen) chatScreen).getChatScreenModel();
            LoadingModel loadingModel = chatScreenModel.getLoadingModel();
            mChatView.configLoadingProgress(loadingModel);
        } catch (ScreenNotFoundException e) {
            LogManager.e(TAG, "loadLoadingModel: ", e);
            mChatView.showToast("Error: fail to show Loading view");
        }

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        ModuleEventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        ModuleEventBus.getDefault().unregister(this);
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
                mChatView.showMessage(templateModels.get(0));
            } else {
                mChatView.showMessages(templateModels);
            }

//            if (templateModels != null && templateModels.size() > 0) {
//                TemplateModel templateModel = templateModels.get(0);
//                if (templateModel.isTextToSpeech() && templateModel.getTtsText() != null) {
//                    mChatView.textToSpeechMsg(templateModel.getTtsText());
//                }
//            }

        }
        mChatView.showProgressIndicator(false);
        mChatView.showTyping(false);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onInitError(InitErrorEvent errorEvent) {
        mChatView.showInitError();
        mChatView.scrollToLast();
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
                    mChatView.showLoginDialog(loginModel);
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
                    mChatView.showOTPDialog(otpScreen.getmOtpModel());

            } catch (ScreenNotFoundException e) {
                LogManager.e(TAG, "onLoginDialogEvent: ", e);
            }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onMessengerError(SendErrorEvent errorEvent) {
        // TODO remove hard coded error message
        mChatView.showToast("Fail to send message, server error!");
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onNetworkEvent(NetworkEvent netEvent) {
        mChatView.updateNetworkStatus(netEvent.isConnected());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onMessageStatus(MessageStatusEvent messageStatusEvent) {
        LogManager.d(TAG, ">>msg onMessageStatus: Msg Id= " + messageStatusEvent.getMessageId()
                + " >>status>> " + messageStatusEvent.getStatus());
        mChatView.updateMessageStatus(messageStatusEvent.getMessageId(), messageStatusEvent.getStatus());
    }
}
