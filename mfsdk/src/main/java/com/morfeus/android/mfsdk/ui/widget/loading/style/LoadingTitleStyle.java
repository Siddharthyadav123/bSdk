package com.morfeus.android.mfsdk.ui.widget.loading.style;

import android.os.Parcel;

public class LoadingTitleStyle implements LoadingTextStyle {

    public static final Creator<LoadingTitleStyle> CREATOR = new Creator<LoadingTitleStyle>() {
        @Override
        public LoadingTitleStyle createFromParcel(Parcel source) {
            return new LoadingTitleStyle(source);
        }

        @Override
        public LoadingTitleStyle[] newArray(int size) {
            return new LoadingTitleStyle[size];
        }
    };
    private String color;

    public LoadingTitleStyle() {
    }

    protected LoadingTitleStyle(Parcel in) {
        this.color = in.readString();
    }

    @Override
    public String getLoadingTextColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadingTitleStyle)) return false;
        LoadingTitleStyle that = (LoadingTitleStyle) o;
        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        return result;
    }
}
