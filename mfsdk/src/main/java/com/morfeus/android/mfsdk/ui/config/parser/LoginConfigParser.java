package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.LoginModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;

final class LoginConfigParser implements ConfigParser {
    private static final String TAG = LoginConfigParser.class.getSimpleName();

    private static LoginConfigParser INSTANCE;

    private LoginConfigParser() {
        // No-op
    }

    public static LoginConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LoginConfigParser();
        return INSTANCE;
    }


    @Override
    public LoginModel parse(@NonNull String jsonStr) {
        LoginModel loginModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                //making use of gson builder to de-serialize the model
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LoginModel.class, new LoginModelDeserializer());
                Gson gson = gsonBuilder.create();
                loginModel = gson.fromJson(screenJSON.toString(), LoginModel.class);
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
        return loginModel;
    }
}
