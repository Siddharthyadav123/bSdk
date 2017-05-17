package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.EditTextStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.InputViewStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InputViewModelDeserializer implements JsonDeserializer<InputViewModel> {

    @Override
    public InputViewModel deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context)
            throws JsonParseException {

        Gson gson = new Gson();

        //input view members
        String id = null;
        InputViewStyle style = null;
        List<BaseModel> baseModels = new ArrayList<>();

        JsonObject inputViewJsonObject = (JsonObject) json;
        //parsing id
        if (inputViewJsonObject.has(ConfigParser.KEY_ID)) {
            id = inputViewJsonObject.get(ConfigParser.KEY_ID).getAsString();
        }

        //parsing style
        if (inputViewJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            style = new InputViewStyle();

            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
                style.setBackgroundImage(styleJSON.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString());
            }
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                style.setBackgroundColor(styleJSON.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString());
            }
        }

        //parsing text input json
        if (inputViewJsonObject.has(ConfigParser.KEY_TEXT_INPUT)) {
            EditTextModel inputText = deserializeInputText(inputViewJsonObject);
            baseModels.add(inputText);
        }

        //parsing send button json
        if (inputViewJsonObject.has(ConfigParser.KEY_SEND_BUTTON)) {
            ImageButtonModel sendButton = deserializeImageButtonModel(ConfigParser.KEY_SEND_BUTTON,
                    inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_SEND_BUTTON));
            baseModels.add(sendButton);
        }

        return new InputViewModel(id, style, baseModels);
    }

    /**
     * method to parse input text
     *
     * @param inputViewJsonObject
     * @return
     */
    private EditTextModel deserializeInputText(JsonObject inputViewJsonObject) {
        JsonObject textInputJsonObject = inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_TEXT_INPUT);

        //text input members
        String placeholderText = null;
        String textInputId = ConfigParser.KEY_TEXT_INPUT;
        EditTextStyle textInputStyle = null;
        List<BaseModel> textInputModels = new ArrayList<>();

        if (textInputJsonObject.has(ConfigParser.KEY_PLACEHOLDER_HIFEN_TEXT)) {
            placeholderText = textInputJsonObject.get(ConfigParser.KEY_PLACEHOLDER_HIFEN_TEXT).getAsString();
        }

        //parsing text input style
        if (textInputJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJson = textInputJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);

            textInputStyle = new EditTextStyle();
            if (styleJson.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
                textInputStyle.setBackgroundImage(styleJson.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString());
            }
            if (styleJson.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                textInputStyle.setBackgroundColor(styleJson.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString());
            }
        }

        //parsing left button from parent json and will put into the text-input model
        if (inputViewJsonObject.has(ConfigParser.KEY_LEFT_BUTTON)) {
            ImageButtonModel leftButtonModel = deserializeImageButtonModel(ConfigParser.KEY_LEFT_BUTTON,
                    inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_LEFT_BUTTON));
            textInputModels.add(leftButtonModel);
        }

        //parsing right button1 from parent json and will put into the text-input model
        if (inputViewJsonObject.has(ConfigParser.KEY_RIGHT_BUTTON1)) {
            ImageButtonModel rightButton1Model = deserializeImageButtonModel(ConfigParser.KEY_RIGHT_BUTTON1,
                    inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_RIGHT_BUTTON1));
            textInputModels.add(rightButton1Model);
        }

        //parsing right button2 from parent json and will put into the text-input model
        if (inputViewJsonObject.has(ConfigParser.KEY_RIGHT_BUTTON2)) {
            ImageButtonModel rightButton2Model = deserializeImageButtonModel(ConfigParser.KEY_RIGHT_BUTTON2,
                    inputViewJsonObject.getAsJsonObject(ConfigParser.KEY_RIGHT_BUTTON2));
            textInputModels.add(rightButton2Model);
        }

        //finally adding all the members of input text and returning the same
        return new EditTextModel(textInputId, textInputStyle, textInputModels, placeholderText);
    }

    /**
     * Method to parse image button model
     *
     * @param buttonId
     * @param buttonJsonObject
     * @return
     */
    private ImageButtonModel deserializeImageButtonModel(String buttonId, JsonObject buttonJsonObject) {
        Gson gson = new Gson();

        String image = null;
        String action = null;
        List<ImageButtonModel.Content> contents = null;

        if (buttonJsonObject.has(ConfigParser.KEY_IMAGE)) {
            image = buttonJsonObject.get(ConfigParser.KEY_IMAGE).getAsString();
        }

        if (buttonJsonObject.has(ConfigParser.KEY_ACTION)) {
            action = buttonJsonObject.get(ConfigParser.KEY_ACTION).getAsString();
        }

        if (buttonJsonObject.has(ConfigParser.KEY_CONTENT)) {
            String contentArrayString = buttonJsonObject.getAsJsonArray(ConfigParser.KEY_CONTENT).toString();
            contents = gson.fromJson(contentArrayString, new TypeToken<List<ImageButtonModel.Content>>() {
            }.getType());
        }

        //making the image button model
        ImageButtonModel imageButtonModel = new ImageButtonModel(buttonId);
        imageButtonModel.setImage(image);
        imageButtonModel.setAction(action);
        imageButtonModel.setContents(contents);

        return imageButtonModel;
    }
}
