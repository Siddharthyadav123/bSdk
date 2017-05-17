package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

public final class MfImageCardTemplateModel extends TemplateModel {

    private String textMessage;

    private String image;

    public MfImageCardTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
