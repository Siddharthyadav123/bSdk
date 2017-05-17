package com.morfeus.android.mfsdk.ui.widget.dialog.otp;

public interface OTPView {

    /**
     * Inflates OTP view.
     */
    void initView();

    /**
     * Set data into OTP view component.
     *
     * @param otpModel data model to set data in view.
     */
    void setOTPModel(OTPModel otpModel);
}
