package com.morfeus.android.mfsdk.messenger.source.parser;

public class ParserTestHelper {

    public static final String TEST_JSON_MF_INFO_CARD_TEMPLATE = "{\n" +
            "\t\"conversation\" : {\n" +
            "\t\t\"from\" : \"5w47394784104\",\n" +
            "\t\t\"to\" : \"455308117027085\",\n" +
            "\t\t\"applicationId\" : \"72018127503568257761\"\n" +
            "\t},\n" +
            "\t\"messages\" : [{\n" +
            "\t\t\t\"id\" : \"mid.1486016734623909129163442002\",\n" +
            "\t\t\t\"from\" : \"5w47394784104\",\n" +
            "\t\t\t\"to\" : \"455308117027085\",\n" +
            "\t\t\t\"message\" : {\n" +
            "\t\t\t\t\"type\" : \"custom\",\n" +
            "\t\t\t\t\"card\" : {\n" +
            "\t\t\t\t\t\"templateType\" : \"InfoCardTemplate\",\n" +
            "\t\t\t\t\t\"content\" : [{\n" +
            "\t\t\t\t\t\t\t\"element\" : {\n" +
            "\t\t\t\t\t\t\t\t\"title\" : {\n" +
            "\t\t\t\t\t\t\t\t\t\"text\" : \"Welcome to HDFC Bank.\",\n" +
            "\t\t\t\t\t\t\t\t\t\"type\" : \"label\"\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"subtitle\" : {\n" +
            "\t\t\t\t\t\t\t\t\t\"text\" : \"Losing a wallet is painful but should not be an emergency. I will help you block your debit cards immediately. No more waiting on the phone.\",\n" +
            "\t\t\t\t\t\t\t\t\t\"type\" : \"label\"\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"image\" : {\n" +
            "\t\t\t\t\t\t\t\t\t\"type\" : \"image\",\n" +
            "\t\t\t\t\t\t\t\t\t\"imageUrl\" : \"\"\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"buttons\" : [{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"text\" : \"Block my Debit Card(s)\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"type\" : \"button\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"action\" : \"{\\\"payloadData\\\":{\\\"intent\\\":\\\"BLOCK-CARD-ACTION\\\",\\\"type\\\":null,\\\"data\\\":{\\\"message\\\":\\\"block cards\\\"},\\\"payeeCategory\\\":null,\\\"message\\\":null},\\\"timeStamp\\\":\\\"1486016734614\\\"}\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"buttonType\" : \"postback\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";
    static final String TEST_SIMPLE_TEXT_MESSAGE_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG001\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"text\": \"Welcome to Active AI\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_SIMPLE_IMAGE_MESSAGE_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG002\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"image\",\n" +
            "        \"text\": \"This is a image caption, This is a image caption, This is a image caption for 3 times.\",\n" +
            "        \"thumb\": \"adfasdfdf\",\n" +
            "        \"imageName\": \"HDFCBlockDebitCard3\",\n" +
            "        \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-3.png\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_INFO_CARD_MESSAGE_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG003\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"InfoCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Current Account\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"XXXX-9876\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"HDFCBlockDebitCard1\",\n" +
            "                  \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-1.png\"\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"See Last Transaction\",\n" +
            "                    \"action\": \"SendMessage\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\",\n" +
            "                      \"background-color\": \"000000\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_BUTTON_CARD_MESSAGE_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG003\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"ButtonCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Login\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"You need to be logged in to view you Account details.\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"Login\",\n" +
            "                    \"action\": \"Login\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\",\n" +
            "                      \"background-color\": \"00FFRR\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_HORIZONTAL_MESSAGE_LIST_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG006\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"InfoCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Current Account\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"XXXX-9876\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"HDFCBlockDebitCard1\",\n" +
            "                  \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-1.png\"\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"See Last Transaction\",\n" +
            "                    \"action\": \"SendMessage\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\",\n" +
            "                      \"background-color\": \"000000\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Master Card\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"XXXX-9876\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"HDFCBlockDebitCard3\",\n" +
            "                  \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-3.png\"\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"Last Transactions\",\n" +
            "                    \"action\": \"SendMessage\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\",\n" +
            "                      \"background-color\": \"\"\n" +
            "                    }\n" +
            "                  },\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"Pay Outstanding\",\n" +
            "                    \"action\": \"SendMessage\",\n" +
            "                    \"payload\": \"BlockCard - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\",\n" +
            "                      \"background-color\": \"\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_LIST_CARD_MESSAGE_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG007\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"ListCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"elementStyle\": \"rowLarge\",\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Current Account\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"debit\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"debitcard01\",\n" +
            "                  \"imageUrl\": \"http://google.com\"\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"Shop Now\",\n" +
            "                    \"action\": \"postback\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              },\n" +
            "              \"style\": {\n" +
            "                \"background-color\": \"FFCCAA\"\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Bull Market Continues to Rally\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"The markets react positively to \",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"description\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"peterssendreceiveapp.ngrok.io\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"debitcard01\",\n" +
            "                  \"imageUrl\": \"http://google.com\"\n" +
            "                }\n" +
            "              },\n" +
            "              \"style\": {\n" +
            "                \"background-color\": \"FFCCAA\"\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Saving Account\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"credited\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"debitcard01\",\n" +
            "                  \"imageUrl\": \"http://google.com\"\n" +
            "                },\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"Buy\",\n" +
            "                    \"action\": \"postback\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              },\n" +
            "              \"style\": {\n" +
            "                \"background-color\": \"FFCCAA\"\n" +
            "              }\n" +
            "            },\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"elementStyle\": \"rowButton\",\n" +
            "                \"buttons\": [\n" +
            "                  {\n" +
            "                    \"type\": \"button\",\n" +
            "                    \"text\": \"View more\",\n" +
            "                    \"action\": \"postback\",\n" +
            "                    \"payload\": \"Last TRansaction - XXXX-9876\",\n" +
            "                    \"style\": {\n" +
            "                      \"textcolor\": \"FFFFFF\"\n" +
            "                    }\n" +
            "                  }\n" +
            "                ]\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_MULTIPLE_MESSAGES_JSON = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG001\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"text\": \"Welcome to Active AI\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG002\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"image\",\n" +
            "        \"text\": \"This is a image caption, This is a image caption, This is a image caption for 3 times.\",\n" +
            "        \"thumb\": \"adfasdfdf\",\n" +
            "        \"imageName\": \"HDFCBlockDebitCard3\",\n" +
            "        \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-3.png\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"messageid\": \"MSG003\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"InfoCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Welcome to Active AI\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"How can i help you ?\",\n" +
            "                  \"style\": {\n" +
            "                    \"textcolor\": \"FFFFFF\"\n" +
            "                  }\n" +
            "                },\n" +
            "                \"image\": {\n" +
            "                  \"type\": \"image\",\n" +
            "                  \"imagetype\": \"png\",\n" +
            "                  \"imageName\": \"HDFCBlockDebitCard4\",\n" +
            "                  \"imageUrl\": \"https://s3.ap-south-1.amazonaws.com/hdfcdemo/HDFC-Block-Debit-Card-4.png\"\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static final String TEST_EMPTY_MESSAGES_JSON = "{\n" +
            "  \"messages\": [\n" +
            "  \n" +
            "  ]\n" +
            "}";

    static final String TEST_NOTIFICATION_MESSAGE_JSON
            = "{\n" +
            "  \"messages\": [\n" +
            "    {\n" +
            "      \"to\": \"morfues_user\",\n" +
            "      \"from\": \"morfues_bot\",\n" +
            "      \"id\": \"MSG003\",\n" +
            "      \"message\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"card\": {\n" +
            "          \"templateType\": \"InfoCardTemplate\",\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"element\": {\n" +
            "                \"title\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"Hello Amit\"\n" +
            "                },\n" +
            "                \"subtitle\": {\n" +
            "                  \"type\": \"label\",\n" +
            "                  \"text\": \"I'm back\"\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
