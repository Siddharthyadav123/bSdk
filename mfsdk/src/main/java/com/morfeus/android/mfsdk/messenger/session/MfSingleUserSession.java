package com.morfeus.android.mfsdk.messenger.session;

import android.content.Context;

import com.morfeus.android.mfsdk.messenger.message.model.OutgoingMsgModel;
import com.morfeus.android.mfsdk.messenger.session.model.SessionModel;
import com.morfeus.android.mfsdk.messenger.source.MfConnectionManager;
import com.morfeus.android.mfsdk.messenger.utils.NetUtil;
import com.morfeus.android.mfsdk.messenger.utils.UniqueBlockingQueue;

/**
 * Single User chat session
 */
public class MfSingleUserSession extends MfSession {

    public MfSingleUserSession(Context context,
                               SessionModel sessionModel,
                               UniqueBlockingQueue<Object> actualMessageQueue,
                               UniqueBlockingQueue<OutgoingMsgModel> offlineMessageQueue,
                               MfConnectionManager connectionManager,
                               NetUtil netUtil) {
        super(context, sessionModel, actualMessageQueue, offlineMessageQueue,
                connectionManager, netUtil);
    }

    @Override
    void invalidate() {
        // TODO invalidate this session, trash all the content
    }

}
