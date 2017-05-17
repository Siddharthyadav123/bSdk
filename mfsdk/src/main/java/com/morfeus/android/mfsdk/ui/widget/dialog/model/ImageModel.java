package com.morfeus.android.mfsdk.ui.widget.dialog.model;

import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

/**
 * Image Model used to represent image in login screen.
 */
public class ImageModel {
    private MfDialogStyle style;
    private String type;
    private String name;
    private String url;
    private String imageType;

    public MfDialogStyle getStyle() {
        return style;
    }

    public void setStyle(MfDialogStyle style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
