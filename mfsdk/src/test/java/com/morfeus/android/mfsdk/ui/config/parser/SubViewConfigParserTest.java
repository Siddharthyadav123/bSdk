package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SubViewConfigParserTest {

    private static final String[] TEST_SUBVIEW_ITEM_IDS = new String[]{
            "menuview", "smileyview", "voiceview", "feedbackview"};

    SubViewConfigParser mSubViewConfigParser;

    @Before
    public void setUp() throws Exception {
        mSubViewConfigParser = SubViewConfigParser.getInstance();
    }

    @Test
    public void testParse_returnSubViewModel() throws Exception {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);
        assertTrue("Error: Failed to parse subview json string. Not returning SubViewModel!",
                baseModel instanceof SubViewModel);
        assertEquals("Error: Invalid id of SubViewModel!", "subview", ((SubViewModel) baseModel).getId());
    }

    @Test
    public void testParse_subViewModelContainsStyle() {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);
        assertNotNull("Error: SubViewModel doesn't containing Style", baseModel.getStyle());
    }

    @Test
    public void testParse_subViewModelContainsSubViewItems() {
        BaseModel baseModel = mSubViewConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse subview json string! Parser returning null.",
                baseModel);
        assertEquals("Error: SubViewModel not containing all SubViewItemModel", 4,
                ((SubViewModel) baseModel).getSubComponentModels().size());

        HashMap<String, BaseModel> hashSubViewItemId = new HashMap<>();

        for (BaseModel subViewItemModel : ((SubViewModel) baseModel).getSubComponentModels()) {
            if (subViewItemModel instanceof SubViewItemModel) {
                String id = ((SubViewItemModel) subViewItemModel).getId();
                hashSubViewItemId.put(id, subViewItemModel);
            } else {
                fail("Error: Invalid Model type inside SubViewModel!");
            }
        }

        for (String string : TEST_SUBVIEW_ITEM_IDS) {
            assertTrue("Error: SubViewModel having SubViewItemModel with invalid id! " + string,
                    hashSubViewItemId.containsKey(string));
        }
    }

}