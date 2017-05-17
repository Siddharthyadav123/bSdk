package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.CENTER_VERTICAL;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class EditTextComponent extends LinearLayout implements Component {

    @Action
    String mAction;
    private EditText mEditText;
    private EditTextModel mModel;
    private Style mStyle;
    private ActionManager mActionManager;
    private ComponentFactory mComponentFactory;

    public EditTextComponent(Context context,
                             @NonNull ComponentFactory factory,
                             @NonNull Style style,
                             @NonNull BaseModel model,
                             @Action String action,
                             @NonNull ActionManager actionManager) {
        super(context);
        checkArgument(model instanceof EditTextModel);

        mComponentFactory = checkNotNull(factory);
        mStyle = checkNotNull(style);
        mModel = (EditTextModel) checkNotNull(model);
        mAction = null; // Composite component don't require action.
        mActionManager = checkNotNull(actionManager);
    }

    public EditTextComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    public EditTextComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // No-op
    }

    @Override
    public void initView() throws ComponentException {
        setGravity(CENTER_VERTICAL);
        List<ImageButtonComponent> components = createSubComponents(mModel.getSubComponentModels());

        for (ImageButtonComponent imageButtonComponent : components) {
            switch (imageButtonComponent.getData().getId()) {
                case ButtonPosition.LEFT_BUTTON:
                    addLeftComponent(imageButtonComponent);
                    break;
                case ButtonPosition.RIGHT_BUTTON_1:
                    addRightComponent1(imageButtonComponent);
                    break;
                case ButtonPosition.RIGHT_BUTTON_2:
                    addRightComponent2(imageButtonComponent);
                    break;
                default:
                    throw new ComponentNotSupportedException("Error: " + imageButtonComponent.getData().getId()
                            + " not supported!");
            }
        }
    }

    private List<ImageButtonComponent> createSubComponents(List<BaseModel> models) throws ComponentNotSupportedException {
        List<ImageButtonComponent> components = new ArrayList<>();

        for (BaseModel model : models) {
            try {
                // Only buttons can be the sub-component of EditTextComponent.
                if (model instanceof ImageButtonModel) {
                    ImageButtonComponent component =
                            (ImageButtonComponent) mComponentFactory.createComponent(
                                    getContext(),
                                    Component.COMPONENT_IMAGE_BUTTON,
                                    model.getAction(),
                                    mActionManager,
                                    model,
                                    model.getStyle());

                    components.add(component);
                }
            } catch (ComponentNotFoundException e) {
                e.printStackTrace();
            }
        }
        return components;
    }

    @Override
    public void setStyle(Style style) {
        mStyle = checkNotNull(style);
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public void setAction(@Action String action) {
        mAction = checkNotNull(action);
    }

    @Override
    public void setComponentId(@IdRes int id) {
        setId(id);
    }

    @Override
    public BaseModel getData() {
        return mModel;
    }

    @Override
    public void setData(BaseModel baseModel) {
        checkArgument(baseModel instanceof EditTextModel);
        mModel = (EditTextModel) baseModel;
    }

    @Override
    public void show(boolean show) {
        if (show)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);
    }

    public void addLeftComponent(@NonNull Component component) throws ComponentException {
        checkNotNull(component);

        component.initView();
        component.setComponentId(R.id.component_edittext_leftbutton);
//        component.inflate(getContext(), this, false);

        ((ImageButtonComponent) component).setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout.LayoutParams leftGravityParams = new LinearLayout.LayoutParams(
                getPxFromDP(24), getPxFromDP(24));
        int margine = getPxFromDP(4);
        leftGravityParams.setMargins(margine * 4, margine, margine, margine);
        addView((View) component, leftGravityParams);
    }

    public void addRightComponent1(@NonNull Component component) throws ComponentException {
        checkNotNull(component);

        component.initView();
        component.setComponentId(R.id.component_edittext_rightbutton1);
//        component.inflate(getContext(), this, false);

        ((ImageButtonComponent) component).setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                getPxFromDP(24), getPxFromDP(24));

        int margine = getPxFromDP(4);
        rightGravityParams.setMargins(margine, margine, margine, margine);

        addView((View) component, rightGravityParams);
    }

    private int getPxFromDP(int dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics()
        );
        return (int) px;
    }

    public void addRightComponent2(@NonNull Component component) throws ComponentException {
        checkNotNull(component);
        component.initView();
        component.setComponentId(R.id.component_edittext_rightbutton2);
//        component.inflate(getContext(), this, false);

        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                getPxFromDP(24), getPxFromDP(24));
        rightGravityParams.gravity = Gravity.END;
        int margine = getPxFromDP(4);
        rightGravityParams.setMargins(margine, margine, margine, margine);
        addView((View) component, rightGravityParams);
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        EditTextComponent editTextComponent = (EditTextComponent) LayoutInflater.from(context)
                .inflate(R.layout.component_edittext, this);
        mEditText = (EditText) this.findViewById(R.id.component_edittext_et);
        mEditText.setHint(LanguageRepository.getInstance().getText(mModel.getPlaceholderText()));

        return editTextComponent;
    }

    private interface ButtonPosition {
        String LEFT_BUTTON = "leftbutton";
        String RIGHT_BUTTON_1 = "rightbutton1";
        String RIGHT_BUTTON_2 = "rightbutton2";
    }
}
