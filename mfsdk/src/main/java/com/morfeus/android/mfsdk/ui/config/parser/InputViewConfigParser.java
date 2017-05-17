package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.InputViewModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;

public class InputViewConfigParser implements ConfigParser {

    private static InputViewConfigParser INSTANCE;

    private InputViewConfigParser() {
    }

    public static InputViewConfigParser getInstance() {
        if (INSTANCE == null) {
            return new InputViewConfigParser();
        }
        return INSTANCE;
    }

    @Override
    public BaseModel parse(@NonNull String jsonStr) {
        InputViewModel inputViewModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_FOOTER)) {
                    JsonObject footerJSON = screenJSON.getAsJsonObject(KEY_FOOTER);
                    if (footerJSON.has(KEY_INPUT_VIEW)) {
                        //get input view
                        JsonObject inputViewJsonObject = footerJSON.getAsJsonObject(KEY_INPUT_VIEW);

                        //making use of gson builder to de-serialize the model
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(InputViewModel.class, new InputViewModelDeserializer());
                        Gson gson = gsonBuilder.create();
                        inputViewModel = gson.fromJson(inputViewJsonObject.toString(), InputViewModel.class);
                        inputViewModel.setId(KEY_INPUT_VIEW);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputViewModel;
    }
}
