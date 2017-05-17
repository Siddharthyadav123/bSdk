package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SuggestionViewConfigParserTest {
    SuggestionViewConfigParser mSuggestionViewConfigParser;

    @Before
    public void setUp() throws Exception {
        mSuggestionViewConfigParser = SuggestionViewConfigParser.getInstance();
    }

    @Test
    public void testParse_returnSuggestionViewModel() throws Exception {
        BaseModel baseModel = mSuggestionViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse shortcutview json string! Parser returning null.",
                baseModel);
        assertTrue("Error: Failed to parse shortcutview json string. Not returning SuggestionViewModel!",
                baseModel instanceof SuggestionViewModel);
        assertEquals("Error: Invalid id in SuggestionViewModel", "shortcutview", baseModel.getId());
    }

    @Test
    public void testParse_suggestionViewModelStyle() {
        BaseModel baseModel = mSuggestionViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse shortcutview json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: SuggestionViewModel doesn't containing Style", baseModel.getStyle());
    }

    @Test
    public void testParse_suggestionViewModelContainsSubViewItems() {
        BaseModel baseModel = mSuggestionViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse shortcutview json string! Parser returning null.",
                baseModel);
        assertEquals("Error: SuggestionViewModel not containing all SuggestionButtonModel", 4,
                ((SuggestionViewModel) baseModel).getSubComponentModels().size());

        HashMap<String, BaseModel> hashSuggestionViewItemId = new HashMap<>();

        for (BaseModel suggestionViewItemModel : ((SuggestionViewModel) baseModel).getSubComponentModels()) {
            if (suggestionViewItemModel instanceof SuggestionButtonModel) {
                String id = ((SuggestionButtonModel) suggestionViewItemModel).getId();
                hashSuggestionViewItemId.put(id, suggestionViewItemModel);
            } else {
                fail("Error: Invalid Model type inside SuggestionViewModel!");
            }
        }

    }


}
