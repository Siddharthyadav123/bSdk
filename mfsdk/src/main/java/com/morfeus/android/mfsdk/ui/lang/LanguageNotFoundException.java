package com.morfeus.android.mfsdk.ui.lang;


public class LanguageNotFoundException extends Exception {

    LanguageNotFoundException(String message) {
        super(message);
    }

    public LanguageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

