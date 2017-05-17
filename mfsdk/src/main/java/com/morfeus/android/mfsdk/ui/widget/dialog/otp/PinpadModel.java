package com.morfeus.android.mfsdk.ui.widget.dialog.otp;

import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

/**
 * Pinpad Model used to represent pinpad in OTP screen.
 */
public class PinpadModel {
    private int numberOfCharater;
    private MfDialogStyle style;

    public PinpadModel(int numberOfCharater, MfDialogStyle style) {
        this.numberOfCharater = numberOfCharater;
        this.style = style;
    }

    public int getNumberOfCharater() {
        return numberOfCharater;
    }

    public MfDialogStyle getStyle() {
        return style;
    }

}
