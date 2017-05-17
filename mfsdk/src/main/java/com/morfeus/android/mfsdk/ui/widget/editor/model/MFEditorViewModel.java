package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class MFEditorViewModel implements CompositeBaseModel, Parcelable {

    private String id;
    private List<BaseModel> subComponents;


    public MFEditorViewModel(String id, List<BaseModel> subComponents) {
        this.id = id;
        this.subComponents = subComponents;
    }

    public void setSubComponents(List<BaseModel> subComponents) {
        this.subComponents = subComponents;
    }

    @Override
    public List<BaseModel> getSubComponentModels() {
        return subComponents;
    }

    /**
     * MFEditorView don't have style
     *
     * @return null
     */
    @Override
    @Nullable
    public Style getStyle() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * MFEditorView don't have action
     *
     * @return null
     */
    @Override
    @Nullable
    public String getAction() {
        return null;
    }

    @Override
    public String toString() {
        return "MFEditorViewModel{" +
                "id='" + id + '\'' +
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
        dest.writeList(this.subComponents);

    }

    protected MFEditorViewModel(Parcel in) {
        this.id = in.readString();
        this.subComponents = new ArrayList<BaseModel>();
        in.readList(this.subComponents, BaseModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<MFEditorViewModel> CREATOR = new Parcelable.Creator<MFEditorViewModel>() {
        @Override
        public MFEditorViewModel createFromParcel(Parcel source) {
            return new MFEditorViewModel(source);
        }

        @Override
        public MFEditorViewModel[] newArray(int size) {
            return new MFEditorViewModel[size];
        }
    };
}
