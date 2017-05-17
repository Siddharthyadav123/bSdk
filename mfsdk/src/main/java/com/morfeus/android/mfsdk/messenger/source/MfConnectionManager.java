package com.morfeus.android.mfsdk.messenger.source;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.ManagerMediator;
import com.morfeus.android.mfsdk.messenger.MessengerConfig;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.MfSession;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.net.MfRemoteService;
import com.morfeus.android.mfsdk.messenger.source.net.request.MfRequestModel;
import com.morfeus.android.mfsdk.messenger.source.net.request.RequestFactory;
import com.morfeus.android.mfsdk.messenger.source.parser.MfMsgParser;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.push.model.PushModel;
import com.morfeus.android.mfsdk.volley.AuthFailureError;
import com.morfeus.android.mfsdk.volley.Request;
import com.morfeus.android.mfsdk.volley.Response;
import com.morfeus.android.mfsdk.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ConnectionManager
 */
//TODO add method for logout
public final class MfConnectionManager {

    private static final String TAG = MfConnectionManager.class.getSimpleName();
    private static MfConnectionManager sInstance;
    private final ManagerMediator mManagerMediator;
    private final MfRemoteService mRemoteService;
    private final RequestFactory mRequestFactory;
    private final NetUtil mNetUtil;
    private final MessengerConfig mMessengerConfig;

    private MfConnectionManager(ManagerMediator managerMediator,
                                MfRemoteService remoteService,
                                RequestFactory requestFactory,
                                NetUtil netUtil,
                                MessengerConfig messengerConfig) {
        mManagerMediator = managerMediator;
        mRemoteService = remoteService;
        mRequestFactory = requestFactory;
        mNetUtil = netUtil;
        mMessengerConfig = checkNotNull(messengerConfig);
    }

    public static MfConnectionManager getInstance(@NonNull ManagerMediator managerMediator,
                                                  @NonNull MfRemoteService remoteService,
                                                  @NonNull RequestFactory requestFactory,
                                                  @NonNull NetUtil netUtil,
                                                  @NonNull MessengerConfig messengerConfig) {
        if (sInstance == null)
            sInstance = new MfConnectionManager(managerMediator, remoteService,
                    requestFactory, netUtil, messengerConfig);
        return sInstance;
    }

    public void sendInitLogin(Response.Listener listener, Response.ErrorListener errorListener,
                              Parcelable parcelable, String token) {
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json; charset=utf8");

        MFSDKProperties sdkProperties = mMessengerConfig.getSdkProperties();
        String botId = sdkProperties.getBotDetails().get("botId");

        PushModel pushModel = (PushModel) parcelable;
        if (pushModel != null) {
            String pushId = pushModel.getPushId();
        }

        //TODO if pushId if not empty send it to server with request body.
        MfRequestModel requestModel = new MfRequestModel
                .Builder(mapHeader, listener, errorListener)
                .setBody("{\n" +
                        "\t\"messageContent\": \"Hi\",\n" +
                        "\t\"messageId\": 969478759462780,\n" +
                        "\t\"messageType\": \"text\",\n" +
                        "\t\"mediaProvider\": \"nuance\",\n" +
//                        "\t\"customerId\": \"" + mMessengerConfig.getCustomerId() + "\",\n" +
                        "\t\"appSessionToken\": \"" + mMessengerConfig.getAppSessionToken() + "\",\n" +
                        "\t\"deviceId\": 990000862471854,\n" +
                        "\t\"pushToken\": \"" + token + "\",\n" +
                        "\t\"deviceBrand\": \"Android\",\n" +
                        "\t\"deviceModel\": \"LG\",\n" +
                        "\t\"sdkType\": \"A\",\n" +
                        "\t\"sdkVersion\": \"1.0\",\n" +
                        "\t\"osVersion\": \"5.0\",\n" +
                        "\t\"os\": \"Android\"\n" +
                        "}")
                .setBotId(botId) // TODO bot id from user
                .build();

        LogManager.d(TAG, "WEB API: INIT LOGIN REQUEST BODY = " + requestModel.getBody());
        Request request = mRequestFactory.getRequest(MfService.INIT_POST_REQUEST, requestModel);
        mRemoteService.init(request);
    }

