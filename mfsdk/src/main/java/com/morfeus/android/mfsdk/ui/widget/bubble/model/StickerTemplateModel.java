package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Model class used represent data used for
 * {@link com.morfeus.android.mfsdk.ui.widget.bubble.StickerTemplateView}.
 */
public class StickerTemplateModel extends TemplateModel {
    private String mIMG;

    public StickerTemplateModel(@NonNull String templateID) {
        checkNotNull(templateID);
        checkArgument(!templateID.isEmpty(), "Error: Template ID can't be empty!");
        this.templateID = templateID;
    }

    public String getImage() {
        return mIMG;
    }

    public void setImage(String image) {
        this.mIMG = image;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
