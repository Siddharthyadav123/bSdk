package com.morfeus.android.mfsdk.ui.action.event;

/**
 * Represents back event performed by device back button or
 * actionbar back button.
 */
public final class BackEvent {
    private final String mAction;

    public BackEvent(String action) {
        this.mAction = action;
    }

    public String getAction() {
        return mAction;
    }
}
