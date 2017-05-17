package com.morfeus.android.mfsdk.ui.action.event;

public class UIMessageEvent {
    private final String mAction;
    private final String mPayload;
    private final String mMSG;
    private String mImage;
    private final String mMsgType;
    private boolean mTextToSpeechMsg;
    private String mMsgId;

    public UIMessageEvent(String action, String payload, String msg, String image,
                          String msgType, String msgId) {
        this.mAction = action;
        this.mPayload = payload;
        this.mMSG = msg;
        this.mImage = image;
        this.mMsgType = msgType;
        this.mMsgId = msgId;
    }

    public String getAction() {
        return mAction;
    }

    public String getPayload() {
        return mPayload;
    }

    public String getMsg() {
        return mMSG;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getmMsgType() {
        return mMsgType;
    }

    public boolean ismTextToSpeechMsg() {
        return mTextToSpeechMsg;
    }

    public String getmMsgId() {
        return mMsgId;
    }

    public void setmTextToSpeechMsg(boolean mTextToSpeechMsg) {
        this.mTextToSpeechMsg = mTextToSpeechMsg;
    }
}
