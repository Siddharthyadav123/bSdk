package com.morfeus.android.mfsdk.messenger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.event.LoginRequestEvent;
import com.morfeus.android.mfsdk.messenger.event.NotificationEvent;
import com.morfeus.android.mfsdk.messenger.event.TimeoutEvent;
import com.morfeus.android.mfsdk.messenger.injection.MsgDataSourceInjector;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.parser.MfMsgParser;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.ui.action.event.DestroyEvent;
import com.morfeus.android.mfsdk.ui.action.event.UIMessageEvent;

import org.greenrobot.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class implements the {@link MfSdkMsg}
 */
@SuppressLint("StaticFieldLeak")
public class MfSdkMsgKit implements MfSdkMsg {
    private static final String TAG = MfSdkMsgKit.class.getSimpleName();

    private static MfSdkMsgKit INSTANCE;

    private ManagerMediator mManagerMediator;

    private MessengerConfig mMessengerConfig;

    private Context mContext;

    private MfSdkMsgKit(Context context, String appSessionToken,
                        String customerId, MFSDKProperties sdkProperties) {
        mContext = context;
        mMessengerConfig = MessengerConfig.create(appSessionToken, customerId, sdkProperties);
        mManagerMediator = MsgDataSourceInjector.provideManagerMediator(mContext);
    }

    public static MfSdkMsgKit createInstance(Context context,
                                             String appSessionToken,
                                             String customerId,
                                             MFSDKProperties sdkProperties) {
        if (INSTANCE == null) {
            checkNotNull(context);
            INSTANCE = new MfSdkMsgKit(context, appSessionToken, customerId, sdkProperties);
        }
        return INSTANCE;
    }

    public static MfSdkMsgKit getInstance() {
        return INSTANCE;
    }

    @Override
    public void init() throws MfSdkMsgInitializationException {
        NetUtil.createInstance(mContext);
        if (!ModuleEventBus.getDefault().isRegistered(this))
            ModuleEventBus.getDefault().register(this);
        LogManager.i(TAG, "SDK INITIALIZATION: MESSAGE KIT INITIALIZED");
    }

    @Override
    public void loadInitMessage(Parcelable parcelable) {
        String appSessionToken = mMessengerConfig.getAppSessionToken();
        if (TextUtils.isEmpty(appSessionToken)) {
            mManagerMediator.initRequest(mContext, parcelable);
            LogManager.d(TAG, "loadInitMessage: initRequest...");
        } else {
            mManagerMediator.initLoginRequest(mContext, parcelable);
            LogManager.d(TAG, "loadInitMessage: initLoginRequest...");
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onSendMessageEvent(UIMessageEvent uiMessageEvent) {
        sendMessage(uiMessageEvent.getMsg(), uiMessageEvent.getmMsgType(),
                uiMessageEvent.ismTextToSpeechMsg(), uiMessageEvent.getmMsgId());
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onNotificationCard(NotificationEvent notificationEvent) {
        LogManager.d(TAG, "onNotificationCard: pushModel > " + notificationEvent.getCardJson());
        MfMsgModel mfMsgModel
                = MfMsgParser.getInstance().parse(true, notificationEvent.getCardJson());
        LogManager.d(TAG, "onNotificationCard: MfMsgModel > " + mfMsgModel.toString());
        mManagerMediator.postMessage(mfMsgModel);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onDestroy(DestroyEvent destroyEvent) {
        if (destroyEvent.isExit()) {

//            if (ModuleEventBus.getDefault().isRegistered(this))
//                ModuleEventBus.getDefault().unregister(this);

            mManagerMediator.exit(mContext);
        }
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginRequestEvent(LoginRequestEvent event) {
        mManagerMediator.userLoginRequest(
                mContext,
                event.getUsername(),
                event.getPassword(),
                event.getToken());
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onTimeoutEvent(TimeoutEvent timeoutEvent) {
        mManagerMediator.postTimeoutRequest(
                mContext,
                timeoutEvent.isAutoLogout());
    }

    @Override
    public void sendMessage(String message, String msgType, boolean speechToText, String msgId) {
        // default temp chat id "10000" used as currently only one chat bot we have
        mManagerMediator.messageRequest(mContext, "10000", message, msgType, msgId, speechToText);
    }

    // TODO temp method used to provide default msg loaded from MFMessage.json
    public MfMsgModel getDefaultMsg() {
        return null;
    }

}
