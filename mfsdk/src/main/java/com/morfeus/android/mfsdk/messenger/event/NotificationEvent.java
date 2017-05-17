package com.morfeus.android.mfsdk.messenger.event;

/**
 * This event occurs when card json received through notification.
 */
public final class NotificationEvent {
    private final String cardJson;


    public NotificationEvent(String cardJson) {
        this.cardJson = cardJson;
    }

    public String getCardJson() {
        return cardJson;
    }
}
