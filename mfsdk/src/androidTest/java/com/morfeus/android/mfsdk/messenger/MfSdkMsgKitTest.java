package com.morfeus.android.mfsdk.messenger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.messenger.injection.MsgDataSourceInjector;
import com.morfeus.android.mfsdk.messenger.source.MsgDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MfSdkMsgKitTest {

    private MfSdkMsgKit mMfSdkMsgKit;
    private MsgDataSource mMockMsgDataSource;
    private Context mockContext;

    @Before
    public void setUp() throws Exception {
        mockContext = InstrumentationRegistry.getTargetContext();
        mMockMsgDataSource = mock(MsgDataSource.class);
        MsgDataSourceInjector.setMsgDataSource(mMockMsgDataSource);

        HashMap<String, String> mapBotId = new HashMap<String, String>();
        mapBotId.put("botId", "11a80171753554");
        MFSDKProperties properties = new MFSDKProperties(
                mapBotId, "", 1, "", "", "", true, true);
        String appSessionToken = "2312312";
        String customerId = "50263281";

        mMfSdkMsgKit = MfSdkMsgKit.createInstance(
                mockContext,
                appSessionToken,
                customerId,
                properties);
    }

    @Test
    public void testMfSdkMsgKit_InteractionWithMsgDataSource() throws MfSdkMsgInitializationException {
        mMfSdkMsgKit.init();
        verify(mMockMsgDataSource, times(1)).loadDefaultMsg();
        mMfSdkMsgKit.getDefaultMsg();
        verify(mMockMsgDataSource, times(1)).getDefaultMsg();
    }

}