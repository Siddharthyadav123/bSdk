package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.PinpadModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.TextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import java.lang.reflect.Type;
import java.util.HashMap;

public class OTPModelDeserializer implements JsonDeserializer<OTPModel> {
    @Override
    public OTPModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String id = null;
        OTPModel.Header header = null;
        OTPModel.Body body = null;
        OTPModel.Footer footer = null;

        JsonObject otpScreenJson = (JsonObject) json;

        if (otpScreenJson.has(ConfigParser.KEY_ID)) {
            id = otpScreenJson.get(ConfigParser.KEY_ID).getAsString();
        }
        if (otpScreenJson.has(ConfigParser.KEY_HEADER)) {
            header = parseHeader(otpScreenJson.get(ConfigParser.KEY_HEADER).getAsJsonObject());
        }
        if (otpScreenJson.has(ConfigParser.KEY_BODY)) {
            body = parseBody(otpScreenJson.get(ConfigParser.KEY_BODY).getAsJsonObject());
        }
        if (otpScreenJson.has(ConfigParser.KEY_FOOTER)) {
            footer = parseFooter(otpScreenJson.get(ConfigParser.KEY_FOOTER).getAsJsonObject());
        }
        OTPModel loginModel = new OTPModel(id, header, body, footer);
        return loginModel;
    }


    private MfDialogStyle parseStyle(JsonObject styleJson) {
        String backgroundImage = null;
        String backgroundColor = null;
        String textColor = null;
        String borderColor = null;
        String horizontalAlignment = null;
        int height = 0;
        String maskImage = null;
        String unmaskImage = null;


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

        if (styleJson.has(ConfigParser.KEY_MASK_IMAGE)) {
            maskImage = styleJson.get(ConfigParser.KEY_MASK_IMAGE).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_UNMASK_IMAGE)) {
            unmaskImage = styleJson.get(ConfigParser.KEY_UNMASK_IMAGE).getAsString();
        }

        if (styleJson.has(ConfigParser.KEY_HEIGHT)) {
            try {
                height = Integer.parseInt(styleJson.get(ConfigParser.KEY_HEIGHT).getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new MfDialogStyle.Builder().setBackgroundColor(backgroundColor).setTextColor(textColor)
                .setHorizontalAlignment(horizontalAlignment).setBorderColor(borderColor)
                .setHeight(height).setMaskImage(maskImage).setUnmaskImage(unmaskImage)
                .setBackgroundImage(backgroundImage).build();
    }

    private OTPModel.Body parseBody(JsonObject asJsonObject) {
        MfDialogStyle style = null;
        TextModel subheaderText = null;
        PinpadModel pinpadModel = null;

        if (asJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = asJsonObject.get(ConfigParser.KEY_STYLE).getAsJsonObject();
            style = parseStyle(styleJson);
        }

        if (asJsonObject.has(ConfigParser.KEY_SUB_HEADER_TEXT)) {
            JsonObject headerTextJson = asJsonObject.get(ConfigParser.KEY_SUB_HEADER_TEXT).getAsJsonObject();
            MfDialogStyle headerStyle = null;
            String text = null;

            if (headerTextJson.has(ConfigParser.KEY_TEXT)) {
                text = headerTextJson.get(ConfigParser.KEY_TEXT).getAsString();

                if (headerTextJson.has(ConfigParser.KEY_STYLE)) {
                    JsonObject styleJson = headerTextJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                    headerStyle = parseStyle(styleJson);
                }
            }
            subheaderText = new TextModel(text, headerStyle);
        }

        if (asJsonObject.has(ConfigParser.KEY_PINPAD)) {
            JsonObject pindPadJson = asJsonObject.get(ConfigParser.KEY_PINPAD).getAsJsonObject();
            int numberOfCharater = 0;
            MfDialogStyle pinpadStyle = null;
            if (pindPadJson.has(ConfigParser.KEY_NUMBER_OF_CHARACTER)) {
                try {
                    numberOfCharater = Integer.parseInt(pindPadJson.get(ConfigParser.KEY_NUMBER_OF_CHARACTER).getAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (pindPadJson.has(ConfigParser.KEY_STYLE)) {
                JsonObject styleJson = pindPadJson.get(ConfigParser.KEY_STYLE).getAsJsonObject();
                pinpadStyle = parseStyle(styleJson);
            }
            pinpadModel = new PinpadModel(numberOfCharater, pinpadStyle);
        }


        OTPModel.Body body = new OTPModel.Body(style, subheaderText, pinpadModel);
        return body;
    }

    private OTPModel.Header parseHeader(JsonObject asJsonObject) {
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


        OTPModel.Header header = new OTPModel.Header(style, textModel);
        return header;
    }

    private OTPModel.Footer parseFooter(JsonObject asJsonObject) {
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
        OTPModel.Footer footer = new OTPModel.Footer(style, mapButtonModel);
        return footer;
    }
}
