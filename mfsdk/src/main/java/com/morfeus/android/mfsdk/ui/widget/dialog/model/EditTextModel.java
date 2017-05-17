package com.morfeus.android.mfsdk.ui.widget.dialog.model;

import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

/**
 * This model is used to represent the EditText inside dialog.
 */

public class EditTextModel {
    private String placeHolderText;
    private MfDialogStyle style;

    public EditTextModel(String placeHolderText, MfDialogStyle style) {
        this.placeHolderText = placeHolderText;
        this.style = style;
    }

    public String getPlaceHolderText() {
        return placeHolderText;
    }

    public MfDialogStyle getStyle() {
        return style;
    }
}
