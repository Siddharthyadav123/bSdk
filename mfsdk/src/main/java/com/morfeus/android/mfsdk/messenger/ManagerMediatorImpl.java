package com.morfeus.android.mfsdk.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.account.AccountManager;
import com.morfeus.android.mfsdk.messenger.event.LoginRequestEvent;
import com.morfeus.android.mfsdk.messenger.event.MessageEvent;
import com.morfeus.android.mfsdk.messenger.event.MessageStatusEvent;
import com.morfeus.android.mfsdk.messenger.event.error.InitErrorEvent;
import com.morfeus.android.mfsdk.messenger.event.error.SendErrorEvent;
import com.morfeus.android.mfsdk.messenger.message.MessageManager;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.roster.MfRosterManager;
import com.morfeus.android.mfsdk.messenger.session.MfSession;
import com.morfeus.android.mfsdk.messenger.session.MfSessionFactory;
import com.morfeus.android.mfsdk.messenger.session.MfSessionManager;
import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.net.request.RequestFactory;
import com.morfeus.android.mfsdk.messenger.source.parser.MfMsgParser;
import com.morfeus.android.mfsdk.push.InstanceIdService;
import com.morfeus.android.mfsdk.volley.Response;
import com.morfeus.android.mfsdk.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

// TODO refactor
public class ManagerMediatorImpl implements ManagerMediator {
    private static final String TAG = ManagerMediatorImpl.class.getSimpleName();
    private static ManagerMediatorImpl sInstance;

    private ManagerFactory mManagerFactory;

    private RequestFactory mRequestFactory;

    private ManagerMediatorImpl(ManagerFactory managerFactory,
                                RequestFactory requestFactory) {
        mManagerFactory = managerFactory;
        mRequestFactory = requestFactory;
    }


    public static ManagerMediator createInstance(@NonNull ManagerFactory managerFactory,
                                                 @NonNull RequestFactory requestFactory) {
        if (sInstance == null)
            sInstance = new ManagerMediatorImpl(managerFactory, requestFactory);
        return sInstance;
    }

