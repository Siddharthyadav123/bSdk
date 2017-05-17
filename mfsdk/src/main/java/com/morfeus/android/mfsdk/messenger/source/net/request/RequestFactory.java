package com.morfeus.android.mfsdk.messenger.source.net.request;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.source.MfService;
import com.morfeus.android.mfsdk.volley.Request;

/**
 * Factory to provide request based on request type.
 * Request types are defined into {@link MfService}.
 */
public class RequestFactory {

    private static final String TAG = RequestFactory.class.getSimpleName();

    private static RequestFactory sInstance;

    private MfURLFactory mURLFactory;

    private RequestFactory(MfURLFactory urlFactory) {
        mURLFactory = urlFactory;
    }

    public static RequestFactory getInstance(MfURLFactory urlFactory) {
        if (sInstance == null) {
            sInstance = new RequestFactory(urlFactory);
        }
        return sInstance;
    }


    @SuppressWarnings("unchecked")
    public Request getRequest(int id, @NonNull MfRequestModel data) {
        switch (id) {
            case MfService.INIT_POST_REQUEST: {
                MfURLModel urlModel = new MfURLModel();
                urlModel.addPath("botId", data.getBotId());

                String urlStr = mURLFactory.getUrl(id, urlModel);

                return new GenericRequest(Request.Method.POST, urlStr, data.getBody(),
                        data.getListener(), data.getErrorListener(), data.getHeader());

            }
            case MfService.MESSAGE_POST_REQUEST: {
                MfURLModel urlModel = new MfURLModel();
                urlModel.addPath("botId", data.getBotId());

                String urlStr = mURLFactory.getUrl(id, urlModel);

                return new GenericRequest(Request.Method.POST, urlStr, data.getBody(),
                        data.getListener(), data.getErrorListener(), data.getHeader());

            }
            case MfService.LOGIN_POST_REQUEST: {
                MfURLModel urlModel = new MfURLModel();
                // TODO Add path


                String urlStr = mURLFactory.getUrl(id, urlModel);

                return new GenericRequest(Request.Method.POST, urlStr, data.getBody(),
                        data.getListener(), data.getErrorListener(), data.getHeader());
            }
            case MfService.TIMEOUT_POST_REQUEST: {
                //TODO  timeout related changes in method type
                MfURLModel urlModel = new MfURLModel();
                // TODO Add path


                String urlStr = mURLFactory.getUrl(id, urlModel);

                return new GenericRequest(Request.Method.POST, urlStr, data.getBody(),
                        data.getListener(), data.getErrorListener(), data.getHeader());
            }
            default:
                throw new IllegalArgumentException(TAG + ", Error: Request not found!");

        }
    }
}
