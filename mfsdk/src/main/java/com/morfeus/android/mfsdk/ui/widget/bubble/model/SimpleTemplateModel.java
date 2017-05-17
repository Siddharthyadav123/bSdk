package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link SimpleTemplateModel} is <b>immutable</b> data model which implements {@link TemplateModel}.
 */
public final class SimpleTemplateModel extends TemplateModel {

    private String title;

    public SimpleTemplateModel(String title, @NonNull String templateID) {
        checkNotNull(templateID);
        checkArgument(!templateID.isEmpty(), "Error: Template ID can't be empty!");

        this.title = title;
        this.templateID = templateID;
    }

    public SimpleTemplateModel() {
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
