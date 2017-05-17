package com.morfeus.android.mfsdk.core.config;

import com.morfeus.android.mfsdk.core.config.entity.Config;

/**
 * This interface represents core config provided by user.
 */
public interface ConfigManager {

    interface Read {
        void readLaunchConfig();
    }

    interface Write {

    }

    Config loadConfig();

}
