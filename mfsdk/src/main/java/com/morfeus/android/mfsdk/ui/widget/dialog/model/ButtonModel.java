package com.morfeus.android.mfsdk.ui.widget.dialog.model;

import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

/**
 * Button model used to represent the button in login screen
 */

public class ButtonModel {
    private MfDialogStyle style;
    private String text;

    public ButtonModel(MfDialogStyle style, String text) {
        this.style = style;
        this.text = text;
    }

    public MfDialogStyle getStyle() {
        return style;
    }

    public String getText() {
        return text;
    }
}
