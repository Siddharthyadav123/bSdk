package com.morfeus.android.mfsdk.ui.screen.entity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.screen.chat.ChatScreenModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents the property of Chat screen.
 */
public class ChatScreen implements Screen {

    public static final String EXTRA_MSG_WELCOME = "extra_msg_welcome";

    public static final String EXTRA_MSG_SECONDARY = "extra_msg_secondary";

    public static final String EXTRA_MSG_MF_EDITOR_MODEL = "extra_msg_mf_editor_model";

    private String welcomeMessage;

    private String secondaryMessage;

    private ChatScreenModel chatScreenModel;

    private Context parentContext;

    public ChatScreen(@NonNull Context parentContext,
                      String welcomeMessage,
                      String secondaryMessage,
                      ChatScreenModel chatScreenModel) {
        this.parentContext = checkNotNull(parentContext);
        this.welcomeMessage = welcomeMessage;
        this.secondaryMessage = secondaryMessage;
        this.chatScreenModel = chatScreenModel;
    }

    public Context getParentContext() {
        return parentContext;
    }

    public void setParentContext(Context ctx) {
        parentContext = ctx;
    }

    public ChatScreenModel getChatScreenModel() {
        return chatScreenModel;
    }

    public void setChatScreenModel(ChatScreenModel mfEditorModel) {
        this.chatScreenModel = mfEditorModel;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getSecondaryMessage() {
        return secondaryMessage;
    }

    public void setSecondaryMessage(String secondaryMessage) {
        this.secondaryMessage = secondaryMessage;
    }

    @Override @NonNull
    public String getType() {
        return Screen.CHAT_SCREEN;
    }
}
