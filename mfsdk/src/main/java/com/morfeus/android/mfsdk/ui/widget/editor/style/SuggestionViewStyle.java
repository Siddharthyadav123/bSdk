package com.morfeus.android.mfsdk.ui.widget.editor.style;

import android.os.Parcel;
import android.support.annotation.Nullable;

public class SuggestionViewStyle implements Style {

    private int height;

    private String backgroundColor;

    private ButtonStyle buttonStyle;

    public SuggestionViewStyle(ButtonStyle buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ButtonStyle getButtonStyle() {
        return buttonStyle;
    }

    public void setButtonStyle(ButtonStyle buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    @Override
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Not background image
     *
     * @return null
     */
    @Override
    @Nullable
    public String getBackgroundImage() {
        return null;
    }

    public static class ButtonStyle implements android.os.Parcelable {

        private String borderColor;

        private int borderWidth;

        private int cornerRadius;

        private String labelColor;

        private String backgroundColor;

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public int getBorderWidth() {
            return borderWidth;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public int getCornerRadius() {
            return cornerRadius;
        }

        public void setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        public String getLabelColor() {
            return labelColor;
        }

        public void setLabelColor(String labelColor) {
            this.labelColor = labelColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.borderColor);
            dest.writeInt(this.borderWidth);
            dest.writeInt(this.cornerRadius);
            dest.writeString(this.labelColor);
            dest.writeString(this.backgroundColor);
        }

        public ButtonStyle() {
        }

        protected ButtonStyle(Parcel in) {
            this.borderColor = in.readString();
            this.borderWidth = in.readInt();
            this.cornerRadius = in.readInt();
            this.labelColor = in.readString();
            this.backgroundColor = in.readString();
        }

        public static final Creator<ButtonStyle> CREATOR = new Creator<ButtonStyle>() {
            @Override
            public ButtonStyle createFromParcel(Parcel source) {
                return new ButtonStyle(source);
            }

            @Override
            public ButtonStyle[] newArray(int size) {
                return new ButtonStyle[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeString(this.backgroundColor);
        dest.writeParcelable(this.buttonStyle, flags);
    }

    protected SuggestionViewStyle(Parcel in) {
        this.height = in.readInt();
        this.backgroundColor = in.readString();
        this.buttonStyle = in.readParcelable(ButtonStyle.class.getClassLoader());
    }

    public static final Creator<SuggestionViewStyle> CREATOR = new Creator<SuggestionViewStyle>() {
        @Override
        public SuggestionViewStyle createFromParcel(Parcel source) {
            return new SuggestionViewStyle(source);
        }

        @Override
        public SuggestionViewStyle[] newArray(int size) {
            return new SuggestionViewStyle[size];
        }
    };
}
