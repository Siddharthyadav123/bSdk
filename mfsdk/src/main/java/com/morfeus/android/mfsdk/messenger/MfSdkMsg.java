package com.morfeus.android.mfsdk.messenger;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Interface represent the interface to the Messenger module of MFSdk.
 */
public interface MfSdkMsg {
    /**
     * Initialize {@link MfSdkMsg}
     *
     * @throws MfSdkMsgInitializationException thrown when {@link MfSdkMsg} initialization fails
     */
    void init() throws MfSdkMsgInitializationException;

    /**
     * Make init request to server to initialize conversation.
     * Init request returns welcome message on successful initialization.
     *
     * @return Welcome message on success.
     */
    void loadInitMessage(Parcelable parcelable);

    /**
     * Send message to server
     *
     * @param message
     * @param msgType
     * @param speechToText
     * @param msgId        : unique id for outgoing message
     */
    void sendMessage(String message, @NonNull String msgType, boolean speechToText, @NonNull String msgId);

}
