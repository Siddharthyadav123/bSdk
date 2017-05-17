package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatScreenModel;
import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.loading.style.LoadingTitleStyle;

public class ScreenConfigParser implements ConfigParser {

    private static ScreenConfigParser INSTANCE;

    private ScreenConfigParser() {
        // No-op
    }

    public static ScreenConfigParser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScreenConfigParser();
        }
        return INSTANCE;
    }

    @Override
    public ChatScreenModel parse(@NonNull String jsonStr) {
        // TODO make generic
        ChatScreenModel chatScreenModel = null;

        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_ID)) {
                    String id = screenJSON.get(KEY_ID).getAsString();
                    chatScreenModel = new ChatScreenModel(id);
                    if (screenJSON.has(KEY_BODY)) {
                        parseBody(chatScreenModel, screenJSON);
                    }
                    if (screenJSON.has(KEY_HEADER)) {
                        parseHeader(jsonStr, chatScreenModel);
                    }
                    if (screenJSON.has(KEY_FOOTER)) {
                        parseFooter(jsonStr, chatScreenModel);
                    }
                    if (screenJSON.has(KEY_INIT_VIEW)) {
                        parseLoadingInitView(chatScreenModel, screenJSON);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatScreenModel;
    }


    private void parseFooter(@NonNull String jsonStr, ChatScreenModel chatScreenModel) {
        MfEditorConfigParser mfEditorConfigParser = MfEditorConfigParser.getInstance();
        MFEditorViewModel mfEditorViewModel = (MFEditorViewModel) mfEditorConfigParser.parse(jsonStr);
        chatScreenModel.setMfEditorViewModel(mfEditorViewModel);
    }

    private void parseHeader(@NonNull String jsonStr, ChatScreenModel chatScreenModel) {
        HeaderConfigParser headerConfigParser = HeaderConfigParser.getInstance();
        ActionbarModel actionbarModel = headerConfigParser.parse(jsonStr);
        chatScreenModel.setActionbarModel(actionbarModel);
    }

    private void parseBody(ChatScreenModel chatScreenModel, JsonObject screenJSON) {
        ChatScreenModel.Body bodyModel;
        ChatScreenModel.Body.Style styleModel;
        bodyModel = new ChatScreenModel.Body();
        JsonObject bodyJSON = screenJSON.getAsJsonObject(KEY_BODY);
        if (bodyJSON.has(KEY_STYLE)) {
            styleModel = new ChatScreenModel.Body.Style();
            JsonObject styleJSON = bodyJSON.getAsJsonObject(KEY_STYLE);
            if (styleJSON.has(KEY_BACKGROUND_IMAGE)) {
                String bgImage = styleJSON.get(KEY_BACKGROUND_IMAGE).getAsString();
                styleModel.setBackgroundImage(bgImage);
            }
            if (styleJSON.has(KEY_BACKGROUND_COLOR)) {
                String bgColor = styleJSON.get(KEY_BACKGROUND_COLOR).getAsString();
                styleModel.setBackgroundColor(bgColor);
            }
            bodyModel.setStyle(styleModel);
        }
        chatScreenModel.setBody(bodyModel);
    }

    private void parseLoadingInitView(ChatScreenModel chatScreenModel, JsonObject screenJSON) {
        LoadingModel loadingModel = new LoadingModel();
        LoadingModel.Logo logo;
        LoadingModel.LoadingText loadingText;
        LoadingModel.ProgressView progressView;

        JsonObject initJson = screenJSON.getAsJsonObject(KEY_INIT_VIEW);
        if (initJson.has(KEY_LOGO)) {
            logo = new LoadingModel.Logo();
            JsonObject logoJson = initJson.getAsJsonObject(KEY_LOGO);
            String imageName = logoJson.get(KEY_IMAGE_NAME).getAsString();
            logo.setImageName(imageName);
            loadingModel.setLogo(logo);
        }

        if (initJson.has(KEY_LOADING_TEXT)) {
            loadingText = new LoadingModel.LoadingText();
            JsonObject loadingTextJson = initJson.getAsJsonObject(KEY_LOADING_TEXT);
            String text = loadingTextJson.get(KEY_LABEL).getAsString();
            loadingText.setLabel(text);

            if (loadingTextJson.has(KEY_STYLE)) {
                LoadingTitleStyle loadingTitleStyle = new LoadingTitleStyle();
                JsonObject styleJSON = loadingTextJson.getAsJsonObject(KEY_STYLE);
                String color = styleJSON.get(KEY_COLOR).getAsString();
                loadingTitleStyle.setColor(color);
                loadingText.setStyle(loadingTitleStyle);
            }
            loadingModel.setLoadingText(loadingText);
        }

        if (initJson.has(KEY_PROGRESS_VIEW)) {
            progressView = new LoadingModel.ProgressView();
            JsonObject progressViewJson = initJson.getAsJsonObject(KEY_PROGRESS_VIEW);
            String color = progressViewJson.get(KEY_COLOR).getAsString();
            progressView.setColor(color);
            loadingModel.setProgressView(progressView);
        }
        chatScreenModel.setLoadingModel(loadingModel);
    }
}
