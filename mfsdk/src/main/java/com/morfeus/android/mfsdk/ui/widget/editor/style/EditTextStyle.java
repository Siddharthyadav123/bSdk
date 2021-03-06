package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;
import android.support.annotation.ColorInt;

public class EditTextStyle implements Style {

    private String backgroundColor;

    private String backgroundImage;

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

    public EditTextStyle() {
    }

    protected EditTextStyle(Parcel in) {
        this.backgroundColor = in.readString();
        this.backgroundImage = in.readString();
    }

    public static final Creator<EditTextStyle> CREATOR = new Creator<EditTextStyle>() {
        @Override
        public EditTextStyle createFromParcel(Parcel source) {
            return new EditTextStyle(source);
        }

        @Override
        public EditTextStyle[] newArray(int size) {
            return new EditTextStyle[size];
        }
    };
}
