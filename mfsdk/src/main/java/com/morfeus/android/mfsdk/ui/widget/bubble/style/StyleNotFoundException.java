package com.morfeus.android.mfsdk.ui.widget.bubble.style;

/**
 * Thrown when style not found for given template type in {@link StyleFactory}
 */
public class StyleNotFoundException extends Exception {

    public StyleNotFoundException(String message) {
        super(message);
    }

    public StyleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
