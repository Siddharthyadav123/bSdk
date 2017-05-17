package com.morfeus.android.mfsdk.ui.widget.header.style;

import android.os.Parcel;
import android.os.Parcelable;

public class ActionbarStyle implements HeaderStyle, Parcelable {
    public static final Parcelable.Creator<ActionbarStyle> CREATOR = new Parcelable.Creator<ActionbarStyle>() {
        @Override
        public ActionbarStyle createFromParcel(Parcel source) {
            return new ActionbarStyle(source);
        }

        @Override
        public ActionbarStyle[] newArray(int size) {
            return new ActionbarStyle[size];
        }
    };
    private String backgroundColor;
    private String backgroundImage;

    public ActionbarStyle() {
    }

    protected ActionbarStyle(Parcel in) {
        this.backgroundColor = in.readString();
        this.backgroundImage = in.readString();
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionbarStyle)) return false;

        ActionbarStyle that = (ActionbarStyle) o;

        if (backgroundColor != null ? !backgroundColor.equals(that.backgroundColor) : that.backgroundColor != null)
            return false;
        return backgroundImage != null ? backgroundImage.equals(that.backgroundImage) : that.backgroundImage == null;

    }

    @Override
    public int hashCode() {
        int result = backgroundColor != null ? backgroundColor.hashCode() : 0;
        result = 31 * result + (backgroundImage != null ? backgroundImage.hashCode() : 0);
        return result;
    }
}
