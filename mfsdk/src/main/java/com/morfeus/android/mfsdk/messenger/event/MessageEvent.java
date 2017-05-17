package com.morfeus.android.mfsdk.messenger.event;

import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;

/**
 * This event represents incoming/outgoing message event.
 */
public final class MessageEvent {
    private final MfMsgModel mMfMsgModel;

    public MessageEvent(MfMsgModel mMfMsgModel) {
        this.mMfMsgModel = mMfMsgModel;
    }

    public MfMsgModel getMessage() {
        return mMfMsgModel;
    }
}
