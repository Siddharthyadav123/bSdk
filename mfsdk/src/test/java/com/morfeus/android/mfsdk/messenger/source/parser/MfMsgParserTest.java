package com.morfeus.android.mfsdk.messenger.source.parser;

import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgData;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgItems;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MfMsgParserTest {
    private MfMsgParser mMFMsgParser;

    @Before
    public void setUp() throws Exception {
        mMFMsgParser = MfMsgParser.getInstance();
    }

    @Test
    public void testParse_simpleTextMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_SIMPLE_TEXT_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse SimpleTextMessage!", msgModel);

        // MsgModel can contain only one MfMsgItem into list
        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        simpleTextMessage_containsSingleMfMsgItem(msgItems);

        // MfMsgData always will be null.
        MfMsgData msgData = msgItems.get(0).getMsgData();
        simpleTextMessage_containsNoCustomCard(msgData.getMfCards());
    }

    private void simpleTextMessage_containsNoCustomCard(MFCardData card) {
        assertNull("Error: Invalid SimpleTextMessage!", card);
    }

    private void simpleTextMessage_containsSingleMfMsgItem(List<MfMsgItems> msgItems) {
        assertThat(1, is(equalTo(msgItems.size())));
    }

    @Test
    public void testParse_simpleImageMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_SIMPLE_IMAGE_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse SimpleImageMessage!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        simpleImageMessage_containsSingleMfMsgItem(msgItems);

        MfMsgData msgData = msgItems.get(0).getMsgData();
        simpleImageMessage_containsNoCustomCard(msgData.getMfCards());
    }

    private void simpleImageMessage_containsNoCustomCard(MFCardData card) {
        assertNull("Error: Invalid SimpleImageMessage!", card);
    }

    private void simpleImageMessage_containsSingleMfMsgItem(List<MfMsgItems> msgItems) {
        assertThat(msgItems.size(), is(equalTo(1)));
    }

    @Test
    public void testParse_infoCardMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_INFO_CARD_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse InfoCardMessage!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        assertThat(1, is(equalTo(msgItems.size())));

        MfMsgData msgData = msgItems.get(0).getMsgData();
        containsCardJsonArray(msgData.getMfCards());
    }

    private void containsCardJsonArray(MFCardData data) {
        assertNotNull("Error: Invalid InfoCardMessage!", data.getCardDataJsonArray());
    }

    @Test
    public void testParse_listCardMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_LIST_CARD_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse ListCardMessage!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        assertThat(1, is(equalTo(msgItems.size())));

        MfMsgData msgData = msgItems.get(0).getMsgData();
        MFCardData mfCardData = msgData.getMfCards();

        containsCardJsonArray(mfCardData);
    }

    @Test
    public void testParse_buttonCardMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_BUTTON_CARD_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse ButtonCardMessage!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        assertThat(1, is(equalTo(msgItems.size())));

        MfMsgData msgData = msgItems.get(0).getMsgData();
        MFCardData mfCardData = msgData.getMfCards();

        containsCardJsonArray(mfCardData);
    }

    @Test
    public void testParse_horizontalMessages() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_HORIZONTAL_MESSAGE_LIST_JSON);
        assertNotNull("Error: Failed to parse Horizontal message list!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        assertThat(1, is(equalTo(msgItems.size())));

        MfMsgData msgData = msgItems.get(0).getMsgData();
        MFCardData mfCardData = msgData.getMfCards();

        containsCardJsonArray(mfCardData);
    }

    @Test
    public void testParse_multipleMessages() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_MULTIPLE_MESSAGES_JSON);
        assertNotNull("Error: Failed to parse multiple messages!", msgModel);

        List<MfMsgItems> msgItems = msgModel.getMsgItems();
        assertThat(3, is(equalTo(msgItems.size())));
    }

    @Test
    public void testParse_emptyMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_EMPTY_MESSAGES_JSON);
        assertNotNull("Error: Failed to parse on empty message", msgModel);
    }

    @Test
    public void testParse_notificationMessage() {
        MfMsgModel msgModel
                = mMFMsgParser.parse(true, ParserTestHelper.TEST_NOTIFICATION_MESSAGE_JSON);
        assertNotNull("Error: Failed to parse on notification message", msgModel);
    }
}

