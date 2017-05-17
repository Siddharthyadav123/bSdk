package com.morfeus.android.mfsdk.ui.widget.editor.factory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.EditTextComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.EmojiSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.FeedbackSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.ImageButtonComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.InputViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.MenuSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SubViewComponent.SubViewItem;
import com.morfeus.android.mfsdk.ui.widget.editor.SuggestionButtonComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SuggestionViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.TabLayoutComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.VoiceSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

public class PrimitiveComponentFactory extends ComponentFactory {

    private static PrimitiveComponentFactory INSTANCE;

    private PrimitiveComponentFactory() {
    }

    public static PrimitiveComponentFactory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PrimitiveComponentFactory();
        return INSTANCE;
    }

    @Override
    public Component createComponent(@NonNull Context ctx,
                                     @Component.Type String type,
                                     @Nullable @Component.Action String action,
                                     @NonNull ActionManager actionManager,
                                     @NonNull BaseModel model,
                                     @Nullable Style style) throws ComponentNotFoundException {
        switch (type) {
            case Component.COMPONENT_IMAGE_BUTTON:
                return new ImageButtonComponent(ctx, model, style, action, actionManager);
            case Component.COMPONENT_IMAGE:
                return null;
            case Component.COMPONENT_TABLAYOUT:
                return new TabLayoutComponent(ctx, this, style, model, actionManager);
            case Component.COMPONENT_SUBVIEW:
                return new SubViewComponent(ctx, this, model, actionManager);
            case Component.COMPONENT_SUGGESTION_BUTTON:
                return new SuggestionButtonComponent(ctx, action, this, style, actionManager, model);
            case Component.COMPONENT_EDITTEXT:
                return new EditTextComponent(ctx, this, style, model, action, actionManager);
            case Component.COMPONENT_INPUT_VIEW:
                return new InputViewComponent(ctx, this, style, model, actionManager);
            case Component.COMPONENT_SUBVIEW_PAGER:
                return new SubViewComponent(ctx, this, model, actionManager);
            case Component.COMPONENT_SUGGESTION_VIEW:
                return new SuggestionViewComponent(ctx, this, style, model, actionManager);
            case Component.COMPONENT_SUBVIEW_ITEM_MENU:
                return new MenuSubViewComponent(ctx, model, actionManager);
            case Component.COMPONENT_SUBVIEW_ITEM_SMILY:
                return new EmojiSubViewComponent(ctx, model, actionManager);
            case SubViewItem.SUBVIEW_ITEM_SETTING:
                return null;
            case Component.COMPONENT_SUBVIEW_ITEM_FEEDBACK:
                return new FeedbackSubViewComponent(ctx, model, actionManager);
            case Component.COMPONENT_SUBVIEW_ITEM_VOICE:
                return new VoiceSubViewComponent(ctx, model, actionManager);
            default:
                throw new ComponentNotFoundException("Error: " + type + " not found!");
        }
    }
}
