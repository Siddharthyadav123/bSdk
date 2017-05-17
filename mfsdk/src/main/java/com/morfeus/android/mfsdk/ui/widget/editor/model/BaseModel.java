package com.morfeus.android.mfsdk.ui.widget.editor.model;

import android.os.Parcelable;

import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

public interface BaseModel extends Parcelable {

    Style getStyle();

    String getId();

    @Component.Action
    String getAction();
}
