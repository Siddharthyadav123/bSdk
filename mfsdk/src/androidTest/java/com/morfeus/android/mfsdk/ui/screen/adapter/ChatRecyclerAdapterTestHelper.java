package com.morfeus.android.mfsdk.ui.screen.adapter;

public final class ChatRecyclerAdapterTestHelper {

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

    public static final String TEST_JSON_MF_INFO_CARD_WITH_BUTTONS_TEMPLATE = " {\n" +
            "        \"action\": \"\",\n" +
            "            \"payload\": \"\",\n" +
            "            \"element\": {\n" +
            "        \"title\": {\n" +
            "            \"type\": \"label\",\n" +
            "                    \"text\": \"Savign Account\",\n" +
            "                    \"style\": {\n" +
            "                \"textcolor\": \"#FFFFFF\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"subtitle\": {\n" +
            "            \"type\": \"label\",\n" +
            "                    \"text\": \"XXXX-1234\",\n" +
            "                    \"style\": {\n" +
            "                \"textcolor\": \"#FFFFFF\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"cardimage\": {\n" +
            "            \"type\": \"image\",\n" +
            "                    \"imagetype\": \"png\",\n" +
            "                    \"imagename\": \"receiver_topmale\",\n" +
            "                    \"imageulr\": \"http://google.com\"\n" +
            "        },\n" +
            "        \"buttons\": [{\n" +
            "            \"type\": \"button\",\n" +
            "                    \"text\": \"Set As Default\",\n" +
            "                    \"action\": \"SendMessage\",\n" +
            "                    \"payload\": \"XXXX-1234\",\n" +
            "                    \"style\": {\n" +
            "                \"textcolor\": \"#FFFFFF\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"button\",\n" +
            "                \"text\": \"Last Transaction\",\n" +
            "                \"action\": \"SendMessage\",\n" +
            "                \"payload\": \"XXXX-1234\",\n" +
            "                \"style\": {\n" +
            "            \"textcolor\": \"#FFFFFF\"\n" +
            "        }\n" +
            "        }]\n" +
            "    }\n" +
            "    }";


}
