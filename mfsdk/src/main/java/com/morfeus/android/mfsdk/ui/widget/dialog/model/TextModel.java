package com.morfeus.android.mfsdk.ui.widget.dialog.model;

import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

/**
 * Text model used to represent text view into login screen
 */
public class TextModel {
    private MfDialogStyle style;
    private String text;

    public TextModel(String text, MfDialogStyle style) {
        this.text = text;
        this.style = style;
    }

    public MfDialogStyle getStyle() {
        return style;
    }

    public String getText() {
        return text;
    }
}
