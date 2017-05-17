package com.morfeus.android.mfsdk.voice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import static com.google.api.client.repackaged.com.google.common.base.Preconditions.checkNotNull;

/**
 * Fetch the Google Speech access token on background thread.
 */
public class AccessTokenThread extends HandlerThread {
    private static final String TAG = AccessTokenThread.class.getSimpleName();

    private static final int GET = 1;

    private static final String PREFS = "AccessTokenLoader";
    private static final String PREF_ACCESS_TOKEN_VALUE
            = "access_token_value";
    private static final String PREF_ACCESS_TOKEN_EXPIRATION_TIME
            = "access_token_expiration_time";

    private Handler mHandler;

    private Handler mClientHandler;

    private Context mContext;

    public AccessTokenThread(@NonNull Context context, @NonNull Handler clientHandler) {
        super("AccessTokenThread");
        mClientHandler = checkNotNull(clientHandler);
        mContext = checkNotNull(context);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET: {
                        AccessToken token = loadAccessToken();

                        if (token == null) {
                            mClientHandler.sendEmptyMessage(-1);
                            return;
                        }

                        mClientHandler.sendMessage(
                                mClientHandler.obtainMessage(0, loadAccessToken()));

                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Error: operation is not supported!");
                }
            }
        };
        fetch();
    }

    /**
     * Fetch {@link AccessToken} and pass to given handler.
     */
    private void fetch() {
        mHandler.sendEmptyMessage(GET);
    }

    private AccessToken loadAccessToken() {
        final SharedPreferences prefs =
                mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String tokenValue = prefs.getString(PREF_ACCESS_TOKEN_VALUE, null);
        long expirationTimeMillis = prefs.getLong(PREF_ACCESS_TOKEN_EXPIRATION_TIME, -1);

        // Check if the current token is still valid for a while
        if (tokenValue != null && expirationTimeMillis > 0) {
            Date expirationTime = new Date(expirationTimeMillis);
            if (expirationTime.after(aWhileFromNow())) {
                return new AccessToken(tokenValue, expirationTime);
            }
        }

        // ***** WARNING *****
        // In this sample, we load the credential from a JSON file stored in a raw resource folder
        // of this client app. You should never do this in your app. Instead, store the file in your
        // server and obtain an access token from there.
        // *******************
        final InputStream stream = mContext.getResources().openRawResource(R.raw.credential);
        try {
            final GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(SpeechRecognitionAPI.SCOPE);
            final AccessToken token = credentials.refreshAccessToken();
            prefs.edit()
                    .putString(PREF_ACCESS_TOKEN_VALUE, token.getTokenValue())
                    .putLong(PREF_ACCESS_TOKEN_EXPIRATION_TIME, token.getExpirationTime().getTime())
                    .apply();
            return token;
        } catch (IOException e) {
            LogManager.e(TAG, "Failed to obtain access token.", e);
        }
        return null;
    }

    private Date aWhileFromNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();
    }
}
