package com.morfeus.android.mfsdk.ui.screen.view;

import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public final class ChatActivityTestHelper {

    public static final String TEST_JSON_MF_INFO_CARD_TEMPLATE = "{\n" +
            "\t\t\t\t\t\"element\": {\n" +
            "\t\t\t\t\t\t\"title\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"Welcome to Active AI\",\n" +
            "\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\"textcolor\": \"#2222FF\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"subtitle\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"How can i help you ?\",\n" +
            "\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\"textcolor\": \"#2222FF\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"welcomeimage\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"image\",\n" +
            "\t\t\t\t\t\t\t\"imagetype\": \"png\",\n" +
            "\t\t\t\t\t\t\t\"imagename\": \"receiver_topmale\",\n" +
            "\t\t\t\t\t\t\t\"imageulr\": \"http://google.com\"\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}";

    public static final String TEST_JSON_MF_LIST_CARD_TEMPLATE = "{\n" +
            "\t\t\t\t\t\t\"items\": [{\n" +
            "\t\t\t\t\t\t\t\"title\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Today's Top News\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"subtitle\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"President signs new bill\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"description\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"petersendreceiveapp.nrgok.io\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"cellimage\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"image\",\n" +
            "\t\t\t\t\t\t\t\t\"imagetype\": \"png\",\n" +
            "\t\t\t\t\t\t\t\t\"imagename\": \"receiver_topmale\",\n" +
            "\t\t\t\t\t\t\t\t\"imageurl\": \"http://google.com\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"button\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Read More\",\n" +
            "\t\t\t\t\t\t\t\t\"action\": \"SendMessage\",\n" +
            "\t\t\t\t\t\t\t\t\"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\",\n" +
            "\t\t\t\t\t\t\t\t\t\"background-color\": \"\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"title\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Bull Market Continues to Rally\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"subtitle\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"The markets react positively to\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"description\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"petersendreceiveapp.nrgok.io\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"cellimage\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"image\",\n" +
            "\t\t\t\t\t\t\t\t\"imagetype\": \"png\",\n" +
            "\t\t\t\t\t\t\t\t\"imagename\": \"receiver_topmale\",\n" +
            "\t\t\t\t\t\t\t\t\"imageurl\": \"http://google.com\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"button\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Read\",\n" +
            "\t\t\t\t\t\t\t\t\"action\": \"SendMessage\",\n" +
            "\t\t\t\t\t\t\t\t\"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\",\n" +
            "\t\t\t\t\t\t\t\t\t\"background-color\": \"\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"title\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Forest Fires\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"subtitle\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"The dry season has results in\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"description\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"label\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"cellimage\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"image\",\n" +
            "\t\t\t\t\t\t\t\t\"imagetype\": \"png\",\n" +
            "\t\t\t\t\t\t\t\t\"imagename\": \"receiver_topmale\",\n" +
            "\t\t\t\t\t\t\t\t\"imageurl\": \"http://google.com\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"button\": {\n" +
            "\t\t\t\t\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\t\t\t\t\"text\": \"Read\",\n" +
            "\t\t\t\t\t\t\t\t\"action\": \"SendMessage\",\n" +
            "\t\t\t\t\t\t\t\t\"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "\t\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\",\n" +
            "\t\t\t\t\t\t\t\t\t\"background-color\": \"\"\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t}],\n" +
            "\t\t\t\t\t\t\"buttonmore\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"View More\",\n" +
            "\t\t\t\t\t\t\t\"action\": \"SendMessage\",\n" +
            "\t\t\t\t\t\t\t\"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "\t\t\t\t\t\t\t\"style\": {\n" +
            "\t\t\t\t\t\t\t\t\"textcolor\": \"FFFFFF\",\n" +
            "\t\t\t\t\t\t\t\t\"background-color\": \"\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}";

    private static final String TEST_INFO_CARD_TEMPLATE_ID = "InfoCardTemplate";
    private static final String TEST_LIST_CARD_TEMPLATE_ID = "ListCardTemplate";

    public static List<TemplateModel> createTemplateModels() {
        List<TemplateModel> templateModels = new ArrayList<>();

//        MFCardData mfInfogCardTemplateData = new MfInfoCardTemplateData();
//        mfInfogCardTemplateData.deserialize(TEST_JSON_MF_INFO_CARD_TEMPLATE);

        templateModels.add(mock(TemplateModel.class));

        return templateModels;
    }

    public static List<TemplateModel> createListModels() {
        List<TemplateModel> templateModels = new ArrayList<>();

        MFCardData mfListCardTemplateData = new MFCardData();
        mfListCardTemplateData.deserialize(TEST_JSON_MF_LIST_CARD_TEMPLATE);

//        MfInfoCardTemplateModel mfInfoCardTemplateModel = new MfInfoCardTemplateModel(
//                TEST_LIST_CARD_TEMPLATE_ID,
//                mfListCardTemplateData
//        );
        templateModels.add(mock(TemplateModel.class));

        return templateModels;
    }
}
