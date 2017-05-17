package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.List;

public class SubViewItemModel implements BaseModel {

    private String id;

    private Style style = null; // SubViewComponent don't have style

    private String action = null; // SubViewComponent don't have action

    private List<Content> contents;

    private List<SmileySubViewModel> smileySubViewModelList; //for smiley subview

    public SubViewItemModel(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void setSmileySubViewModelList(List<SmileySubViewModel> smileySubViewModelList) {
        this.smileySubViewModelList = smileySubViewModelList;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<SmileySubViewModel> getSmileySubViewModelList() {
        return smileySubViewModelList;
    }

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
    @Nullable
    public String getAction() {
        return action;
    }

    public static class SmileySubViewModel implements android.os.Parcelable {
        private String image;
        private List<Content> contents;

        public SmileySubViewModel(String image, List<Content> contents) {
            this.image = image;
            this.contents = contents;
        }

        protected SmileySubViewModel(Parcel in) {
            image = in.readString();
            contents = in.createTypedArrayList(Content.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image);
            dest.writeTypedList(contents);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SmileySubViewModel> CREATOR = new Creator<SmileySubViewModel>() {
            @Override
            public SmileySubViewModel createFromParcel(Parcel in) {
                return new SmileySubViewModel(in);
            }

            @Override
            public SmileySubViewModel[] newArray(int size) {
                return new SmileySubViewModel[size];
            }
        };

        public String getImage() {
            return image;
        }

        public List<Content> getContents() {
            return contents;
        }

    }

    public static class Content implements android.os.Parcelable {
        private String image;
        private String label;
        private String action;
        private String payload;

        public Content(String image, String label, String action, String payload) {
            this.image = image;
            this.label = label;
            this.action = action;
            this.payload = payload;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getAction() {
            return action;
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

        @Override
        public String toString() {
            return "Content{" +
                    "image='" + image + '\'' +
                    ", label='" + label + '\'' +
                    ", action='" + action + '\'' +
                    ", payload='" + payload + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.image);
            dest.writeString(this.label);
            dest.writeString(this.action);
            dest.writeString(this.payload);
        }

        protected Content(Parcel in) {
            this.image = in.readString();
            this.label = in.readString();
            this.action = in.readString();
            this.payload = in.readString();
        }

        public static final Creator<Content> CREATOR = new Creator<Content>() {
            @Override
            public Content createFromParcel(Parcel source) {
                return new Content(source);
            }

            @Override
            public Content[] newArray(int size) {
                return new Content[size];
            }
        };
    }

    @Override
    public String toString() {
        return "SubViewItemModel{" +
                "id='" + id + '\'' +
                ", style=" + style +
                ", action='" + action + '\'' +
                ", contents=" + contents +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.style, flags);
        dest.writeString(this.action);
        dest.writeTypedList(this.contents);
        dest.writeTypedList(this.smileySubViewModelList);
    }

    protected SubViewItemModel(Parcel in) {
        this.id = in.readString();
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.action = in.readString();
        this.contents = in.createTypedArrayList(Content.CREATOR);
        this.smileySubViewModelList = in.createTypedArrayList(SmileySubViewModel.CREATOR);
    }

    public static final Creator<SubViewItemModel> CREATOR = new Creator<SubViewItemModel>() {
        @Override
        public SubViewItemModel createFromParcel(Parcel source) {
            return new SubViewItemModel(source);
        }

        @Override
        public SubViewItemModel[] newArray(int size) {
            return new SubViewItemModel[size];
        }
    };
}
