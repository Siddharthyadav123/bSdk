package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

public class MfLoadingTemplateModel extends TemplateModel {

    public MfLoadingTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {
        // No-op
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
