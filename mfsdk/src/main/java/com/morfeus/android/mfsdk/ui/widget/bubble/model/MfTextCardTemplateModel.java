package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

public final class MfTextCardTemplateModel extends TemplateModel {

    private String textMessage;

    public MfTextCardTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
