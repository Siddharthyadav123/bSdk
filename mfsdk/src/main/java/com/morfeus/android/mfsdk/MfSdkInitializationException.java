package com.morfeus.android.mfsdk;

/**
 * Thrown when a MfSdk fails to initialize during init process
 */
public class MfSdkInitializationException extends Exception {

    public MfSdkInitializationException(String message) {
        super(message);
    }

    public MfSdkInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
