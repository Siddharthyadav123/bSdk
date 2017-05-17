package com.morfeus.android.mfsdk.messenger.source.net.request;

import com.google.gson.JsonSyntaxException;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.volley.AuthFailureError;
import com.morfeus.android.mfsdk.volley.NetworkResponse;
import com.morfeus.android.mfsdk.volley.ParseError;
import com.morfeus.android.mfsdk.volley.Response;
import com.morfeus.android.mfsdk.volley.toolbox.HttpHeaderParser;
import com.morfeus.android.mfsdk.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GenericRequest<T> extends JsonRequest<T> {
    private static final String TAG = GenericRequest.class.getSimpleName();

    private Map<String, String> headers = null;

    /**
     * Basically, this is the constructor which is called by the others.
     * It allows you to send an object of type A to the server and expect a JSON representing a object of type B.
     * The problem with the #JsonObjectRequest is that you expect a JSON at the end.
     * We can do better than that, we can directly receive our POJO.
     * That's what this class does.
     *
     * @param method:        HTTP Method
     * @param url:           url to be called
     * @param requestBody:   The body being sent
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    public GenericRequest(int method, String url, String requestBody,
                          Response.Listener<T> listener, Response.ErrorListener errorListener,
                          Map<String, String> headers) {
        super(method, url, requestBody, listener,
                errorListener);
        this.headers = headers;
        configureRequest();
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        LogManager.d(TAG, "parseNetworkResponse: " + response.toString());
        try {
            // If it's not muted; we just need to create our POJO from the returned JSON and handle correctly the errors
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("headers", response.headers);
            responseMap.put("data", json);

            T parsedObject = (T) responseMap;
            return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        LogManager.d(TAG, "getHeaders: " + headers.toString());
        return headers != null ? headers : super.getHeaders();
    }

    private void configureRequest() {
        // Set retry policy
        // Add headers, for auth for example
        // ...
    }
}
