package com.morfeus.android.mfsdk.ui.screen.parser.entity;

import android.support.annotation.NonNull;

/**
 * Builds {@link MfElementStyleData}
 */
public final class MfElementStyleData {
    private String position;
    private String sentImage;
    private String deliveredImage;
    private String backgroundColor;
    private String backgroundImage;
    private String backgroundColorShape;
    private int paddingLeft;
    private int paddingRight;
    private String messageTimeColor;
    private String messageSenderColor;
    private String botImage;
    private int maxWidth;
    private String textColor;
    private int textSize;
    private String thumbImage;
    private String sliderColor;
    private String fillColor;
    private String playImage;
    private String pinImage;
    private String rateIcon;
    private String rateOff;

    public MfElementStyleData(Builder builder) {
        this.position = builder.position;
        this.sentImage = builder.sentImage;
        this.deliveredImage = builder.deliveredImage;
        this.backgroundColor = builder.backgroundColor;
        this.backgroundImage = builder.backgroundImage;
        this.backgroundColorShape = builder.backgroundColorShape;
        this.paddingLeft = builder.paddingLeft;
        this.paddingRight = builder.paddingRight;
        this.messageTimeColor = builder.messageTimeColor;
        this.messageSenderColor = builder.messageSenderColor;
        this.botImage = builder.botImage;
        this.maxWidth = builder.maxWidth;
        this.textColor = builder.textColor;
        this.textSize = builder.textSize;
        this.thumbImage = builder.thumbImage;
        this.sliderColor = builder.sliderColor;
        this.fillColor = builder.fillColor;
        this.playImage = builder.playImage;
        this.pinImage = builder.pinImage;
        this.rateIcon = builder.rateIcon;
        this.rateOff = builder.rateOff;
    }

    public String getPosition() {
        return position;
    }

    public String getSentImage() {
        return sentImage;
    }

    public String getDeliveredImage() {
        return deliveredImage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getBackgroundColorShape() {
        return backgroundColorShape;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public String getMessageTimeColor() {
        return messageTimeColor;
    }

    public String getMessageSenderColor() {
        return messageSenderColor;
    }

    public String getBotImage() {
        return botImage;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public String getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public String getSliderColor() {
        return sliderColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public String getPlayImage() {
        return playImage;
    }

    public String getPinImage() {
        return pinImage;
    }

    public String getRateIcon() {
        return rateIcon;
    }

    public String getRateOff() {
        return rateOff;
    }

    public static class Builder {
        private String position;
        private String sentImage;
        private String deliveredImage;
        private String backgroundColor;
        private String backgroundImage;
        private String backgroundColorShape;
        private int paddingLeft;
        private int paddingRight;
        private String messageTimeColor;
        private String messageSenderColor;
        private String botImage;
        private int maxWidth;
        private String textColor;
        private int textSize;
        private String thumbImage;
        private String sliderColor;
        private String fillColor;
        private String playImage;
        private String pinImage;
        private String rateIcon;
        private String rateOff;

        public Builder() {
        }

        public Builder setPosition(@NonNull String position) {
            this.position = position;
            return this;
        }

        public Builder setSentImage(@NonNull String sentImage) {
            this.sentImage = sentImage;
            return this;
        }

        public Builder setDeliveredImage(@NonNull String deliveredImage) {
            this.deliveredImage = deliveredImage;
            return this;
        }

        public Builder setBackgroundColor(@NonNull String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackgroundImage(@NonNull String backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder setBackgroundColorShape(@NonNull String backgroundColorShape) {
            this.backgroundColorShape = backgroundColorShape;
            return this;
        }

        public Builder setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
            return this;
        }

        public Builder setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
            return this;
        }

        public Builder setMessageTimeColor(@NonNull String messageTimeColor) {
            this.messageTimeColor = messageTimeColor;
            return this;
        }

        public Builder setMessageSenderColor(@NonNull String messageSenderColor) {
            this.messageSenderColor = messageSenderColor;
            return this;
        }

        public Builder setBotImage(@NonNull String botImage) {
            this.botImage = botImage;
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder setTextColor(@NonNull String textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setThumbImage(String thumbImage) {
            this.thumbImage = thumbImage;
            return this;
        }

        public Builder setSliderColor(String sliderColor) {
            this.sliderColor = sliderColor;
            return this;
        }

        public Builder setFillColor(String fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        public Builder setPlayImage(String playImage) {
            this.playImage = playImage;
            return this;
        }

        public Builder setPinImage(String pinImage) {
            this.pinImage = pinImage;
            return this;
        }

        public Builder setRateIcon(String rateIcon) {
            this.rateIcon = rateIcon;
            return this;
        }

        public Builder setRateOff(String rateOff) {
            this.rateOff = rateOff;
            return this;
        }

        public MfElementStyleData build() {
            return new MfElementStyleData(this);
        }
    }
}
