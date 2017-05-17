package com.morfeus.android.mfsdk.messenger.event;

import com.morfeus.android.mfsdk.messenger.message.MessageStatus;

/**
 * This event represent updated status of message.
 */
public final class MessageStatusEvent {
    private final int status;
    private final String messageId;

    public MessageStatusEvent(@MessageStatus int status, String messageId) {
        this.status = status;
        this.messageId = messageId;
    }

    public int getStatus() {
        return status;
    }

    public String getMessageId() {
        return messageId;
    }
}
