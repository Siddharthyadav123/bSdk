package com.morfeus.android.mfsdk.ui.widget.dialog.login;

/**
 * This interface represents the login view.
 */
public interface LoginView {

    /**
     * Inflates login view.
     */
    void initView();

    /**
     * Set data into login view component.
     *
     * @param loginModel data model to set data in view.
     */
    void setLoginModel(LoginModel loginModel);
}
