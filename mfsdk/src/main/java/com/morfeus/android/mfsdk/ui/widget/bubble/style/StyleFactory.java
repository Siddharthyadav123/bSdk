package com.morfeus.android.mfsdk.ui.widget.bubble.style;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides the style for the template.
 */
public class StyleFactory {
    private static StyleFactory INSTANCE;

    private HashMap<String, Style> mapTemplateIDStyle;

    private StyleFactory() {
        mapTemplateIDStyle = new HashMap<>();
    }

    public static StyleFactory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StyleFactory();
        return INSTANCE;
    }

    public void register(@NonNull Style style) {
        checkNotNull(style);
        String templateType = style.getTemplateType();
        checkNotNull(templateType);

        if (!mapTemplateIDStyle.containsKey(templateType)) {
            mapTemplateIDStyle.put(templateType, style);
        }
    }

    public void unregister(@NonNull Style style) {
        checkNotNull(style);
        String templateType = style.getTemplateType();
        checkNotNull(templateType);

        if (mapTemplateIDStyle.containsKey(templateType)) {
            mapTemplateIDStyle.remove(templateType);
        }
    }

    @Nullable
    public Style getStyle(@NonNull String templateType) throws StyleNotFoundException{
        checkNotNull(templateType);
        if (mapTemplateIDStyle.containsKey(templateType)){
            return mapTemplateIDStyle.get(templateType);
        } else {
            throw new StyleNotFoundException("Error: " + templateType + " not found!");
        }
    }

}
