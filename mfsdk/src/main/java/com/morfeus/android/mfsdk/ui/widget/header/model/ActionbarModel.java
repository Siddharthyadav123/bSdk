package com.morfeus.android.mfsdk.ui.widget.header.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.morfeus.android.mfsdk.ui.widget.header.style.ActionbarStyle;
import com.morfeus.android.mfsdk.ui.widget.header.style.Style;

import java.util.ArrayList;
import java.util.List;

public class ActionbarModel implements Parcelable {
    public static final Parcelable.Creator<ActionbarModel> CREATOR = new Parcelable.Creator<ActionbarModel>() {
        @Override
        public ActionbarModel createFromParcel(Parcel source) {
            return new ActionbarModel(source);
        }

        @Override
        public ActionbarModel[] newArray(int size) {
            return new ActionbarModel[size];
        }
    };
    private String id;
    private ActionbarStyle style;
    private Profile profile;
    private List<Button> buttons;
    private List<Text> texts;

    public ActionbarModel() {
    }

    protected ActionbarModel(Parcel in) {
        this.id = in.readString();
        this.style = in.readParcelable(ActionbarStyle.class.getClassLoader());
        this.profile = in.readParcelable(Profile.class.getClassLoader());
        this.buttons = new ArrayList<Button>();
        in.readList(this.buttons, Button.class.getClassLoader());
        this.texts = new ArrayList<Text>();
        in.readList(this.texts, Text.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }

    public ActionbarStyle getStyle() {
        return style;
    }

    public void setStyle(ActionbarStyle style) {
        this.style = style;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.style, flags);
        dest.writeParcelable(this.profile, flags);
        dest.writeList(this.buttons);
        dest.writeList(this.texts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionbarModel)) return false;

        ActionbarModel that = (ActionbarModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;
        if (buttons != null ? !buttons.equals(that.buttons) : that.buttons != null) return false;
        return texts != null ? texts.equals(that.texts) : that.texts == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (buttons != null ? buttons.hashCode() : 0);
        result = 31 * result + (texts != null ? texts.hashCode() : 0);
        return result;
    }

    public static class Text implements Parcelable {
        public static final Creator<Text> CREATOR = new Creator<Text>() {
            @Override
            public Text createFromParcel(Parcel source) {
                return new Text(source);
            }

            @Override
            public Text[] newArray(int size) {
                return new Text[size];
            }
        };
        private String id;
        private Style style;
        private String label;

        public Text() {
        }

        protected Text(Parcel in) {
            this.id = in.readString();
            this.style = in.readParcelable(Style.class.getClassLoader());
            this.label = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Style getStyle() {
            return style;
        }

        public void setStyle(Style style) {
            this.style = style;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeParcelable(this.style, flags);
            dest.writeString(this.label);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Text)) return false;

            Text text = (Text) o;

            if (id != null ? !id.equals(text.id) : text.id != null) return false;
            if (style != null ? !style.equals(text.style) : text.style != null) return false;
            return label != null ? label.equals(text.label) : text.label == null;

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (style != null ? style.hashCode() : 0);
            result = 31 * result + (label != null ? label.hashCode() : 0);
            return result;
        }
    }

    public static class Profile implements Parcelable {
        public static final Creator<Profile> CREATOR = new Creator<Profile>() {
            @Override
            public Profile createFromParcel(Parcel source) {
                return new Profile(source);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };
        String image;

        public Profile() {
        }


        protected Profile(Parcel in) {
            this.image = in.readString();
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.image);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Profile)) return false;

            Profile profile = (Profile) o;

            return image != null ? image.equals(profile.image) : profile.image == null;

        }

        @Override
        public int hashCode() {
            return image != null ? image.hashCode() : 0;
        }
    }

    public static class Button implements Parcelable {
        public static final Creator<Button> CREATOR = new Creator<Button>() {
            @Override
            public Button createFromParcel(Parcel source) {
                return new Button(source);
            }

            @Override
            public Button[] newArray(int size) {
                return new Button[size];
            }
        };
        private String id;
        private String label;
        private String image;
        private String action;

        public Button() {
        }

        protected Button(Parcel in) {
            this.id = in.readString();
            this.label = in.readString();
            this.image = in.readString();
            this.action = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.label);
            dest.writeString(this.image);
            dest.writeString(this.action);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Button)) return false;

            Button button = (Button) o;

            if (id != null ? !id.equals(button.id) : button.id != null) return false;
            if (label != null ? !label.equals(button.label) : button.label != null) return false;
            if (image != null ? !image.equals(button.image) : button.image != null) return false;
            return action != null ? action.equals(button.action) : button.action == null;

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (label != null ? label.hashCode() : 0);
            result = 31 * result + (image != null ? image.hashCode() : 0);
            result = 31 * result + (action != null ? action.hashCode() : 0);
            return result;
        }
    }
}
