package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutModel implements BaseModel {

    private String id;

    private String type;

    private Style style;

    private List<BaseModel> baseModels;

    public TabLayoutModel(String id, String type, Style style, List<BaseModel> baseModels) {
        this.id = id;
        this.type = type;
        this.style = style;
        this.baseModels = baseModels;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<BaseModel> getBaseModels() {
        return baseModels;
    }

    public void setBaseModels(List<BaseModel> baseModels) {
        this.baseModels = baseModels;
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
        return null; // Toolbar view is container which can't have action
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeParcelable(this.style, flags);
        dest.writeTypedList(this.baseModels);
    }

    protected TabLayoutModel(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.baseModels = (ArrayList)in.createTypedArrayList(CREATOR);
    }

    public static final Creator<TabLayoutModel> CREATOR = new Creator<TabLayoutModel>() {
        @Override
        public TabLayoutModel createFromParcel(Parcel source) {
            return new TabLayoutModel(source);
        }

        @Override
        public TabLayoutModel[] newArray(int size) {
            return new TabLayoutModel[size];
        }
    };
}
