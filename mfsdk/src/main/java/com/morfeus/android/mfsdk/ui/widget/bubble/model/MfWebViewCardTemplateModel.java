package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

public class MfWebViewCardTemplateModel extends TemplateModel {

    private String url;

    public MfWebViewCardTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}