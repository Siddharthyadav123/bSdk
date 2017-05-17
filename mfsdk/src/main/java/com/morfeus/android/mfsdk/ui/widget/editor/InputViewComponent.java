package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class InputViewComponent extends RelativeLayout implements Component {

    private InputViewModel mModel;

    private Style mStyle;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    @SuppressWarnings("WrongConstant")
    public InputViewComponent(Context context,
                              @NonNull ComponentFactory factory,
                              @NonNull Style style,
                              @NonNull BaseModel model,
                              @NonNull ActionManager actionManager) {
        super(context);

        mComponentFactory = checkNotNull(factory);
        mStyle = checkNotNull(style);
        mModel = (InputViewModel) checkNotNull(model);
        mActionManager = checkNotNull(actionManager);
    }

    public InputViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    public InputViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // No-op
    }

    @Override
    public void initView() throws ComponentException {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getPxFromDP(24), getPxFromDP(24));
        int margine = getPxFromDP(4);
        setPadding(margine, 0, margine, 0);
        setLayoutParams(layoutParams);

        List<Component> components = createSubComponents(mModel.getBaseModels());
        if (mModel.getStyle().getBackgroundColor() != null)
            setBackgroundColor(Color.parseColor(mModel.getStyle().getBackgroundColor()));
        for (Component component : components) {
            switch (component.getData().getId()) {
                case COMPONENT_EDITTEXT:
                    addEditTextComponent(component);
                    break;
                case COMPONENT_INPUT_VIEW_SEND_BUTTON:
                    addImageButtonComponent(component);
                    break;
                default:
                    throw new ComponentNotSupportedException("Error: " + component.getData().getId()
                            + " not supported!");

            }
        }
    }

    private int getPxFromDP(int dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics()
        );
        return (int) px;
    }

    private List<Component> createSubComponents(List<BaseModel> models) throws ComponentNotSupportedException, ComponentNotFoundException {
        List<Component> components = new ArrayList<>();

        for (BaseModel model : models) {
            // Only EditTextComponent and ImageButtonComponent is supported into inputViewComponent.
            if (model instanceof EditTextModel || model instanceof ImageButtonModel) {
                String componentType;

                if (model instanceof EditTextModel) {
                    componentType = COMPONENT_EDITTEXT;
                } else {
                    componentType = COMPONENT_IMAGE_BUTTON;
                }

                Component component =
                        mComponentFactory.createComponent(
                                getContext(),
                                componentType,
                                model.getAction(),
                                mActionManager,
                                model,
                                model.getStyle()
                        );
                components.add(component);
            }
        }

        return components;
    }

    @Override
    public void setStyle(@Nullable Style style) {
        mStyle = style;
    }

    @Override
    public void setAction(@Nullable @Action String action) {
        // No-op
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
        mModel = (InputViewModel) checkNotNull(baseModel);
    }

    @Override
    public void show(boolean show) {
        if (show)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);
    }

    public void addEditTextComponent(Component component) throws ComponentException {
        checkNotNull(component);

        component.initView();
        component.setComponentId(R.id.component_edittext);
        component.inflate(getContext(), this, false);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_LEFT);
        layoutParams.addRule(LEFT_OF, R.id.component_inputview_ibc_send);
        addView((View) component, layoutParams);
    }

    public void addImageButtonComponent(Component component) throws ComponentException {
        checkNotNull(component);

        component.initView();
        component.setComponentId(R.id.component_inputview_ibc_send);
        component.inflate(getContext(), this, false);

        ((ImageButtonComponent) component).setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                getPxFromDP(30), getPxFromDP(30));
        layoutParams.addRule(ALIGN_PARENT_RIGHT);
        int margine = getPxFromDP(4);
        int padding = getPxFromDP(8);
        ((ImageButtonComponent) component).setPadding(padding, padding, padding, padding);
        layoutParams.setMargins(margine, margine, margine, margine);
        layoutParams.addRule(CENTER_VERTICAL);

        addView((View) component, layoutParams);
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return (InputViewComponent) LayoutInflater.from(getContext())
                .inflate(R.layout.component_inputview, this);
    }
}
