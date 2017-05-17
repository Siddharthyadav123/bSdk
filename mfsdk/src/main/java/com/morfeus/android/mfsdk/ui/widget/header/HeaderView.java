package com.morfeus.android.mfsdk.ui.widget.header;

import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.header.style.Style;

public interface HeaderView {

    void initView();

    void setStyle(Style style);

    ActionbarModel getData();

    void setData(@NonNull ActionbarModel model);

    void setHeaderTitle(@NonNull String title);

    void setHeaderSubTitle(@NonNull String subTitle);

    void setProfileImage(@NonNull String imageName);

    /**
     * Set state of actionbar
     *
     * @param state state to change.
     */
    void setState(@ActionbarHeaderView.State int state);

}
