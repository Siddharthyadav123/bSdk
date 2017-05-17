package com.morfeus.android.mfsdk.ui;

import com.morfeus.android.mfsdk.MfSdkInitializationException;

/**
 * Thrown when a MfSdkUI fails to initialize during init process
 */
public class MfSdkUIInitializationException extends MfSdkInitializationException {

    public MfSdkUIInitializationException(String message) {
        super(message);
    }

    public MfSdkUIInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
