package com.morfeus.android.mfsdk.messenger.source.entity;

public final class MfMsgItems {
    private String to;
    private String from;
    private String msgId;
    private boolean isIncoming;
    private MfMsgData msgData;

    public MfMsgItems(String to, String from, String msgId) {
        this.to = to;
        this.from = from;
        this.msgId = msgId;
    }

    public boolean isInComing() {
        return isIncoming;
    }

    public void setIncoming(boolean isIncoming) {
        this.isIncoming = isIncoming;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMsgId() {
        return msgId;
    }

    public MfMsgData getMsgData() {
        return msgData;
    }

    public void setMsgData(MfMsgData msgData) {
        this.msgData = msgData;
    }

    @Override
    public String toString() {
        return "MfMsgItems{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgData=" + msgData +
                '}';
    }
}
