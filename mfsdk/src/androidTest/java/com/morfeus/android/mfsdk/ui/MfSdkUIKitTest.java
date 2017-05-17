package com.morfeus.android.mfsdk.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MfSdkUIKitTest {

    private static final String TEST_TEMPLATE_ID = "temp_01";
    private Context mockContext;
    private ConfigManager mockConfigManager;
    private TemplateFactory mockTemplateFactory;
    private ActionManager mockActionManager;
    private TemplateView mockTemplateView;
    private MfSdkUI mfSdkUI;

    @Before
    public void setUp() throws Exception {
        mockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        mockConfigManager = mock(ConfigManager.class);
        mockActionManager = mock(ActionManager.class);
        mockTemplateFactory = mock(TemplateFactory.class);
        mockTemplateView = mock(TemplateView.class);

        mfSdkUI = new MfSdkUIKit.Builder(mockContext)
                .setActionManager(mockActionManager)
                .setConfigManager(mockConfigManager)
                .setTemplateFactory(mockTemplateFactory)
                .build();
    }

    @Test
    public void testInit_callConfigManagerToLoadConfig() throws ConfigLoadException, MfSdkUIInitializationException {
        mfSdkUI.init();
        verify(mockConfigManager, times(1)).loadConfig(mock(ConfigManager.OnConfigLoad.class));
    }

    @Test
    public void testRegisterTemplate_registerTemplateInTemplateFactory() {
        ArgumentCaptor<String> templateIDArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TemplateView> templateViewArgumentCaptor
                = ArgumentCaptor.forClass(TemplateView.class);

        mfSdkUI.registerTemplate(TEST_TEMPLATE_ID, mockTemplateView);
        verify(mockTemplateFactory).registerTemplate(
                templateIDArg.capture(), templateViewArgumentCaptor.capture());

        assertEquals(TEST_TEMPLATE_ID, templateIDArg.getValue());
        assertEquals(mockTemplateView, templateViewArgumentCaptor.getValue());
    }

}