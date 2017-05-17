package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementStyleData;

import java.util.ArrayList;
import java.util.List;

public final class MfInfoCardTemplateModel extends TemplateModel implements Comparable<MfInfoCardTemplateModel> {
    private List<MfComponentData> componentDatas;
    private int maxButtonToShow = -1;

    public MfInfoCardTemplateModel(@NonNull String templateId, @NonNull MFCardData MFCardData) {
        this.templateID = templateId;
        this.cardData = MFCardData;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {
        JsonObject individualMfCardsContent = jsonArray.get(0).getAsJsonObject();
        List<MfComponentData> componentList = null;
        if (individualMfCardsContent.has(MsgParser.KEY_ELEMENT)) {
            JsonObject elementJsonObject = individualMfCardsContent.getAsJsonObject(MsgParser.KEY_ELEMENT);
            componentList = deserializeMsgDataCardContentsElement(elementJsonObject);
        }
        this.componentDatas = componentList;
    }

    private List<MfComponentData> deserializeMsgDataCardContentsElement(JsonObject elementJsonObject) {
        List<MfComponentData> mfCardsList = new ArrayList<>();

        if (elementJsonObject.has(MsgParser.KEY_TITLE)) {
            JsonObject titleElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_TITLE);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_TITLE, titleElementJsonObject);
            MfComponentData mfComponentData = new MfComponentData(mfElementData);
            mfCardsList.add(mfComponentData);
        }

        if (elementJsonObject.has(MsgParser.KEY_SUBTITLE)) {
            JsonObject subTitleElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_SUBTITLE);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_SUBTITLE, subTitleElementJsonObject);
            MfComponentData mfComponentData = new MfComponentData(mfElementData);
            mfCardsList.add(mfComponentData);
        }

        if (elementJsonObject.has(MsgParser.KEY_IMAGE)) {
            JsonObject welcomeImageElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_IMAGE);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_IMAGE, welcomeImageElementJsonObject);
            MfComponentData mfComponentData = new MfComponentData(mfElementData);
            mfCardsList.add(mfComponentData);
        }

        if (elementJsonObject.has(MsgParser.KEY_CARD_IMAGE)) {
            JsonObject cardImageElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_CARD_IMAGE);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_CARD_IMAGE, cardImageElementJsonObject);
            MfComponentData mfComponentData = new MfComponentData(mfElementData);
            mfCardsList.add(mfComponentData);
        }

        if (elementJsonObject.has(MsgParser.KEY_BUTTONS)) {
            JsonArray buttonsElementJsonObject = elementJsonObject.getAsJsonArray(MsgParser.KEY_BUTTONS);
            List<MfElementData> buttonsList = new ArrayList<>();

            for (int i = 0; i < buttonsElementJsonObject.size(); i++) {
                JsonObject buttonJson = buttonsElementJsonObject.get(i).getAsJsonObject();
                buttonsList.add(deserializeElement(MsgParser.KEY_BUTTONS, buttonJson));
            }
            MfComponentData mfComponentData = new MfComponentData(buttonsList);
            mfCardsList.add(mfComponentData);
        }
        return mfCardsList;
    }

    private MfElementData deserializeElement(String id, JsonObject titleElementJsonObject) {
        String type = null;
        String text = null;
        String imageType = null;
        String imageName = null;
        String imageUrl = null;
        String action = null;
        String payload = null;
        MfElementStyleData styleData = null;

        if (titleElementJsonObject.has(MsgParser.KEY_TYPE)) {
            type = titleElementJsonObject.get(MsgParser.KEY_TYPE).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_TEXT)) {
            text = titleElementJsonObject.get(MsgParser.KEY_TEXT).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_IMAGE_TYPE)) {
            imageType = titleElementJsonObject.get(MsgParser.KEY_IMAGE_TYPE).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_IMAGE_NAME)) {
            imageName = titleElementJsonObject.get(MsgParser.KEY_IMAGE_NAME).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_IMAGE_URL)) {
            imageUrl = titleElementJsonObject.get(MsgParser.KEY_IMAGE_URL).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_ACTION)) {
            action = titleElementJsonObject.get(MsgParser.KEY_ACTION).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_PAYLOAD)) {
            payload = String.valueOf(titleElementJsonObject.get(MsgParser.KEY_PAYLOAD));
        }

        if (titleElementJsonObject.has(MsgParser.KEY_STYLE)) {
            JsonObject elementStyle = titleElementJsonObject.getAsJsonObject(MsgParser.KEY_STYLE);
            String textColor = null;
            String backgroundColor = null;

            if (elementStyle.has(MsgParser.KEY_TEXTCOLOR)) {
                textColor = elementStyle.get(MsgParser.KEY_TEXTCOLOR).getAsString();
            }

            if (elementStyle.has(MsgParser.KEY_BACKGROUND_COLOR)) {
                backgroundColor = elementStyle.get(MsgParser.KEY_BACKGROUND_COLOR).getAsString();
            }
            styleData = new MfElementStyleData.Builder().setTextColor(textColor)
                    .setBackgroundColor(backgroundColor).build();
        }

        MfElementData mfElementData = new MfElementData.Builder(id, type).setText(text)
                .setImageType(imageType).setImageName(imageName)
                .setAction(action).setPayload(payload).setImageUrl(imageUrl)
                .setStyleData(styleData).build();

        return mfElementData;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public List<MfComponentData> getComponentDatas() {
        return componentDatas;
    }

    public int getMaxButtonToShow() {
        return maxButtonToShow;
    }

    public void setMaxButtonToShow(int maxButtonToShow) {
        this.maxButtonToShow = maxButtonToShow;
    }

    @Override
    public int compareTo(MfInfoCardTemplateModel mfInfoCardTemplateModel) {
        int selfTitleLenght = 0;
        int opponentObjTitleLenght = 0;

        for (MfComponentData mfComponentData : componentDatas) {
            MfElementData mfElementData = mfComponentData.getElementData();
            if (mfElementData != null && "title".equalsIgnoreCase(mfElementData.getId())) {
                String title = mfElementData.getText();
                selfTitleLenght = title.length();
            }
        }

        List<MfComponentData> compareObjComponentDatas = mfInfoCardTemplateModel.getComponentDatas();
        for (MfComponentData mfComponentData : compareObjComponentDatas) {
            MfElementData mfElementData = mfComponentData.getElementData();
            if (mfElementData != null && "title".equalsIgnoreCase(mfElementData.getId())) {
                String title = mfElementData.getText();
                opponentObjTitleLenght = title.length();
            }
        }

        if (selfTitleLenght > opponentObjTitleLenght) {
            return -1;
        } else if (selfTitleLenght < opponentObjTitleLenght) {
            return 1;
        } else {
            return 0;
        }
    }
}
