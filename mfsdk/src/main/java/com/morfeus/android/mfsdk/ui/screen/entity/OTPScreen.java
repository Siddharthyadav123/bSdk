package com.morfeus.android.mfsdk.ui.screen.entity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class OTPScreen implements Screen {

    private Context mParentContext;

    private OTPModel mOtpModel;

    public OTPScreen(Context context, OTPModel otpModel) {
        mParentContext = checkNotNull(context);
//        mLoginModel = checkNotNull(loginModel);
        mOtpModel = otpModel;
    }

    public OTPModel getmOtpModel() {
        return mOtpModel;
    }

    public Context getParentContext() {
        return mParentContext;
    }

    @Override
    public void setParentContext(Context ctx) {
        mParentContext = ctx;
    }

    @NonNull
    @Override
    public String getType() {
        return Screen.OTP_SCREEN;
    }
}
