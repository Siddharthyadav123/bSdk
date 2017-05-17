package com.morfeus.android.mfsdk.ui.widget.editor.factory;

import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// TODO remove
public class ComponentFactoryProvider {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            PRIMITIVE_COMPONENT_FACTORY, COMPOSITE_COMPONENT_FACTORY
    })
    @interface Factory{}

    public static final int PRIMITIVE_COMPONENT_FACTORY = 1;
    public static final int COMPOSITE_COMPONENT_FACTORY = 2;

    public static ComponentFactory getFactory(@Factory int factory) {
        switch (factory) {
            case PRIMITIVE_COMPONENT_FACTORY:
                return PrimitiveComponentFactory.getInstance();
            case COMPOSITE_COMPONENT_FACTORY:
                return CompositeComponentFactory.getInstance();
            default:
                throw new IllegalArgumentException("Error: invalid factory type!");
        }
    }
}
