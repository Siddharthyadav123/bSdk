package com.morfeus.android.mfsdk.ui.screen.entity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginScreen implements Screen {

    private Context mParentContext;

    private LoginModel mLoginModel;

    public LoginScreen(Context context, LoginModel loginModel) {
        mParentContext = checkNotNull(context);
//        mLoginModel = checkNotNull(loginModel);
        mLoginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return mLoginModel;
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
        return Screen.LOGIN_SCREEN;
    }
}
