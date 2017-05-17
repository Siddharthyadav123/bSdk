package com.morfeus.android.mfsdk.ui.config.parser;

public class ConfigParserHelper {

    public static final String TEST_JSON_STR = "{\n" +
            "  \"screen\": {\n" +
            "    \"id\": \"chat\",\n" +
            "    \"cardUIType\": \"MF_CARDUI_OPTION1\",\n" +
            "    \"screenUIType\": \"MF_SCREENUI_OPTION1\",\n" +
            "    \"body\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"bgChat\",\n" +
            "        \"background-color\": \"#F1F1F1\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"header\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"\",\n" +
            "        \"background-color\": \"#023F61\"\n" +
            "      },\n" +
            "      \"leftbuttons\": [\n" +
            "        {\n" +
            "          \"label\": \"back\",\n" +
            "          \"image\": \"ic_arrow_back\",\n" +
            "          \"action\": \"ic_arrow_back\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"rightbuttons\": [\n" +
            "      ],\n" +
            "      \"headerText\": {\n" +
            "        \"label\": \"Morfeus\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#ffffff\",\n" +
            "          \"horizontal-alignment\": \"left\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"subheaderText\": {\n" +
            "        \"label\": \"Online\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#ffffff\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"profileImage\": {\n" +
            "      }\n" +
            "    },\n" +
            "    \"footer\": {\n" +
            "      \"inputview\": {\n" +
            "        \"style\": {\n" +
            "          \"height\": \"50\",\n" +
            "          \"background-color\": \"#ffffff\"\n" +
            "        },\n" +
            "        \"textinput\": {\n" +
            "          \"placeholder-text\": \"Type here...\",\n" +
            "          \"style\": {\n" +
            "            \"background-image\": \"ic_like_off\",\n" +
            "            \"background-color\": \"#F1F1F1\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"sendButton\": {\n" +
            "          \"image\": \"ic_send\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"toolbarview\": {\n" +
            "        \"type\": \"MF_TOOLBAR_DEFAULT\",\n" +
            "        \"style\": {\n" +
            "          \"height\": \"50\",\n" +
            "          \"background-color\": \"#F1F1F1\"\n" +
            "        },\n" +
            "        \"content\": [\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_off\",\n" +
            "            \"image_sel\": \"ic_menu_on\",\n" +
            "            \"subview\": \"menuview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_text_off\",\n" +
            "            \"image_sel\": \"ic_text_on\",\n" +
            "            \"subview\": \"textview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_emoji_off\",\n" +
            "            \"image_sel\": \"ic_emoji_on\",\n" +
            "            \"subview\": \"smileyview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_record_off\",\n" +
            "            \"image_sel\": \"ic_record_on\",\n" +
            "            \"subview\": \"voiceview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_like_off\",\n" +
            "            \"image_sel\": \"ic_like_on\",\n" +
            "            \"subview\": \"feedbackview\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"shortcutview\": {\n" +
            "        \"style\": {\n" +
            "          \"height\": \"37\",\n" +
            "          \"background-color\": \"#FFFFFF\",\n" +
            "          \"button\": {\n" +
            "            \"border-color\": \"#023F61\",\n" +
            "            \"border-width\": \"1\",\n" +
            "            \"corner-radius\": \"15\",\n" +
            "            \"label-color\": \"#023F61\",\n" +
            "            \"background-color\": \"#E4E4E4\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"content\": [\n" +
            "          {\n" +
            "            \"label\": \"Account Balance\",\n" +
            "            \"action\": \"sendmessage\",\n" +
            "            \"image\": \"red_weel\",\n" +
            "            \"payload\": \"Account balance\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Funds Transfer\",\n" +
            "            \"action\": \"sendmessage\",\n" +
            "            \"image\": \"dark_blue_airplane\",\n" +
            "            \"payload\": \"Funds transfer\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Recharge\",\n" +
            "            \"action\": \"IMAGEPICKER\",\n" +
            "            \"payload\": \"Recharge\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Block Cards\",\n" +
            "            \"action\": \"IMAGEPICKER\",\n" +
            "            \"image\": \"start_filled\",\n" +
            "            \"payload\": \"Block my card\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"subview\": {\n" +
            "      \"style\": {\n" +
            "        \"height\": \"120\",\n" +
            "        \"background-color\": \"#F5F5F5\"\n" +
            "      },\n" +
            "      \"content\": {\n" +
            "        \"menuview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_acbalance\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"BALANCE\",\n" +
            "              \"payload\": \"What's my balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_dollar_inactive\",\n" +
            "              \"label\": \"FUNDS TRANSFER\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Funds transfer\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_billpay\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"BILL PAYMENT\",\n" +
            "              \"payload\": \"Bill payment\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_recharge\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"RECHARGE\",\n" +
            "              \"payload\": \"Recharge\"\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        \"smileyview\": [\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_acbalance\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"image\": \"image_funds\",\n" +
            "                \"action\": \"RESET PIN\",\n" +
            "                \"payload\": \"What's my balance\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_dollar\",\n" +
            "                \"action\": \"RECHARGE\",\n" +
            "                \"payload\": \"Recharge\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_dollar_inactive\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"image\": \"image_funds\",\n" +
            "                \"action\": \"RESET PIN\",\n" +
            "                \"payload\": \"What's my balance\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_dollar\",\n" +
            "                \"action\": \"RECHARGE\",\n" +
            "                \"payload\": \"Recharge\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"voiceview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_like\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_dislike\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        \"feedbackview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_like\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_dislike\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"initView\": {\n" +
            "      \"logo\": {\n" +
            "        \"imageName\": \"ic_loading\"\n" +
            "      },\n" +
            "      \"loadingText\": {\n" +
            "        \"label\": \"Loading\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#303F9F\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"progressView\": {\n" +
            "        \"color\": \"#303F9F\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    public static final String TEST_CARD_STYLE_JSON_STR = "{\n" +
            "  \"cardStyles\": {\n" +
            "    \"messagetime\": {\n" +
            "      \"style\": {\n" +
            "        \"position\": \"between_message\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"messagesender\": {\n" +
            "      \"style\": {\n" +
            "        \"position\": \"inside_bubble\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"messagestatus\": {\n" +
            "      \"style\": {\n" +
            "        \"sent-image\": \"abc.png\",\n" +
            "        \"delivered-image\": \"abc.png\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"cards\": [\n" +
            "      {\n" +
            "        \"cardType\": \"text\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#ffffff\",\n" +
            "            \"background-image\": \"chat-axis-incoming\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"7\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"messagesender-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#0085FE\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"3\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#FFFFFF\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"image\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"0\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"0\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"InfoCardTemplate\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"InfoCardWithButtonsTemplate\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"description\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#999999\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"button\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"description\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#999999\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"button\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"SimleyCardTemplate\",\n" +
            "        \"showasbig\": \"no\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"OTPCardTemplate\",\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"rounded/withtail/rectangle\",\n" +
            "            \"padding-right\": \"0\",\n" +
            "            \"messagetime-color\": \"#FFFFFF\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\",\n" +
            "              \"maskCharacter\": \"*\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"HybridCardTemplate\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"ListCardTemplate\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"0\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"description\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#999999\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"button\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"0\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"description\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#999999\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"button\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"webview\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#ffffff\",\n" +
            "            \"background-image\": \"chat-axis-incoming\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"7\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"messagesender-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#0085FE\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"3\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#FFFFFF\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"cardType\": \"location\",\n" +
            "        \"incoming\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-left\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"outgoing\": {\n" +
            "          \"style\": {\n" +
            "            \"background-color\": \"#FFFFFF\",\n" +
            "            \"background-image\": \"\",\n" +
            "            \"background-color-shape\": \"bordered\",\n" +
            "            \"padding-right\": \"2\",\n" +
            "            \"messagetime-color\": \"#000000\",\n" +
            "            \"bot-image\": \"abc.png\",\n" +
            "            \"max-width\": \"230\"\n" +
            "          },\n" +
            "          \"title\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#000000\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"subtitle\": {\n" +
            "            \"style\": {\n" +
            "              \"text-color\": \"#666666\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    public static final String TEST_NEW_SUB_VIEW = "{\n" +
            "  \"screen\": {\n" +
            "    \"id\": \"chat\",\n" +
            "    \"cardUIType\": \"MF_CARDUI_OPTION1\",\n" +
            "    \"screenUIType\": \"MF_SCREENUI_OPTION1\",\n" +
            "    \"body\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"bgChat\",\n" +
            "        \"background-color\": \"#F1F1F1\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"header\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"\",\n" +
            "        \"background-color\": \"#023F61\"\n" +
            "      },\n" +
            "      \"leftbuttons\": [\n" +
            "        {\n" +
            "          \"label\": \"back\",\n" +
            "          \"image\": \"ic_arrow_back\",\n" +
            "          \"action\": \"ic_arrow_back\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"rightbuttons\": [\n" +
            "      ],\n" +
            "      \"headerText\": {\n" +
            "        \"label\": \"Morfeus\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#ffffff\",\n" +
            "          \"horizontal-alignment\": \"left\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"subheaderText\": {\n" +
            "        \"label\": \"Online\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#ffffff\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"profileImage\": {\n" +
            "      }\n" +
            "    },\n" +
            "    \"footer\": {\n" +
            "      \"inputview\": {\n" +
            "        \"style\": {\n" +
            "          \"height\": \"50\",\n" +
            "          \"background-color\": \"#ffffff\"\n" +
            "        },\n" +
            "        \"textinput\": {\n" +
            "          \"placeholder-text\": \"Type here...\",\n" +
            "          \"style\": {\n" +
            "            \"background-image\": \"ic_like_off\",\n" +
            "            \"background-color\": \"#F1F1F1\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"sendButton\": {\n" +
            "          \"image\": \"ic_send\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"toolbarview\": {\n" +
            "        \"type\": \"MF_TOOLBAR_DEFAULT\",\n" +
            "        \"style\": {\n" +
            "          \"height\": \"50\",\n" +
            "          \"background-color\": \"#F1F1F1\"\n" +
            "        },\n" +
            "        \"content\": [\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_off\",\n" +
            "            \"image_sel\": \"ic_menu_on\",\n" +
            "            \"subview\": \"menuview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_text_off\",\n" +
            "            \"image_sel\": \"ic_text_on\",\n" +
            "            \"subview\": \"textview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_emoji_off\",\n" +
            "            \"image_sel\": \"ic_emoji_on\",\n" +
            "            \"subview\": \"smileyview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_record_off\",\n" +
            "            \"image_sel\": \"ic_record_on\",\n" +
            "            \"subview\": \"voiceview\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_like_off\",\n" +
            "            \"image_sel\": \"ic_like_on\",\n" +
            "            \"subview\": \"feedbackview\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"shortcutview\": {\n" +
            "        \"style\": {\n" +
            "          \"height\": \"37\",\n" +
            "          \"background-color\": \"#FFFFFF\",\n" +
            "          \"button\": {\n" +
            "            \"border-color\": \"#023F61\",\n" +
            "            \"border-width\": \"1\",\n" +
            "            \"corner-radius\": \"15\",\n" +
            "            \"label-color\": \"#023F61\",\n" +
            "            \"background-color\": \"#E4E4E4\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"content\": [\n" +
            "          {\n" +
            "            \"label\": \"Account Balance\",\n" +
            "            \"action\": \"sendmessage\",\n" +
            "            \"image\": \"red_weel\",\n" +
            "            \"payload\": \"Account balance\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Funds Transfer\",\n" +
            "            \"action\": \"sendmessage\",\n" +
            "            \"image\": \"dark_blue_airplane\",\n" +
            "            \"payload\": \"Funds transfer\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Recharge\",\n" +
            "            \"action\": \"IMAGEPICKER\",\n" +
            "            \"payload\": \"Recharge\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"label\": \"Block Cards\",\n" +
            "            \"action\": \"IMAGEPICKER\",\n" +
            "            \"image\": \"start_filled\",\n" +
            "            \"payload\": \"Block my card\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"subview\": {\n" +
            "      \"style\": {\n" +
            "        \"height\": \"120\",\n" +
            "        \"background-color\": \"#ffffff\"\n" +
            "      },\n" +
            "      \"content\": {\n" +
            "        \"menuview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_acbalance\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"BALANCE\",\n" +
            "              \"payload\": \"What's my balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_dollar_inactive\",\n" +
            "              \"label\": \"FUNDS TRANSFER\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Funds transfer\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_billpay\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"BILL PAYMENT\",\n" +
            "              \"payload\": \"Bill payment\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_menu_recharge\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"label\": \"RECHARGE\",\n" +
            "              \"payload\": \"Recharge\"\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        \"smileyview\": [\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_acbalance\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"image\": \"image_funds\",\n" +
            "                \"action\": \"RESET PIN\",\n" +
            "                \"payload\": \"What's my balance\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_dollar\",\n" +
            "                \"action\": \"RECHARGE\",\n" +
            "                \"payload\": \"Recharge\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"image\": \"ic_menu_dollar_inactive\",\n" +
            "            \"content\": [\n" +
            "              {\n" +
            "                \"image\": \"image_funds\",\n" +
            "                \"action\": \"RESET PIN\",\n" +
            "                \"payload\": \"What's my balance\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_conversion\",\n" +
            "                \"action\": \"FUNDS TRANSFER\",\n" +
            "                \"payload\": \"Funds Transfer\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_security\",\n" +
            "                \"action\": \"Block Card\",\n" +
            "                \"payload\": \"Block Card\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"image\": \"image_dollar\",\n" +
            "                \"action\": \"RECHARGE\",\n" +
            "                \"payload\": \"Recharge\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"voiceview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_like\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_dislike\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        \"feedbackview\": {\n" +
            "          \"content\": [\n" +
            "            {\n" +
            "              \"image\": \"ic_like\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"image\": \"ic_dislike\",\n" +
            "              \"action\": \"sendmessage\",\n" +
            "              \"payload\": \"Balance\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"initView\": {\n" +
            "      \"logo\": {\n" +
            "        \"imageName\": \"ic_loading\"\n" +
            "      },\n" +
            "      \"loadingText\": {\n" +
            "        \"label\": \"Loading\",\n" +
            "        \"style\": {\n" +
            "          \"color\": \"#303F9F\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"progressView\": {\n" +
            "        \"color\": \"#303F9F\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    public static final String TEST_LOCATION_SCREEN_JSON = "{\n" +
            "  \"screen\": {\n" +
            "    \"id\": \"LocationScreen\",\n" +
            "    \"header\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"abc.png\",\n" +
            "        \"background-color\": \"#023F61\",\n" +
            "        \"border-color\": \"#000000\"\n" +
            "      },\n" +
            "      \"leftbutton\": {\n" +
            "        \"label\": \"back\",\n" +
            "        \"image\": \"ic_arrow_back\",\n" +
            "        \"action\": \"ic_arrow_back\"\n" +
            "      },\n" +
            "      \"headerText\": {\n" +
            "        \"text\": \"Location\",\n" +
            "        \"style\": {\n" +
            "          \"text-color\": \"#ffffff\",\n" +
            "          \"horizontal-alignment\": \"left\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"body\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"bgCat\",\n" +
            "        \"background-color\": \"#ffffff\"\n" +
            "      },\n" +
            "      \"map\": {\n" +
            "        \"type\": \"MAP_TYPE_NORMAL\",\n" +
            "        \"style\": {\n" +
            "          \"pin-image\": \"ic_map_pin\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"footer\": {\n" +
            "      \"style\": {\n" +
            "        \"background-image\": \"abc.png\",\n" +
            "        \"background-color\": \"#ffffff\",\n" +
            "        \"border-color\": \"#000000\"\n" +
            "      },\n" +
            "      \"sendlocation\": {\n" +
            "        \"leftbutton\": {\n" +
            "          \"label\": \"location_btn\",\n" +
            "          \"image\": \"ic_location_btn\"\n" +
            "        },\n" +
            "        \"title\": {\n" +
            "          \"text\": \"Current Location\",\n" +
            "          \"style\": {\n" +
            "            \"text-color\": \"#000000\",\n" +
            "            \"horizontal-alignment\": \"left\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"rightbutton\": {\n" +
            "          \"label\": \"send\",\n" +
            "          \"image\": \"ic_send\",\n" +
            "          \"action\": \"send_location\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"showlocation\": {\n" +
            "        \"leftbutton\": {\n" +
            "          \"label\": \"map_pin\",\n" +
            "          \"image\": \"ic_map_pin\"\n" +
            "        },\n" +
            "        \"title\": {\n" +
            "          \"text\": \"Your Location\",\n" +
            "          \"style\": {\n" +
            "            \"text-color\": \"#000000\",\n" +
            "            \"horizontal-alignment\": \"left\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"rightbutton\": {\n" +
            "          \"label\": \"share\",\n" +
            "          \"image\": \"ic_location_share\",\n" +
            "          \"action\": \"location_share\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
