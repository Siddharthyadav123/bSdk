package com.morfeus.android.mfsdk.ui.screen;

import android.support.annotation.NonNull;

public interface BaseView<T> {
    void setPresenter(@NonNull T presenter);
}
