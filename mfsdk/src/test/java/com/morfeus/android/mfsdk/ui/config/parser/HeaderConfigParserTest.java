package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HeaderConfigParserTest {
    HeaderConfigParser mHeaderConfigParser;

    @Before
    public void setUp() throws Exception {
        mHeaderConfigParser = HeaderConfigParser.getInstance();
    }

    @Test
    public void testParse_returnHeaderModel() throws Exception {
        ActionbarModel actionbarModel = mHeaderConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ActionbarModel json string! Parser returning null.",
                actionbarModel);
        assertEquals("Error: Invalid id in ActionbarModel", "header", actionbarModel.getId());
    }

    @Test
    public void testParse_checkButtonsExistence() throws Exception {
        ActionbarModel actionbarModel = mHeaderConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ActionbarModel json string! Parser returning null.",
                actionbarModel);
        assertNotNull("Error: Failed to parse ActionbarModel buttons are not found.",
                actionbarModel.getButtons());
        assertEquals("Error: Number of Buttons size not matches. (there has to be only left button)",
                1, actionbarModel.getButtons().size());
    }

    @Test
    public void testParse_checkStyleExistence() throws Exception {
        ActionbarModel actionbarModel = mHeaderConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ActionbarModel json string! Parser returning null.",
                actionbarModel);
        assertNotNull("Error: Failed to parse ActionbarModel style is not found.",
                actionbarModel.getStyle());
    }

    @Test
    public void testParse_checkHeaderAndSubHeaderTextExistence() throws Exception {
        ActionbarModel actionbarModel = mHeaderConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ActionbarModel json string! Parser returning null.",
                actionbarModel);
        assertNotNull("Error: Failed to find header texts list ",
                actionbarModel.getTexts());
        assertEquals("Error: Number of Header texts not matches. (It should be two)",
                2, actionbarModel.getTexts().size());
    }
}
