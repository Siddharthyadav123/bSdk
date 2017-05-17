package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.ActionManagerImpl;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.EditTextStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class EditTextComponentTest {
    private static final String LEFT_BUTTON = "leftbutton";
    private static final String RIGHT_BUTTON_1 = "rightbutton1";
    private static final String RIGHT_BUTTON_2 = "rightbutton2";
    private static final String TEST_ACTION = Component.ACTION_CAMERA;
    private static List<BaseModel> TEST_SUB_COMPONENT_MODELS =
            new ArrayList<>();
    EditTextComponent mEditTextComponent;
    Context mMockContext;
    ComponentFactory mMockComponentFactory;
    EditTextStyle mMockEditTextStyle;
    EditTextModel mEditTextModel;
    ActionManagerImpl mMockActionManager;

    @Before
    public void setUp() throws Exception {
        TEST_SUB_COMPONENT_MODELS.addAll(prepareSubComponentModels());

        mMockContext = InstrumentationRegistry.getTargetContext();
        mMockComponentFactory = mock(ComponentFactory.class);
        mMockEditTextStyle = mock(EditTextStyle.class);
        mEditTextModel = new EditTextModel(Component.COMPONENT_EDITTEXT, mMockEditTextStyle, TEST_SUB_COMPONENT_MODELS, "");
        mMockActionManager = mock(ActionManagerImpl.class);

    }

    private List<BaseModel> prepareSubComponentModels() {
        List<BaseModel> baseModels = new ArrayList<>();
        baseModels.add(new ImageButtonModel(LEFT_BUTTON));
        baseModels.add(new ImageButtonModel(RIGHT_BUTTON_1));
        baseModels.add(new ImageButtonModel(RIGHT_BUTTON_2));
        return baseModels;
    }

    @Test
    public void testInitView_initSubComponent() {
        try {
            ImageButtonModel imageButtonModel = mock(ImageButtonModel.class);
            ImageButtonComponent mockComponent = mock(ImageButtonComponent.class);
            ImageButtonModel mockImageButtonModel = mock(ImageButtonModel.class);

            when(mMockComponentFactory.createComponent(
                    any(Context.class),
                    anyString(),
                    anyString(),
                    any(ActionManager.class),
                    any(BaseModel.class),
                    any(Style.class))).thenReturn(mockComponent);

            when(mockComponent.getData()).thenReturn(mockImageButtonModel);

            mEditTextComponent = new EditTextComponent(
                    mMockContext,
                    mMockComponentFactory,
                    mMockEditTextStyle,
                    mEditTextModel,
                    TEST_ACTION,
                    mMockActionManager
            );

            verify(mMockComponentFactory.createComponent(
                    any(Context.class),
                    Component.COMPONENT_IMAGE_BUTTON,
                    TEST_ACTION,
                    any(ActionManager.class),
                    any(BaseModel.class),
                    any(Style.class)
            ), times(3));


        } catch (ComponentNotSupportedException e) {
            e.printStackTrace();
        } catch (ComponentNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addLeftButton_containsLeftButton() {

    }

    @Test
    public void addRightButton1_containsRightButton1() {

    }

    @Test
    public void addRightButton2_containsRightButton2() {

    }


}