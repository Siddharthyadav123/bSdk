package com.morfeus.android.mfsdk.ui.screen.chat;

import android.os.Parcel;
import android.os.Parcelable;

import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;

public class ChatScreenModel implements Parcelable {
    public static final Parcelable.Creator<ChatScreenModel> CREATOR = new Parcelable.Creator<ChatScreenModel>() {
        @Override
        public ChatScreenModel createFromParcel(Parcel source) {
            return new ChatScreenModel(source);
        }

        @Override
        public ChatScreenModel[] newArray(int size) {
            return new ChatScreenModel[size];
        }
    };
    private String id;
    private String cardUIType;
    private String screenUIType;
    private Body body;
    // header
    private ActionbarModel actionbarModel;
    // footer
    private MFEditorViewModel mfEditorViewModel;
    //loading
    private LoadingModel loadingModel;

    public ChatScreenModel(String id) {
        this.id = id;
    }

    protected ChatScreenModel(Parcel in) {
        this.id = in.readString();
        this.cardUIType = in.readString();
        this.screenUIType = in.readString();
        this.body = in.readParcelable(Body.class.getClassLoader());
        this.actionbarModel = in.readParcelable(ActionbarModel.class.getClassLoader());
        this.mfEditorViewModel = in.readParcelable(MFEditorViewModel.class.getClassLoader());
        this.loadingModel = in.readParcelable(LoadingModel.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardUIType() {
        return cardUIType;
    }

    public void setCardUIType(String cardUIType) {
        this.cardUIType = cardUIType;
    }

    public String getScreenUIType() {
        return screenUIType;
    }

    public void setScreenUIType(String screenUIType) {
        this.screenUIType = screenUIType;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public LoadingModel getLoadingModel() {
        return loadingModel;
    }

    public void setLoadingModel(LoadingModel loadingModel) {
        this.loadingModel = loadingModel;
    }

    public ActionbarModel getActionbarModel() {
        return actionbarModel;
    }

    public void setActionbarModel(ActionbarModel actionbarModel) {
        this.actionbarModel = actionbarModel;
    }

    public MFEditorViewModel getMfEditorViewModel() {
        return mfEditorViewModel;
    }

    public void setMfEditorViewModel(MFEditorViewModel mfEditorViewModel) {
        this.mfEditorViewModel = mfEditorViewModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cardUIType);
        dest.writeString(this.screenUIType);
        dest.writeParcelable(this.body, flags);
        dest.writeParcelable(this.actionbarModel, flags);
        dest.writeParcelable(this.mfEditorViewModel, flags);
        dest.writeParcelable(this.loadingModel, flags);
    }

    public static class Body implements Parcelable {

        public static final Creator<Body> CREATOR = new Creator<Body>() {
            @Override
            public Body createFromParcel(Parcel source) {
                return new Body(source);
            }

            @Override
            public Body[] newArray(int size) {
                return new Body[size];
            }
        };
        private Style style;

        public Body() {
        }

        protected Body(Parcel in) {
            this.style = in.readParcelable(Style.class.getClassLoader());
        }

        public Style getStyle() {
            return style;
        }

        public void setStyle(Style style) {
            this.style = style;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.style, flags);
        }

        public static class Style implements Parcelable {
            public static final Creator<Style> CREATOR = new Creator<Style>() {
                @Override
                public Style createFromParcel(Parcel source) {
                    return new Style(source);
                }

                @Override
                public Style[] newArray(int size) {
                    return new Style[size];
                }
            };
            private String backgroundImage;
            private String backgroundColor;

            public Style() {
            }

            protected Style(Parcel in) {
                this.backgroundImage = in.readString();
                this.backgroundColor = in.readString();
            }

            public String getBackgroundImage() {
                return backgroundImage;
            }

            public void setBackgroundImage(String backgroundImage) {
                this.backgroundImage = backgroundImage;
            }

            public String getBackgroundColor() {
                return backgroundColor;
            }

            public void setBackgroundColor(String backgroundColor) {
                this.backgroundColor = backgroundColor;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.backgroundImage);
                dest.writeString(this.backgroundColor);
            }
        }
    }
}
