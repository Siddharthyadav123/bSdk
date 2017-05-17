package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SuggestionViewStyle;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SuggestionViewComponent extends LinearLayout implements Component {

    private SuggestionViewStyle mStyle;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    private SuggestionViewModel mModel;

    private HorizontalScrollView mHorizontalScrollView;

    public SuggestionViewComponent(Context context,
                                   @NonNull ComponentFactory componentFactory,
                                   @NonNull Style style,
                                   @NonNull BaseModel baseModel,
                                   @NonNull ActionManager actionManager) {
        super(context);
        mComponentFactory = checkNotNull(componentFactory);
        mActionManager = checkNotNull(actionManager);
        mModel = (SuggestionViewModel) checkNotNull(baseModel);
        mStyle = (SuggestionViewStyle) checkNotNull(style);
    }

    public SuggestionViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    @Override
    public void setStyle(Style style) {
        mStyle = (SuggestionViewStyle) checkNotNull(style);
    }

    @Override
    public void setAction(@Action String action) {
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
        mModel = (SuggestionViewModel) checkNotNull(baseModel);
    }

    @Override
    public void show(boolean show) {
        if (show)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);
    }

    @Override
    public void initView() throws ComponentException {
        // TODO refine layout parameter
        if (!TextUtils.isEmpty(mStyle.getBackgroundColor()))
            setBackgroundColor(Color.parseColor(mStyle.getBackgroundColor()));
        if (mStyle.getHeight() != 0)
            setMinimumHeight(mStyle.getHeight());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        mHorizontalScrollView = new HorizontalScrollView(getContext());
        mHorizontalScrollView.setLayoutParams(layoutParams);
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        addView(mHorizontalScrollView);

        LinearLayout llyContainer = new LinearLayout(getContext());
        llyContainer.setLayoutParams(layoutParams);
        llyContainer.setOrientation(LinearLayout.HORIZONTAL);

        List<Component> components = createSubComponents(mModel.getSubComponentModels());

        for (Component component : components) {
            component.initView();
            llyContainer.addView((View) component);
        }
        mHorizontalScrollView.addView(llyContainer);
    }

    private List<Component> createSubComponents(List<BaseModel> models) throws ComponentNotSupportedException, ComponentNotFoundException {
        List<Component> components = new ArrayList<>();

        for (BaseModel model : models) {
            // Only SuggestionButton can be the sub-component of SuggestionViewComponent
            if (model instanceof SuggestionButtonModel) {
                Component component = mComponentFactory.createComponent(
                        getContext(),
                        COMPONENT_SUGGESTION_BUTTON,
                        model.getAction(),
                        mActionManager,
                        model,
                        mStyle
                );
                components.add(component);
            } else {
                throw new ComponentNotSupportedException(
                        "Error: " + model.getClass().getSimpleName()
                                + " is not supported into " + this.getClass().getSimpleName());
            }
        }
        return components;
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return null;
    }
}
