package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class SuggestionViewModel implements CompositeBaseModel, Parcelable {

    private String id;

    private Style style;

    private List<BaseModel> subComponentModels;

    public SuggestionViewModel(String id) {
        this.id = id;
    }

    @Override
    public List<BaseModel> getSubComponentModels() {
        return subComponentModels;
    }

    public void setSubComponentModels(List<BaseModel> subComponents) {
        this.subComponentModels = subComponents;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * SuggestionView don't have action.
     * @return null
     */
    @Override
    @Nullable
    public String getAction() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.style, flags);
        dest.writeList(this.subComponentModels);
    }

    protected SuggestionViewModel(Parcel in) {
        this.id = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.subComponentModels = new ArrayList<BaseModel>();
        in.readList(this.subComponentModels, BaseModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<SuggestionViewModel> CREATOR = new Parcelable.Creator<SuggestionViewModel>() {
        @Override
        public SuggestionViewModel createFromParcel(Parcel source) {
            return new SuggestionViewModel(source);
        }

        @Override
        public SuggestionViewModel[] newArray(int size) {
            return new SuggestionViewModel[size];
        }
    };
}
