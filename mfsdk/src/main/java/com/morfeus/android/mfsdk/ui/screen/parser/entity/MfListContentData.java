package com.morfeus.android.mfsdk.ui.screen.parser.entity;

import android.support.annotation.NonNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfListContentData {
    private String action;
    private String elementStyle;
    private List<MfComponentData> elementsList;
    private MfElementStyleData style = null;

    public MfListContentData(@NonNull List<MfComponentData> elementsList) {
        this.elementsList = checkNotNull(elementsList);
    }

    public String getElementStyle() {
        return elementStyle;
    }

    public void setElementStyle(String elementStyle) {
        this.elementStyle = elementStyle;
    }

    public List<MfComponentData> getElementsList() {
        return elementsList;
    }

    public MfElementStyleData getStyle() {
        return style;
    }

    public void setStyle(MfElementStyleData style) {
        this.style = style;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
