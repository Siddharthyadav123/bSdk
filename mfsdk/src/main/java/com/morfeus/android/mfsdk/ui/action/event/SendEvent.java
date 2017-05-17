package com.morfeus.android.mfsdk.ui.action.event;

public final class SendEvent {
    private final String mMSG;
    private final String mMSGWithEmoji;
    private boolean mTextToSpeechMsg;
    private boolean isContainEmoji;

    public SendEvent(String msg) {
        this(msg, null, false);
    }

    public SendEvent(String msg, String msgWithEmoji, boolean containEmoji) {
        mMSG = msg;
        mMSGWithEmoji = msgWithEmoji;
        isContainEmoji = containEmoji;
    }

    public String getMsg() {
        return mMSG;
    }

    public boolean isTextToSpeechMsg() {
        return mTextToSpeechMsg;
    }

    public void setTextToSpeechMsg(boolean textToSpeechMsg) {
        this.mTextToSpeechMsg = textToSpeechMsg;
    }

    public String getTextToSpeechMsg() {
        return mMSGWithEmoji;
    }

    public boolean isContainEmoji() {
        return isContainEmoji;
    }
}
