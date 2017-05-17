package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcel;
import android.support.annotation.DrawableRes;

import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImageButtonModel implements BaseModel {

    private List<Content> contents = null;

    private String id;

    private String image;

    private String imageSel;

    private String action;

    @DrawableRes
    private int drawableImage;

    public ImageButtonModel(String id) {
        this.id = checkNotNull(id);
    }

    @Override
    public Style getStyle() {
        return null;
    }

    public String getImageSel() {
        return imageSel;
    }

    public void setImageSel(String imageSel) {
        this.imageSel = imageSel;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    @Component.Action
    public String getAction() {
        return null;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public static class Content implements android.os.Parcelable {
        String label;
        String imagePath;
        @DrawableRes
        int drawableImage;
        String action;

        public Content(String label, String imagePath, int drawableImage, String action) {
            this.label = label;
            this.imagePath = imagePath;
            this.drawableImage = drawableImage;
            this.action = action;
        }

        public String getLabel() {
            return label;
        }

        public String getImagePath() {
            return imagePath;
        }

        public int getDrawableImage() {
            return drawableImage;
        }

        public String getAction() {
            return action;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.label);
            dest.writeString(this.imagePath);
            dest.writeInt(this.drawableImage);
            dest.writeString(this.action);
        }

        protected Content(Parcel in) {
            this.label = in.readString();
            this.imagePath = in.readString();
            this.drawableImage = in.readInt();
            this.action = in.readString();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageButtonModel)) return false;

        ImageButtonModel that = (ImageButtonModel) o;

        if (drawableImage != that.drawableImage) return false;
        if (!contents.equals(that.contents)) return false;
        if (!id.equals(that.id)) return false;
        if (!image.equals(that.image)) return false;
        if (!imageSel.equals(that.imageSel)) return false;
        return action.equals(that.action);

    }

    @Override
    public int hashCode() {
        int result = contents.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + imageSel.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + drawableImage;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.contents);
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeString(this.imageSel);
        dest.writeString(this.action);
        dest.writeInt(this.drawableImage);
    }

    protected ImageButtonModel(Parcel in) {
        this.contents = in.createTypedArrayList(Content.CREATOR);
        this.id = in.readString();
        this.image = in.readString();
        this.imageSel = in.readString();
        this.action = in.readString();
        this.drawableImage = in.readInt();
    }

    public static final Creator<ImageButtonModel> CREATOR = new Creator<ImageButtonModel>() {
        @Override
        public ImageButtonModel createFromParcel(Parcel source) {
            return new ImageButtonModel(source);
        }

        @Override
        public ImageButtonModel[] newArray(int size) {
            return new ImageButtonModel[size];
        }
    };
}
