package com.morfeus.android.mfsdk.ui.config.parser;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ConfigParserProvider {
    static final int INPUT_VIEW_CONFIG_PARSER = 1;
    static final int TOOLBAR_VIEW_CONFIG_PARSER = 2;
    static final int SUGGESTION_VIEW_CONFIG_PARSER = 3;
    static final int SUBVIEW_CONFIG_PARSER = 4;
    static final int HEADER_CONFIG_PARSER = 5;
    static final int MF_EDITOR_CONFIG_PARSER = 6;
    public static final int CARD_STYLE_CONFIG_PARSER = 7;
    public static final int SCREEN_CONFIG_PARSER = 8;
    public static final int LOGIN_SCREEN_CONFIG_PARSER = 9;
    public static final int OTP_SCREEN_CONFIG_PARSER = 10;
    public static final int MF_TEXT_RESOURCE_CONFIG_PARSER = 11;
    public static final int LOCATION_CONFIG_PARSER = 12;

    private static ConfigParserProvider sInstance;

    private ConfigParserProvider() {
        // No-op
    }

    public static ConfigParserProvider getInstance() {
        if (sInstance == null)
            sInstance = new ConfigParserProvider();
        return sInstance;
    }

    public ConfigParser getParser(@Parser int parser) {
        switch (parser) {
            case INPUT_VIEW_CONFIG_PARSER:
                return InputViewConfigParser.getInstance();
            case TOOLBAR_VIEW_CONFIG_PARSER:
                return ToolbarViewConfigParser.getInstance();
            case SUGGESTION_VIEW_CONFIG_PARSER:
                return SuggestionViewConfigParser.getInstance();
            case SUBVIEW_CONFIG_PARSER:
                return SubViewConfigParser.getInstance();
            case MF_EDITOR_CONFIG_PARSER:
                return MfEditorConfigParser.getInstance();
            case HEADER_CONFIG_PARSER:
                return HeaderConfigParser.getInstance();
            case CARD_STYLE_CONFIG_PARSER:
                return CardStyleParser.getInstance();
            case SCREEN_CONFIG_PARSER:
                return ScreenConfigParser.getInstance();
            case LOGIN_SCREEN_CONFIG_PARSER:
                return LoginConfigParser.getInstance();
            case OTP_SCREEN_CONFIG_PARSER:
                return OTPConfigParser.getInstance();
            case MF_TEXT_RESOURCE_CONFIG_PARSER:
                return TextResourceConfigParser.getInstance();
            case LOCATION_CONFIG_PARSER:
                return LocationConfigParser.getInstance();
            default:
                throw new IllegalArgumentException("Error: invalid parser type!");
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            INPUT_VIEW_CONFIG_PARSER,
            TOOLBAR_VIEW_CONFIG_PARSER,
            SUGGESTION_VIEW_CONFIG_PARSER,
            SUBVIEW_CONFIG_PARSER,
            HEADER_CONFIG_PARSER,
            MF_EDITOR_CONFIG_PARSER,
            CARD_STYLE_CONFIG_PARSER,
            SCREEN_CONFIG_PARSER,
            LOGIN_SCREEN_CONFIG_PARSER,
            MF_TEXT_RESOURCE_CONFIG_PARSER
    })
    @interface Parser {
    }
}
