package com.morfeus.android.mfsdk.messenger.message;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.messenger.session.MfSession;
import com.morfeus.android.mfsdk.messenger.session.MfSessionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MessageManagerTest {
    private MessageManager mMessageManager;
    private MfSessionManager mMockSessionManager;

    @Before
    public void setUp() throws Exception {
        mMockSessionManager = mock(MfSessionManager.class);
        mMessageManager = MessageManager.getInstance(
                RuntimeEnvironment.application.getApplicationContext(),
                mMockSessionManager);
    }

    @Test
    public void testSendMessage_addMessageInChatSession() {
        MfSession mockSession = mock(MfSession.class);
        when(mMockSessionManager.getSession("chatId001")).thenReturn(mockSession);
        mMessageManager.addMessage("chatId001", "message", "type", 1 + "", false);
        verify(mMockSessionManager, times(1)).getSession("chatId001");
    }
}