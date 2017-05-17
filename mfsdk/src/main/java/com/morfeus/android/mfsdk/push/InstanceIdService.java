package com.morfeus.android.mfsdk.push;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.morfeus.android.mfsdk.log.LogManager;

/**
 * Retrieve latest token and store it for further use.
 */
public class InstanceIdService extends FirebaseInstanceIdService {

    public static final String MFSDK_FCN_TOKEN = "mfsdk_fcn_token";
    public static final String MFSDK_PREF = "mfsdk";
    private static final String TAG = InstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        saveToken(token);
        sendTokenToServer(token);
    }


    private void saveToken(String token) {
        LogManager.d(TAG, "Token: " + token);
        SharedPreferences.Editor editor =
                getApplicationContext().getSharedPreferences(MFSDK_PREF, MODE_PRIVATE).edit();
        editor.putString(MFSDK_FCN_TOKEN, token);
        editor.apply();
    }

    private void sendTokenToServer(String token) {
        // TODO send token to server
    }


}
