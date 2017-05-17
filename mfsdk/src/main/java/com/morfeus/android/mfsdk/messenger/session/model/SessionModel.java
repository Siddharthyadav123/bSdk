package com.morfeus.android.mfsdk.messenger.session.model;

import android.support.annotation.NonNull;

/**
 * Model class for session
 */
// TODO more details to add
public class SessionModel {
    private String userId;
    private String chatId;
    private String XCRFToken;
    private String cookies;

    public SessionModel(String userId, String chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getXCRFToken() {
        return XCRFToken;
    }

    public void setXCRFToken(@NonNull String token) {
        XCRFToken = token;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
