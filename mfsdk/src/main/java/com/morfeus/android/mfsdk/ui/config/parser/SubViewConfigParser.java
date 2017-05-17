package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.SubViewModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;

public class SubViewConfigParser implements ConfigParser {

    private static SubViewConfigParser INSTANCE;

    private SubViewConfigParser() {
        // No-op
    }

    public static SubViewConfigParser getInstance() {
        if (INSTANCE == null) {
            return new SubViewConfigParser();
        }
        return INSTANCE;
    }

    @Override
    public BaseModel parse(@NonNull String jsonStr) {
        SubViewModel subViewModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_SUB_VIEW)) {
                    //get input view
                    JsonObject subViewJSON = screenJSON.getAsJsonObject(KEY_SUB_VIEW);

                    //making use of gson builder to de-serialize the model
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(SubViewModel.class, new SubViewModelDeserializer());
                    Gson gson = gsonBuilder.create();
                    subViewModel = gson.fromJson(subViewJSON.toString(), SubViewModel.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subViewModel;
    }
}
