package com.morfeus.android.mfsdk.ui.widget.loading;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;

public interface LoadingView {
    void initView();

    void setData(@NonNull LoadingModel model);

    LoadingModel getData();

    void setLoadingText(@NonNull String title);

    void setLoadingImage(@NonNull String imageName);
}
