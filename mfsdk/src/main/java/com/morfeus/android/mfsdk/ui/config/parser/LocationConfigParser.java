package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.LocationModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;

public class LocationConfigParser implements ConfigParser {
    private static final String TAG = LocationConfigParser.class.getSimpleName();

    private static LocationConfigParser INSTANCE;

    private LocationConfigParser() {
        // No-op
    }

    public static LocationConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LocationConfigParser();
        return INSTANCE;
    }


    @Override
    public LocationScreenModel parse(@NonNull String jsonStr) {
        LocationScreenModel locationScreenModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                //making use of gson builder to de-serialize the model
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocationScreenModel.class, new LocationModelDeserializer());
                Gson gson = gsonBuilder.create();
                locationScreenModel = gson.fromJson(screenJSON.toString(), LocationScreenModel.class);
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
        return locationScreenModel;
    }
}
