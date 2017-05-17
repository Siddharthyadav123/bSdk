package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class InputViewModel implements BaseModel {

    private String id;

    private Style style;

    private List<BaseModel> baseModels;

    public InputViewModel(String id, Style style, List<BaseModel> baseModels) {
        this.id = id;
        this.style = style;
        this.baseModels = baseModels;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setBaseModels(List<BaseModel> baseModels) {
        this.baseModels = baseModels;
    }

    public List<BaseModel> getBaseModels() {
        return baseModels;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
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
        dest.writeTypedList(this.baseModels);
    }

    protected InputViewModel(Parcel in) {
        this.id = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.baseModels = (ArrayList)in.createTypedArrayList(CREATOR);
    }

    public static final Creator<InputViewModel> CREATOR = new Creator<InputViewModel>() {
        @Override
        public InputViewModel createFromParcel(Parcel source) {
            return new InputViewModel(source);
        }

        @Override
        public InputViewModel[] newArray(int size) {
            return new InputViewModel[size];
        }
    };
}
