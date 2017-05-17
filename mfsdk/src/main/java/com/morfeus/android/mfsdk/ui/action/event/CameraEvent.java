package com.morfeus.android.mfsdk.ui.action.event;

/**
 * Represents camera event to open device camera.
 */
public final class CameraEvent {
    private final String mAction;

    public CameraEvent(String action) {
        this.mAction = action;
    }

    public String getAction() {
        return mAction;
    }
}
