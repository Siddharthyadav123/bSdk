package com.morfeus.android.mfsdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.morfeus.android.mfsdk.core.MfSdkCore;
import com.morfeus.android.mfsdk.core.MfSdkCoreInitializationException;
import com.morfeus.android.mfsdk.ui.MfSdkUI;
import com.morfeus.android.mfsdk.ui.MfSdkUIInitializationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MfSdkTest {
    Context mMockContext;

    @Before
    public void setUp() {
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
    }

    @Test
    public void testInitializeMfSdk_initializeMfSdkUIAndMfSdkCoreModule() throws MfSdkCoreInitializationException, MfSdkUIInitializationException {
        MfSdkCore mockMfSdkCore = mock(MfSdkCore.class);
        MfSdkUI mockMfSdkUI = mock(MfSdkUI.class);

//        ModuleInjector.setsMfSdkCore(mockMfSdkCore);
//        ModuleInjector.setsMfSdkUI(mockMfSdkUI);

//        MfSdkKit.getInstance(mMockContext);
//
//        verify(mockMfSdkCore).init();
//        verify(mockMfSdkUI).init();
//
//        MfSdkKit.destroyInstance();
    }


}