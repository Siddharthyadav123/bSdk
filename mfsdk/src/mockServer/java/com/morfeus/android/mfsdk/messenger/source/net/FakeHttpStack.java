package com.morfeus.android.mfsdk.messenger.source.net;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.volley.AuthFailureError;
import com.morfeus.android.mfsdk.volley.Request;
import com.morfeus.android.mfsdk.volley.toolbox.HttpStack;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Fake http stack used to mock response.
 */
public class FakeHttpStack implements HttpStack {
    private static final int SIMULATE_DELAY_MS = 2000;
    private static final String TAG = FakeHttpStack.class.getSimpleName();

    private static final String DEFAULT_RESPONSE
            = "{\n" +
            "\t\"message\": \"No response found\"\n" +
            "}";
    private static final String INIT_REQUEST
            = "https://" + BuildConfig.BASE_URL + "/v1/channels/" + BuildConfig.botId + "/init";

    private static final String MESSAGE_REQUEST
            = "https://" + BuildConfig.BASE_URL + "/v1/channels/" + BuildConfig.botId + "/message";
    private static final String XCSRF_TOKEN = "25d4e56d-c6fd-4253-b77b-f8c674bcdd51";

    private static final String LOGIN_REQUEST
            = "https://" + BuildConfig.BASE_URL + "/v1/login";

    private static final String LOGIN_REQUEST_FAIL
            = "login/fail";

    private Context mContext;

    FakeHttpStack(Context context) {
        mContext = context;
    }

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
            throws IOException, AuthFailureError {
        try {
            Thread.sleep(SIMULATE_DELAY_MS);
        } catch (InterruptedException e) {

        }

        Log.d(TAG, "performRequesttest: " + request.getHeaders().toString());
        HttpResponse response
                = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
        List<Header> headers = defaultHeaders();
        response.setHeaders(headers.toArray(new Header[0]));

        if (INIT_REQUEST.equalsIgnoreCase(request.getUrl())) {
            response.setEntity(createEntity(request, response, INIT_REQUEST));
        } else if (MESSAGE_REQUEST.equalsIgnoreCase(request.getUrl())) {
            if (!XCSRF_TOKEN.equals(request.getHeaders().get("X-XSRF-TOKEN"))) {
                response.setStatusLine(
                        new BasicStatusLine(
                                HttpVersion.HTTP_1_1,
                                401,
                                "Unauthorized"
                        )
                );
            } else {
                response.setEntity(createEntity(request, response, MESSAGE_REQUEST));
            }
        } else if (LOGIN_REQUEST.equalsIgnoreCase(request.getUrl())) {
            String body = new String(request.getBody(), "UTF-8");
            Log.d(TAG, "performRequest: body > " + body);
            if (isValidLogin(body)) {
                response.setStatusLine(
                        new BasicStatusLine(
                                HttpVersion.HTTP_1_1,
                                401,
                                "Unauthorized"
                        )
                );
                //response.setEntity(createEntity(request, response, LOGIN_REQUEST));
            } else {
                response.setStatusLine(
                        new BasicStatusLine(
                                HttpVersion.HTTP_1_1,
                                401,
                                "Unauthorized"
                        )
                );
//                response.setEntity(createEntity(request, response, LOGIN_REQUEST_FAIL));
            }

        }

        return response;
    }

    private boolean isValidLogin(String body) {
        if (!body.contains("hi"))
            return false;
        if (!body.contains("pass"))
            return false;
        if (!body.contains("1234567TGH"))
            return false;
        return true;
    }

