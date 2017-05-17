package com.morfeus.android.mfsdk.ui.widget.editor.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

// TODO remove
public class CompositeComponentFactory extends ComponentFactory {

    private static CompositeComponentFactory INSTANCE;

    private CompositeComponentFactory() { }

    public static CompositeComponentFactory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CompositeComponentFactory();
        return INSTANCE;
    }

    @Override
    public Component createComponent(@NonNull Context ctx,
                              @Component.Type String type,
                              @Component.Action String action,
                              @NonNull ActionManager actionManager,
                              @NonNull BaseModel model,
                              @NonNull Style style) throws ComponentNotFoundException {
        switch (type) {
            case Component.COMPONENT_EDITTEXT:
                return null;
            case Component.COMPONENT_INPUT_VIEW:
                return null;
            case Component.COMPONENT_SUBVIEW_PAGER:
                return null;
            case Component.COMPONENT_SUGGESTION_VIEW:
                return null;
            default:
                throw new ComponentNotFoundException("Error: " + type + " not found!");
        }
    }
}
