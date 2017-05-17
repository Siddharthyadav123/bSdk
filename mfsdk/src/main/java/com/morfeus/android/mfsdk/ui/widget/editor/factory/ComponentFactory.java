package com.morfeus.android.mfsdk.ui.widget.editor.factory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

// TODO remove
public abstract class ComponentFactory {

    public abstract Component createComponent(@NonNull Context ctx,
                                       @Component.Type String type,
                                       @Nullable @Component.Action String action,
                                       @NonNull ActionManager actionManager,
                                       @NonNull BaseModel model,
                                       @Nullable Style style) throws ComponentNotFoundException, ComponentNotSupportedException;
}
