package com.morfeus.android.mfsdk.ui.config.parser.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.ToolbarViewStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToolbarViewModelDeserializer implements JsonDeserializer<TabLayoutModel> {
    @Override
    public TabLayoutModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = null;
        ToolbarViewStyle style = null;
        List<BaseModel> baseModels = new ArrayList<>();

        JsonObject toolViewJsonObject = (JsonObject) json;

        //parsing tyep
        if (toolViewJsonObject.has(ConfigParser.KEY_TYPE)) {
            type = toolViewJsonObject.get(ConfigParser.KEY_TYPE).getAsString();
        }

        //parsing style
        if (toolViewJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = toolViewJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            String backgroundColor = null;
            String backgroundImage = null;
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
                backgroundImage = styleJSON.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString();
            }
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                backgroundColor = styleJSON.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString();
            }
            style = new ToolbarViewStyle(backgroundColor, backgroundImage);
        }

        //parsing content
        if (toolViewJsonObject.has(ConfigParser.KEY_CONTENT)) {
            JsonArray contentJsonArray = toolViewJsonObject.getAsJsonArray(ConfigParser.KEY_CONTENT);
            baseModels = deserializeToolbarContent(contentJsonArray);
        }

        return new TabLayoutModel(ConfigParser.KEY_TOOLBAR_VIEW, type, style, baseModels);
    }

    private List<BaseModel> deserializeToolbarContent(JsonArray contentJsonArray) {
        List<BaseModel> baseModels = new ArrayList<>();
        if (contentJsonArray != null) {
            for (int i = 0; i < contentJsonArray.size(); i++) {
                JsonObject individualContentJson = contentJsonArray.get(i).getAsJsonObject();
                String image = null;
                String image_sel = null;
                String action = null;
                String subview = null;


                if (individualContentJson.has(ConfigParser.KEY_IMAGE)) {
                    image = individualContentJson.get(ConfigParser.KEY_IMAGE).getAsString();
                }

                if (individualContentJson.has(ConfigParser.KEY_IMAGE_SEL)) {
                    image_sel = individualContentJson.get(ConfigParser.KEY_IMAGE_SEL).getAsString();
                }

                if (individualContentJson.has(ConfigParser.KEY_ACTION)) {
                    action = individualContentJson.get(ConfigParser.KEY_ACTION).getAsString();

                    //setting action as "menuview" if it is "subview"
                    if (action.equalsIgnoreCase(ConfigParser.KEY_SUB_VIEW)) {
                        action = ConfigParser.KEY_MENU_VIEW;
                    }
                }

                if (individualContentJson.has(ConfigParser.KEY_SUB_VIEW)) {
                    subview = individualContentJson.get(ConfigParser.KEY_SUB_VIEW).getAsString();
                }

                //making the image button model
                ImageButtonModel imageButtonModel = new ImageButtonModel(subview);
                imageButtonModel.setImage(image);
                imageButtonModel.setImageSel(image_sel);
                imageButtonModel.setAction(action);
                baseModels.add(imageButtonModel);
            }
        }

        return baseModels;
    }
}
