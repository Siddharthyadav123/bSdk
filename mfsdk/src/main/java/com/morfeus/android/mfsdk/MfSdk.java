package com.morfeus.android.mfsdk;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.messenger.source.exception.MfMsgModelException;

/**
 * Interface represents the set api exposed by MFSdk.
 */
public interface MfSdk {

    /**
     * Register new chat bubble view to display.
     *
     * @param template {@link Template} class containing required param to register template.
     * @throws MfMsgModelException thrown when Template format is invalid.
     */
    void registerTemplate(@NonNull Template template) throws MfMsgModelException;

    /**
     * Initialize sdk with provided app session token.
     *
     * @throws MfSdkInitializationException thrown when any fail to initialize sdk.
     */
    void initWithAppSessionToken() throws MfSdkInitializationException;

    /**
     * Opens chat conversation screen.
     *
     * @param ctx
     * @param language: User can pass null if multi language not supported
     */
    void showConversation(Context ctx, String language);

    interface KeepAliveCallback {
        void onKeepAlive(boolean keepAlive);
    }
}
