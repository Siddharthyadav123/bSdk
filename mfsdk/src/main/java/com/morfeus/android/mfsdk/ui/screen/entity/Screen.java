package com.morfeus.android.mfsdk.ui.screen.entity;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * This interface represent the screen and its property.
 */
//TODO Builder
public interface Screen {

    String CHAT_SCREEN = "chat_screen";
    String LOGIN_SCREEN = "login_screen";
    String OTP_SCREEN = "otp_screen";
    String LOCATION_SCREEN = "location_screen";

    @NonNull
    String getType();

    void setParentContext(Context ctx);
}
