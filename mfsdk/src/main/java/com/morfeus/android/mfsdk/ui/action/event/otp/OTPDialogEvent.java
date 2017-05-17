package com.morfeus.android.mfsdk.ui.action.event.otp;

/**
 * This event represent action to display login dialog.
 */
public final class OTPDialogEvent {
    private final boolean show;

    public OTPDialogEvent(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }
}
