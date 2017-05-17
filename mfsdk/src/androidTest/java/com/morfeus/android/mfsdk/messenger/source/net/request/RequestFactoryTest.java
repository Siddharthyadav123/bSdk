package com.morfeus.android.mfsdk.messenger.source.net.request;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.messenger.source.MfService;
import com.morfeus.android.mfsdk.volley.Request;
import com.morfeus.android.mfsdk.volley.Response;
import com.morfeus.android.mfsdk.volley.VolleyError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class RequestFactoryTest {

    private RequestFactory mFactory;

    private MfURLFactory mURLFactory;

    @Before
    public void setUp() {
        mURLFactory = MfURLFactory.getInstance();
        mFactory = RequestFactory.getInstance(mURLFactory);
    }

    @Test
    public void getRequest_returnRequest() {
        String botId = "443k2j4jk23";

        Request initRequest
                = getInitRequest(botId, MfService.INIT_POST_REQUEST);

        assertNotNull(initRequest);
        checkInitRequestUrl(initRequest);

        Request messageRequest
                = getInitRequest(botId, MfService.MESSAGE_POST_REQUEST);

        assertNotNull(messageRequest);
        checkMessageRequest(messageRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRequest_throwNotFoundException() {
        mFactory.getRequest(-1, mock(MfRequestModel.class));
    }

    private void checkMessageRequest(Request messageRequest) {
        String actualUrl = messageRequest.getUrl();
        String expectedUrl
                = "https://morfeus-demo.active.ai/morfeus/v1/channels/443k2j4jk23/message";
        assertEquals(expectedUrl, actualUrl);
    }

    private Request getInitRequest(String botId, int initPostRequest) {
        MfRequestModel data = getRequestData(botId);
        return mFactory.getRequest(initPostRequest, data);
    }

    @NonNull
    private MfRequestModel getRequestData(String botId) {
        MfRequestModel.Builder builder = new MfRequestModel.Builder(
                new HashMap<String, String>(),
                getSuccessListener(),
                getErrorListener()
        );
        builder.setBody("");
        builder.setBotId(botId);
        return builder.build();
    }

    @NonNull
    private Response.Listener getSuccessListener() {
        return new Response.Listener() {
            @Override
            public void onResponse(Object response) {

            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }

    private void checkInitRequestUrl(Request initRequest) {
        String actualUrl = initRequest.getUrl();
        String expectedUrl
                = "https://morfeus-demo.active.ai/morfeus/v1/channels/443k2j4jk23/init";
        assertEquals(expectedUrl, actualUrl);
    }


}