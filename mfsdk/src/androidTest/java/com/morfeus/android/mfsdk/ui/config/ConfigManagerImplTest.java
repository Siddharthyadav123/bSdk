package com.morfeus.android.mfsdk.ui.config;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParserProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ConfigManagerImplTest {

    Context mockContext;

    ConfigManager mConfigManager;

    ConfigParserProvider mockParserProvider;

    ConfigUtils mockConfigUtils;

    @Before
    public void setUp() throws Exception {
        mockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        mockParserProvider = mock(ConfigParserProvider.class);
        mockConfigUtils = mock(ConfigUtils.class);
        mConfigManager = ConfigManagerImpl.createInstance(
                mockContext,
                mockParserProvider,
                mockConfigUtils);

    }

    @Test
    public void testLoadConfig_callConfigReaderToReadConfig() {
        ConfigParser screenParser = mock(ConfigParser.class);
        ConfigParser styleParser = mock(ConfigParser.class);

        when(mockParserProvider.getParser(
                ConfigParserProvider.SCREEN_CONFIG_PARSER)
        ).thenReturn(screenParser);

        when(mockParserProvider.getParser(
                ConfigParserProvider.CARD_STYLE_CONFIG_PARSER)
        ).thenReturn(styleParser);

        try {
            mConfigManager.loadConfig(mock(ConfigManager.OnConfigLoad.class));
        } catch (ConfigLoadException e) {
            e.printStackTrace();
        }

        verify(screenParser).parse(anyString());
        verify(styleParser).parse(anyString());
    }

    @After
    public void tearDown() throws Exception {
        ConfigManagerImpl.destroyInstance();
    }

}