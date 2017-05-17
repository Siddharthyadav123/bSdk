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
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubViewModelDeserializer implements JsonDeserializer<SubViewModel> {
    @Override
    public SubViewModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        SubViewStyle style = null;
        List<BaseModel> subComponents = new ArrayList<>();
        JsonObject subViewJsonObject = (JsonObject) json;

        //parsing style
        if (subViewJsonObject.has(ConfigParser.KEY_STYLE)) {
            JsonObject styleJSON = subViewJsonObject.getAsJsonObject(ConfigParser.KEY_STYLE);
            style = new SubViewStyle();
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_IMAGE)) {
                style.setBackgroundImage(styleJSON.get(ConfigParser.KEY_BACKGROUND_IMAGE).getAsString());
            }
            if (styleJSON.has(ConfigParser.KEY_BACKGROUND_COLOR)) {
                style.setBackgroundColor(styleJSON.get(ConfigParser.KEY_BACKGROUND_COLOR).getAsString());
            }
        }
        //parsing content
        if (subViewJsonObject.has(ConfigParser.KEY_CONTENT)) {
            JsonObject contentJsonObject = subViewJsonObject.getAsJsonObject(ConfigParser.KEY_CONTENT);
            subComponents = deserializeSubViewContent(contentJsonObject, style);
        }

        return new SubViewModel(ConfigParser.KEY_SUB_VIEW, style, subComponents);
    }

    private List<BaseModel> deserializeSubViewContent(JsonObject contentJsonObject,
                                                      SubViewStyle style) {
        Gson gson = new Gson();

        List<BaseModel> subComponents = new ArrayList<>();
        String[] conentIds = {
                ConfigParser.KEY_MENU_VIEW,
                ConfigParser.KEY_SMILEY_VIEW,
                ConfigParser.KEY_SETTINGS_VIEW,
                ConfigParser.KET_VOICE_VIEW,
                ConfigParser.KEY_FEEDBACK_VIEW
        };

        for (int i = 0; i < conentIds.length; i++) {
            if (contentJsonObject.has(conentIds[i])) {
                SubViewItemModel subViewItemModel = new SubViewItemModel(conentIds[i]);
                subViewItemModel.setStyle(style);
                //TODO parsing
//                if (subViewJsonObj.has(MsgParser.KEY_STYLE)) {
//                    JsonObject styleJSON = subViewJsonObj.getAsJsonObject(MsgParser.KEY_STYLE);
//                    SubViewStyle style = new SubViewStyle();
//                    if (styleJSON.has(MsgParser.KEY_BACKGROUND_COLOR)) {
//                        style.setBackgroundColor(styleJSON.get(MsgParser.KEY_BACKGROUND_COLOR).getAsString());
//                    }
//                }
                if (conentIds[i].equalsIgnoreCase(ConfigParser.KEY_SMILEY_VIEW)) {
                    JsonArray subViewJsonArray = contentJsonObject.getAsJsonArray(conentIds[i]);
                    subViewItemModel = parseSmileyView(subViewItemModel, subViewJsonArray);
                } else {
                    JsonObject subViewJsonObj = contentJsonObject.getAsJsonObject(conentIds[i]);
                    if (subViewJsonObj.has(ConfigParser.KEY_CONTENT)) {
                        String contentArrayString = subViewJsonObj.getAsJsonArray(ConfigParser.KEY_CONTENT).toString();
                        List<SubViewItemModel.Content> contents = gson.fromJson(contentArrayString, new TypeToken<List<SubViewItemModel.Content>>() {
                        }.getType());
                        subViewItemModel.setContents(contents);
                    }
                }

                subComponents.add(subViewItemModel);
            }
        }


        return subComponents;
    }

    private SubViewItemModel parseSmileyView(SubViewItemModel subViewItemModel
            , JsonArray subViewJsonArray) {

        if (subViewJsonArray != null && subViewJsonArray.size() > 0) {
            Gson gson = new Gson();
            List<SubViewItemModel.SmileySubViewModel> smileySubViewModelList = new ArrayList<>();

            for (int i = 0; i < subViewJsonArray.size(); i++) {
                String image = null;
                List<SubViewItemModel.Content> contents = null;
                JsonObject subViewJsonObj = subViewJsonArray.get(i).getAsJsonObject();
                if (subViewJsonObj.has(ConfigParser.KEY_IMAGE)) {
                    image = subViewJsonObj.get(ConfigParser.KEY_IMAGE).getAsString();
                }
                if (subViewJsonObj.has(ConfigParser.KEY_CONTENT)) {
                    String contentArrayString = subViewJsonObj.getAsJsonArray(ConfigParser.KEY_CONTENT).toString();
                    contents = gson.fromJson(contentArrayString, new TypeToken<List<SubViewItemModel.Content>>() {
                    }.getType());
                }
                smileySubViewModelList.add(new SubViewItemModel.SmileySubViewModel(image, contents));
            }
            subViewItemModel.setSmileySubViewModelList(smileySubViewModelList);
        }

        return subViewItemModel;
    }
}
