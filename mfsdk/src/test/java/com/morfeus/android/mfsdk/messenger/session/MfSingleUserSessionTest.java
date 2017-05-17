package com.morfeus.android.mfsdk.messenger.session;

import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.messenger.utils.UniqueBlockingQueue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@PrepareForTest(MfConnectionManager.class)
public class MfSingleUserSessionTest {
    private static final OutgoingMsgModel TEST_MESSAGE
            = new OutgoingMsgModel(023034, "hi", "text", 10101 + "");
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    private MfSession mMfSingleUserSession;
    private SessionModel mMockSession;
    private UniqueBlockingQueue<Object> mMockActualMessageQueue;
    private UniqueBlockingQueue<OutgoingMsgModel> mMockOfflineMessageQueue;
    private MfConnectionManager mMockConnectionManager;

    @Before
    public void setUp() throws Exception {

        mMockSession = mock(SessionModel.class);
        mMockActualMessageQueue = mock(UniqueBlockingQueue.class);
        mMockOfflineMessageQueue = mock(UniqueBlockingQueue.class);
        mMockConnectionManager = PowerMockito.mock(MfConnectionManager.class);

        mMfSingleUserSession = new MfSingleUserSession(
                RuntimeEnvironment.application.getApplicationContext(),
                mMockSession,
                mMockActualMessageQueue,
                mMockOfflineMessageQueue,
                mMockConnectionManager,
                NetUtil.getInstance()
        );
    }

    @Test
    public void testQueue_addMessage() {
        OutgoingMsgModel outgoingMsgModel = mock(OutgoingMsgModel.class);

        mMfSingleUserSession.addMessage(outgoingMsgModel);
        verify(mMockActualMessageQueue, times(1)).add(any(OutgoingMsgModel.class));

        mMfSingleUserSession.clearMessages();
    }

    @Test
    public void testQueue_removeMessage() {
        mMfSingleUserSession.addMessage(TEST_MESSAGE);
        mMfSingleUserSession.addMessage(mock(OutgoingMsgModel.class));

        mMfSingleUserSession.removeMessage(TEST_MESSAGE);
        verify(mMockActualMessageQueue, times(1)).remove(TEST_MESSAGE);
        mMfSingleUserSession.clearMessages();
    }

    @Test
    public void testAddMessage_sendMessageRequest() {
        when(mMockActualMessageQueue.add(TEST_MESSAGE)).thenReturn(true);
        mMfSingleUserSession.addMessage(TEST_MESSAGE);

        verify(mMockConnectionManager, times(1)).sendMessage(
                TEST_MESSAGE,
                mMfSingleUserSession,
                any(MfSession.OnMessageSendListener.class));

        mMfSingleUserSession.clearMessages();
    }
}