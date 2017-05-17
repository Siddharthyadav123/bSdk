package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.SuggestionViewModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;

public class SuggestionViewConfigParser implements ConfigParser {

    private static SuggestionViewConfigParser INSTANCE;

    private SuggestionViewConfigParser() {
        // No-op
    }

    public static SuggestionViewConfigParser getInstance() {
        if (INSTANCE == null) {
            return new SuggestionViewConfigParser();
        }
        return INSTANCE;
    }

    @Override
    public BaseModel parse(@NonNull String jsonStr) {
        SuggestionViewModel suggestionViewModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_FOOTER)) {
                    JsonObject footerJSON = screenJSON.getAsJsonObject(KEY_FOOTER);
                    if (footerJSON.has(KEY_SHORTCUT_VIEW)) {
                        //get input view
                        JsonObject shortcutJsonObj = footerJSON.getAsJsonObject(KEY_SHORTCUT_VIEW);

                        //making use of gson builder to de-serialize the model
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(SuggestionViewModel.class, new SuggestionViewModelDeserializer());
                        Gson gson = gsonBuilder.create();
                        suggestionViewModel = gson.fromJson(shortcutJsonObj.toString(), SuggestionViewModel.class);
                        suggestionViewModel.setId(KEY_SHORTCUT_VIEW);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestionViewModel;
    }
}
