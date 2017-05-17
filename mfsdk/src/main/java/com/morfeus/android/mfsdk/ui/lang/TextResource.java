package com.morfeus.android.mfsdk.ui.lang;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will keep the language specific resources
 */
public class TextResource implements Parcelable {
    private String lang;
    private HashMap<String, String> resource = new HashMap<>();

    public TextResource(String lang, HashMap<String, String> resource) {
        this.lang = lang;
        this.resource = resource;
    }

    protected TextResource(Parcel in) {
        lang = in.readString();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            resource.put(key, value);
        }
    }

    public static final Creator<TextResource> CREATOR = new Creator<TextResource>() {
        @Override
        public TextResource createFromParcel(Parcel in) {
            return new TextResource(in);
        }

        @Override
        public TextResource[] newArray(int size) {
            return new TextResource[size];
        }
    };

    public String getLang() {
        return lang;
    }

    public HashMap<String, String> getResource() {
        return resource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lang);
        parcel.writeInt(resource.size());
        for (Map.Entry<String, String> entry : resource.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }
}
