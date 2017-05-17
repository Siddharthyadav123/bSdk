package com.morfeus.android.mfsdk.ui.widget.bubble.model;


import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class TickTimeTemplateModel extends TemplateModel {

    private final String mTime;
    private String mMSG;
    private boolean mFirstTick;
    private boolean mSecondTick;

    public TickTimeTemplateModel(String message, String time, @NonNull String templateID) {
        checkNotNull(templateID);
        checkArgument(!templateID.isEmpty(), "Error: Template ID can't be empty!");

        this.mTime = time;
        this.mMSG = message;
        this.templateID = templateID;
    }

    public String getMessage() {
        return mMSG;
    }

    public String getTime() {
        return mTime;
    }

    public boolean getFirstTick() {
        return mFirstTick;
    }

    public void setFirstTick(boolean tick) {
        this.mFirstTick = tick;
    }

    public boolean getSecondTick() {
        return mSecondTick;
    }

    public void setSecondTick(boolean tick) {
        this.mSecondTick = tick;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }
}
