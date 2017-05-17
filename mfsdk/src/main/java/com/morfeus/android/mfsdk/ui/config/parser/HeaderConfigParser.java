package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.ActionBarModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;

public class HeaderConfigParser implements ConfigParser {

    private static HeaderConfigParser INSTANCE;

    private HeaderConfigParser() {
        // No-op
    }

    public static HeaderConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HeaderConfigParser();
        return INSTANCE;
    }

    @Override
    public ActionbarModel parse(@NonNull String jsonStr) {
        ActionbarModel actionbarModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_HEADER)) {
                    //get input view
                    JsonObject headerJSON = screenJSON.getAsJsonObject(KEY_HEADER);

                    //making use of gson builder to de-serialize the model
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(ActionbarModel.class, new ActionBarModelDeserializer());
                    Gson gson = gsonBuilder.create();
                    actionbarModel = gson.fromJson(headerJSON.toString(), ActionbarModel.class);
                    actionbarModel.setId(KEY_HEADER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actionbarModel;
    }
}
