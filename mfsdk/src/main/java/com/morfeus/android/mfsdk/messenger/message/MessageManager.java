package com.morfeus.android.mfsdk.messenger.message;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.MfSession;
import com.morfeus.android.mfsdk.messenger.session.MfSessionManager;

import java.util.Date;

/**
 * This class is responsible for interacting with sessions
 * for adding, removing and updating status of message in session.
 */
public final class MessageManager {

    private static MessageManager sInstance;

    private final MfSessionManager mSessionManager;

    private Context mContext;

    private MessageManager(Context context, MfSessionManager sessionManager) {
        mSessionManager = sessionManager;
        mContext = context;
    }

    public static MessageManager getInstance(@NonNull Context context,
                                             @NonNull MfSessionManager sessionManager) {
        if (sInstance == null)
            sInstance = new MessageManager(context, sessionManager);
        return sInstance;
    }

    public void addMessage(@NonNull String chatId, @NonNull String message, @NonNull String msgType,
                           String msgId, boolean speechToText) {
        MfSession session = mSessionManager.getSession(chatId);
        if (session == null) {
            throw new IllegalStateException("Error: No Session found for " + chatId);
        }

        OutgoingMsgModel outgoingMsgModel = new OutgoingMsgModel(new Date().getTime(), message, msgType, msgId);
        outgoingMsgModel.setSpeechToText(speechToText);
        session.addMessage(outgoingMsgModel);
    }
}
