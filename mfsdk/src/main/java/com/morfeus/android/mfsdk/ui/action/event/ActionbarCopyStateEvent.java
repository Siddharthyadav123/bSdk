package com.morfeus.android.mfsdk.ui.action.event;

public final class ActionbarCopyStateEvent {
    private final String text;

    public ActionbarCopyStateEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