    private HttpEntity createEntity(Request<?> request, HttpResponse response, String url)
            throws UnsupportedEncodingException {
        switch (url) {
            case INIT_REQUEST: {
                int resourceId = mContext.getResources().getIdentifier(
                        "fake_res_init", "raw", mContext.getApplicationContext().getPackageName());
                if (resourceId == 0) {
                    Log.e(TAG, "Error: No fake file named found for " + INIT_REQUEST);
                } else {
                    InputStream stream = mContext.getResources().openRawResource(resourceId);
                    try {
                        String string = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                        return new StringEntity(string);
                    } catch (IOException e) {
                        Log.e(TAG, "Error: fail to read " + "fake_res_init", e);
                    }
                }
            }
            break;
            case MESSAGE_REQUEST: {
                try {
                    return formFakeCardBasedOnMsgText(request, response);
                } catch (AuthFailureError | JSONException authFailureError) {
                    Log.d(TAG, "Error: " + authFailureError.getMessage());
                }
            }
            break;
            case LOGIN_REQUEST: {
                int resourceId = mContext.getResources().getIdentifier(
                        "fake_res_login", "raw", mContext.getApplicationContext().getPackageName());
                if (resourceId == 0) {
                    Log.e(TAG, "Error: No fake file named found for " + INIT_REQUEST);
                } else {
                    InputStream stream = mContext.getResources().openRawResource(resourceId);
                    try {
                        String string = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                        return new StringEntity(string);
                    } catch (IOException e) {
                        Log.e(TAG, "Error: fail to read " + "fake_res_init", e);
                    }
                }
            }
            break;
            case LOGIN_REQUEST_FAIL: {
                int resourceId = mContext.getResources().getIdentifier(
                        "fake_res_login_fail", "raw", mContext.getApplicationContext().getPackageName());
                if (resourceId == 0) {
                    Log.e(TAG, "Error: No fake file named found for " + INIT_REQUEST);
                } else {
                    InputStream stream = mContext.getResources().openRawResource(resourceId);
                    try {
                        String string = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                        return new StringEntity(string);
                    } catch (IOException e) {
                        Log.e(TAG, "Error: fail to read " + "fake_res_init", e);
                    }
                }
            }
            break;
        }

        return new StringEntity(DEFAULT_RESPONSE);
    }

    private HttpEntity formFakeCardBasedOnMsgText(Request<?> request, HttpResponse response)
            throws AuthFailureError, UnsupportedEncodingException, JSONException {
        String resourceFileName = null;
        String requestBody = new String(request.getBody());
        JSONObject reqBodyJson = new JSONObject(requestBody);
        String msgText = reqBodyJson.getString("messageContent");

        if ("infocard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_info_card";
        } else if ("login".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_login_infocard";
        } else if ("infocardwithbutton".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_info_card_with_button";
        } else if ("listcard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_list_card";
        } else if ("imagecard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_image_msg";
        } else if ("textcard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_text_msg";
        } else if ("Last TRansaction - XXXX-9876".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_image_msg";
        } else if ("XXXX-1234".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_list_card";
        } else if ("btncard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_card";
        } else if ("btncardotp".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_card_otp";
        } else if ("reccard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_reciept_card";
        } else if ("touchcard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_touch_card";
        } else if ("btnlimitcard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_limit_card";
        } else if ("btnwithinfocard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_card_with_card_info";
        } else if ("btnwithstatuscard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_status_info";
        } else if ("btnwithremindercard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_reminder_info";
        } else if ("btnwithtransactioncard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_transaction_info";
        } else if ("btnwithimageinfocard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_image_info";
        } else if ("btnwithvideotipscard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_btn_with_video_tips";
        } else if ("btnwithloaninfocard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_rec_btn_with_loan_info";
        } else if ("mapcard".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_map_card";
        } else if ("list null".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_list_null";
        } else if ("webview".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_webview_card";
        } else if ("erro".equalsIgnoreCase(msgText)) {
            response.setStatusLine(
                    new BasicStatusLine(
                            HttpVersion.HTTP_1_1,
                            401,
                            "Unauthorized"
                    )
            );
            resourceFileName = "fake_res_list_null";
        } else if ("hori".equalsIgnoreCase(msgText)) {
            resourceFileName = "fake_res_hori";
        } else {
            resourceFileName = "fake_res_text_msg";
        }


        if (resourceFileName != null) {
            HttpEntity string = getHttpEntity(resourceFileName);
            if (string != null) return string;
        }

        return new StringEntity(DEFAULT_RESPONSE);
    }

    @Nullable
    private HttpEntity getHttpEntity(String resourceFileName) {
        int resourceId = mContext.getResources().getIdentifier(
                resourceFileName, "raw", mContext.getApplicationContext().getPackageName());
        if (resourceId == 0) {
            Log.e(TAG, "Error: No fake file named found for " + MESSAGE_REQUEST);
        } else {
            InputStream stream = mContext.getResources().openRawResource(resourceId);
            try {
                String string = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
                return new StringEntity(string);
            } catch (IOException e) {
                Log.e(TAG, "Error: fail to read " + resourceFileName, e);
            }
        }
        return null;
    }

    private List<Header> defaultHeaders() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd mmm yyyy HH:mm:ss zzz");
        return Lists.<Header>newArrayList(
                new BasicHeader("Date", dateFormat.format(new Date())),
                new BasicHeader("Server",
                        /* Data below is header info of my server */
                        "Apache/1.3.42 (Unix) mod_ssl/2.8.31 OpenSSL/0.9.8e"),
                new BasicHeader("X-CSRF-Token", XCSRF_TOKEN)
        );
    }
}
