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

public class MfButtonCardTemplateWithReminderInfoModel extends TemplateModel {
    private List<MfListContentData> mfListContentDatas;

    public MfButtonCardTemplateWithReminderInfoModel(@NonNull String templateId, @NonNull MFCardData MFCardData) {
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

        MfListContentData mfListContentData = new MfListContentData(mfCardsList);
        mfListContentData.setElementStyle(elementStyle);
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
        int coverPercentage = 0;


        if (titleElementJsonObject.has(MsgParser.KEY_TYPE)) {
            type = titleElementJsonObject.get(MsgParser.KEY_TYPE).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_COVER_PERCENTAGE)) {
            try {
                coverPercentage = Integer.parseInt(titleElementJsonObject.get(MsgParser.KEY_COVER_PERCENTAGE).getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (titleElementJsonObject.has(MsgParser.KEY_TEXT)) {
            text = titleElementJsonObject.get(MsgParser.KEY_TEXT).getAsString();
        }

        if (titleElementJsonObject.has(MsgParser.KEY_IMAGE_TYPE)) {
            imageType = titleElementJsonObject.get(MsgParser.KEY_IMAGE_TYPE).getAsString();
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
                .setImageType(imageType).setImageName(imageName).setCoverPercentage(coverPercentage)
                .setAction(action).setPayload(payload).setImageUrl(imageUrl)
                .setStyleData(styleData).build();

        return mfElementData;
    }

    public List<MfListContentData> getMfListContentDatas() {
        return mfListContentDatas;
    }
}
