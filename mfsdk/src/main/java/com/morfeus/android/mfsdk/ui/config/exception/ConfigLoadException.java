package com.morfeus.android.mfsdk.ui.config.exception;

/**
 * Thrown when fails to load config properly
 */
public class ConfigLoadException extends Exception {

    public ConfigLoadException(String message) {
        super(message);
    }

    public ConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
