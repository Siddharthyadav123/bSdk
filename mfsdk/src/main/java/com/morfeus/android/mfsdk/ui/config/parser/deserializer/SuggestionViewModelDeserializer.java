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
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SuggestionViewStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SuggestionViewModelDeserializer implements JsonDeserializer<SuggestionViewModel> {
    @Override
    public SuggestionViewModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        String id = null;
        SuggestionViewStyle style = null;
        List<BaseModel> subComponentModels = new ArrayList<>();

        JsonObject suggestionViewJsonObject = (JsonObject) json;
        //parsing id
        if (suggestionViewJsonObject.has(ConfigParser.KEY_ID)) {
            id = suggestionViewJsonObject.get(ConfigParser.KEY_ID).getAsString();
        }

        //parsing style
        if (suggestionViewJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = suggestionViewJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            style = deserializeSuggestionStyle(styleJSON);
        }

        //parsing content
        if (suggestionViewJsonObject.has(ConfigParser.KEY_CONTENT)) {
            String contentArrayString = suggestionViewJsonObject.getAsJsonArray(ConfigParser.KEY_CONTENT).toString();
            subComponentModels = gson.fromJson(contentArrayString, new TypeToken<List<SuggestionButtonModel>>() {
            }.getType());
            if (subComponentModels != null) {
                for (int i = 0; i < subComponentModels.size(); i++) {
                    ((SuggestionButtonModel) subComponentModels.get(i)).setStyle(style);
                }
            }
        }

        SuggestionViewModel suggestionViewModel = new SuggestionViewModel(id);
        suggestionViewModel.setStyle(style);
        suggestionViewModel.setSubComponentModels(subComponentModels);

        return suggestionViewModel;
    }

    private SuggestionViewStyle deserializeSuggestionStyle(JsonObject styleJSON) {
        int height = 0;
        String backgroundColor = null;
        SuggestionViewStyle.ButtonStyle buttonStyle = null;

        if (styleJSON.has(ConfigParser.KEY_HEIGHT)) {
            height = Integer.parseInt(styleJSON.get(ConfigParser.KEY_HEIGHT).getAsString());
        }
        if (styleJSON.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
            backgroundColor = styleJSON.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString();
        }

        if (styleJSON.has(ConfigParser.KEY_BUTTON)) {
            buttonStyle = new SuggestionViewStyle.ButtonStyle();
            JsonObject buttonJsonObj = styleJSON.getAsJsonObject(ConfigParser.KEY_BUTTON);

            if (buttonJsonObj.has(ConfigParser.KEY_BORDER_COLOR)) {
                buttonStyle.setBorderColor(buttonJsonObj.get(ConfigParser.KEY_BORDER_COLOR).getAsString());
            }

            if (buttonJsonObj.has(ConfigParser.KEY_BORDER_WIDTH)) {
                buttonStyle.setBorderWidth(Integer.parseInt(buttonJsonObj.get(ConfigParser.KEY_BORDER_WIDTH).getAsString()));
            }

            if (buttonJsonObj.has(ConfigParser.KEY_CORNER_RADIUS)) {
                buttonStyle.setCornerRadius(Integer.parseInt(buttonJsonObj.get(ConfigParser.KEY_CORNER_RADIUS).getAsString()));
            }

            if (buttonJsonObj.has(ConfigParser.KEY_LABEL_COLOR)) {
                buttonStyle.setLabelColor(buttonJsonObj.get(ConfigParser.KEY_LABEL_COLOR).getAsString());
            }

            if (buttonJsonObj.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                buttonStyle.setBackgroundColor(buttonJsonObj.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString());
            }

        }
        //suggestion view style
        SuggestionViewStyle style = new SuggestionViewStyle(buttonStyle);
        style.setHeight(height);
        style.setBackgroundColor(backgroundColor);
        return style;
    }
}
