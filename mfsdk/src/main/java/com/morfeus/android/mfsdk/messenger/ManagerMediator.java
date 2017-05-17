package com.morfeus.android.mfsdk.messenger;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;

/**
 * Mediator responsible for providing communication between all
 * Manager.
 */
public interface ManagerMediator {

    /**
     * Make init request.
     *
     * @param context    application context
     * @param parcelable pushModel which implements parcelable
     */
    void initRequest(Context context, Parcelable parcelable);

    /**
     * Make init login request
     *
     * @param context    application context
     * @param parcelable pushModel which implements parcelable
     */
    void initLoginRequest(Context context, Parcelable parcelable);

    /**
     * Send message to server.
     *
     * @param context
     * @param chatId
     * @param message
     * @param msgType
     * @param msgId
     * @param textToSpeechMsg
     */
    void messageRequest(Context context, String chatId, String message, String msgType, String msgId, boolean textToSpeechMsg);

    /**
     * Send user login request to server.
     */
    void userLoginRequest(Context context, String username, String password, String token);


    /**
     * Post new message status on {@link ModuleEventBus} to notify UI module.
     * <table Parameter="Status">
     * <tr>
     * <td><b>Status code</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>0</td>
     * <td>Message not send</td>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Message send</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Message read</td>
     * </tr>
     * </table>
     *
     * @param status       status of message for given model
     * @param messageModel message model
     */
    void updateMessageStatus(@MessageStatus int status, OutgoingMsgModel messageModel);

    /**
     * Call this method on exit of framework.
     */
    void exit(Context context);

    /**
     * Post incoming message model.
     *
     * @param messageModel incoming message from server.
     */
    void postMessage(@NonNull MfMsgModel messageModel);

    /**
     * Post error message on {@link ModuleEventBus} to notify UI module
     */
    void postError(String message);

    /**
     * Post Timeout request
     */
    void postTimeoutRequest(Context context, boolean isAutoLogout);
}
