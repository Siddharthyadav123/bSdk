package com.morfeus.android.mfsdk.ui.action.entity;

import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.screen.entity.Screen;

/**
 * This class contains the details for launch action.
 */
public class LaunchAction implements Action {

    private Screen screen;

    public LaunchAction(Screen screen) {
        this.screen = screen;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Nullable
    @Override
    public Screen getScreen() {
        return screen;
    }
}
