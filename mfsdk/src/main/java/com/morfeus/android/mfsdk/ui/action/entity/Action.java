package com.morfeus.android.mfsdk.ui.action.entity;

import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.screen.entity.Screen;

/**
 * This interface represents the Action to be performed.
 */
// Builder
public interface Action {

    int LAUNCH_ACTION = 0;

    int getType();

    @Nullable Screen getScreen();
}
