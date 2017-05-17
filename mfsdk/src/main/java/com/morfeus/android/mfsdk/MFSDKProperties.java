package com.morfeus.android.mfsdk;

import com.morfeus.android.mfsdk.log.LogManager;

import java.util.HashMap;

/**
 * This class represent immutable sdk properties
 */

public final class MFSDKProperties {
    private static final String TAG = MFSDKProperties.class.getSimpleName();
    private final HashMap<String, String> botDetails;
    private final String pushToken;
    private final int inactivityTimeout;
    private final String domain;
    private final String googleVoiceKey;
    private final String nuanceKey;
    private final boolean isSupportRetry;
    private final boolean isSuportMultilanguage;

    private String customer;
    private String appSessionId;

    private static final int DEFAULT_SESSION_TIMEOUT_IN_SECS = 60 * 5; //5 mins
    private int sessionTimeoutInSecs = DEFAULT_SESSION_TIMEOUT_IN_SECS;

    public MFSDKProperties(HashMap<String, String> botDetails,
                           String pushToken,
                           int inactivityTimeout,
                           String domain,
                           String googleVoiceKey,
                           String nuanceKey, boolean suportRetry,
                           boolean suportMultilanguage) {
        this.botDetails = botDetails;
        this.pushToken = pushToken;
        this.inactivityTimeout = inactivityTimeout;
        this.domain = domain;
        this.googleVoiceKey = googleVoiceKey;
        this.nuanceKey = nuanceKey;
        this.isSupportRetry = suportRetry;
        this.isSuportMultilanguage = suportMultilanguage;
        showLogs();
    }

    private void showLogs() {
        if (botDetails != null && botDetails.containsKey("botId")) {
            LogManager.i(TAG, "SDK INITIALIZATION: BOT_ID =" + botDetails.get("botId"));
        }
        LogManager.i(TAG, "SDK INITIALIZATION: DOMAIN =" + domain);
        LogManager.i(TAG, "SDK INITIALIZATION: SUPPORT_RETRY =" + isSupportRetry);
        LogManager.i(TAG, "SDK INITIALIZATION: SUPPORT_MULTI_LANGUAGE =" + isSuportMultilanguage);
        LogManager.i(TAG, "SDK INITIALIZATION: SESSION TIMEOUT IN SECONDS =" + sessionTimeoutInSecs);
    }

    public HashMap<String, String> getBotDetails() {
        return botDetails;
    }

    public String getPushToken() {
        return pushToken;
    }

    public int getInactivityTimeout() {
        return inactivityTimeout;
    }

    public String getDomain() {
        return domain;
    }

    public String getGoogleVoiceKey() {
        return googleVoiceKey;
    }

    public String getNuanceKey() {
        return nuanceKey;
    }

    public boolean isSupportRetry() {
        return isSupportRetry;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAppSessionId() {
        return appSessionId;
    }

    public void setAppSessionId(String appSessionId) {
        this.appSessionId = appSessionId;
    }

    public boolean isSuportMultilanguage() {
        return isSuportMultilanguage;
    }

    public int getSessionTimeoutInSecs() {
        return sessionTimeoutInSecs;
    }

    public void setSessionTimeoutInSecs(int sessionTimeoutInSecs) {
        this.sessionTimeoutInSecs = sessionTimeoutInSecs;
    }
}
