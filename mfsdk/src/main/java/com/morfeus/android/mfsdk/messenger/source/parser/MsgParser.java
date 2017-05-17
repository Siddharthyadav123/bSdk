package com.morfeus.android.mfsdk.messenger.source.parser;

/**
 * Parse Message received from server
 */
public interface MsgParser {

    String KEY_MESSAGES = "messages";
    String KEY_TO = "to";
    String KEY_FROM = "from";
    String KEY_MESSAGE_ID = "id";
    String KEY_MESSAGE = "message";

    String KEY_TYPE = "type";
    String KEY_TEXT = "text";
    String KEY_THUMB = "thumb";
    String KEY_URL = "url";

    String KEY_LAT = "latitude";
    String KEY_LONG = "longitude";

    String KEY_TEXT_TO_SPEECH = "textToSpeech";
    String KEY_TTS_TEXT = "TTSText";

    String KEY_CARD = "card";
    String KEY_TEMPLATE_TYPE = "templateType";
    String KEY_CONTENT = "content";
    String KEY_ELEMENT = "element";


    String KEY_TITLE = "title";
    String KEY_STYLE = "style";

    String KEY_SUBTITLE = "subtitle";
    String KEY_DESCRIPTION = "description";
    String KEY_IMAGE = "image";


    String KEY_CARD_IMAGE = "cardimage";
    String KEY_IMAGE_TYPE = "imagetype";
    String KEY_IMAGE_NAME = "imageName";
    String KEY_IMAGE_URL = "imageUrl";

    String KEY_IMAGE_NAME_N_CAPITAL = "imageName";
    String KEY_IMAGE_URL_U_CAPITAL = "imageUrl";


    String KEY_BUTTONS = "buttons";
    String KEY_ACTION = "action";
    String KEY_PAYLOAD = "payload";
    String KEY_TEXTCOLOR = "textcolor";
    String KEY_TEXTSIZE = "textsize";
    String KEY_BACKGROUND_COLOR = "background-color";

    String KEY_ITEMS = "items";
    String KEY_CELL_IMAGE = "cellimage";
    String KEY_BUTTON = "button";
    String KEY_BUTTON_MORE = "buttonmore";

    String KEY_ELEMENT_STYLE = "elementStyle";

    String KEY_LABEL_1 = "label1";
    String KEY_DETAILS_1 = "details1";
    String KEY_LABEL_2 = "label2";
    String KEY_DETAILS_2 = "details2";

    String KEY_SLIDER = "slider";
    String KEY_MINIMUN_VALUE = "minimumValue";
    String KEY_MAXIMUM_VALUE = "maximumValue";
    String KEY_INTERVAL = "interval";
    String KEY_THUMB_IMAGE = "thumbImage";
    String KEY_SLIDER_COLOR = "sliderColor";

    String KEY_ELEMENT_ARRAY = "elementArray";

    String KEY_GAUGE = "gauge";
    String KEY_COVER_PERCENTAGE = "coverPercentage";
    String KEY_FILL_COLOR = "fill-color";
    String KEY_SUB_TEXT = "subText";
    String KEY_DISPLAY = "display";


    String KEY_VIDEO = "video";
    String KEY_VIDEO_TYPE = "videoType";
    String KEY_VIDEO_NAME = "videoName";
    String KEY_VIDEO_URL = "videoUrl";
    String KEY_PLAY_IMAGE = "play-image";

    String KEY_MAP = "map";
    String KEY_LATITUDE = "latitude";
    String KEY_LONGITUDE = "longitude";
    String KEY_PIN_IMAGE = "pin-image";


    String KEY_RATE = "rate";
    String KEY_VALUE = "value";
    String KEY_RATE_ICON = "rateIcon";
    String KEY_RATE_OFF = "rateOff";


    Object parse(boolean isIncoming, String jsonStr);
}
