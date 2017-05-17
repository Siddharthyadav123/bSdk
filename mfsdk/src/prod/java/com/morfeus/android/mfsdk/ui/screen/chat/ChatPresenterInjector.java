package com.morfeus.android.mfsdk.ui.screen.chat;

import com.morfeus.android.mfsdk.messenger.MfSdkMsg;
import com.morfeus.android.mfsdk.messenger.MfSdkMsgKit;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.ActionManagerImpl;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManagerImpl;
import com.morfeus.android.mfsdk.ui.screen.BaseView;
import com.morfeus.android.mfsdk.ui.screen.mapper.MessageModelMapper;
import com.morfeus.android.mfsdk.ui.screen.parser.TemplateModelParser;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;

public class ChatPresenterInjector {
    private static ChatPresenterInjector INSTANCE;

    private ChatPresenterInjector() {
    }

    public static ChatPresenterInjector getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChatPresenterInjector();
        return INSTANCE;
    }

    public ChatContract.Presenter getPresenter(BaseView baseView) {
        ActionManager actionManager = ActionManagerImpl.getInstance();
        ConfigManager configManager = ConfigManagerImpl.getInstance();
        MfSdkMsg mfSdkMsg = MfSdkMsgKit.getInstance();
        TemplateModelFactory templateModelFactory = TemplateModelFactory.getInstance();
        MessageModelMapper modelMapper = MessageModelMapper.createInstance(templateModelFactory);
        // TODO instead of mapper inject template model parser.
        TemplateModelParser templateParser = TemplateModelParser.getInstance(templateModelFactory);

        return new ChatPresenter(
                actionManager,
                configManager,
                (ChatContract.View) baseView,
                mfSdkMsg,
                templateParser);
    }

    public void setChatPresenter(ChatPresenter chatPresenter) {
        // No-op
    }
}