    public void sendInit(Response.Listener listener, Response.ErrorListener errorListener) {
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json; charset=utf8");

        // TODO currently it's assumed only one bot is available.
        // TODO Need to change when multiple bots become available
        MFSDKProperties sdkProperties = mMessengerConfig.getSdkProperties();
        String botId = sdkProperties.getBotDetails().get("botId");

        MfRequestModel requestModel = new MfRequestModel
                .Builder(mapHeader, listener, errorListener)
                .setBody("{\n" +
                        "\t\"messageContent\": \"Hi\",\n" +
                        "\t\"messageId\": 969478759462780,\n" +
                        "\t\"messageType\": \"text\",\n" +
                        "\t\"mediaProvider\": \"nuance\",\n" +
                        "\t\"customerId\": \"" + mMessengerConfig.getCustomerId() + "\",\n" +
                        "\t\"appSessionToken\": \"" + mMessengerConfig.getAppSessionToken() + "\",\n" +
                        "\t\"deviceId\": 990000862471854,\n" +
                        "\t\"pushToken\": \"1\",\n" +
                        "\t\"deviceBrand\": \"Android\",\n" +
                        "\t\"deviceModel\": \"LG\",\n" +
                        "\t\"sdkType\": \"W\",\n" +
                        "\t\"sdkVersion\": \"1.0\",\n" +
                        "\t\"osVersion\": \"5.0\",\n" +
                        "\t\"os\": \"Android\"\n" +
                        "}")
                .setBotId(botId)
                .build();

        Request request = mRequestFactory.getRequest(MfService.INIT_POST_REQUEST, requestModel);
        mRemoteService.init(request);
    }

    public void sendMessage(final OutgoingMsgModel messageModel, final MfSession session,
                            final MfSession.OnMessageSendListener sessionListener) {
        if (mNetUtil.isConnected()) {
            sendMessageRequest(messageModel, session, sessionListener);
        } else {
            sessionListener.onFail("Error: Internet not connected!");
        }
    }

