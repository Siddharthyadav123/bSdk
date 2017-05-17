package com.morfeus.android.mfsdk.ui.action.event.chat;

/**
 * This event represents typing indication event,
 */
public final class TypingIndicatorEvent {
    private final boolean show;

    public TypingIndicatorEvent(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }
}
