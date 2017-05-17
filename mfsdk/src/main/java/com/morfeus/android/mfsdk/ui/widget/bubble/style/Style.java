package com.morfeus.android.mfsdk.ui.widget.bubble.style;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Style {
    public static final int INCOMING = 0;
    public static final int OUTGOING = 1;
    public static final int CHAT_BOT = 2;
    public static final int NONE = -1;

    private final String templateType;

    @Type
    private final int type;

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
    private List<Style> componentStyle;
    private boolean showAsBig;

    private Style(Builder builder) {
        this.templateType = builder.templateType;
        this.type = builder.type;
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
        this.componentStyle = builder.componentStyle;
        this.showAsBig = builder.showAsBig;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INCOMING, OUTGOING, CHAT_BOT, NONE})
    public @interface Type {
    }

    public String getTemplateType() {
        return templateType;
    }

    public int getType() {
        return type;
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

    public List<Style> getComponentStyle() {
        return componentStyle;
    }

    public boolean isShowAsBig() {
        return showAsBig;
    }

    public static class Builder {

        private final String templateType;

        @Type
        private final int type;

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
        private List<Style> componentStyle;
        private boolean showAsBig;

        public Builder(@NonNull String templateType, @Type int type) {
            this.templateType = checkNotNull(templateType);
            this.type = type;
        }

        public Builder setPosition(@NonNull String position) {
            this.position = checkNotNull(position);
            return this;
        }

        public Builder setSentImage(@NonNull String sentImage) {
            this.sentImage = checkNotNull(sentImage);
            return this;
        }

        public Builder setDeliveredImage(@NonNull String deliveredImage) {
            this.deliveredImage = checkNotNull(deliveredImage);
            return this;
        }

        public Builder setBackgroundColor(@NonNull String backgroundColor) {
            this.backgroundColor = checkNotNull(backgroundColor);
            return this;
        }

        public Builder setBackgroundImage(@NonNull String backgroundImage) {
            this.backgroundImage = checkNotNull(backgroundImage);
            return this;
        }

        public Builder setBackgroundColorShape(@NonNull String backgroundColorShape) {
            this.backgroundColorShape = checkNotNull(backgroundColorShape);
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
            this.messageTimeColor = checkNotNull(messageTimeColor);
            return this;
        }

        public Builder setMessageSenderColor(@NonNull String messageSenderColor) {
            this.messageSenderColor = checkNotNull(messageSenderColor);
            return this;
        }

        public Builder setBotImage(@NonNull String botImage) {
            this.botImage = checkNotNull(botImage);
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder setTextColor(@NonNull String textColor) {
            this.textColor = checkNotNull(textColor);
            return this;
        }

        public Builder setShowAsBig(boolean showAsBig) {
            this.showAsBig = showAsBig;
            return this;
        }

        public Builder addComponentStyle(@NonNull Style style) {
            checkNotNull(style);

            if (this.componentStyle == null)
                this.componentStyle = new ArrayList<Style>();

            this.componentStyle.add(style);
            return this;
        }

        public Style build() {
            return new Style(this);
        }
    }
}

