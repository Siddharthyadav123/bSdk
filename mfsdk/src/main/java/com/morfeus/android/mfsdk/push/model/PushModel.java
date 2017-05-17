package com.morfeus.android.mfsdk.push.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Model used for push notification.
 */
public class PushModel implements Parcelable {
    public static final Parcelable.Creator<PushModel> CREATOR = new Parcelable.Creator<PushModel>() {
        @Override
        public PushModel createFromParcel(Parcel source) {
            return new PushModel(source);
        }

        @Override
        public PushModel[] newArray(int size) {
            return new PushModel[size];
        }
    };
    private final String title;
    private final String description;
    private final String card; // card json
    private final String pushId;

    public PushModel(String title, String description, String card, String pushId) {
        if (TextUtils.isEmpty(title))
            this.title = "Morfeus";
        else
            this.title = title;

        if (TextUtils.isEmpty(description))
            this.description = "";
        else
            this.description = description;

        this.card = card;
        this.pushId = pushId;
    }

    protected PushModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.card = in.readString();
        this.pushId = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Json string represents card
     */
    public String getCard() {
        return card;
    }

    public String getPushId() {
        return pushId;
    }

    @Override
    public String toString() {
        return "PushModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", card='" + card + '\'' +
                ", pushId='" + pushId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.card);
        dest.writeString(this.pushId);
    }
}
