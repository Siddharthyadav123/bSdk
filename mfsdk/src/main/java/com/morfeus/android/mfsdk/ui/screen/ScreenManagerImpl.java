package com.morfeus.android.mfsdk.ui.screen;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.screen.entity.ChatScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.screen.exception.UnsupportedScreenException;

/**
 * Manages the screen which are being/are displayed.
 */
public class ScreenManagerImpl implements ScreenManager {

    private static ScreenManagerImpl INSTANCE;
    private Screen mCurrentScreen;

    public static ScreenManagerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScreenManagerImpl();
        }
        return INSTANCE;
    }


    @Override
    public void launch(@NonNull Screen screen) throws UnsupportedScreenException {
        //TODO strategy
        switch (screen.getType()) {
            case Screen.CHAT_SCREEN:
                //if (mCurrentScreen == null) {
                Context context = ((ChatScreen) screen).getParentContext();
                ChatActivity.start(
                        context,
                        ((ChatScreen) screen).getWelcomeMessage(),
                        ((ChatScreen) screen).getSecondaryMessage(),
                        ((ChatScreen) screen).getChatScreenModel());
                mCurrentScreen = screen;
                break;
            default:
                throw new UnsupportedScreenException("Error: This screen not defined!");
        }
    }
}
