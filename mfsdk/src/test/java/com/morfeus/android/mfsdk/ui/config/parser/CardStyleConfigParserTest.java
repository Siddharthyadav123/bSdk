package com.morfeus.android.mfsdk.ui.config.parser;


import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CardStyleConfigParserTest {
    CardStyleParser cardStyleParser;

    @Before
    public void setUp() throws Exception {
        cardStyleParser = CardStyleParser.getInstance();
    }

    @Test
    public void testParse_checkDifferentCardStyle() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);
        assertNotNull("Error: Failed to parse messagetime style.",
                StyleFactory.getInstance().getStyle(ConfigParser.CardStyleKey.KEY_MESSAGE_TIME));

        assertNotNull("Error: Failed to parse messagesender style.",
                StyleFactory.getInstance().getStyle(ConfigParser.CardStyleKey.KEY_MESSAGE_SENDER));

        assertNotNull("Error: Failed to parse messagestatus style.",
                StyleFactory.getInstance().getStyle(ConfigParser.CardStyleKey.KEY_MESSAGE_STATUS));

    }


    @Test
    public void testParse_checkTextCardExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: Text card incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("text-" + ConfigParser.CardStyleKey.KEY_INCOMING));

        assertNotNull("Error: Text card outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("text-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }

    @Test
    public void testParse_checkImageCardExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: Image card incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("image-" + ConfigParser.CardStyleKey.KEY_INCOMING));

        assertNotNull("Error: Image card outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("image-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }

    @Test
    public void testParse_checkInfoCardExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: InfoCard incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("InfoCardTemplate-" + ConfigParser.CardStyleKey.KEY_INCOMING));

        assertNotNull("Error: InfoCard outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("InfoCardTemplate-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }

    @Test
    public void testParse_checkInfoCardWithButtonsTemplateExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: InfoCardWithButtonsTemplate incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("InfoCardWithButtonsTemplate-" + ConfigParser.CardStyleKey.KEY_INCOMING));

        assertNotNull("Error: InfoCardWithButtonsTemplate outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("InfoCardWithButtonsTemplate-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }

    @Test
    public void testParse_checkSimleyCardTemplateExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: SimleyCardTemplate incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("SimleyCardTemplate"));

    }

    @Test
    public void testParse_checkOTPCardTemplateExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: OTPCardTemplate outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("OTPCardTemplate-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }

    @Test
    public void testParse_checkListCardTemplateExistence() throws Exception {
        cardStyleParser.parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);

        assertNotNull("Error: ListCardTemplate incoming style not found in Factory.",
                StyleFactory.getInstance().getStyle("ListCardTemplate-" + ConfigParser.CardStyleKey.KEY_INCOMING));

        assertNotNull("Error: ListCardTemplate outgoing style not found in Factory.",
                StyleFactory.getInstance().getStyle("ListCardTemplate-" + ConfigParser.CardStyleKey.KEY_OUTGOING));
    }
}
