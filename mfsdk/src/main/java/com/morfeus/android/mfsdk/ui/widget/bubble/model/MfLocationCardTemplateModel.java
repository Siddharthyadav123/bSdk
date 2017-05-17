package com.morfeus.android.mfsdk.ui.widget.bubble.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

public class MfLocationCardTemplateModel extends TemplateModel {
    private String textMessage;
    private double latitude;
    private double longitude;

    public MfLocationCardTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getUrl() {
        String staticMapURL = "https://maps.googleapis.com/maps/api/staticmap?center="
                + latitude + "," + longitude + "&" + "zoom=11&scale=false&size=300x150" +
                "&maptype=roadmap&format=png&visual_refresh=true&" + "markers=size:" +
                "mid%7Ccolor:0xff0000%7Clabel:1%7C" + latitude + "," + longitude;
        return staticMapURL;
    }

    public String getImageName() {
        String mapImageName = "map_image_" + latitude + "_" + longitude;
        return mapImageName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }


}
