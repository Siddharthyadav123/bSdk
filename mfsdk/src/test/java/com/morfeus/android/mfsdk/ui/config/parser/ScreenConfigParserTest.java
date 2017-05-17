package com.morfeus.android.mfsdk.ui.config.parser;

import com.morfeus.android.mfsdk.ui.screen.chat.ChatScreenModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScreenConfigParserTest {

    ScreenConfigParser mScreenConfigParser;

    @Before
    public void setUp() throws Exception {
        mScreenConfigParser = ScreenConfigParser.getInstance();
    }

    @Test
    public void testParse_returnChatScreenModel() throws Exception {
        ChatScreenModel chatScreenModel = mScreenConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ChatScreenModel json string! Parser returning null.",
                chatScreenModel);
        assertEquals("Error: Invalid id in ChatScreenModel", "chat", chatScreenModel.getId());
    }

    @Test
    public void testParse_containsHeader() {
        ChatScreenModel chatScreenModel = mScreenConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ChatScreenModel json string! Parser returning null.",
                chatScreenModel);
        assertEquals("Error: Invalid id in ChatScreenModel", "chat", chatScreenModel.getId());

        assertEquals("Error: ChatScreenModel not containing Header", "header",
                chatScreenModel.getActionbarModel().getId());

    }

    @Test
    public void testParse_containsBody() {
        ChatScreenModel chatScreenModel = mScreenConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ChatScreenModel json string! Parser returning null.",
                chatScreenModel);
        assertEquals("Error: Invalid id in ChatScreenModel", "chat", chatScreenModel.getId());

        assertNotNull("Error: ChatScreenModel not containing Body", chatScreenModel.getBody());
    }

    @Test
    public void testParse_containFooter() {
        ChatScreenModel chatScreenModel = mScreenConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ChatScreenModel json string! Parser returning null.",
                chatScreenModel);
        assertEquals("Error: Invalid id in ChatScreenModel", "chat", chatScreenModel.getId());

        assertEquals("Error: ChatScreenModel not containing Footer", "footer",
                chatScreenModel.getMfEditorViewModel().getId());
    }

    @Test
    public void testParse_containLoading() {
        ChatScreenModel chatScreenModel = mScreenConfigParser.parse(ConfigParserHelper.TEST_JSON_STR);
        assertNotNull("Error: Failed to parse ChatScreenModel json string! Parser returning null.",
                chatScreenModel);
        assertEquals("Error: Invalid id in ChatScreenModel", "chat", chatScreenModel.getId());

        assertEquals("Error: ChatScreenModel not containing Loading", "Loading",
                chatScreenModel.getLoadingModel().getLoadingText().getLabel());
    }


}