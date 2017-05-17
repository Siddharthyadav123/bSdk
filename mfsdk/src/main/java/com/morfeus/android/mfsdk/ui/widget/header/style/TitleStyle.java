package com.morfeus.android.mfsdk.ui.widget.header.style;

import android.os.Parcel;

public class TitleStyle implements TextStyle {

    public static final Creator<TitleStyle> CREATOR = new Creator<TitleStyle>() {
        @Override
        public TitleStyle createFromParcel(Parcel source) {
            return new TitleStyle(source);
        }

        @Override
        public TitleStyle[] newArray(int size) {
            return new TitleStyle[size];
        }
    };
    private String textColor;
    private String horizontalAlignment;

    public TitleStyle() {
    }

    protected TitleStyle(Parcel in) {
        this.textColor = in.readString();
        this.horizontalAlignment = in.readString();
    }

    @Override
    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public String getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.textColor);
        dest.writeString(this.horizontalAlignment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleStyle)) return false;

        TitleStyle that = (TitleStyle) o;

        if (textColor != null ? !textColor.equals(that.textColor) : that.textColor != null)
            return false;
        return horizontalAlignment != null ? horizontalAlignment.equals(that.horizontalAlignment) : that.horizontalAlignment == null;

    }

    @Override
    public int hashCode() {
        int result = textColor != null ? textColor.hashCode() : 0;
        result = 31 * result + (horizontalAlignment != null ? horizontalAlignment.hashCode() : 0);
        return result;
    }
}
