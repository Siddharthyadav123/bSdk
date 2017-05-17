package com.morfeus.android.mfsdk.messenger.source.net.request;

import android.net.Uri;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.source.MfService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MfURLFactory {

    private static final String TAG = MfURLFactory.class.getSimpleName();

    private static MfURLFactory sInstance;

    private Uri.Builder mBuilder;

    private MfURLFactory() {
        // No-op
    }

    public static synchronized MfURLFactory getInstance() {
        if (sInstance == null)
            sInstance = new MfURLFactory();
        return sInstance;
    }

    String getUrl(int id, MfURLModel urlModel) {
        Uri uri;
        mBuilder = new Uri.Builder();
        mBuilder.scheme("https");
        mBuilder.authority(BuildConfig.BASE_URL);

        switch (id) {
            case MfService.INIT_POST_REQUEST:
                uri = mBuilder.appendPath("v1")
                        .appendPath("channels")
                        .appendPath(urlModel.getPath("botId")) // dynamic path
                        .appendPath("init")
                        .build();
                return getUrlStr(uri);
            case MfService.MESSAGE_POST_REQUEST:
                uri = mBuilder.appendPath("v1")
                        .appendPath("channels")
                        .appendPath(urlModel.getPath("botId")) // dynamic path
                        .appendPath("message")
                        .build();
                return getUrlStr(uri);
            case MfService.LOGIN_POST_REQUEST:
                uri = mBuilder.appendPath("v1")
                        .appendPath("login")
                        .build();
                return getUrlStr(uri);
            case MfService.TIMEOUT_POST_REQUEST:
                //TODO  timeout related changes in URL
                uri = mBuilder.appendPath("v1")
                        .appendPath("login")
                        .build();
                return getUrlStr(uri);
            default:
                throw new IllegalArgumentException("Error: Url not found!");
        }
    }

    private String getUrlStr(Uri uri) {
        String urlStr = null;
        try {
            urlStr = URLDecoder.decode(uri.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogManager.e(TAG, e.getMessage());
        }
        return urlStr;
    }
}
