package com.morfeus.android.mfsdk.ui.config.parser;


import android.support.annotation.NonNull;

/**
 * Provides configuration provided into config.json.
 */
//TODO-DESIGN need to perform on non-ui thread.
//TODO move config constant to build.gradle
public interface ConfigParser {

    String KEY_ID = "id";
    String KEY_SCREEN = "screen";
    String KEY_BODY = "body";
    String KEY_CARD_UI_TYPE = "cardUIType";
    String KEY_SCREEN_UI_TYPE = "screenUIType";
    String KEY_FOOTER = "footer";
    String KEY_INPUT_VIEW = "inputview";
    String KEY_STYLE = "style";
    String KEY_TEXT_INPUT = "textinput";
    String KEY_LEFT_BUTTON = "leftbutton";
    String KEY_RIGHT_BUTTON = "rightbutton";

    String KEY_INIT_VIEW = "initView";
    String KEY_LOGO = "logo";
    String KEY_LOADING_TEXT = "loadingText";
    String KEY_PROGRESS_VIEW = "progressView";
    String KEY_IMAGE_NAME = "imageName";
    String KEY_IMAGE_TYPE = "imageType";
    String KEY_IMAGE_URL = "imageUrl";
    String KEY_TEXT = "text";

    String KEY_LEFT_BUTTONS = "leftbuttons";
    String KEY_RIGHT_BUTTONS = "rightbuttons";

    String KEY_RIGHT_BUTTON1 = "rightbutton1";
    String KEY_RIGHT_BUTTON2 = "rightbutton2";
    String KEY_SEND_BUTTON = "sendButton";
    String KEY_SEND_BUTTON_B_SMALL = "sendbutton";
    String KEY_CANCEL_BUTTON = "cancelbutton";

    String KEY_IMAGE = "image";
    String KEY_ACTION = "action";
    String KEY_CONTENT = "content";

    String KEY_MAP = "map";
    String KEY_PIN_IMAGE = "pin-image";
    String KEY_SEND_LOCATION = "sendlocation";
    String KEY_SHOW_LOCATION = "showlocation";

    String KEY_HEIGHT = "height";
    String KEY_BACKGROUND_IMAGE = "background-image";
    String KEY_BACKGROUND_COLOR = "background-color";

    String KEY_TOOLBAR_VIEW = "toolbarview";
    String KEY_TYPE = "type";
    String KEY_IMAGE_SEL = "image_sel";
    String KEY_SUB_VIEW = "subview";

    String KEY_MENU_VIEW = "menuview";
    String KEY_SMILEY_VIEW = "smileyview";
    String KEY_SETTINGS_VIEW = "settingsview";
    String KEY_FEEDBACK_VIEW = "feedbackview";
    String KET_VOICE_VIEW = "voiceview";

    String KEY_BUTTON = "button";
    String KEY_SHORTCUT_VIEW = "shortcutview";

    String KEY_BORDER_COLOR = "border-color";
    String KEY_BORDER_WIDTH = "border-width";
    String KEY_CORNER_RADIUS = "corner-radius";
    String KEY_LABEL_COLOR = "label-color";

    String KEY_HEADER = "header";
    String KEY_LABEL = "label";

    String KEY_HEADER_TEXT = "headerText";
    String KEY_SUB_HEADER_TEXT = "subheaderText";

    String KEY_COLOR = "color";
    String KEY_HORIZONTAL_ALIGNMENT = "horizontal-alignment";
    String KEY_PROFILE_IMAGE = "profileImage";

    String KEY_USER_NAME = "username";
    String KEY_PASSWORD = "password";
    String KEY_PLACEHOLDER_TEXT = "placeholderText";
    String KEY_TEXT_COLOR = "text-color";

    String KEY_PINPAD = "pinpad";
    String KEY_NUMBER_OF_CHARACTER = "numberOfCharater";
    String KEY_MASK_IMAGE = "mask-image";
    String KEY_UNMASK_IMAGE = "unmask-image";

    String KEY_PLACEHOLDER_HIFEN_TEXT = "placeholder-text";


    Object parse(@NonNull String jsonStr);

    /**
     * Set of key used to determine the style parameter of card
     */
    interface CardStyleKey {
        String KEY_CARD_STYLES = "cardStyles";
        String KEY_MESSAGE_TIME = "messagetime";
        String KEY_MESSAGE_SENDER = "messagesender";
        String KEY_MESSAGE_STATUS = "messagestatus";

        String KEY_POSITION = "position";
        String KEY_SENT_IMAGE = "sent-image";
        String KEY_DELIVERED_IMAGE = "delivered-image";

        String KEY_TEXT_COLOR = "text-color";

        String KEY_CARDS = "cards";
        String KEY_CARDS_TYPE = "cardType";
        String KEY_INCOMING = "incoming";
        String KEY_OUTGOING = "outgoing";

        String KEY_BACKGROUND_COLOR = "background-color";
        String KEY_BACKGROUND_IMAGE = "background-image";
        String KEY_BACKGROUND_COLOR_SHAPE = "background-color-shape";
        String KEY_PADDING_LEFT = "padding-left";
        String KEY_PADDING_RIGHT = "padding-right";
        String KEY_MESSAGE_TIME_COLOR = "messagetime-color";
        String KEY_MESSAGE_SENDER_COLOR = "messagesender-color";
        String KEY_BOT_IMAGE = "bot-image";
        String KEY_MAX_WIDTH = "max-width";

        String KEY_TITLE = "title";
        String KEY_SUB_TITLE = "subtitle";
        String KEY_DESCRIPTION = "description";
        String KEY_BUTTON = "button";

        String KEY_SHOW_AS = "showasbig";

    }

    interface TextResourceKey {
        String KEY_LANG = "lang";
        String KEY_RESOURCE = "resource";
    }
}
