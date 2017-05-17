package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link SimpleImageTemplateModel} is <b>immutable</b> data model which
 * implements {@link TemplateModel}.
 */
public final class SimpleImageTemplateModel extends TemplateModel {

    private String primaryMessage;

    private String secondaryMessage;

    private Bitmap imageBitmap;

    public SimpleImageTemplateModel(String primaryMessage, String secondaryMessage,
                                    Bitmap imageBitmap, @NonNull String templateID) {
        checkNotNull(templateID);
        checkArgument(!templateID.isEmpty(), "Error: Template ID can't be empty!");

        this.primaryMessage = primaryMessage;
        this.secondaryMessage = secondaryMessage;
        this.imageBitmap = imageBitmap;
        this.templateID = templateID;
    }

    public String getPrimaryMessage() {
        return primaryMessage;
    }

    public String getSecondaryMessage() {
        return secondaryMessage;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
