package com.morfeus.android.mfsdk.ui.widget.editor.style;

public class LocationScreenStyle {
    private String backgroundImage;
    private String backgroundColor;
    private String textColor;
    private String borderColor;
    private String horizontalAlignment;
    private String maskImage;
    private String unmaskImage;
    private int height = 0;
    private String pinImage;


    private LocationScreenStyle(Builder builder) {
        this.backgroundColor = builder.backgroundColor;
        this.backgroundImage = builder.backgroundImage;
        this.textColor = builder.textColor;
        this.borderColor = builder.borderColor;
        this.horizontalAlignment = builder.horizontalAlignment;
        this.maskImage = builder.maskImage;
        this.unmaskImage = builder.unmaskImage;
        this.height = builder.height;
        this.pinImage = builder.pinImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public String getMaskImage() {
        return maskImage;
    }

    public String getUnmaskImage() {
        return unmaskImage;
    }

    public int getHeight() {
        return height;
    }

    public String getPinImage() {
        return pinImage;
    }

    public static class Builder {
        private String backgroundImage;
        private String backgroundColor;
        private String textColor;
        private String borderColor;
        private String horizontalAlignment;
        private String maskImage;
        private String unmaskImage;
        private int height = 0;
        private String pinImage;

        public Builder() {
            // No-op
        }

        public Builder setBackgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBorderColor(String borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        public Builder setHorizontalAlignment(String horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
            return this;
        }


        public Builder setMaskImage(String maskImage) {
            this.maskImage = maskImage;
            return this;
        }

        public Builder setUnmaskImage(String unmaskImage) {
            this.unmaskImage = unmaskImage;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setPinImage(String pinImage) {
            this.pinImage = pinImage;
            return this;
        }

        public LocationScreenStyle build() {
            return new LocationScreenStyle(this);
        }
    }
}
