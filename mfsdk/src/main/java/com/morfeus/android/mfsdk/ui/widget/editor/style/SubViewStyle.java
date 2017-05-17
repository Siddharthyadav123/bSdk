package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;

public class SubViewStyle implements Style {

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

    public SubViewStyle() {
    }

    protected SubViewStyle(Parcel in) {
        this.backgroundColor = in.readString();
        this.backgroundImage = in.readString();
    }

    public static final Creator<SubViewStyle> CREATOR = new Creator<SubViewStyle>() {
        @Override
        public SubViewStyle createFromParcel(Parcel source) {
            return new SubViewStyle(source);
        }

        @Override
        public SubViewStyle[] newArray(int size) {
            return new SubViewStyle[size];
        }
    };
}
