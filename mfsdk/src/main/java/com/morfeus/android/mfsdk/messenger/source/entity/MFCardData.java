package com.morfeus.android.mfsdk.messenger.source.entity;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for CardModel
 */
public final class MFCardData {
    private String templateType;
    private String action;
    private String payload;
    private JsonArray cardDataJsonArray;

    public MFCardData() {
    }

    public MFCardData deserialize(@NonNull String jsonStr) {
        return null;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(@NonNull String templateType) {
        this.templateType = checkNotNull(templateType);
    }

    public JsonArray getCardDataJsonArray() {
        return cardDataJsonArray;
    }

    public void setCardDataJsonArray(JsonArray cardDataJsonArray) {
        this.cardDataJsonArray = cardDataJsonArray;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "MFCardData{" +
                "templateType='" + templateType + '\'' +
                ", action='" + action + '\'' +
                ", payload='" + payload + '\'' +
                ", cardDataJsonArray=" + cardDataJsonArray +
                '}';
    }
}
