package com.morfeus.android.mfsdk.messenger;

import com.morfeus.android.mfsdk.MfSdkInitializationException;

/**
 * Thrown when a MfSdkMsg fails to initialize during init process
 */
public class MfSdkMsgInitializationException extends MfSdkInitializationException {

    public MfSdkMsgInitializationException(String message) {
        super(message);
    }

    public MfSdkMsgInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
