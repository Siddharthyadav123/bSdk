package com.morfeus.android.mfsdk.messenger.source.entity;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MfMsgData {

    private String msgType;
    private String text;
    private String thumb;
    private String url;
    private MFCardData mfCards;
    private boolean textToSpeech;
    private String ttsText;
    private String imageName;
    private double latitude;
    private double longitude;


    public MfMsgData(@NonNull String msgType) {
        this.msgType = checkNotNull(msgType);
    }

    public String getMsgType() {
        return msgType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MFCardData getMfCards() {
        return mfCards;
    }

    public void setMfCards(MFCardData mfCards) {
        this.mfCards = mfCards;
    }

    public boolean isTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(boolean textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public String getTtsText() {
        return ttsText;
    }

    public void setTtsText(String ttsText) {
        this.ttsText = ttsText;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getImageName() {
        return imageName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "MfMsgData{" +
                "msgType='" + msgType + '\'' +
                ", text='" + text + '\'' +
                ", thumb='" + thumb + '\'' +
                ", url='" + url + '\'' +
                ", textToSpeech='" + textToSpeech + '\'' +
                ", ttsText='" + ttsText + '\'' +
                ", imageName='" + imageName + '\'' +
                ", mfCards=" + mfCards +
                '}';
    }
}
