package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ImageModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.TextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginModelDeserializer implements JsonDeserializer<LoginModel> {
    @Override
    public LoginModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String id = null;
        LoginModel.Header header = null;
        LoginModel.Body body = null;
        LoginModel.Footer footer = null;

        JsonObject loginScreenJson = (JsonObject) json;

        if (loginScreenJson.has(ConfigParser.KEY_ID)) {
            id = loginScreenJson.get(ConfigParser.KEY_ID).getAsString();
        }
        if (loginScreenJson.has(ConfigParser.KEY_HEADER)) {
            header = parseHeader(loginScreenJson.get(ConfigParser.KEY_HEADER).getAsJsonObject());
        }
        if (loginScreenJson.has(ConfigParser.KEY_BODY)) {
            body = parseBody(loginScreenJson.get(ConfigParser.KEY_BODY).getAsJsonObject());
        }
        if (loginScreenJson.has(ConfigParser.KEY_FOOTER)) {
            footer = parseFooter(loginScreenJson.get(ConfigParser.KEY_FOOTER).getAsJsonObject());
        }
        LoginModel loginModel = new LoginModel(id, header, body, footer);
        return loginModel;
    }


    private MfDialogStyle parseStyle(JsonObject styleJson) {
        String backgroundImage = null;
        String backgroundColor = null;
        String textColor = null;
        String borderColor = null;
        String horizontalAlignment = null;

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
            horizontalAlignment = styleJson.get(ConfigParser.KEY_HORIZONTAL_ALIGNMENT).getAsString();
        }

        return new MfDialogStyle.Builder().setBackgroundColor(backgroundColor).setTextColor(textColor)
                .setHorizontalAlignment(horizontalAlignment).setBorderColor(borderColor)
                .setBackgroundImage(backgroundImage).build();
    }

    private LoginModel.Body parseBody(JsonObject asJsonObject) {
        MfDialogStyle style = null;
        ImageModel imageModel = null;
        HashMap<String, EditTextModel> mapEditTextModel = new HashMap<>();

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_IMAGE)) {
            JsonObject imageJson = asJsonObject.get(ConfigParser.KEY_IMAGE).getAsJsonObject();
            String type = null;
            String name = null;
            String url = null;
            String imageType = null;

            if (imageJson.has(ConfigParser.KEY_TYPE)) {
                type = imageJson.get(ConfigParser.KEY_TYPE).getAsString();
            }

            if (imageJson.has(ConfigParser.KEY_IMAGE_TYPE)) {
                imageType = imageJson.get(ConfigParser.KEY_IMAGE_TYPE).getAsString();
            }

            if (imageJson.has(ConfigParser.KEY_IMAGE_NAME)) {
                name = imageJson.get(ConfigParser.KEY_IMAGE_NAME).getAsString();
            }

            if (imageJson.has(ConfigParser.KEY_IMAGE_URL)) {
                url = imageJson.get(ConfigParser.KEY_IMAGE_URL).getAsString();
            }
            imageModel = new ImageModel();
            imageModel.setType(type);
            imageModel.setImageType(imageType);
            imageModel.setName(name);
            imageModel.setUrl(url);
        }

        if (asJsonObject.has(ConfigParser.KEY_USER_NAME)) {
            JsonObject editTextJson = asJsonObject.get(ConfigParser.KEY_USER_NAME).getAsJsonObject();
            String placeHolderText = null;
            MfDialogStyle userNameStyle = null;

            if (editTextJson.has(ConfigParser.KEY_PLACEHOLDER_TEXT)) {
                placeHolderText = editTextJson.get(ConfigParser.KEY_PLACEHOLDER_TEXT).getAsString();

                if (editTextJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = editTextJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    userNameStyle = parseStyle(styleJson);
                }
            }
            EditTextModel editTextModel = new EditTextModel(placeHolderText, userNameStyle);
            mapEditTextModel.put(ConfigParser.KEY_USER_NAME, editTextModel);
        }

        if (asJsonObject.has(ConfigParser.KEY_PASSWORD)) {
            JsonObject editTextJson = asJsonObject.get(ConfigParser.KEY_PASSWORD).getAsJsonObject();
            String placeHolderText = null;
            MfDialogStyle userNameStyle = null;

            if (editTextJson.has(ConfigParser.KEY_PLACEHOLDER_TEXT)) {
                placeHolderText = editTextJson.get(ConfigParser.KEY_PLACEHOLDER_TEXT).getAsString();

                if (editTextJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = editTextJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    userNameStyle = parseStyle(styleJson);
                }
            }
            EditTextModel editTextModel = new EditTextModel(placeHolderText, userNameStyle);
            mapEditTextModel.put(ConfigParser.KEY_PASSWORD, editTextModel);
        }

        LoginModel.Body body = new LoginModel.Body(style, imageModel, mapEditTextModel);
        return body;
    }

    private LoginModel.Header parseHeader(JsonObject asJsonObject) {
        MfDialogStyle style = null;
        TextModel textModel = null;

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_HEADER_TEXT)) {
            JsonObject headerTextJson = asJsonObject.get(ConfigParser.KEY_HEADER_TEXT).getAsJsonObject();
            MfDialogStyle headerStyle = null;
            String text = null;

            if (headerTextJson.has(ConfigParser.KEY_TEXT)) {
                text = headerTextJson.get(ConfigParser.KEY_TEXT).getAsString();

                if (headerTextJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = headerTextJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    headerStyle = parseStyle(styleJson);
                }
            }
            textModel = new TextModel(text, headerStyle);
        }

        LoginModel.Header header = new LoginModel.Header(style, textModel);
        return header;
    }

    private LoginModel.Footer parseFooter(JsonObject asJsonObject) {
        MfDialogStyle style = null;
        HashMap<String, ButtonModel> mapButtonModel = new HashMap<>();

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_SEND_BUTTON_B_SMALL)) {
            JsonObject sendBtnJson = asJsonObject.get(ConfigParser.KEY_SEND_BUTTON_B_SMALL).getAsJsonObject();
            MfDialogStyle btnStyle = null;
            String text = null;

            if (sendBtnJson.has(ConfigParser.KEY_TEXT)) {
                text = sendBtnJson.get(ConfigParser.KEY_TEXT).getAsString();

                if (sendBtnJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = sendBtnJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    btnStyle = parseStyle(styleJson);
                }
            }
            ButtonModel buttonModel = new ButtonModel(btnStyle, text);
            mapButtonModel.put(ConfigParser.KEY_SEND_BUTTON_B_SMALL, buttonModel);
        }


        if (asJsonObject.has(ConfigParser.KEY_CANCEL_BUTTON)) {
            JsonObject cancelBtnJson = asJsonObject.get(ConfigParser.KEY_CANCEL_BUTTON).getAsJsonObject();
            MfDialogStyle btnStyle = null;
            String text = null;

            if (cancelBtnJson.has(ConfigParser.KEY_TEXT)) {
                text = cancelBtnJson.get(ConfigParser.KEY_TEXT).getAsString();

                if (cancelBtnJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = cancelBtnJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    btnStyle = parseStyle(styleJson);
                }
            }
            ButtonModel buttonModel = new ButtonModel(btnStyle, text);
            mapButtonModel.put(ConfigParser.KEY_CANCEL_BUTTON, buttonModel);
        }
        LoginModel.Footer footer = new LoginModel.Footer(style, mapButtonModel);
        return footer;
    }


}
