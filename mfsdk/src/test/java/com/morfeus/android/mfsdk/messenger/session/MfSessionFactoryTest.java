package com.morfeus.android.mfsdk.messenger.session;

import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MfSessionFactoryTest {
    private MfSessionFactory mFactory;

    @Before
    public void setUp() throws Exception {
        mFactory = MfSessionFactory.getInstance();
    }

    @Test
    public void testCreateSession() {
        SessionModel mockSession = mock(SessionModel.class);
        when(mockSession.getUserId()).thenReturn("1112222");
        when(mockSession.getChatId()).thenReturn("2222");
        when(mockSession.getXCRFToken()).thenReturn("XKFOOEMLODMEKDLFOFFKDH");
        when(mockSession.getCookies()).thenReturn("DDDSS");

        MfSession session = mFactory.createSession(
                MfSession.SINGLE_USER_SESSION,
                mockSession,
                RuntimeEnvironment.application.getApplicationContext()
        );
        assertNotNull(session);
        SessionModel actualSession = session.getData();
        assertEquals(mockSession, actualSession);
    }

}