package com.morfeus.android.mfsdk.core;

import com.morfeus.android.mfsdk.MfSdkInitializationException;

/**
 * Thrown when a MfSdkCore fails to initialize during initRequest process
 */
public class MfSdkCoreInitializationException extends MfSdkInitializationException {

    public MfSdkCoreInitializationException(String message) {
        super(message);
    }

    public MfSdkCoreInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
