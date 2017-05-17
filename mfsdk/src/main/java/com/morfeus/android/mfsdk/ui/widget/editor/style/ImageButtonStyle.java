package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.support.annotation.StringDef;

public class ImageButtonStyle implements Style {

    private String backgroundImage;

    private String backgroundColor;

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public String getBackgroundImage() {
        return backgroundImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backgroundImage);
        dest.writeString(this.backgroundColor);
    }

    public ImageButtonStyle() {
    }

    protected ImageButtonStyle(Parcel in) {
        this.backgroundImage = in.readString();
        this.backgroundColor = in.readString();
    }

    public static final Creator<ImageButtonStyle> CREATOR = new Creator<ImageButtonStyle>() {
        @Override
        public ImageButtonStyle createFromParcel(Parcel source) {
            return new ImageButtonStyle(source);
        }

        @Override
        public ImageButtonStyle[] newArray(int size) {
            return new ImageButtonStyle[size];
        }
    };
}
