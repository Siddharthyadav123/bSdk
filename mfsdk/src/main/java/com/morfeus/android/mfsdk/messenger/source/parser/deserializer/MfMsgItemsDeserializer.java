package com.morfeus.android.mfsdk.messenger.source.parser.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgItems;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MfMsgItemsDeserializer implements JsonDeserializer<MfMsgModel> {

    @Override
    public MfMsgModel deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        JsonArray messagesJson = (JsonArray) json;
        ArrayList<MfMsgItems> mfMsgItemses = new ArrayList<>();

        for (int i = 0; i < messagesJson.size(); i++) {
            String to = null;
            String from = null;
            String msgId = null;
            MfMsgData msgData = null;

            JsonObject individualMfMsgItemJsonObject = messagesJson.get(i).getAsJsonObject();

            if (individualMfMsgItemJsonObject.has(MsgParser.KEY_TO)) {
                if (individualMfMsgItemJsonObject.get(MsgParser.KEY_TO) != null)
                    to = individualMfMsgItemJsonObject.get(MsgParser.KEY_TO).getAsString();
            }

            if (individualMfMsgItemJsonObject.has(MsgParser.KEY_FROM)) {
                from = individualMfMsgItemJsonObject.get(MsgParser.KEY_FROM).getAsString();
            }

            if (individualMfMsgItemJsonObject.has(MsgParser.KEY_MESSAGE_ID)) {
                msgId = String.valueOf(individualMfMsgItemJsonObject.get(MsgParser.KEY_MESSAGE_ID));
            }

            if (individualMfMsgItemJsonObject.has(MsgParser.KEY_MESSAGE)) {
                JsonObject messageDataJsonObject
                        = individualMfMsgItemJsonObject.getAsJsonObject(MsgParser.KEY_MESSAGE);
                msgData = deserializeMsgData(messageDataJsonObject);
            }

            MfMsgItems msgItem = new MfMsgItems(to, from, msgId);
            msgItem.setMsgData(msgData);
            mfMsgItemses.add(msgItem);
        }

        return new MfMsgModel(mfMsgItemses);
    }

    private MfMsgData deserializeMsgData(JsonObject messageDataJsonObject) {
        String msgType = "";
        String text = null;
        String thumb = null;
        String url = null;
        boolean textToSpeech = false;
        String ttsText = null;
        MFCardData MFCardData = null;
        double latitude = 0d;
        double longitude = 0d;

        if (messageDataJsonObject.has(MsgParser.KEY_TYPE)) {
            msgType = messageDataJsonObject.get(MsgParser.KEY_TYPE).getAsString();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_TEXT_TO_SPEECH)) {
            textToSpeech = messageDataJsonObject.get(MsgParser.KEY_TEXT_TO_SPEECH).getAsBoolean();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_TTS_TEXT)) {
            ttsText = messageDataJsonObject.get(MsgParser.KEY_TTS_TEXT).getAsString();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_TEXT)) {
            text = messageDataJsonObject.get(MsgParser.KEY_TEXT).getAsString();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_THUMB)) {
            thumb = messageDataJsonObject.get(MsgParser.KEY_THUMB).getAsString();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_URL)) {
            url = messageDataJsonObject.get(MsgParser.KEY_URL).getAsString();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_LAT)) {
            latitude = messageDataJsonObject.get(MsgParser.KEY_LAT).getAsDouble();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_LONG)) {
            longitude = messageDataJsonObject.get(MsgParser.KEY_LONG).getAsDouble();
        }

        if (messageDataJsonObject.has(MsgParser.KEY_URL)) {
            url = messageDataJsonObject.get(MsgParser.KEY_URL).getAsString();
        }


        if (messageDataJsonObject.has(MsgParser.KEY_CARD)) {
            JsonObject cardJsonObject = messageDataJsonObject.getAsJsonObject(MsgParser.KEY_CARD);
            String templateType = cardJsonObject.get(MsgParser.KEY_TEMPLATE_TYPE).getAsString();

            MFCardData = new MFCardData();
            MFCardData.setTemplateType(templateType);

            if (cardJsonObject.has(MsgParser.KEY_CONTENT)) {
                JsonArray contentJsonArray = cardJsonObject.getAsJsonArray(MsgParser.KEY_CONTENT);
                MFCardData.setCardDataJsonArray(contentJsonArray);
            }
        }

        MfMsgData mfMsgData = new MfMsgData(msgType);
        mfMsgData.setText(text);
        mfMsgData.setThumb(thumb);
        mfMsgData.setUrl(url);
        mfMsgData.setTextToSpeech(textToSpeech);
        mfMsgData.setTtsText(ttsText);
        mfMsgData.setMfCards(MFCardData);
        mfMsgData.setLatitude(latitude);
        mfMsgData.setLongitude(longitude);
        return mfMsgData;
    }


//    private MfElementData deserializeElement(String id, JsonObject titleElementJsonObject) {
//        String type = null;
//        String text = null;
//        String action = null;
//        String payload = null;
//        MfElementStyleData styleData = null;
//
//        if (titleElementJsonObject.has(MsgParser.KEY_TYPE)) {
//            type = titleElementJsonObject.get(MsgParser.KEY_TYPE).getAsString();
//        }
//
//        if (titleElementJsonObject.has(MsgParser.KEY_TEXT)) {
//            text = titleElementJsonObject.get(MsgParser.KEY_TEXT).getAsString();
//        }
//
//        if (titleElementJsonObject.has(MsgParser.KEY_ACTION)) {
//            action = titleElementJsonObject.get(MsgParser.KEY_ACTION).getAsString();
//        }
//
//        if (titleElementJsonObject.has(MsgParser.KEY_PAYLOAD)) {
//            payload = titleElementJsonObject.get(MsgParser.KEY_PAYLOAD).getAsString();
//        }
//
//        if (titleElementJsonObject.has(MsgParser.KEY_STYLE)) {
//            JsonObject elementStyle = titleElementJsonObject.getAsJsonObject(MsgParser.KEY_STYLE);
//            String textColor = null;
//            String backgroundColor = null;
//
//            if (elementStyle.has(MsgParser.KEY_TEXTCOLOR)) {
//                textColor = elementStyle.get(MsgParser.KEY_TEXTCOLOR).getAsString();
//            }
//
//            if (elementStyle.has(MsgParser.KEY_BACKGROUND_COLOR)) {
//                backgroundColor = elementStyle.get(MsgParser.KEY_BACKGROUND_COLOR).getAsString();
//            }
//            styleData = new MfElementStyleData.Builder().setTextColor(textColor)
//                    .setBackgroundColor(backgroundColor).build();
//        }
//
//        MfElementData mfElementData = new MfElementData.Builder(id, type).setText(text)
//                .setAction(action).setPayload(payload)
//                .setStyleData(styleData).build();
//
//        return mfElementData;
//    }
}