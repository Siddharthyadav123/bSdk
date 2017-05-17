package com.morfeus.android.mfsdk.ui.widget.editor.factory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.ImageButtonComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class PrimitiveComponentFactoryTest {

    ComponentFactory componentFactory;
    Context mockContext;

    @Before
    public void setUp() throws Exception {
        componentFactory = ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY);
        mockContext = InstrumentationRegistry.getTargetContext();

        assertNotNull(componentFactory);
    }

    @Test
    public void createComponent_createsProperComponent() {
        try {
            Component component = componentFactory.createComponent(
                    mockContext,
                    Component.COMPONENT_IMAGE_BUTTON,
                    "action",
                    mock(ActionManager.class),
                    mock(ImageButtonModel.class),
                    mock(Style.class)
            );
            assertNotNull(component);
            assertTrue(component instanceof ImageButtonComponent);
        } catch (ComponentNotFoundException e) {
            e.printStackTrace();
        } catch (ComponentNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ComponentNotFoundException.class)
    public void createComponent_throwComponentNotFoundException() throws ComponentNotFoundException, ComponentNotSupportedException {
        Component component = componentFactory.createComponent(
                mockContext,
                "newButton",
                "action",
                mock(ActionManager.class),
                mock(ImageButtonModel.class),
                mock(Style.class)
        );
    }

}