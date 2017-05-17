package com.morfeus.android.mfsdk.messenger.event.error;

/**
 * This event represents error occur during sending message.
 */
public final class SendErrorEvent {
    private final String message;

    public SendErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
