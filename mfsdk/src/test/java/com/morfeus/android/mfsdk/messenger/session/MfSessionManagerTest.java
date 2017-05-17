package com.morfeus.android.mfsdk.messenger.session;

import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MfSessionManagerTest {

    private MfSession mMockSession;

    @Before
    public void setUp() throws Exception {
        mMockSession = mock(MfSession.class);
    }

    @Test
    public void testAddSession() {
        MfSessionManager.destroyInstance();
        MfSessionManager manager = MfSessionManager.getInstance();

        SessionModel mockSession = mock(SessionModel.class);
        when(mMockSession.getData()).thenReturn(mockSession);
        when(mockSession.getChatId()).thenReturn("chatId1");

        manager.addChatSession(mMockSession);
        int n = manager.getSessions().size();
        assertEquals(1, n);

        when(mockSession.getChatId()).thenReturn("chatId2");
        manager.addChatSession(mMockSession);
        n = manager.getSessions().size();
        assertEquals(2, n);
    }

    @Test
    public void testRemoveSession() {
        MfSessionManager.destroyInstance();
        MfSessionManager manager = MfSessionManager.getInstance();

        SessionModel mockSession = mock(SessionModel.class);
        when(mMockSession.getData()).thenReturn(mockSession);
        when(mockSession.getChatId()).thenReturn("chatId1");

        manager.addChatSession(mMockSession);
        int n = manager.getSessions().size();
        assertEquals(1, n);

        manager.removeChatSession("chatId1");
        n = manager.getSessions().size();
        assertEquals(0, n);

    }

    @Test
    public void testClear() {
        MfSessionManager.destroyInstance();
        MfSessionManager manager = MfSessionManager.getInstance();

        SessionModel mockSession = mock(SessionModel.class);
        when(mMockSession.getData()).thenReturn(mockSession);
        when(mockSession.getChatId()).thenReturn("chatId1");

        manager.addChatSession(mMockSession);
        int n = manager.getSessions().size();
        assertEquals(1, n);

        manager.clear();

        n = manager.getSessions().size();
        assertEquals(0, n);
    }

    @Test
    public void testGetSession() {
        MfSessionManager.destroyInstance();
        MfSessionManager manager = MfSessionManager.getInstance();

        SessionModel mockSession = mock(SessionModel.class);
        when(mMockSession.getData()).thenReturn(mockSession);
        when(mockSession.getChatId()).thenReturn("chatId1");

        manager.addChatSession(mMockSession);
        int n = manager.getSessions().size();
        assertEquals(1, n);

        MfSession actualSession = manager.getSession("chatId1");
        assertEquals(mMockSession, actualSession);
    }
}