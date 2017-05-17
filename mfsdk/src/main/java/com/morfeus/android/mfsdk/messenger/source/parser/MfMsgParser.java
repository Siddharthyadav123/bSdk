package com.morfeus.android.mfsdk.messenger.source.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgItems;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.messenger.source.parser.deserializer.MfMsgItemsDeserializer;

import java.util.List;

public class MfMsgParser implements MsgParser {

    private static final String TAG = MfMsgParser.class.getSimpleName();

    private static MfMsgParser sInstance;

    private MfMsgParser() {
    }

    public static MfMsgParser getInstance() {
        if (sInstance == null) {
            sInstance = new MfMsgParser();
        }
        return sInstance;
    }

    @Override
    public MfMsgModel parse(boolean isIncoming, String jsonStr) {
        MfMsgModel mfMsgModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();

            if (parentJSON.has(KEY_MESSAGES)) {
                JsonArray msgJsonArray = parentJSON.getAsJsonArray(KEY_MESSAGES);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(MfMsgModel.class, new MfMsgItemsDeserializer())
                        .create();

                mfMsgModel = gson.fromJson(msgJsonArray.toString(), MfMsgModel.class);
                processMfMsgModel(isIncoming, mfMsgModel);
            }
        } catch (Exception e) {
            LogManager.e(TAG, "Error: " + e.getMessage());
        }

        return mfMsgModel;
    }

    private void processMfMsgModel(boolean isIncoming, MfMsgModel mfMsgModel) {
        if (mfMsgModel != null) {
            List<MfMsgItems> msgItemses = mfMsgModel.getMsgItems();
            if (!msgItemses.isEmpty()) {
                for (MfMsgItems mfMsgItems : msgItemses) {
                    if (mfMsgItems != null) {
                        mfMsgItems.setIncoming(isIncoming);
                    }
                }
            }
        }
    }
}