    public static ManagerMediator getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(TAG + ", Error: "
                    + ManagerMediatorImpl.class.getSimpleName()
                    + " is not initialized. Please call createInstance()");
        }

        return sInstance;
    }

    private long generateRandomNum() {
        return 10000;
    }

    @Override
    public void initRequest(final Context context, Parcelable parcelable) {
        // TODO change to init request instead of initLogin
        Response.Listener initListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                HashMap<String, Object> mapResponse = (HashMap<String, Object>) response;
                Map<String, String> mapHeader = (Map<String, String>) mapResponse.get("headers");
                String XCRFToken = mapHeader.get("X-CSRF-Token");
                String cookies = mapHeader.get("Set-Cookie");

                LogManager.d(TAG, "WEB API: INIT REQUEST HEADER = " + mapHeader.toString());
                LogManager.d(TAG, "WEB API: INIT REQUEST DATA  = " + mapResponse.get("data").toString());

                MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(
                        true,
                        mapResponse.get("data").toString()
                );

                String botId = mfMsgModel.getMsgItems().get(0).getFrom();
                String userId = mfMsgModel.getMsgItems().get(0).getTo();
                String chatId = generateRandomNum() + "";

                // Create user account
                AccountManager accountManager = (AccountManager) mManagerFactory.provideManager(
                        ManagerFactory.ACCOUNT_MANAGER,
                        context
                );
                AccountManager.MfAccount mfAccount = new AccountManager.MfAccount(userId);
                accountManager.createAccount(mfAccount);

                // Add roster contact for bot
                MfRosterManager rosterManager = (MfRosterManager) mManagerFactory.provideManager(
                        ManagerFactory.ROSTER_MANAGER,
                        context
                );
                MfRosterManager.MfRosterContact contact
                        = new MfRosterManager.MfRosterContact(botId);
                rosterManager.addContact(contact);

                // Create session to instantiate chat with bot
                MfSessionManager sessionManager = (MfSessionManager) mManagerFactory.provideManager(
                        ManagerFactory.SESSION_MANAGER,
                        context
                );

                SessionModel sessionData = new SessionModel(userId, chatId);
                if (!TextUtils.isEmpty(XCRFToken))
                    sessionData.setXCRFToken(XCRFToken);
                if (!TextUtils.isEmpty(cookies))
                    sessionData.setCookies(cookies);

                MfSession chatSession = MfSessionFactory
                        .getInstance()
                        .createSession(MfSession.SINGLE_USER_SESSION, sessionData, context);

                sessionManager.addChatSession(chatSession);

                ModuleEventBus.getDefault().post(new MessageEvent(mfMsgModel));
//                } catch (Exception e) {
//                    ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(true, null));
//                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ModuleEventBus.getDefault().post(new InitErrorEvent(error.getMessage()));
            }
        };

        MfConnectionManager connectionManager = (MfConnectionManager) mManagerFactory.provideManager(
                ManagerFactory.CONNECTION_MANAGER,
                context);
        String token = getPushToken(context);
        connectionManager.sendInitLogin(initListener, errorListener, parcelable, token);
    }

    private String getPushToken(Context context) {
        SharedPreferences pref
                = context.getSharedPreferences(InstanceIdService.MFSDK_PREF, Context.MODE_PRIVATE);
        return pref.getString(InstanceIdService.MFSDK_FCN_TOKEN, "");
    }

    @Override
    public void initLoginRequest(final Context context, Parcelable parcelable) {
        Response.Listener initListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                HashMap<String, Object> mapResponse = (HashMap<String, Object>) response;
                Map<String, String> mapHeader = (Map<String, String>) mapResponse.get("headers");
                String XCRFToken = mapHeader.get("X-CSRF-Token");
                String cookies = mapHeader.get("Set-Cookie");

                LogManager.d(TAG, "WEB API: INIT LOGIN REQUEST HEADER = " + mapHeader.toString());
                LogManager.d(TAG, "WEB API: INIT LOGIN REQUEST DATA  = " + mapResponse.toString());


                MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(
                        true,
                        mapResponse.get("data").toString()
                );

                try {
                    String botId = mfMsgModel.getMsgItems().get(0).getFrom();
                    String userId = mfMsgModel.getMsgItems().get(0).getTo();
                    String chatId = generateRandomNum() + "";

                    // Create user account
                    AccountManager accountManager = (AccountManager) mManagerFactory.provideManager(
                            ManagerFactory.ACCOUNT_MANAGER,
                            context
                    );
                    AccountManager.MfAccount mfAccount = new AccountManager.MfAccount(userId);
                    accountManager.createAccount(mfAccount);

                    // Add roster contact for bot
                    MfRosterManager rosterManager = (MfRosterManager) mManagerFactory.provideManager(
                            ManagerFactory.ROSTER_MANAGER,
                            context
                    );
                    MfRosterManager.MfRosterContact contact
                            = new MfRosterManager.MfRosterContact(botId);
                    rosterManager.addContact(contact);

                    // Create session to instantiate chat with bot
                    MfSessionManager sessionManager = (MfSessionManager) mManagerFactory.provideManager(
                            ManagerFactory.SESSION_MANAGER,
                            context
                    );

                    SessionModel sessionData = new SessionModel(userId, chatId);
                    if (!TextUtils.isEmpty(XCRFToken))
                        sessionData.setXCRFToken(XCRFToken);
                    if (!TextUtils.isEmpty(cookies))
                        sessionData.setCookies(cookies);

                    MfSession chatSession = MfSessionFactory
                            .getInstance()
                            .createSession(MfSession.SINGLE_USER_SESSION, sessionData, context);

                    sessionManager.addChatSession(chatSession);

                    ModuleEventBus.getDefault().post(new MessageEvent(mfMsgModel));
                } catch (Exception e) {
                    LogManager.e(TAG, "onResponse: ", e);
                    ModuleEventBus.getDefault().post(new InitErrorEvent("Fail to initialize"));
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ModuleEventBus.getDefault().post(new InitErrorEvent(error.getMessage()));
            }
        };

        MfConnectionManager connectionManager = (MfConnectionManager) mManagerFactory.provideManager(
                ManagerFactory.CONNECTION_MANAGER,
                context);
        String token = getPushToken(context);
        connectionManager.sendInitLogin(initListener, errorListener, parcelable, token);
    }

    @Override
    public void messageRequest(final Context context, String chatId,
                               String message, String msgType, String msgId, boolean speechToText) {
        MessageManager messageManager = (MessageManager) mManagerFactory.provideManager(
                ManagerFactory.MESSAGE_MANAGER,
                context);

        messageManager.addMessage(chatId, message, msgType, msgId, speechToText);
    }

    @Override
    public void userLoginRequest(Context context, String username, String password, String token) {
        Response.Listener loginListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    HashMap<String, Object> mapResponse = (HashMap<String, Object>) response;
                    Map<String, String> mapHeader = (Map<String, String>) mapResponse.get("headers");
                    String XCRFToken = mapHeader.get("X-CSRF-Token");
                    String cookies = mapHeader.get("Set-Cookie");
                    LogManager.d(TAG, "WEB API: INIT USER LOGIN RESPONSE = " + response.toString());
                    LogManager.d(TAG, "WEB API: INIT USER LOGIN RESPONSE HEADER = " + mapHeader.toString());

                    MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(
                            true,
                            mapResponse.get("data").toString()
                    );

                    ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(true, mfMsgModel));
                } catch (Exception e) {
                    ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(false, null));
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(false, null));
            }
        };

        MfSessionManager sessionManager = (MfSessionManager) mManagerFactory.provideManager(
                ManagerFactory.SESSION_MANAGER,
                context);

        MfSession session = sessionManager.getSession("10000");

        MfConnectionManager connectionManager
                = (MfConnectionManager) mManagerFactory.provideManager(
                ManagerFactory.CONNECTION_MANAGER,
                context);

        connectionManager.sendLogin(loginListener, errorListener,
                session, username, password, token);
    }

    @Override
    public void updateMessageStatus(@MessageStatus int status, OutgoingMsgModel messageModel) {
        ModuleEventBus.getDefault().post(new MessageStatusEvent(status, messageModel.getMsgId()));
    }

    @Override
    public void exit(Context context) {
        AccountManager accountManager = (AccountManager) mManagerFactory.provideManager(
                ManagerFactory.ACCOUNT_MANAGER,
                context
        );

        accountManager.clear();

        MfRosterManager rosterManager = (MfRosterManager) mManagerFactory.provideManager(
                ManagerFactory.ROSTER_MANAGER,
                context
        );
        rosterManager.clear();

        MfSessionManager sessionManager = (MfSessionManager) mManagerFactory.provideManager(
                ManagerFactory.SESSION_MANAGER,
                context
        );

        MfConnectionManager connectionManager
                = (MfConnectionManager) mManagerFactory.provideManager(
                ManagerFactory.CONNECTION_MANAGER,
                context);

        connectionManager.destroyTimeoutWorkerThread();
        sessionManager.clear();
    }

    @Override
    public void postMessage(@NonNull MfMsgModel messageModel) {
        ModuleEventBus.getDefault().post(new MessageEvent(messageModel));
    }

    @Override
    public void postError(String message) {
        ModuleEventBus.getDefault().post(new SendErrorEvent(message));
    }

    @Override
    public void postTimeoutRequest(Context context, boolean isAutoLogout) {
        Response.Listener loginListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                LogManager.d(TAG, "Timeout: response success");
                //TODO write timeout success response related code
//                try {
//                    HashMap<String, Object> mapResponse = (HashMap<String, Object>) response;
//                    Map<String, String> mapHeader = (Map<String, String>) mapResponse.get("headers");
//                    String XCRFToken = mapHeader.get("X-CSRF-Token");
//                    String cookies = mapHeader.get("Set-Cookie");
//                    LogManager.d(TAG, "WEB API: INIT USER LOGIN RESPONSE = " + response.toString());
//                    LogManager.d(TAG, "WEB API: INIT USER LOGIN RESPONSE HEADER = " + mapHeader.toString());
//
//                    MfMsgModel mfMsgModel = MfMsgParser.getInstance().parse(
//                            true,
//                            mapResponse.get("data").toString()
//                    );
//
//                    ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(true, mfMsgModel));
//                } catch (Exception e) {
//                    ModuleEventBus.getDefault().post(new LoginRequestEvent.Response(false, null));
//                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogManager.d(TAG, "Timeout: response failed");
                //TODO write timeout failed response related code
            }
        };

        MfSessionManager sessionManager = (MfSessionManager) mManagerFactory.provideManager(
                ManagerFactory.SESSION_MANAGER,
                context);

        MfSession session = sessionManager.getSession("10000");

        MfConnectionManager connectionManager
                = (MfConnectionManager) mManagerFactory.provideManager(
                ManagerFactory.CONNECTION_MANAGER,
                context);

        connectionManager.sendTimeout(loginListener, errorListener,
                session, isAutoLogout);
    }
}
