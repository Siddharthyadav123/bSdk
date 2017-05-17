package com.morfeus.android.mfsdk.messenger.session;

import android.content.Context;
import android.os.Handler;

import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.MessengerConfig;
import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.event.TimeoutEvent;


/**
 * Responsible to perform timeout after an specified interval
 */
public class MfTimeoutWorkerThread {
    private static final String TAG = MfTimeoutWorkerThread.class.getSimpleName();
    private static MfTimeoutWorkerThread sInstance;
    private static Context mContext;
    private long timeoutInMillis;

    private Handler mTimeoutHandler;

    private MfTimeoutWorkerThread(Context context) {
        mContext = context;
        MFSDKProperties sdkProperties = MessengerConfig.getInstance().getSdkProperties();
        timeoutInMillis = sdkProperties.getSessionTimeoutInSecs() * 1000;
    }

    public static synchronized MfTimeoutWorkerThread getInstance(Context context) {
        if (sInstance == null)
            sInstance = new MfTimeoutWorkerThread(context);
        return sInstance;
    }

    public void initTimeoutTread() {
        mTimeoutHandler = new Handler();
        LogManager.d(TAG, "Timeout: init");
        mTimeoutHandler.postDelayed(mTimoutThreadRunnable, timeoutInMillis);
    }

    public void refreshTimeoutTread() {
        if (mTimeoutHandler == null) {
            mTimeoutHandler = new Handler();
        } else {
            mTimeoutHandler.removeCallbacks(mTimoutThreadRunnable);
        }
        LogManager.d(TAG, "Timeout: refreshed");
        mTimeoutHandler.postDelayed(mTimoutThreadRunnable, timeoutInMillis);
    }

    public void destroyTimeoutThread() {
        if (mTimeoutHandler != null) {
            mTimeoutHandler.removeCallbacks(mTimoutThreadRunnable);
        }
        LogManager.d(TAG, "Timeout: destroyed");
        mTimeoutHandler = null;
        sInstance = null;
    }

    private Runnable mTimoutThreadRunnable = new Runnable() {
        @Override
        public void run() {
            LogManager.d(TAG, "Timeout: happens");
            ModuleEventBus.getDefault().post(new TimeoutEvent(true));
        }
    };

}
