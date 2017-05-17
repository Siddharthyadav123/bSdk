package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse Card style from WA_MFCardStyle.json
 */
public class CardStyleParser implements ConfigParser {
    private static CardStyleParser INSTANCE;

    private CardStyleParser() {
        // No-op
    }

    public static CardStyleParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CardStyleParser();
        return INSTANCE;
    }

    /**
     * Always return null.
     *
     * @param jsonStr json string to parse
     * @return null
     */
    @Override
    public Object parse(@NonNull String jsonStr) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject parentJsonObj = parser.parse(jsonStr).getAsJsonObject();
            if (parentJsonObj.has(CardStyleKey.KEY_CARD_STYLES)) {
                JsonObject cardStyleJson
                        = parentJsonObj.getAsJsonObject(CardStyleKey.KEY_CARD_STYLES);

                if (cardStyleJson.has(CardStyleKey.KEY_MESSAGE_TIME)) {
                    JsonObject msgTimeJsonObj
                            = cardStyleJson.getAsJsonObject(CardStyleKey.KEY_MESSAGE_TIME);
                    if (msgTimeJsonObj.has(KEY_STYLE)) {
                        JsonObject msgTimeStyleJsonObj
                                = msgTimeJsonObj.getAsJsonObject(KEY_STYLE);
                        Style style
                                = parseNoneSytle(CardStyleKey.KEY_MESSAGE_TIME, msgTimeStyleJsonObj);
                        StyleFactory.getInstance().register(style);
                    }
                }

                if (cardStyleJson.has(CardStyleKey.KEY_MESSAGE_SENDER)) {
                    JsonObject msgSenderJsonObj
                            = cardStyleJson.getAsJsonObject(CardStyleKey.KEY_MESSAGE_SENDER);
                    if (msgSenderJsonObj.has(KEY_STYLE)) {
                        JsonObject msgTimeStyleJsonObj
                                = msgSenderJsonObj.getAsJsonObject(KEY_STYLE);
                        Style style = parseNoneSytle(
                                CardStyleKey.KEY_MESSAGE_SENDER, msgTimeStyleJsonObj);
                        StyleFactory.getInstance().register(style);
                    }
                }

                if (cardStyleJson.has(CardStyleKey.KEY_MESSAGE_STATUS)) {
                    JsonObject msgStatusStyleJson
                            = cardStyleJson.getAsJsonObject(CardStyleKey.KEY_MESSAGE_STATUS);
                    if (msgStatusStyleJson.has(KEY_STYLE)) {
                        JsonObject msgTimeStyleJsonObj
                                = msgStatusStyleJson.getAsJsonObject(KEY_STYLE);
                        Style style
                                = parseNoneSytle(
                                CardStyleKey.KEY_MESSAGE_STATUS, msgTimeStyleJsonObj);
                        StyleFactory.getInstance().register(style);
                    }
                }

                if (cardStyleJson.has(CardStyleKey.KEY_CARDS)) {
                    JsonArray cardJsonArray = cardStyleJson.getAsJsonArray(CardStyleKey.KEY_CARDS);
                    for (int i = 0; i < cardJsonArray.size(); i++) {
                        JsonObject individualCardJson = cardJsonArray.get(i).getAsJsonObject();
                        parseAndRegisterIndividaulCardStyle(individualCardJson);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseAndRegisterIndividaulCardStyle(JsonObject individualCardJson) {
        String cardType = null;
        if (individualCardJson.has(CardStyleKey.KEY_CARDS_TYPE)) {
            cardType = individualCardJson.get(CardStyleKey.KEY_CARDS_TYPE).getAsString();
        }

        if (individualCardJson.has(CardStyleKey.KEY_SHOW_AS)) {
            boolean showAsBig = false;
            String showAsBigString
                    = individualCardJson.get(CardStyleKey.KEY_SHOW_AS).getAsString();
            if (showAsBigString.equalsIgnoreCase("no")) {
                showAsBig = false;
            } else {
                showAsBig = true;
            }
            Style.Builder styleBuilder = new Style.Builder(cardType, Style.OUTGOING);
            styleBuilder.setShowAsBig(showAsBig);
            Style style = styleBuilder.build();
            StyleFactory.getInstance().register(style);
        }

        if (individualCardJson.has(CardStyleKey.KEY_INCOMING)) {
            JsonObject incomingCardJson
                    = individualCardJson.getAsJsonObject(CardStyleKey.KEY_INCOMING);
            Style incomingStyle
                    = parseIncomingOutgoingCardStyle(
                    cardType + "-" + CardStyleKey.KEY_INCOMING, Style.INCOMING, incomingCardJson
            );
            StyleFactory.getInstance().register(incomingStyle);
        }

        if (individualCardJson.has(CardStyleKey.KEY_OUTGOING)) {
            JsonObject outgoingCardJson
                    = individualCardJson.getAsJsonObject(CardStyleKey.KEY_OUTGOING);
            Style outgoingCartStyle
                    = parseIncomingOutgoingCardStyle(
                    cardType + "-" + CardStyleKey.KEY_OUTGOING, Style.OUTGOING, outgoingCardJson);
            StyleFactory.getInstance().register(outgoingCartStyle);
        }

    }

    private Style parseIncomingOutgoingCardStyle(
            String cardStyleTempType, int type, JsonObject incomingOutgoingCardJson) {

        Style.Builder styleBuilder = new Style.Builder(cardStyleTempType, type);

        List<Style> componentStyle = new ArrayList<>();

        if (incomingOutgoingCardJson.has(KEY_STYLE)) {
            JsonObject mainStyle = incomingOutgoingCardJson.getAsJsonObject(KEY_STYLE);

            if (mainStyle.has(CardStyleKey.KEY_BACKGROUND_COLOR)) {
                styleBuilder.setBackgroundColor(
                        mainStyle.get(CardStyleKey.KEY_BACKGROUND_COLOR).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_BACKGROUND_IMAGE)) {
                styleBuilder.setBackgroundImage(
                        mainStyle.get(CardStyleKey.KEY_BACKGROUND_IMAGE).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_BACKGROUND_COLOR_SHAPE)) {
                styleBuilder.setBackgroundColorShape(
                        mainStyle.get(CardStyleKey.KEY_BACKGROUND_COLOR_SHAPE).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_PADDING_LEFT)) {
                styleBuilder.setPaddingLeft(
                        Integer.parseInt(
                                mainStyle.get(CardStyleKey.KEY_PADDING_LEFT).getAsString()));
            }

            if (mainStyle.has(CardStyleKey.KEY_PADDING_RIGHT)) {
                styleBuilder.setPaddingRight(
                        Integer.parseInt(
                                mainStyle.get(CardStyleKey.KEY_PADDING_RIGHT).getAsString()));
            }

            if (mainStyle.has(CardStyleKey.KEY_MESSAGE_TIME_COLOR)) {
                styleBuilder.setMessageTimeColor(
                        mainStyle.get(CardStyleKey.KEY_MESSAGE_TIME_COLOR).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_MESSAGE_SENDER_COLOR)) {
                styleBuilder.setMessageSenderColor(
                        mainStyle.get(CardStyleKey.KEY_MESSAGE_SENDER_COLOR).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_BOT_IMAGE)) {
                styleBuilder.setBotImage(mainStyle.get(CardStyleKey.KEY_BOT_IMAGE).getAsString());
            }

            if (mainStyle.has(CardStyleKey.KEY_MAX_WIDTH)) {
                styleBuilder.setMaxWidth(
                        Integer.parseInt(mainStyle.get(CardStyleKey.KEY_MAX_WIDTH).getAsString()));
            }
        }

        if (incomingOutgoingCardJson.has(CardStyleKey.KEY_TITLE)) {
            JsonObject titleJson
                    = incomingOutgoingCardJson.getAsJsonObject(CardStyleKey.KEY_TITLE);
            Style titleStyle
                    = parseNoneSytle(CardStyleKey.KEY_TITLE, titleJson);
            componentStyle.add(titleStyle);
        }
        if (incomingOutgoingCardJson.has(CardStyleKey.KEY_SUB_TITLE)) {
            JsonObject subTitleJsonObj
                    = incomingOutgoingCardJson.getAsJsonObject(CardStyleKey.KEY_SUB_TITLE);
            Style subTitleStyle
                    = parseNoneSytle(CardStyleKey.KEY_SUB_TITLE, subTitleJsonObj);
            componentStyle.add(subTitleStyle);
        }

        if (incomingOutgoingCardJson.has(CardStyleKey.KEY_DESCRIPTION)) {
            JsonObject desJsonObj
                    = incomingOutgoingCardJson.getAsJsonObject(CardStyleKey.KEY_DESCRIPTION);
            Style descStyle
                    = parseNoneSytle(CardStyleKey.KEY_DESCRIPTION, desJsonObj);
            componentStyle.add(descStyle);
        }

        if (incomingOutgoingCardJson.has(CardStyleKey.KEY_BUTTON)) {
            JsonObject buttonJsonObj
                    = incomingOutgoingCardJson.getAsJsonObject(CardStyleKey.KEY_BUTTON);
            Style btnStyle = parseNoneSytle(CardStyleKey.KEY_BUTTON, buttonJsonObj);
            componentStyle.add(btnStyle);
        }


        //adding all components
        for (int i = 0; i < componentStyle.size(); i++) {
            styleBuilder.addComponentStyle(componentStyle.get(i));
        }

        Style style = styleBuilder.build();
        return style;
    }


    private Style parseNoneSytle(String templateType, JsonObject msgTimeStyleJsonObj) {
        if (msgTimeStyleJsonObj.has(ConfigParser.KEY_STYLE)) {
            msgTimeStyleJsonObj
                    = msgTimeStyleJsonObj.get(ConfigParser.KEY_STYLE).getAsJsonObject();
        }

        Style.Builder builder = new Style.Builder(templateType, Style.NONE);
        if (msgTimeStyleJsonObj.has(CardStyleKey.KEY_POSITION)) {
            builder.setPosition(msgTimeStyleJsonObj.get(CardStyleKey.KEY_POSITION).getAsString());
        }
        if (msgTimeStyleJsonObj.has(CardStyleKey.KEY_SENT_IMAGE)) {
            builder.setSentImage(
                    msgTimeStyleJsonObj.get(CardStyleKey.KEY_SENT_IMAGE).getAsString());
        }
        if (msgTimeStyleJsonObj.has(CardStyleKey.KEY_DELIVERED_IMAGE)) {
            builder.setDeliveredImage(
                    msgTimeStyleJsonObj.get(CardStyleKey.KEY_DELIVERED_IMAGE).getAsString());
        }
        if (msgTimeStyleJsonObj.has(CardStyleKey.KEY_TEXT_COLOR)) {
            builder.setTextColor(
                    msgTimeStyleJsonObj.get(CardStyleKey.KEY_TEXT_COLOR).getAsString());
        }

        return builder.build();
    }
}
