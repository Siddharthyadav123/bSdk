package com.morfeus.android.mfsdk.ui.action.event;

/**
 * This event represent the destroy event occurs on exit from framework.
 */
public final class DestroyEvent {
    private final boolean isExit;


    public DestroyEvent(boolean isExit) {
        this.isExit = isExit;
    }

    public boolean isExit() {
        return isExit;
    }
}
