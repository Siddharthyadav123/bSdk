package com.morfeus.android.mfsdk.messenger;

import org.greenrobot.eventbus.EventBus;

public class ModuleEventBus extends EventBus {
    private static volatile EventBus sDefaultInstance;

    public static EventBus getDefault() {
        if (sDefaultInstance == null) {
            synchronized (EventBus.class) {
                if (sDefaultInstance == null) {
                    sDefaultInstance = builder().build();
                }
            }
        }
        return sDefaultInstance;
    }
}
