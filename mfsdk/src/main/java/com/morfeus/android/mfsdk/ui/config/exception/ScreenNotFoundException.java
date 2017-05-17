package com.morfeus.android.mfsdk.ui.config.exception;

/**
 * Thrown when screen not found for given screen name.
 */
public class ScreenNotFoundException extends Exception {

    public ScreenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenNotFoundException(String message) {
        super(message);
    }
}
