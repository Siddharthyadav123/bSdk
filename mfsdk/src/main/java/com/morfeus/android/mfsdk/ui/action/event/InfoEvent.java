package com.morfeus.android.mfsdk.ui.action.event;

public final class InfoEvent {
    private final String action;

    public InfoEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
