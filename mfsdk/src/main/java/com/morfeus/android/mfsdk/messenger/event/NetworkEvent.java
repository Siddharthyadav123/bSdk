package com.morfeus.android.mfsdk.messenger.event;

/**
 * This event occurs when device network status get change
 */
public final class NetworkEvent {
    private final boolean isConnected;

    public NetworkEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
