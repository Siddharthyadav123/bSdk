package com.morfeus.android.mfsdk.messenger.event;

/**
 * This event occurs when user has not perform any action for specified time
 */
public class TimeoutEvent {
    private final boolean isAutoLogout;

    public TimeoutEvent(boolean autoLogout) {
        this.isAutoLogout = autoLogout;
    }

    public boolean isAutoLogout() {
        return isAutoLogout;
    }
}
