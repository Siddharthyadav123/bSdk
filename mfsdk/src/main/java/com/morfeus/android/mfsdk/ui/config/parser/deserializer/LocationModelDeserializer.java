package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.LocationScreenStyle;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LocationModelDeserializer implements JsonDeserializer<LocationScreenModel> {
    @Override
    public LocationScreenModel deserialize(
            JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        String id = null;
        LocationScreenModel.Header header = null;
        LocationScreenModel.Body body = null;
        LocationScreenModel.Footer footer = null;

        JsonObject locationScreenJson = (JsonObject) json;

        if (locationScreenJson.has(ConfigParser.KEY_ID)) {
            id = locationScreenJson.get(ConfigParser.KEY_ID).getAsString();
        }
        if (locationScreenJson.has(ConfigParser.KEY_HEADER)) {
            header = parseHeader(locationScreenJson.get(ConfigParser.KEY_HEADER)
                    .getAsJsonObject());
        }
        if (locationScreenJson.has(ConfigParser.KEY_BODY)) {
            body = parseBody(locationScreenJson.get(ConfigParser.KEY_BODY).getAsJsonObject());
        }
        if (locationScreenJson.has(ConfigParser.KEY_FOOTER)) {
            footer = parseFooter(locationScreenJson.get(ConfigParser.KEY_FOOTER)
                    .getAsJsonObject());
        }
        LocationScreenModel locationScreenModel = new LocationScreenModel(id, header,
                body, footer);
        return locationScreenModel;
    }


    private LocationScreenStyle parseStyle(JsonObject styleJson) {
        String backgroundImage = null;
        String backgroundColor = null;
        String textColor = null;
        String borderColor = null;
        String horizontalAlignment = null;
        String pinImage = null;

        if (styleJson.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
            backgroundImage = styleJson.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
            backgroundColor = styleJson.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_TEXT_COLOR)) {
            textColor = styleJson.get(ConfigParser.KEY_TEXT_COLOR).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_BORDER_COLOR)) {
            borderColor = styleJson.get(ConfigParser.KEY_BORDER_COLOR).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_HORIZONTAL_ALIGNMENT)) {
            horizontalAlignment = styleJson.get(ConfigParser.KEY_HORIZONTAL_ALIGNMENT)
                    .getAsString();
        }
        if (styleJson.has(ConfigParser.KEY_PIN_IMAGE)) {
            pinImage = styleJson.get(ConfigParser.KEY_PIN_IMAGE).getAsString();
        }

        return new LocationScreenStyle.Builder()
                .setBackgroundColor(backgroundColor)
                .setTextColor(textColor)
                .setHorizontalAlignment(horizontalAlignment)
                .setBorderColor(borderColor)
                .setBackgroundImage(backgroundImage)
                .setPinImage(pinImage).build();
    }

    private LocationScreenModel.Body parseBody(JsonObject asJsonObject) {
        LocationScreenStyle style = null;
        LocationScreenModel.Map map = null;

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_MAP)) {
            JsonObject mapJson = asJsonObject.get(ConfigParser.KEY_MAP).getAsJsonObject();
            LocationScreenStyle mapStyle = null;
            String type = null;

            if (mapJson.has(ConfigParser.KEY_TYPE)) {
                type = mapJson.get(ConfigParser.KEY_TYPE).getAsString();
            }
            if (mapJson.has(ConfigParser.KEY_STYLE)) {
                JsonObject mapStyleJson = mapJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                mapStyle = parseStyle(mapStyleJson);
            }
            map = new LocationScreenModel.Map(mapStyle, type);
        }

        LocationScreenModel.Body body = new LocationScreenModel.Body(style, map);
        return body;
    }

    private LocationScreenModel.Header parseHeader(JsonObject asJsonObject) {
        LocationScreenStyle style = null;
        LocationScreenModel.ImageModel imageModel = null;
        LocationScreenModel.TextModel textModel = null;

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_LEFT_BUTTON)) {
            imageModel = parseImage(ConfigParser.KEY_LEFT_BUTTON, asJsonObject);
        }

        if (asJsonObject.has(ConfigParser.KEY_HEADER_TEXT)) {
            JsonObject headerTextJson = asJsonObject.get(ConfigParser.KEY_HEADER_TEXT)
                    .getAsJsonObject();
            LocationScreenStyle headerStyle = null;
            String text = null;

            if (headerTextJson.has(ConfigParser.KEY_TEXT)) {
                text = headerTextJson.get(ConfigParser.KEY_TEXT).getAsString();
                if (headerTextJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = headerTextJson.get(ConfigParser.KEY_STYLE)
                            .getAsJsonObject();
                    headerStyle = parseStyle(styleJson);
                }
            }
            textModel = new LocationScreenModel.TextModel(text, headerStyle);
        }
        LocationScreenModel.Header header = new LocationScreenModel.Header(style,
                imageModel, textModel);
        return header;
    }

    private LocationScreenModel.ImageModel parseImage(String imageKey, JsonObject asJsonObject) {
        LocationScreenModel.ImageModel imageModel = null;
        if (asJsonObject.has(imageKey)) {
            JsonObject imageJson = asJsonObject.get(imageKey).getAsJsonObject();
            String label = null;
            String image = null;
            String action = null;

            if (imageJson.has(ConfigParser.KEY_LABEL)) {
                label = imageJson.get(ConfigParser.KEY_LABEL).getAsString();
            }

            if (imageJson.has(ConfigParser.KEY_IMAGE)) {
                image = imageJson.get(ConfigParser.KEY_IMAGE).getAsString();
            }

            if (imageJson.has(ConfigParser.KEY_ACTION)) {
                action = imageJson.get(ConfigParser.KEY_ACTION).getAsString();
            }
            imageModel = new LocationScreenModel.ImageModel();
            imageModel.setLabel(label);
            imageModel.setImage(image);
            imageModel.setAction(action);
        }

        return imageModel;
    }

    private LocationScreenModel.Footer parseFooter(JsonObject asJsonObject) {
        LocationScreenStyle style = null;
        HashMap<String, LocationScreenModel.FooterStrip> fotterStripMap = new HashMap<>();

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_SEND_LOCATION)) {
            LocationScreenModel.FooterStrip sendLocationFotterStrip
                    = parseFotterStrip(ConfigParser.KEY_SEND_LOCATION, asJsonObject);
            if (sendLocationFotterStrip != null) {
                fotterStripMap.put(ConfigParser.KEY_SEND_LOCATION, sendLocationFotterStrip);
            }
        }

        if (asJsonObject.has(ConfigParser.KEY_SHOW_LOCATION)) {
            LocationScreenModel.FooterStrip showLocationFotterStrip
                    = parseFotterStrip(ConfigParser.KEY_SHOW_LOCATION, asJsonObject);
            if (showLocationFotterStrip != null) {
                fotterStripMap.put(ConfigParser.KEY_SHOW_LOCATION, showLocationFotterStrip);
            }
        }

        LocationScreenModel.Footer footer = new LocationScreenModel.Footer(style, fotterStripMap);
        return footer;
    }

    private LocationScreenModel.FooterStrip parseFotterStrip(String key,
                                                             JsonObject asJsonObject) {
        LocationScreenModel.ImageModel leftImageModel = null;
        LocationScreenModel.ImageModel rightImageModel = null;
        LocationScreenModel.TextModel textModel = null;

        if (asJsonObject.has(key)) {
            JsonObject stripJson = asJsonObject.get(key).getAsJsonObject();
            leftImageModel = parseImage(ConfigParser.KEY_LEFT_BUTTON, stripJson);
            rightImageModel = parseImage(ConfigParser.KEY_RIGHT_BUTTON, stripJson);

            if (stripJson.has(ConfigParser.CardStyleKey.KEY_TITLE)) {
                JsonObject headerTextJson = stripJson.get(ConfigParser.CardStyleKey.KEY_TITLE)
                        .getAsJsonObject();
                LocationScreenStyle headerStyle = null;
                String text = null;

                if (headerTextJson.has(ConfigParser.KEY_TEXT)) {
                    text = headerTextJson.get(ConfigParser.KEY_TEXT).getAsString();
                    if (headerTextJson.has(ConfigParser.KEY_STYLE)) {
                        JsonObject styleJson = headerTextJson.get(ConfigParser.KEY_STYLE)
                                .getAsJsonObject();
                        headerStyle = parseStyle(styleJson);
                    }
                }
                textModel = new LocationScreenModel.TextModel(text, headerStyle);
            }
        }

        LocationScreenModel.FooterStrip footerStrip =
                new LocationScreenModel.FooterStrip(leftImageModel, rightImageModel, textModel);
        return footerStrip;
    }


}
