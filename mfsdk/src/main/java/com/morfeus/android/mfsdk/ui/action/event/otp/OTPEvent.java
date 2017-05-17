package com.morfeus.android.mfsdk.ui.action.event.otp;

/**
 * This event represent login operation performed by user.
 */
public final class OTPEvent {
    private final long otp;

    public OTPEvent(long otp) {
        this.otp = otp;
    }

    public long getOtp() {
        return otp;
    }
}
