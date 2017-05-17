package com.morfeus.android.mfsdk.messenger.message.model;

import android.support.annotation.NonNull;

/**
 * Immutable message model used to outgoing messages
 */
public class OutgoingMsgModel {
    private final long timeStamp;
    private final String text;
    private final String type;
    private final String msgId;
    private boolean speechToText = false;

    public OutgoingMsgModel(long timeStamp, String text, String type, @NonNull String msgId) {
        this.timeStamp = timeStamp;
        this.text = text;
        this.type = type;
        this.msgId = msgId;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }


    public String getMsgId() {
        return msgId;
    }

    public boolean isSpeechToText() {
        return speechToText;
    }

    public void setSpeechToText(boolean speechToText) {
        this.speechToText = speechToText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutgoingMsgModel)) return false;

        OutgoingMsgModel that = (OutgoingMsgModel) o;

        if (timeStamp != that.timeStamp) return false;
        if (msgId != that.msgId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (msgId != null ? msgId.hashCode() : 0);
        return result;
    }
}
