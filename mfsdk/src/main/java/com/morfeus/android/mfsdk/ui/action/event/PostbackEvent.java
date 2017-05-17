package com.morfeus.android.mfsdk.ui.action.event;


public class PostbackEvent {
    private final String mAction;
    private final String mPayload;
    private String mImage;

    public PostbackEvent(String payload, String action, String image) {
        this.mAction = action;
        this.mPayload = payload;
        this.mImage = image;
    }

    public String getPayload() {
        return mPayload;
    }

    public String getAction() {
        return mAction;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

}
