package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;

public class InputViewStyle implements Style {

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
        return backgroundImage;
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

    public InputViewStyle() {
    }

    protected InputViewStyle(Parcel in) {
        this.backgroundColor = in.readString();
        this.backgroundImage = in.readString();
    }

    public static final Creator<InputViewStyle> CREATOR = new Creator<InputViewStyle>() {
        @Override
        public InputViewStyle createFromParcel(Parcel source) {
            return new InputViewStyle(source);
        }

        @Override
        public InputViewStyle[] newArray(int size) {
            return new InputViewStyle[size];
        }
    };
}
