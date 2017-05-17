package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.ToolbarViewModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;

public class ToolbarViewConfigParser implements ConfigParser {

    private static ToolbarViewConfigParser INSTANCE;

    private ToolbarViewConfigParser() {
        // No-op
    }

    public static ToolbarViewConfigParser getInstance() {
        if (INSTANCE == null) {
            return new ToolbarViewConfigParser();
        }
        return INSTANCE;
    }

    @Override
    public BaseModel parse(@NonNull String jsonStr) {
        TabLayoutModel tabLayoutModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_FOOTER)) {
                    JsonObject footerJSON = screenJSON.getAsJsonObject(KEY_FOOTER);
                    if (footerJSON.has(KEY_TOOLBAR_VIEW)) {
                        //get input view
                        JsonObject toolbarView = footerJSON.getAsJsonObject(KEY_TOOLBAR_VIEW);

                        //making use of gson builder to de-serialize the model
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(TabLayoutModel.class, new ToolbarViewModelDeserializer());
                        Gson gson = gsonBuilder.create();
                        tabLayoutModel = gson.fromJson(toolbarView.toString(), TabLayoutModel.class);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tabLayoutModel;
    }
}
