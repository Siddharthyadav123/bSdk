package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.header.style.ActionbarStyle;
import com.morfeus.android.mfsdk.ui.widget.header.style.TitleStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActionBarModelDeserializer implements JsonDeserializer<ActionbarModel> {
    @Override
    public ActionbarModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ActionbarStyle style = null;
        List<ActionbarModel.Button> buttons = new ArrayList<>();
        List<ActionbarModel.Text> texts = new ArrayList<>();
        ActionbarModel.Profile profileImage = null;

        JsonObject actionBarJsonObject = (JsonObject) json;

        //parsing style
        if (actionBarJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = actionBarJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            style = new ActionbarStyle();

            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
                style.setBackgroundImage(styleJSON.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString());
            }
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                style.setBackgroundColor(styleJSON.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString());
            }
        }

        //parsing left button
        if (actionBarJsonObject.has(ConfigParser.KEY_LEFT_BUTTONS)) {
            List<ActionbarModel.Button> leftbuttons = deserializeActionBarButtonModel(ConfigParser.KEY_LEFT_BUTTONS,
                    actionBarJsonObject.getAsJsonArray(ConfigParser.KEY_LEFT_BUTTONS));
            buttons.addAll(leftbuttons);
        }

        //parsing right button
        if (actionBarJsonObject.has(ConfigParser.KEY_RIGHT_BUTTONS)) {
            List<ActionbarModel.Button> rightButton = deserializeActionBarButtonModel(ConfigParser.KEY_RIGHT_BUTTONS,
                    actionBarJsonObject.getAsJsonArray(ConfigParser.KEY_RIGHT_BUTTONS));
            buttons.addAll(rightButton);
        }

        //parsing headerText
        if (actionBarJsonObject.has(ConfigParser.KEY_HEADER_TEXT)) {
            ActionbarModel.Text headerText = deserializeActionBarHeaderText(ConfigParser.KEY_HEADER_TEXT,
                    actionBarJsonObject.getAsJsonObject(ConfigParser.KEY_HEADER_TEXT));
            texts.add(headerText);
        }

        //parsing subheaderText
        if (actionBarJsonObject.has(ConfigParser.KEY_SUB_HEADER_TEXT)) {
            ActionbarModel.Text headerText = deserializeActionBarHeaderText(ConfigParser.KEY_SUB_HEADER_TEXT,
                    actionBarJsonObject.getAsJsonObject(ConfigParser.KEY_SUB_HEADER_TEXT));
            texts.add(headerText);
        }

        //parsing profileImage
        if (actionBarJsonObject.has(ConfigParser.KEY_PROFILE_IMAGE)) {
            JsonObject profileJsonObject = actionBarJsonObject.getAsJsonObject(ConfigParser.KEY_PROFILE_IMAGE);
            if (profileJsonObject.has(ConfigParser.KEY_IMAGE)) {
                profileImage = new ActionbarModel.Profile();
                profileImage.setImage(profileJsonObject.get(ConfigParser.KEY_IMAGE).getAsString());
            }
        }

        ActionbarModel actionbarModel = new ActionbarModel();
        actionbarModel.setId(ConfigParser.KEY_HEADER);
        actionbarModel.setStyle(style);
        actionbarModel.setButtons(buttons);
        actionbarModel.setTexts(texts);
        actionbarModel.setProfile(profileImage);

        return actionbarModel;
    }

    private ActionbarModel.Text deserializeActionBarHeaderText(String keyHeaderText, JsonObject headerTextJsonObject) {
        ActionbarModel.Text headerText = new ActionbarModel.Text();
        headerText.setId(keyHeaderText);

        if (headerTextJsonObject.has(ConfigParser.KEY_LABEL)) {
            headerText.setLabel(headerTextJsonObject.get(ConfigParser.KEY_LABEL).getAsString());
        }

        if (headerTextJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = headerTextJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            TitleStyle titleStyle = new TitleStyle();

            if (styleJSON.has(ConfigParser.KEY_COLOR)) {
                titleStyle.setTextColor(styleJSON.get(ConfigParser.KEY_COLOR).getAsString());
            }
            if (styleJSON.has(ConfigParser.KEY_HORIZONTAL_ALIGNMENT)) {
                titleStyle.setHorizontalAlignment(styleJSON.get(ConfigParser.KEY_HORIZONTAL_ALIGNMENT).getAsString());
            }
            headerText.setStyle(titleStyle);
        }
        return headerText;
    }

    private List<ActionbarModel.Button> deserializeActionBarButtonModel(String btnKey, JsonArray buttonJsonArray) {
        Gson gson = new Gson();
        String contentArrayString = buttonJsonArray.toString();
        List<ActionbarModel.Button> buttons = gson.fromJson(contentArrayString, new TypeToken<List<ActionbarModel.Button>>() {
        }.getType());

        if (buttons != null) {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setId(btnKey);
            }
        }
        return buttons;
    }


}
