package com.morfeus.android.mfsdk.ui.screen;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.screen.exception.UnsupportedScreenException;

/**
 * This interface represents the set of api exposed by {@link ScreenManager}.
 */
public interface ScreenManager {

    void launch(@NonNull Screen screen) throws UnsupportedScreenException;
}
