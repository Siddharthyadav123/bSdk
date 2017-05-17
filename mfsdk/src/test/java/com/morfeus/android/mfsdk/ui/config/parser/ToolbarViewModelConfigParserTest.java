package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ToolbarViewModelConfigParserTest {
    ToolbarViewConfigParser mToolbarViewConfigParser;

    @Before
    public void setUp() throws Exception {
        mToolbarViewConfigParser = ToolbarViewConfigParser.getInstance();
    }

    @Test
    public void testParse_returnToolbarViewModel() throws Exception {
        BaseModel baseModel = mToolbarViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse toolbarview json string! Parser returning null.",
                baseModel);
        assertTrue("Error: Failed to parse toolbarview json string. Not returning TabLayoutModel!",
                baseModel instanceof TabLayoutModel);
    }

    @Test
    public void testParse_toolbarViewModelContainsType() {
        BaseModel baseModel = mToolbarViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse toolbarview json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: TabLayoutModel doesn't containing type", ((TabLayoutModel) baseModel).getType());
    }

    @Test
    public void testParse_toolbarViewModelContainsStyle() {
        BaseModel baseModel = mToolbarViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse toolbarview json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: TabLayoutModel doesn't containing Style", baseModel.getStyle());
    }

    @Test
    public void testParse_toolbarViewModelContainsSubViewItems() {
        BaseModel baseModel = mToolbarViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse toolbarview json string! Parser returning null.",
                baseModel);
        assertEquals("Error: TabLayoutModel not containing all ImageButtonModel", 5,
                ((TabLayoutModel) baseModel).getBaseModels().size());

        HashMap<String, BaseModel> hashToolbarViewItemId = new HashMap<>();

        for (BaseModel toolbarViewItemModel : ((TabLayoutModel) baseModel).getBaseModels()) {
            if (toolbarViewItemModel instanceof ImageButtonModel) {
                String id = ((ImageButtonModel) toolbarViewItemModel).getId();
                hashToolbarViewItemId.put(id, toolbarViewItemModel);
            } else {
                fail("Error: Invalid Model type inside ImageButtonModel!");
            }
        }

    }
}
