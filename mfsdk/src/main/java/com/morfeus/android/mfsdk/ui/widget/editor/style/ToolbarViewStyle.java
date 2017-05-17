package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;
import android.support.annotation.ColorInt;

public class ToolbarViewStyle implements Style {

    private String backgroundColor;

    private String backgroundImage;

    public ToolbarViewStyle(String backgroundColor, String backgroundImage) {
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public String getBackgroundImage() {
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backgroundColor);
        dest.writeString(this.backgroundImage);
    }

    protected ToolbarViewStyle(Parcel in) {
        this.backgroundColor = in.readString();
        this.backgroundImage = in.readString();
    }

    public static final Creator<ToolbarViewStyle> CREATOR = new Creator<ToolbarViewStyle>() {
        @Override
        public ToolbarViewStyle createFromParcel(Parcel source) {
            return new ToolbarViewStyle(source);
        }

        @Override
        public ToolbarViewStyle[] newArray(int size) {
            return new ToolbarViewStyle[size];
        }
    };
}
