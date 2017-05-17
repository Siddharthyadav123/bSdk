package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.lang.TextResource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TextResourceConfigParser implements ConfigParser {
    private static final String TAG = TextResourceConfigParser.class.getSimpleName();

    private static TextResourceConfigParser INSTANCE;

    private TextResourceConfigParser() {
        // No-op
    }

    public static TextResourceConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TextResourceConfigParser();
        return INSTANCE;
    }


    @Override
    public Object parse(@NonNull String jsonStr) {
        try {
            JsonParser parser = new JsonParser();
            JsonArray languageJsonArray = parser.parse(jsonStr).getAsJsonArray();
            if (languageJsonArray != null) {
                for (int i = 0; i < languageJsonArray.size(); i++) {
                    JsonObject langugaeJson = languageJsonArray.get(i).getAsJsonObject();
                    if (langugaeJson != null && langugaeJson.has(TextResourceKey.KEY_LANG)) {
                        String lang = langugaeJson.get(TextResourceKey.KEY_LANG).getAsString();
                        HashMap<String, String> resource = new HashMap<>();
                        if (langugaeJson.has(TextResourceKey.KEY_RESOURCE)) {
                            JsonObject resourceJsonObject = langugaeJson.getAsJsonObject(TextResourceKey.KEY_RESOURCE);
                            if (resourceJsonObject != null) {
                                mapResources(resource, resourceJsonObject);
                            }
                        }
                        TextResource textResource = new TextResource(lang, resource);
                        LanguageRepository.getInstance().add(textResource);
                    }
                }
            }

        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
        return null;
    }

    private void mapResources(HashMap<String, String> resource, JsonObject resourceJsonObjet) {
        //iterating all key value pair of resource and putting in hashmap
        Set<Map.Entry<String, JsonElement>> entrySet = resourceJsonObjet.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String resourceValue = resourceJsonObjet.get(entry.getKey()).getAsString();
            resource.put(entry.getKey(), resourceValue);
        }
    }
}
