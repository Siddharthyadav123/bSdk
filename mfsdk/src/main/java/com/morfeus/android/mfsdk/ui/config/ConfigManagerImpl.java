package com.morfeus.android.mfsdk.ui.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParserProvider;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatScreenModel;
import com.morfeus.android.mfsdk.ui.screen.entity.ChatScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.LocationScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.LoginScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.OTPScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implements {@link ConfigManager} to provides configuration from ui-config.json.
 */
public final class ConfigManagerImpl extends ConfigManager {

    private static final String TAG = ConfigManagerImpl.class.getSimpleName();

    private static ConfigManagerImpl sInstance;

    private Context mContext;

    private ConfigParserProvider mParserProvider;

    private ConfigUtils mConfigUtils;

    private ConfigManagerImpl(@NonNull Context context,
                              @NonNull ConfigParserProvider parserProvider,
                              @NonNull ConfigUtils configUtils) {
        mContext = checkNotNull(context);
        mParserProvider = checkNotNull(parserProvider);
        mConfigUtils = checkNotNull(configUtils);

        mapIDScreen = new HashMap<>();
        mapIDSubView = new HashMap<>();
    }

    public static ConfigManager createInstance(@NonNull Context context,
                                               @NonNull ConfigParserProvider parserProvider,
                                               @NonNull ConfigUtils configUtils) {
        if (sInstance == null) {
            sInstance = new ConfigManagerImpl(context, parserProvider, configUtils);
        }
        return sInstance;
    }

    public static ConfigManager getInstance() {
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadConfig(OnConfigLoad callback) throws ConfigLoadException {


        String loginFileName = BuildConfig.UI_LOGIN_CONFIG_FILE;
        String loginConfigString = getConfigString(loginFileName);

        ConfigParser loginConfigParser
                = mParserProvider.getParser(ConfigParserProvider.LOGIN_SCREEN_CONFIG_PARSER);
        LoginModel loginModel
                = (LoginModel) loginConfigParser.parse(loginConfigString);


        String otpFileName = BuildConfig.UI_OTP_CONFIG_FILE;
        String otpConfigString = getConfigString(otpFileName);
        ConfigParser otpConfigParser
                = mParserProvider.getParser(ConfigParserProvider.OTP_SCREEN_CONFIG_PARSER);
        OTPModel otpModel
                = (OTPModel) otpConfigParser.parse(otpConfigString);


        String fileName = BuildConfig.UI_CONFIG_FILE;
        String configString = getConfigString(fileName);

        ConfigParser screenConfigParser
                = mParserProvider.getParser(ConfigParserProvider.SCREEN_CONFIG_PARSER);

        String styleFileName = BuildConfig.UI_DEFAULT_CARD_STYLE_FILE;
        String styleConfigString = getConfigString(styleFileName);
        ConfigParser styleConfigParser
                = mParserProvider.getParser(ConfigParserProvider.CARD_STYLE_CONFIG_PARSER);
        styleConfigParser.parse(styleConfigString);

        // parsing string and storing it into Language repository
        String languageFileName = BuildConfig.LANGUAGE_CONFIG_FILE;
        String languageConfigString = getConfigString(languageFileName);
        ConfigParser languageConfigParser
                = mParserProvider.getParser(ConfigParserProvider.MF_TEXT_RESOURCE_CONFIG_PARSER);
        languageConfigParser.parse(languageConfigString);

        String locationFileName = BuildConfig.LOCATION_CONFIG_FILE;
        String locationConfigString = getConfigString(locationFileName);
        ConfigParser locationConfigParser
                = mParserProvider.getParser(ConfigParserProvider.LOCATION_CONFIG_PARSER);
        LocationScreenModel locationScreenModel
                = (LocationScreenModel) locationConfigParser.parse(locationConfigString);

        // TODO currently returning ChatScreen, will change
        ChatScreenModel chatScreenModel = (ChatScreenModel) screenConfigParser.parse(configString);

        if (chatScreenModel == null)
            throw new ConfigLoadException("Error: ChatScreenModel can't be null!");
        if (chatScreenModel.getMfEditorViewModel() == null)
            throw new ConfigLoadException("Error: MfEditorModel con't be null!");
        if (chatScreenModel.getActionbarModel() == null)
            throw new ConfigLoadException("Error: ActionbarModel can't be null!");
        if (chatScreenModel.getBody() == null)
            throw new ConfigLoadException("Error: ChatScreen Body can't be null!");

        ChatScreen chatScreen = new ChatScreen(
                mContext,
                "Hi",
                "Hello",
                chatScreenModel);

        LoginScreen loginScreen = new LoginScreen(
                mContext,
                loginModel
        );

        OTPScreen otpScreen = new OTPScreen(
                mContext,
                otpModel
        );

        LocationScreen locationScreen = new LocationScreen(
                mContext,
                locationScreenModel
        );

        HashMap<String, Screen> mapScreen = new HashMap<>();
        mapScreen.put(Screen.CHAT_SCREEN, chatScreen);
        mapScreen.put(Screen.LOGIN_SCREEN, loginScreen);
        mapScreen.put(Screen.OTP_SCREEN, otpScreen);
        mapScreen.put(Screen.LOCATION_SCREEN, locationScreen);

        callback.onSuccess(mapScreen);
        isLoaded = true;
    }


    @NonNull
    private String getConfigString(String fileName) throws ConfigLoadException {
        String configJSONStr = mConfigUtils.loadJSONFromAsset(mContext, fileName);

        if (TextUtils.isEmpty(configJSONStr))
            throw new ConfigLoadException("Error: Failed to read MfUIConfig.json");
        return configJSONStr;
    }

}
