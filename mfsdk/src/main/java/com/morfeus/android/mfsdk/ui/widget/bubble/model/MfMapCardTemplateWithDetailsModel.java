package com.morfeus.android.mfsdk.ui.widget.bubble.model;


import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementStyleData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;

import java.util.ArrayList;
import java.util.List;

public class MfMapCardTemplateWithDetailsModel extends TemplateModel {
    private List<MfListContentData> mfListContentDatas;

    public MfMapCardTemplateWithDetailsModel(@NonNull String templateId, @NonNull MFCardData MFCardData) {
        this.templateID = templateId;
        this.cardData = MFCardData;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            List<MfListContentData> mfListContentDatas = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject singleContentJson = jsonArray.get(i).getAsJsonObject();
                MfListContentData contentData = deserializeMsgDataCardContentsElement(singleContentJson);
                mfListContentDatas.add(contentData);
            }
            this.mfListContentDatas = mfListContentDatas;
        }
    }

    private MfListContentData deserializeMsgDataCardContentsElement(JsonObject contentJsonObject) {
        String elementStyle = null;
        List<MfComponentData> mfCardsList = new ArrayList<>();
        MfElementStyleData style = null;

        JsonObject elementJsonObject = contentJsonObject.get(MsgParser.KEY_ELEMENT).getAsJsonObject();

        if (elementJsonObject.has(MsgParser.KEY_ELEMENT_STYLE)) {
            elementStyle = elementJsonObject.get(MsgParser.KEY_ELEMENT_STYLE).getAsString();
        }

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

        if (elementJsonObject.has(MsgParser.KEY_RATE)) {
            JsonObject rateElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_RATE);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_RATE, rateElementJsonObject);
            MfComponentData mfComponentData = new MfComponentData(mfElementData);
            mfCardsList.add(mfComponentData);
        }

        if (elementJsonObject.has(MsgParser.KEY_MAP)) {
            JsonObject mapElementJsonObject = elementJsonObject.getAsJsonObject(MsgParser.KEY_MAP);
            MfElementData mfElementData = deserializeElement(MsgParser.KEY_MAP, mapElementJsonObject);
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

        if (contentJsonObject.has(MsgParser.KEY_STYLE)) {
            JsonObject styleJson = contentJsonObject.get(MsgParser.KEY_STYLE).getAsJsonObject();
            if (styleJson.has(MsgParser.KEY_BACKGROUND_COLOR)) {
                String bgColor = styleJson.get(MsgParser.KEY_BACKGROUND_COLOR).getAsString();
                style = new MfElementStyleData.Builder().setBackgroundColor(bgColor).build();
            }
        }

        MfListContentData mfListContentData = new MfListContentData(mfCardsList);
        mfListContentData.setElementStyle(elementStyle);
        mfListContentData.setStyle(style);
        return mfListContentData;
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
        double latitude = 0;
        double longitude = 0;
        double value = 0;

        if (titleElementJsonObject.has(MsgParser.KEY_TYPE)) {
            type = titleElementJsonObject.get(MsgParser.KEY_TYPE).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_TEXT)) {
            text = titleElementJsonObject.get(MsgParser.KEY_TEXT).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_IMAGE_TYPE)) {
            imageType = titleElementJsonObject.get(MsgParser.KEY_IMAGE_TYPE).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_LATITUDE)) {
            try {
                latitude = Double.parseDouble(titleElementJsonObject.get(MsgParser.KEY_LATITUDE).getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (titleElementJsonObject.has(MsgParser.KEY_LONGITUDE)) {
            try {
                longitude = Double.parseDouble(titleElementJsonObject.get(MsgParser.KEY_LONGITUDE).getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (titleElementJsonObject.has(MsgParser.KEY_VALUE)) {
            try {
                value = Double.parseDouble(titleElementJsonObject.get(MsgParser.KEY_VALUE).getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (titleElementJsonObject.has(MsgParser.KEY_ACTION)) {
            action = titleElementJsonObject.get(MsgParser.KEY_ACTION).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_PAYLOAD)) {
            payload = titleElementJsonObject.get(MsgParser.KEY_PAYLOAD).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_STYLE)) {
            JsonObject elementStyle = titleElementJsonObject.getAsJsonObject(MsgParser.KEY_STYLE);
            String textColor = null;
            String backgroundColor = null;
            String pinImage = null;
            String rateIcon = null;
            String rateOff = null;

            if (elementStyle.has(MsgParser.KEY_TEXTCOLOR)) {
                textColor = elementStyle.get(MsgParser.KEY_TEXTCOLOR).getAsString();
            }

            if (elementStyle.has(MsgParser.KEY_BACKGROUND_COLOR)) {
                backgroundColor = elementStyle.get(MsgParser.KEY_BACKGROUND_COLOR).getAsString();
            }

            if (elementStyle.has(MsgParser.KEY_PIN_IMAGE)) {
                pinImage = elementStyle.get(MsgParser.KEY_PIN_IMAGE).getAsString();
            }

            if (elementStyle.has(MsgParser.KEY_RATE_ICON)) {
                rateIcon = elementStyle.get(MsgParser.KEY_RATE_ICON).getAsString();
            }

            if (elementStyle.has(MsgParser.KEY_RATE_OFF)) {
                rateOff = elementStyle.get(MsgParser.KEY_RATE_OFF).getAsString();
            }

            styleData = new MfElementStyleData.Builder().setTextColor(textColor)
                    .setPinImage(pinImage).setRateIcon(rateIcon).setRateOff(rateOff)
                    .setBackgroundColor(backgroundColor).build();
        }

        MfElementData mfElementData = new MfElementData.Builder(id, type).setText(text)
                .setImageType(imageType).setImageName(imageName)
                .setLatitude(latitude).setLongitude(longitude).setValue(value)
                .setAction(action).setPayload(payload).setImageUrl(imageUrl)
                .setStyleData(styleData).build();

        return mfElementData;
    }

    public List<MfListContentData> getMfListContentDatas() {
        return mfListContentDatas;
    }
}
