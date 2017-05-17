package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

public class SuggestionButtonModel implements BaseModel {

    private String id;

    private String label;

    private String action;

    private String payload;

    private String image;

    private Style style;

    public SuggestionButtonModel(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * SuggestionButtonModel's style is declared into {@link SuggestionViewModel}
     *
     * @return null
     */
    @Override
    @Nullable
    public Style getStyle() {
        return style;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAction() {
        return action;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.label);
        dest.writeString(this.action);
        dest.writeString(this.payload);
        dest.writeString(this.image);
        dest.writeParcelable(this.style, flags);
    }

    protected SuggestionButtonModel(Parcel in) {
        this.id = in.readString();
        this.label = in.readString();
        this.action = in.readString();
        this.payload = in.readString();
        this.image = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
    }

    public static final Creator<SuggestionButtonModel> CREATOR = new Creator<SuggestionButtonModel>() {
        @Override
        public SuggestionButtonModel createFromParcel(Parcel source) {
            return new SuggestionButtonModel(source);
        }

        @Override
        public SuggestionButtonModel[] newArray(int size) {
            return new SuggestionButtonModel[size];
        }
    };
}
