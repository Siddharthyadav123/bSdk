package com.morfeus.android.mfsdk.core.config;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.core.config.entity.Config;


/**
 * Provides configuration provided into config.json.
 */

public class ConfigManagerImpl implements ConfigManager {

    private static final String TAG = ConfigManagerImpl.class.getSimpleName();

    private static ConfigManagerImpl INSTANCE;

    private final ConfigReader mReader;

    private final ConfigWriter mWriter;

    private ConfigManagerImpl(@NonNull ConfigReader reader, @NonNull ConfigWriter writer) {
        mReader = reader;
        mWriter = writer;
    }

    public static ConfigManagerImpl getInstance(ConfigReader reader, ConfigWriter writer) {
        if (INSTANCE == null) {
            INSTANCE = new ConfigManagerImpl(reader, writer);
        }
        return INSTANCE;

    }

    @Override
    public Config loadConfig() {
//        try {
//            String json = null;
//            InputStream is = MfCoreImpl.getContext().getResources().openRawResource(R.raw.config);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//            Log.d(TAG, "loadConfig: " + json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
