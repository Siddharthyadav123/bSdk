package com.morfeus.android.mfsdk.ui.screen.chat;

import com.morfeus.android.mfsdk.ui.screen.BaseView;

public class ChatPresenterInjector {

    private static ChatPresenterInjector INSTANCE;

    private ChatPresenter mChatPresenter;

    private ChatPresenterInjector() {
    }

    public static ChatPresenterInjector getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChatPresenterInjector();
        return INSTANCE;
    }

    public ChatContract.Presenter getPresenter(BaseView baseView) {
        return mChatPresenter;
    }

    public void setChatPresenter(ChatPresenter chatPresenter) {
        mChatPresenter = chatPresenter;
    }
}
