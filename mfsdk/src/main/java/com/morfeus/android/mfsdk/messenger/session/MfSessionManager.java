package com.morfeus.android.mfsdk.messenger.session;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Repository for Chat SessionModel.
 */
public class MfSessionManager {

    private static MfSessionManager sInstance;

    private HashMap<String, MfSession> mMapChatIdSession;

    private MfSessionManager() {
        mMapChatIdSession = new HashMap<>();
    }

    public static MfSessionManager getInstance() {
        if (sInstance == null)
            sInstance = new MfSessionManager();
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public void addChatSession(@NonNull MfSession mfSession) {
        SessionModel session = mfSession.getData();
        mMapChatIdSession.put(session.getChatId(), mfSession);
    }

    public void removeChatSession(String chatId) {
        mMapChatIdSession.remove(chatId);
    }

    public void clearSessions() {
        mMapChatIdSession = null;
    }

    public List<MfSession> getSessions() {
        return new ArrayList<>(mMapChatIdSession.values());
    }

    public MfSession getSession(String chatId) {
        return mMapChatIdSession.get(chatId);
    }

    public void clear() {
        mMapChatIdSession.clear();
    }
}
