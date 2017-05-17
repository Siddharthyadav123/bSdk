package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class SubViewModel implements CompositeBaseModel {

    private String id;

    private String action = null; // SubView container don't have action

    private Style style;

    private List<BaseModel> subComponents;

    public SubViewModel(String id, Style style, List<BaseModel> subComponents) {
        this.id = id;
        this.style = style;
        this.subComponents = subComponents;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setSubComponentModels(List<BaseModel> subComponents) {
        this.subComponents = subComponents;
    }

    @Override
    public List<BaseModel> getSubComponentModels() {
        return subComponents;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override @Nullable
    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "SubViewModel{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", style=" + style +
                ", subComponents=" + subComponents +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.action);
        dest.writeParcelable(this.style, flags);
        dest.writeTypedList(this.subComponents);
    }

    protected SubViewModel(Parcel in) {
        this.id = in.readString();
        this.action = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.subComponents = (ArrayList)in.createTypedArrayList(CREATOR);
    }

    public static final Creator<SubViewModel> CREATOR = new Creator<SubViewModel>() {
        @Override
        public SubViewModel createFromParcel(Parcel source) {
            return new SubViewModel(source);
        }

        @Override
        public SubViewModel[] newArray(int size) {
            return new SubViewModel[size];
        }
    };
}
