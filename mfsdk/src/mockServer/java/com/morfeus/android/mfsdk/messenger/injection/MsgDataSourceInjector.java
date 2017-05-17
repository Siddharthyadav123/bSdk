package com.morfeus.android.mfsdk.messenger.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.ManagerFactory;
import com.morfeus.android.mfsdk.messenger.ManagerMediator;
import com.morfeus.android.mfsdk.messenger.ManagerMediatorImpl;
import com.morfeus.android.mfsdk.messenger.source.MsgDataSource;
import com.morfeus.android.mfsdk.messenger.source.net.request.MfURLFactory;
import com.morfeus.android.mfsdk.messenger.source.net.request.RequestFactory;
import com.morfeus.android.mfsdk.messenger.source.parser.MfMsgParser;
import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MsgDataSourceInjector {

    /**
     * Please provide application context
     */
    public static MsgDataSource provideMsgDataSource(@NonNull Context context) {
        checkNotNull(context);

        MsgParser msgParser = MfMsgParser.getInstance();
        return MsgDataSource.createInstance(context, msgParser);
    }

    public static ManagerMediator provideManagerMediator(@NonNull Context context) {
        checkNotNull(context);
        ManagerFactory managerFactory = ManagerFactory.getInstance();
        MfURLFactory urlFactory = MfURLFactory.getInstance();
        RequestFactory requestFactory = RequestFactory.getInstance(urlFactory);
        return ManagerMediatorImpl.createInstance(managerFactory, requestFactory);
    }

    public static void setMsgDataSource(MsgDataSource msgDataSource) {
        // No-op
    }


}
