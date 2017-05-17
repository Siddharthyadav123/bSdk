package com.morfeus.android.mfsdk.ui.action.event;

import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;

/**
 * This event occurs when message received from server.
 */
public final class MessageReceiveEvent {
    private final MfMsgModel mMfMsgModel;


    public MessageReceiveEvent(MfMsgModel mMfMsgModel) {
        this.mMfMsgModel = mMfMsgModel;
    }

    public MfMsgModel getMessage() {
        return mMfMsgModel;
    }
}
