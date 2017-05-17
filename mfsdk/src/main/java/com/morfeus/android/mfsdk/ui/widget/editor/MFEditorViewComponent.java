package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MFEditorViewComponent extends RelativeLayout
        implements Component {

    private static final String TAG = MFEditorViewComponent.class.getSimpleName();
    private MFEditorViewModel mModel;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    private View mShadowView;

    public MFEditorViewComponent(Context context,
                                 @NonNull BaseModel baseModel,
                                 @NonNull ComponentFactory componentFactory,
                                 @NonNull ActionManager actionManager) {
        super(context);

        mModel = (MFEditorViewModel) checkNotNull(baseModel);
        mComponentFactory = checkNotNull(componentFactory);
        mActionManager = checkNotNull(actionManager);
    }

    public MFEditorViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    @Override
    public void setStyle(Style style) {
        // No-op
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
        mModel = (MFEditorViewModel) checkNotNull(baseModel);
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
        setBackgroundColor(getResources().getColor(R.color.colorWhite));
        addTopSeprator();
        List<Component> components = createSubComponents(mModel.getSubComponentModels());
        for (Component component : components) {
            addComponent(component);
        }
        addFooterViewToSupportShawdow();
    }


    private List<Component> createSubComponents(List<BaseModel> componentModels)
            throws ComponentNotSupportedException, ComponentNotFoundException {
        List<Component> components = new ArrayList<>();

        for (BaseModel model : componentModels) {
            Component component = mComponentFactory.createComponent(
                    getContext(),
                    checkSupported(model.getId()),
                    model.getAction(),
                    mActionManager,
                    model,
                    model.getStyle()
            );
            components.add(component);
        }
        return components;
    }

    @Type
    private String checkSupported(String componentTag) {
        switch (componentTag) {
            case COMPONENT_INPUT_VIEW:
                return COMPONENT_INPUT_VIEW;
            case COMPONENT_TABLAYOUT:
                return COMPONENT_TABLAYOUT;
            case COMPONENT_SUBVIEW:
                return COMPONENT_SUBVIEW;
            case COMPONENT_SUGGESTION_VIEW:
                return COMPONENT_SUGGESTION_VIEW;
            default:
                throw new IllegalArgumentException("Error: " + componentTag + " is not supported!");
        }
    }

    public void addComponent(Component component) throws ComponentException {
        String componentTag = component.getData().getId();
        component.setComponentId(getComponentId(componentTag));
        component.initView();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        setLayoutRelation(layoutParams, getComponentId(componentTag));
        addView((View) component, layoutParams);
    }

    private void setLayoutRelation(LayoutParams layoutParams, int selfComponentId) {
        if (selfComponentId == R.id.component_suggestion_view) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.component_top_seprator);
        } else if (selfComponentId == R.id.component_inputview) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.component_suggestion_view);
        } else if (selfComponentId == R.id.component_tablayout) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.component_inputview);
        } else if (selfComponentId == R.id.component_subview) {
            layoutParams.addRule(RelativeLayout.BELOW, R.id.component_tablayout);
        }
    }

    private void addTopSeprator() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertDpToPixel(1)
        );
        layoutParams.setMargins(0, 0, 0, convertDpToPixel(4));
        View sepratorView = new View(getContext());
        sepratorView.setId(R.id.component_top_seprator);
        sepratorView.setBackgroundColor(getContext().getResources().getColor(R.color.gray));
        sepratorView.setAlpha(0.7f);
        sepratorView.setLayoutParams(layoutParams);
        addView(sepratorView);
    }

    private void addFooterViewToSupportShawdow() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertDpToPixel(0)
        );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.component_subview);
        View supportView = new View(getContext());
        supportView.setId(R.id.component_bottom_supporter_view);
        supportView.setLayoutParams(layoutParams);
        addView(supportView);
    }


    public void disableEditorToolbar() {
        try {
            if (mShadowView == null) {
                mShadowView = new View(getContext());
                mShadowView.setBackgroundColor(getContext().getResources().getColor(R.color.gray));
                mShadowView.setAlpha(0.4f);
            } else if (mShadowView.getParent() != null) {
                removeView(mShadowView);
            }
            //this listener is to block the click of behind child views
            mShadowView.setOnClickListener(new OnClickListener() {
                                               @Override
                                               public void onClick(View view) {

                                               }
                                           }
            );
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.component_bottom_supporter_view);
            mShadowView.setLayoutParams(layoutParams);
            addView(mShadowView);
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    public void enabledEditorToolbar() {
        try {
            if (mShadowView != null) {
                removeView(mShadowView);
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    private int getComponentId(String componentTag) {
        switch (componentTag) {
            case Component.COMPONENT_INPUT_VIEW:
                return R.id.component_inputview;
            case Component.COMPONENT_TABLAYOUT:
                return R.id.component_tablayout;
            case Component.COMPONENT_SUBVIEW:
                return R.id.component_subview;
            case Component.COMPONENT_SUGGESTION_VIEW:
                return R.id.component_suggestion_view;
            default:
                throw new IllegalArgumentException("Error: " + componentTag + " is not supported!");
        }

    }

    @Override
    public Component inflate(Context context, ViewGroup parent, boolean attach) {
        return (MFEditorViewComponent) LayoutInflater.from(getContext())
                .inflate(R.layout.component_mf_editor, parent, attach);
    }

    protected int convertDpToPixel(float dp) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }


}