    private void sendMessageRequest(final OutgoingMsgModel messageModel, MfSession session,
                                    final MfSession.OnMessageSendListener sessionListener) {
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                LogManager.d(TAG, "WEB API: MESSAGE RESPONSE = " + response.toString());

                HashMap<String, Object> mapResponse = (HashMap<String, Object>) response;
                String data = mapResponse.get("data").toString();

                MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(true, data);
                sessionListener.onSuccess(mfMsgModel);
                mManagerMediator.postMessage(mfMsgModel);
                mManagerMediator.updateMessageStatus(MessageStatus.SEND, messageModel);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogManager.e(TAG, "onErrorResponse: " + error.toString());
                sessionListener.onFail(error.getMessage());
                MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(true, "{\n" +
                        "\"conversation\" : {\n" +
                        "\t\t\"from\" : \"5w47394784104\",\n" +
                        "\t\t\"to\" : \"455308117027085\",\n" +
                        "\t\t\"applicationId\" : \"72018127503568257761\"\n" +
                        "\t},\n" +
                        "  \"messages\": [\n" +
                        "    {\n" +
                        "     \t    \"id\" : \"mid.1486016734623909129163442002\",\n" +
                        "          \t\"from\" : \"5w47394784104\",\n" +
                        "          \t\"to\" : \"455308117027085\",\n" +
                        "          \"message\": {\n" +
                        "            \"type\": \"text\",\n" +
                        "            \"text\": \"Sorry error in processing your request.\"\n" +
                        "          }\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}");
                LogManager.d(TAG, "WEB API: REQUEST FAILED");
                mManagerMediator.postMessage(mfMsgModel);
//                mManagerMediator.postError(error.getMessage());
                mManagerMediator.updateMessageStatus(MessageStatus.FAILED, messageModel);
            }
        };

        String msgId = messageModel.getMsgId();
        String msg = messageModel.getText();
        String msgType = messageModel.getType();

        String mediaType = "";
        if (messageModel.isSpeechToText()) {
            mediaType = "speechToText";
        }

        MFSDKProperties sdkProperties = mMessengerConfig.getSdkProperties();
        String botId = sdkProperties.getBotDetails().get("botId");
        String userId = session.getData().getUserId();
        String requestBody;
        // TODO create util creating request body
        if ("postback".equalsIgnoreCase(msgType)) {
            requestBody = "{\n" +
                    "\t\"userId\" : " + userId + ",\n" +
                    "\t\"messageContent\" : " + msg + ",\n" +
                    "\t\"messageId\" : " + msgId + ",\n" +
                    "\t\"messageType\" : \"" + msgType + "\",\n" +
                    "\t\"mediaType\" : \"" + mediaType + "\",\n" +
                    "\t\"deviceId\" : 990000862471854,\n" +
                    "\t\"deviceBrand\" : \"Android\",\n" +
                    "\t\"deviceModel\" : \"LG\",\n" +
                    "\t\"applicationId\" : 928374927349,\n" +
                    "\t\"sdkType\" : \"A\",\n" +
                    "\t\"sdkVersion\" : \"1.0\",\n" +
                    "\t\"osVersion\": \"5.0\",\n" +
                    "\t\"os\": \"Android\"\n" +
                    "}";
        } else {
            requestBody = "{\n" +
                    "\t\"userId\" : " + userId + ",\n" +
                    "\t\"messageContent\" : \"" + msg + "\",\n" +
                    "\t\"messageId\" : \"" + msgId + "\",\n" +
                    "\t\"messageType\" : \"" + msgType + "\",\n" +
                    "\t\"mediaType\" : \"" + mediaType + "\",\n" +
                    "\t\"deviceId\" : 990000862471854,\n" +
                    "\t\"deviceBrand\" : \"Android\",\n" +
                    "\t\"deviceModel\" : \"LG\",\n" +
                    "\t\"applicationId\" : 928374927349,\n" +
                    "\t\"sdkType\" : \"A\",\n" +
                    "\t\"sdkVersion\" : \"1.0\",\n" +
                    "\t\"osVersion\": \"5.0\",\n" +
                    "\t\"os\": \"Android\"\n" +
                    "}";
        }
        LogManager.d(TAG, "WEB API: REQUEST BODY = " + requestBody);

        String XSRFToken = session.getData().getXCRFToken();
        String cookies = session.getData().getCookies();

        // TODO create Util for header creation
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json; charset=utf8");
        mapHeader.put("X-XSRF-TOKEN", XSRFToken);
        mapHeader.put("Cookie", cookies);

        MfRequestModel requestModel = new MfRequestModel
                .Builder(mapHeader, listener, errorListener)
                .setBody(requestBody)
                .setBotId(botId)
                .build();
        Request request = mRequestFactory.getRequest(MfService.MESSAGE_POST_REQUEST, requestModel);
        mRemoteService.sendMessage(request);
    }

    public void sendLogin(Response.Listener listener, Response.ErrorListener errorListener,
                          MfSession session, String username, String password, String token) {

        String XSRFToken = session.getData().getXCRFToken();
        String cookies = session.getData().getCookies();

        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json; charset=utf8");
        // TODO confirm if it can be removed
        mapHeader.put("Cookie", cookies);

        String requestBody = "{\n" +
                "\t\"userName\" : \"" + username + "\",\n" +
                "\t\"password\" : \"" + password + "\",\n" +
                "\t\"token\" : " + token + "\n" +
                "}";
        MfRequestModel requestModel = new MfRequestModel
                .Builder(mapHeader, listener, errorListener)
                .setBody(requestBody)
                .build();
        Request request = mRequestFactory.getRequest(MfService.LOGIN_POST_REQUEST, requestModel);
        try {
            LogManager.d(TAG, "WEB API: LOGIN REQUEST = " + request.toString());
            LogManager.d(TAG, "WEB API: LOGIN REQUEST HEADER = " + request.getHeaders().toString());
            LogManager.d(TAG, "WEB API: LOGIN REQUEST BODY = " + requestBody);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        mRemoteService.sendLogin(request);
    }

    public void sendTimeout(Response.Listener listener, Response.ErrorListener errorListener,
                            MfSession session, boolean isAutoLogout) {

        String XSRFToken = session.getData().getXCRFToken();
        String cookies = session.getData().getCookies();

        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json; charset=utf8");
        // TODO confirm if it can be removed
        mapHeader.put("Cookie", cookies);

        String requestBody = "{\n" +
                "\t\"isAutoLogout\" : \"" + isAutoLogout + "\n" +
                "}";
        MfRequestModel requestModel = new MfRequestModel
                .Builder(mapHeader, listener, errorListener)
                .setBody(requestBody)
                .build();
        Request request = mRequestFactory.getRequest(MfService.TIMEOUT_POST_REQUEST, requestModel);
        try {
            LogManager.d(TAG, "WEB API: TIMEOUT REQUEST = " + request.toString());
            LogManager.d(TAG, "WEB API: TIMEOUT REQUEST HEADER = " + request.getHeaders().toString());
            LogManager.d(TAG, "WEB API: TIMEOUT REQUEST BODY = " + requestBody);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        LogManager.d(TAG, "Timeout: requests api");
        mRemoteService.sendTimeout(request);
    }

    public void destroyTimeoutWorkerThread() {
        mRemoteService.destoryTimeoutWorkerThread();
    }
}
