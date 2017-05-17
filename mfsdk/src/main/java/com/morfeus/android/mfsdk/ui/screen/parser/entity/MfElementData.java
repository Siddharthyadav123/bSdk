package com.morfeus.android.mfsdk.ui.screen.parser.entity;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds {@link MfElementData}
 */
public final class MfElementData {
    private String id;
    private String type;
    private String text;
    private String imageType;
    private String imageName;
    private String imageUrl;
    private String action;
    private String payload;
    private int minimumValue = 0;
    private int maximumValue = 0;
    private int interval = 0;
    private int coverPercentage = 0;
    private MfElementStyleData styleData;
    private String videoType;
    private String videoName;
    private String videoUrl;
    private String subText = null;
    private String display = null;
    private double latitude = 0;
    private double longitude = 0;
    private double value = 0;


    public MfElementData(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.text = builder.text;
        this.imageType = builder.imageType;
        this.imageName = builder.imageName;
        this.imageUrl = builder.imageUrl;
        this.action = builder.action;
        this.payload = builder.payload;
        this.styleData = builder.styleData;
        this.minimumValue = builder.minimumValue;
        this.maximumValue = builder.maximumValue;
        this.interval = builder.interval;
        this.coverPercentage = builder.coverPercentage;
        this.videoType = builder.videoType;
        this.videoName = builder.videoName;
        this.videoUrl = builder.videoUrl;
        this.subText = builder.subText;
        this.display = builder.display;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.value = builder.value;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAction() {
        return action;
    }

    public String getPayload() {
        return payload;
    }

    public MfElementStyleData getStyleData() {
        return styleData;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getInterval() {
        return interval;
    }

    public int getCoverPercentage() {
        return coverPercentage;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getSubText() {
        return subText;
    }

    public String getDisplay() {
        return display;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MfElementData{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", imageType='" + imageType + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", action='" + action + '\'' +
                ", payload='" + payload + '\'' +
                ", minimumValue='" + minimumValue + '\'' +
                ", maximumValue='" + maximumValue + '\'' +
                ", interval='" + interval + '\'' +
                ", coverPercentage='" + coverPercentage + '\'' +
                ", videoType='" + videoType + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", subText='" + subText + '\'' +
                ", display='" + display + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", value='" + value + '\'' +
                ", styleData=" + styleData +
                '}';
    }

    public static class Builder {
        private String id;
        private String type;
        private String text;
        private String imageType;
        private String imageName;
        private String imageUrl;
        private String action;
        private String payload;
        private int minimumValue = 0;
        private int maximumValue = 0;
        private int interval = 0;
        private int coverPercentage = 0;
        private MfElementStyleData styleData;
        private String videoType = null;
        private String videoName = null;
        private String videoUrl = null;
        private String subText = null;
        private String display = null;
        private double latitude = 0;
        private double longitude = 0;
        private double value = 0;


        public Builder(@NonNull String id, @NonNull String type) {
            this.id = checkNotNull(id);
            this.type = checkNotNull(type);
        }

        public Builder setImageType(String imageType) {
            this.imageType = imageType;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setPayload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder setStyleData(MfElementStyleData styleData) {
            this.styleData = styleData;
            return this;
        }

        public Builder setMinimumValue(int minimumValue) {
            this.minimumValue = minimumValue;
            return this;
        }

        public Builder setMaximumValue(int maximumValue) {
            this.maximumValue = maximumValue;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setCoverPercentage(int coverPercentage) {
            this.coverPercentage = coverPercentage;
            return this;
        }

        public Builder setVideoType(String videoType) {
            this.videoType = videoType;
            return this;
        }

        public Builder setVideoName(String videoName) {
            this.videoName = videoName;
            return this;
        }

        public Builder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder setSubText(String subText) {
            this.subText = subText;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setValue(double value) {
            this.value = value;
            return this;
        }

        public MfElementData build() {
            return new MfElementData(this);
        }
    }
}
