package com.morfeus.android.mfsdk.ui.action.event.login;

import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;

/**
 * This event represent login operation performed by user.
 */
public final class LoginEvent {
    private final String username;
    private final String password;
    private final String token;

    public LoginEvent(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public static class Response {
        private final boolean isSuccess;
        private final MfMsgModel mfMsgModel;

        public Response(boolean isSuccess, MfMsgModel mfMsgModel) {
            this.isSuccess = isSuccess;
            this.mfMsgModel = mfMsgModel;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public MfMsgModel getMfMsgModel() {
            return mfMsgModel;
        }
    }
}
