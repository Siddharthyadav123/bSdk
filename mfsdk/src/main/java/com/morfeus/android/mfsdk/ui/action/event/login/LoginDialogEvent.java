package com.morfeus.android.mfsdk.ui.action.event.login;

/**
 * This event represent action to display login dialog.
 */
public final class LoginDialogEvent {
    private final boolean show;
    private final String token;

    public LoginDialogEvent(boolean show, String token) {
        this.show = show;
        this.token = token;
    }

    public boolean isShow() {
        return show;
    }

    public String getToken() {
        return token;
    }
}
