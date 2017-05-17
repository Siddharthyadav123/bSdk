package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

public class EditTextModel implements CompositeBaseModel {

    private String id;

    private Style style;

    private List<BaseModel> baseModels;

    private String placeholderText;

    public EditTextModel(String id, Style style,
                         List<BaseModel> baseModels,
                         String placeholderText) {
        this.id = id;
        this.style = style;
        this.baseModels = baseModels;
        this.placeholderText = placeholderText;
    }

    public List<BaseModel> getSubComponentModels() {
        return baseModels;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getPlaceholderText() {
        return placeholderText;
    }

    /**
     * {@link EditTextModel} don't have action. Action will be null.
     *
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
        dest.writeTypedList(this.baseModels);
        dest.writeString(this.placeholderText);
    }

    protected EditTextModel(Parcel in) {
        this.id = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.baseModels = (ArrayList) in.createTypedArrayList(CREATOR);
        this.placeholderText = in.readString();
    }

    public static final Creator<EditTextModel> CREATOR = new Creator<EditTextModel>() {
        @Override
        public EditTextModel createFromParcel(Parcel source) {
            return new EditTextModel(source);
        }

        @Override
        public EditTextModel[] newArray(int size) {
            return new EditTextModel[size];
        }
    };
}
