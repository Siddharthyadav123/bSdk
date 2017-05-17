package com.morfeus.android.mfsdk.ui.action.event;

/**
 * This event represent message event occurred when
 * user send or receive message.
 */
public final class MessageEvent {
    private final String mAction;
    private final String mPayload;
    private final String mMSG;
    private String mImage;
    private String mMsgId;

    public MessageEvent(String action, String payload, String msg, String image, String msgId) {
        mAction = action;
        mPayload = payload;
        mMSG = msg;
        mImage = image;
        mMsgId = msgId;
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

    public String getmMsgId() {
        return mMsgId;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
