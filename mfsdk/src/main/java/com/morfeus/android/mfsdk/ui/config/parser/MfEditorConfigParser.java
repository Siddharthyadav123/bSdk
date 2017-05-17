package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;

import java.util.ArrayList;
import java.util.List;

public class MfEditorConfigParser implements ConfigParser {

    private static MfEditorConfigParser INSTANCE;

    private MfEditorConfigParser() {
        // No-op
    }

    public static MfEditorConfigParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MfEditorConfigParser();
        return INSTANCE;
    }

    @Override
    public BaseModel parse(@NonNull String jsonStr) {
        MFEditorViewModel mfEditorViewModel = null;

        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJSON = parser.parse(jsonStr).getAsJsonObject();
            if (parentJSON.has(KEY_SCREEN)) {
                JsonObject screenJSON = parentJSON.getAsJsonObject(KEY_SCREEN);
                if (screenJSON.has(KEY_FOOTER)) {
                    SuggestionViewConfigParser suggestionParser
                            = SuggestionViewConfigParser.getInstance();
                    SuggestionViewModel suggestionModel
                            = (SuggestionViewModel) suggestionParser.parse(jsonStr);

                    InputViewConfigParser inputViewParser = InputViewConfigParser.getInstance();
                    InputViewModel inputViewModel = (InputViewModel) inputViewParser.parse(jsonStr);

                    ToolbarViewConfigParser toolbarParser = ToolbarViewConfigParser.getInstance();
                    TabLayoutModel tabModel = (TabLayoutModel) toolbarParser.parse(jsonStr);

                    SubViewConfigParser subviewParser = SubViewConfigParser.getInstance();
                    SubViewModel subViewModel = (SubViewModel) subviewParser.parse(jsonStr);

                    List<BaseModel> baseModels = new ArrayList<>();
                    baseModels.add(suggestionModel);
                    baseModels.add(inputViewModel);
                    baseModels.add(tabModel);
                    baseModels.add(subViewModel);

                    mfEditorViewModel = new MFEditorViewModel(KEY_FOOTER, baseModels);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mfEditorViewModel;
    }
}
