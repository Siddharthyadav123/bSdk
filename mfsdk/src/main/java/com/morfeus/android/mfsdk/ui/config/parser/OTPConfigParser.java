package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.config.parser.deserializer.OTPModelDeserializer;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;

public class OTPConfigParser implements ConfigParser {
    private static final String TAG = OTPConfigParser.class.getSimpleName();

    private static OTPConfigParser INSTANCE;

    private OTPConfigParser() {
        // No-op
    }

    public static OTPConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new OTPConfigParser();
        return INSTANCE;
    }


    @Override
    public OTPModel parse(@NonNull String jsonStr) {
        OTPModel otpModel = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                //making use of gson builder to de-serialize the model
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(OTPModel.class, new OTPModelDeserializer());
                Gson gson = gsonBuilder.create();
                otpModel = gson.fromJson(screenJSON.toString(), OTPModel.class);
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
        return otpModel;
    }
}
