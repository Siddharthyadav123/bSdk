package com.morfeus.android.mfsdk.messenger.source.net.request;

import com.morfeus.android.mfsdk.volley.Response.ErrorListener;
import com.morfeus.android.mfsdk.volley.Response.Listener;

import java.util.Map;

/**
 * Builder for building {@link MfRequestModel}.
 * {@link MfRequestModel} used as request data model which are
 * required for creating {@link com.android.volley.Request}.
 *
 * See Also: {@link RequestFactory} and {@link GenericRequest}
 */
public class MfRequestModel {
    private final Listener listener;
    private final ErrorListener errorListener;
    private final Map<String, String> header;
    private String body = "";
    private String botId;

    MfRequestModel(Builder builder) {
        this.listener = builder.listener;
        this.errorListener = builder.errorListener;
        this.header = builder.header;
        this.botId = builder.botId;
        this.body = builder.body;
    }

    public String getBotId() {
        return botId;
    }

    public String getBody() {
        return body;
    }

    public Listener getListener() {
        return listener;
    }

    public ErrorListener getErrorListener() {
        return errorListener;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public static class Builder {
        private final Listener listener;
        private final ErrorListener errorListener;
        private final Map<String, String> header;

        private String body = "";
        private String botId;


        public Builder(Map<String, String> header,
                       Listener listener,
                       ErrorListener errorListener) {
            this.listener = listener;
            this.errorListener = errorListener;
            this.header = header;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setBotId(String botId) {
            this.botId = botId;
            return this;
        }

        public MfRequestModel build() {
            return new MfRequestModel(this);
        }


    }
}
