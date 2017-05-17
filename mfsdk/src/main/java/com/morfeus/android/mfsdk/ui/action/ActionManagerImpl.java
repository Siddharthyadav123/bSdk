package com.morfeus.android.mfsdk.ui.action;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.event.LoginRequestEvent;
import com.morfeus.android.mfsdk.ui.action.entity.Action;
import com.morfeus.android.mfsdk.ui.action.event.BackEvent;
import com.morfeus.android.mfsdk.ui.action.event.CameraEvent;
import com.morfeus.android.mfsdk.ui.action.event.DestroyEvent;
import com.morfeus.android.mfsdk.ui.action.event.InfoEvent;
import com.morfeus.android.mfsdk.ui.action.event.MessageEvent;
import com.morfeus.android.mfsdk.ui.action.event.MessageReceiveEvent;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.action.event.SendEvent;
import com.morfeus.android.mfsdk.ui.action.event.UIMessageEvent;
import com.morfeus.android.mfsdk.ui.action.event.chat.TypingIndicatorEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginDialogEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginEvent;
import com.morfeus.android.mfsdk.ui.action.event.otp.OTPDialogEvent;
import com.morfeus.android.mfsdk.ui.action.exception.UnsupportedActionException;
import com.morfeus.android.mfsdk.ui.screen.ScreenManagerImpl;
import com.morfeus.android.mfsdk.ui.screen.exception.UnsupportedScreenException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * This class is responsible for managing action of widgets and screen events.
 */
//TODO fail open close
public final class ActionManagerImpl implements ActionManager {

    private static final String TAG = ActionManagerImpl.class.getSimpleName();

    private static ActionManagerImpl INSTANCE;
    private final ScreenManagerImpl mScreenManager;

    //TODO-DESIGN dependency problem.
    private ActionManagerImpl() {
        mScreenManager = ScreenManagerImpl.getInstance();
        onStart();
    }

    public static ActionManagerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActionManagerImpl();
        }
        return INSTANCE;
    }

    @Override
    public void onStart() {
        ModuleEventBus.getDefault().register(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        ModuleEventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onSendEvent(SendEvent sendEvent) {
        String msgId = System.currentTimeMillis() + "";

        MessageEvent messageEvent = new MessageEvent(null, null, sendEvent.getMsg(), null, msgId);
        EventBus.getDefault().post(messageEvent);


        String message;
        if (sendEvent.isContainEmoji()) {
            message = sendEvent.getTextToSpeechMsg();
        } else {
            message = sendEvent.getMsg();
        }
        //passing null for params which are not exist for send event such as payload,action and image
        UIMessageEvent uiMessageEvent = new UIMessageEvent(
                null,
                null,
                message,
                null,
                "text",
                msgId
        );
        uiMessageEvent.setmTextToSpeechMsg(sendEvent.isTextToSpeechMsg());
        ModuleEventBus.getDefault().post(uiMessageEvent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onPayloadEvent(PayloadEvent payloadEvent) {
        String msgId = System.currentTimeMillis() + "";

        MessageEvent messageEvent = new MessageEvent(
                payloadEvent.getAction(),
                payloadEvent.getPayload(),
                null,
                payloadEvent.getImage(),
                msgId
        );
        EventBus.getDefault().post(messageEvent);

        UIMessageEvent uiMessageEvent = new UIMessageEvent(
                payloadEvent.getAction(),
                payloadEvent.getPayload(),
                payloadEvent.getPayload(),
                null,
                "text",
                msgId
        );

        ModuleEventBus.getDefault().post(uiMessageEvent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onPostBackEvent(PostbackEvent postbackEvent) {
        String action = postbackEvent.getAction();
        String payload = postbackEvent.getPayload();

        // We check for action first. If action is not null then we will consider this postback
        // event as action else we will consider it as simple post back message.
        if (action != null) {
            switch (action) {
                case "authenticateWithLogin":
                    EventBus.getDefault().post(new LoginDialogEvent(true, payload));
                    break;
                case "authenticateWithOtp":
                    EventBus.getDefault().post(new OTPDialogEvent(true));
                    break;
                default: {
                    EventBus.getDefault().post(new TypingIndicatorEvent(true));
                    String msgId = System.currentTimeMillis() + "";
                    UIMessageEvent uiMessageEvent = new UIMessageEvent(
                            postbackEvent.getAction(),
                            postbackEvent.getPayload(),
                            postbackEvent.getPayload(), //In case of postback message and payload are same.
                            null,
                            "postback",
                            msgId);
                    ModuleEventBus.getDefault().post(uiMessageEvent);
                }
//                    throw new IllegalArgumentException("Error: " + action + " is not supported!");
            }
        } else {
            String msgId = System.currentTimeMillis() + "";
            UIMessageEvent uiMessageEvent = new UIMessageEvent(
                    postbackEvent.getAction(),
                    postbackEvent.getPayload(),
                    postbackEvent.getPayload(), //In case of postback message and payload are same.
                    null,
                    "postback", msgId);
            ModuleEventBus.getDefault().post(uiMessageEvent);
            EventBus.getDefault().post(new TypingIndicatorEvent(true));
        }

    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onCameraEvent(CameraEvent cameraEvent) {
        //passing null to other parameters because camera only having action.
        MessageEvent messageEvent = new MessageEvent(cameraEvent.getAction(), null, null, null, null);
        EventBus.getDefault().post(messageEvent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginResponse(LoginRequestEvent.Response responseEvent) {

    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onInfoEvent(InfoEvent infoEvent) {
        //passing null to other parameters because info event only having action.
        MessageEvent messageEvent = new MessageEvent(infoEvent.getAction(), null, null, null, null);
        EventBus.getDefault().post(messageEvent);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onBackEvent(BackEvent backEvent) {
        //passing null to other parameters because info event only having action.
        MessageEvent messageEvent = new MessageEvent(backEvent.getAction(), null, null, null, null);
        EventBus.getDefault().post(messageEvent);
        ModuleEventBus.getDefault().post(new DestroyEvent(true));
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onMessageEvent(com.morfeus.android.mfsdk.messenger.event.MessageEvent event) {
        EventBus.getDefault().post(new MessageReceiveEvent(event.getMessage()));
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginResponseEvent(LoginRequestEvent.Response response) {
        if (response.isSuccess()) {
            if (response.getMfMsgModel() != null) {
                EventBus.getDefault().post(new LoginEvent.Response(true, response.getMfMsgModel()));
            }
        } else {
            EventBus.getDefault().post(new LoginEvent.Response(false, null));
        }

    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        ModuleEventBus.getDefault().post(
                new LoginRequestEvent(
                        loginEvent.getUsername(),
                        loginEvent.getPassword(),
                        loginEvent.getToken())
        );
    }

    @Override
    public void execute(@NonNull Action action) throws UnsupportedActionException {
        // TODO-DESIGN strategy to design action.
        switch (action.getType()) {
            case Action.LAUNCH_ACTION:
                assert action.getScreen() != null;
                try {
                    mScreenManager.launch(action.getScreen());
                } catch (UnsupportedScreenException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new UnsupportedActionException("Error: Action is not supported");
        }
    }
}
