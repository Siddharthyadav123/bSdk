package com.morfeus.android.mfsdk.ui.widget.loading.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.morfeus.android.mfsdk.ui.widget.loading.style.Style;


public class LoadingModel implements Parcelable {
    public static final Creator<LoadingModel> CREATOR = new Creator<LoadingModel>() {
        @Override
        public LoadingModel createFromParcel(Parcel in) {
            return new LoadingModel(in);
        }

        @Override
        public LoadingModel[] newArray(int size) {
            return new LoadingModel[size];
        }
    };

    private Logo logo;
    private LoadingText loadingText;
    private ProgressView progressView;

    public LoadingModel() {
    }


    protected LoadingModel(Parcel in) {
        logo = in.readParcelable(Logo.class.getClassLoader());
        loadingText = in.readParcelable(LoadingText.class.getClassLoader());
        progressView = in.readParcelable(ProgressView.class.getClassLoader());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(logo, i);
        parcel.writeParcelable(loadingText, i);
        parcel.writeParcelable(progressView, i);
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public LoadingText getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(LoadingText loadingText) {
        this.loadingText = loadingText;
    }

    public ProgressView getProgressView() {
        return progressView;
    }

    public void setProgressView(ProgressView progressView) {
        this.progressView = progressView;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadingModel)) return false;

        LoadingModel that = (LoadingModel) o;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (loadingText != null ? !loadingText.equals(that.loadingText) : that.loadingText != null)
            return false;
        return progressView != null ? progressView.equals(that.progressView) : that.progressView == null;
    }

    @Override
    public int hashCode() {
        int result = logo != null ? logo.hashCode() : 0;
        result = 31 * result + (loadingText != null ? loadingText.hashCode() : 0);
        result = 31 * result + (progressView != null ? progressView.hashCode() : 0);
        return result;
    }


    public static class Logo implements Parcelable {
        public static final Creator<Logo> CREATOR = new Creator<Logo>() {
            @Override
            public Logo createFromParcel(Parcel in) {
                return new Logo(in);
            }

            @Override
            public Logo[] newArray(int size) {
                return new Logo[size];
            }
        };

        private String imageName;


        public Logo() {
        }

        protected Logo(Parcel in) {
            imageName = in.readString();
        }


        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(imageName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Logo)) return false;
            Logo logo = (Logo) o;
            return imageName != null ? imageName.equals(logo.imageName) : logo.imageName == null;

        }

        @Override
        public int hashCode() {
            int result = imageName != null ? imageName.hashCode() : 0;
            return result;
        }
    }

    public static class LoadingText implements Parcelable {
        public static final Creator<LoadingText> CREATOR = new Creator<LoadingText>() {
            @Override
            public LoadingText createFromParcel(Parcel in) {
                return new LoadingText(in);
            }

            @Override
            public LoadingText[] newArray(int size) {
                return new LoadingText[size];
            }
        };
        private String label;
        private Style style;

        public LoadingText() {
        }

        protected LoadingText(Parcel in) {
            label = in.readString();
            style = in.readParcelable(Style.class.getClassLoader());
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(label);
            parcel.writeParcelable(style, i);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LoadingText)) return false;
            LoadingText loadingText = (LoadingText) o;
            if (style != null ? !style.equals(loadingText.style) : loadingText.style != null)
                return false;
            return loadingText != null ? loadingText.equals(loadingText.label) : loadingText.label == null;

        }

        @Override
        public int hashCode() {
            int result = label != null ? label.hashCode() : 0;
            result = 31 * result + (style != null ? style.hashCode() : 0);
            return result;
        }
    }

    public static class ProgressView implements Parcelable {
        public static final Creator<ProgressView> CREATOR = new Creator<ProgressView>() {
            @Override
            public ProgressView createFromParcel(Parcel in) {
                return new ProgressView(in);
            }

            @Override
            public ProgressView[] newArray(int size) {
                return new ProgressView[size];
            }
        };


        private String color;

        public ProgressView() {
        }

        protected ProgressView(Parcel in) {
            color = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(color);
        }

        @Override
        public int describeContents() {
            return 0;
        }


        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProgressView)) return false;
            ProgressView progressView = (ProgressView) o;
            return progressView != null ? progressView.equals(progressView.color) : progressView.color == null;

        }

        @Override
        public int hashCode() {
            int result = color != null ? color.hashCode() : 0;
            return result;
        }
    }
}
