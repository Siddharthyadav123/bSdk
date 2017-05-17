package com.morfeus.android.mfsdk.messenger.event.error;

/**
 * This class represents the init error.
 */
public final class InitErrorEvent {
    private final String message;

    public InitErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
