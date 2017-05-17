package com.morfeus.android.mfsdk.ui.widget.bubble.inject;

import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;

public final class Injection {

    public static StyleFactory provideStyleFactory() {
        return StyleFactory.getInstance();
    }
}
