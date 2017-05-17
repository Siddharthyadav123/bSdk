package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentNotSupportedException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SubViewComponent extends LinearLayout implements Component {
    private static final String TAG = SubViewComponent.class.getSimpleName();

    private NonSwipeableViewPager mVPSubViewContainer;

    private SubViewAdapter mSubViewAdapter;

    private SubViewModel mModel;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    private List<Component> mItemComponents;

    private List<OnPageChangeListener> mPageChangeListener = new ArrayList<>();

    private String mPrviousTabId = null;


    private TabLayoutComponent.OnTabChangeListener mTabChangeListener
            = new TabLayoutComponent.OnTabChangeListener() {
        @Override
        public void onClick(@NonNull String tabId) {
            if (tabId.equalsIgnoreCase("textview")) {
                show(false);
                mPrviousTabId = tabId;
                return;
            } else if (tabId.equalsIgnoreCase("locationview")) {
                mPrviousTabId = tabId;
                ((ChatActivity) getContext()).launchLocationSelectionActivity();
                return;
            }

            if (mPrviousTabId == null) {
                mPrviousTabId = tabId;
            }

            if (getVisibility() == VISIBLE && mPrviousTabId.equalsIgnoreCase(tabId)) {
                show(false);
            } else {
                show(true);
                int position = mSubViewAdapter.getPositionByComponentId(tabId);
                mVPSubViewContainer.setCurrentItem(position);
                if (isVoiceTabId(tabId))
                    mSubViewAdapter.startRecording(position);
            }
            mPrviousTabId = tabId;
        }
    };


    public SubViewComponent(Context context,
                            @NonNull ComponentFactory factory,
                            @NonNull BaseModel model,
                            @NonNull ActionManager actionManager) {
        super(context);
        mComponentFactory = checkNotNull(factory);
        mActionManager = checkNotNull(actionManager);
        mModel = (SubViewModel) checkNotNull(model);
    }

    public SubViewComponent(Context context, AttributeSet attrs) {
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
        mModel = (SubViewModel) checkNotNull(baseModel);
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
        setComponentBackground();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 188,
                getResources().getDisplayMetrics()
        );

        mItemComponents = createSubViewItemComponents(mModel.getSubComponentModels());

        mVPSubViewContainer = (NonSwipeableViewPager) LayoutInflater.from(getContext())
                .inflate(R.layout.component_subview_viewpager, this, false);

        mSubViewAdapter = new SubViewAdapter(getContext(), mItemComponents);
        mVPSubViewContainer.setAdapter(mSubViewAdapter);
        mVPSubViewContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // No-op
            }

            @Override
            public void onPageSelected(int position) {
                Component component = mSubViewAdapter.getItem(position);
                String id = component.getData().getId();

                for (OnPageChangeListener listener : mPageChangeListener) {
                    listener.onPageSelected(id, position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // No-op
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) px
        );

        addView(mVPSubViewContainer, layoutParams);
    }

    public TabLayoutComponent.OnTabChangeListener getTabChangeListener() {
        return mTabChangeListener;
    }

    public void registerPageChangeListener(OnPageChangeListener listener) {
        checkNotNull(listener);
        if (!mPageChangeListener.contains(listener))
            mPageChangeListener.add(listener);
    }

    public void unregisterPageChangeListener(OnPageChangeListener listener) {
        checkNotNull(listener);
        if (mPageChangeListener.contains(listener))
            mPageChangeListener.remove(listener);
    }

    private List<Component> createSubViewItemComponents(List<BaseModel> baseModels)
            throws ComponentNotSupportedException, ComponentNotFoundException {
        List<Component> components = new ArrayList<>();

        for (BaseModel itemModel : baseModels) {
            Component component = mComponentFactory.createComponent(
                    getContext(),
                    checkSupported(itemModel.getId()),
                    itemModel.getAction(),
                    mActionManager,
                    itemModel,
                    itemModel.getStyle()
            );
            components.add(component);
        }
        return components;
    }

    @Type
    private String checkSupported(String componentTag) {
        switch (componentTag) {
            case COMPONENT_SUBVIEW_ITEM_MENU:
                return COMPONENT_SUBVIEW_ITEM_MENU;
            case COMPONENT_SUBVIEW_ITEM_SMILY:
                return COMPONENT_SUBVIEW_ITEM_SMILY;
            case COMPONENT_SUBVIEW_ITEM_SETTING:
                return COMPONENT_SUBVIEW_ITEM_SETTING;
            case COMPONENT_SUBVIEW_ITEM_FEEDBACK:
                return COMPONENT_SUBVIEW_ITEM_FEEDBACK;
            case COMPONENT_SUBVIEW_ITEM_VOICE:
                return COMPONENT_SUBVIEW_ITEM_VOICE;
            default:
                throw new IllegalArgumentException("Error: " + componentTag + " is not supported!");
        }
    }

    private void setComponentBackground() {
        if (mModel.getStyle() == null)
            return;
        if (!TextUtils.isEmpty(mModel.getStyle().getBackgroundImage())) {
            setImage(mModel.getStyle().getBackgroundImage());
        } else {
            if (!TextUtils.isEmpty(mModel.getStyle().getBackgroundColor())) {
                setBackgroundColor(Color.parseColor(mModel.getStyle().getBackgroundColor()));
            }
        }
    }

    private void setImage(String imageName) {
        checkNotNull(imageName);
        int resourceId = getResourceId(imageName);
        setBackgroundResource(resourceId);
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return (SubViewComponent) LayoutInflater.from(getContext())
                .inflate(R.layout.component_subview, this);
    }

    public void stopRecording() {
        int position = mSubViewAdapter.getPositionByComponentId(SubViewItem.SUBVIEW_ITEM_VOICE);
        VoiceSubViewComponent voiceSubViewComponent
                = (VoiceSubViewComponent) mItemComponents.get(position);
        voiceSubViewComponent.stopRecording();
    }


    private boolean isVoiceTabId(String tabId) {
        return tabId.equalsIgnoreCase(Component.COMPONENT_SUBVIEW_ITEM_VOICE);
    }

    public interface SubViewItem {
        String SUBVIEW_ITEM_MENU = "menuview";
        String SUBVIEW_ITEM_SMILY = "smileyview";
        String SUBVIEW_ITEM_SETTING = "settingsview";
        String SUBVIEW_ITEM_FEEDBACK = "feedbackview";
        String SUBVIEW_ITEM_VOICE = "voiceview";
    }

    public interface OnPageChangeListener {
        void onPageSelected(@NonNull String pageId, int position);
    }

    public static class SubViewAdapter extends PagerAdapter {

        private static final String TAG = SubViewAdapter.class.getSimpleName();

        private Context mContext;

        private List<Component> mComponents;


        public SubViewAdapter(Context context, List<Component> components) {
            mContext = context;
            this.mComponents = components;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Component component = mComponents.get(position);
            try {
                component.initView();
                setComponentId(component);
                component.inflate(mContext, container, false);
                container.addView((View) component);
            } catch (ComponentException e) {
                LogManager.e(TAG, "Error: Failed to initialize subview item component!", e);
            }
            return component;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return mComponents.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private Component getItem(int position) {
            return mComponents.get(position);
        }

        private int getPositionByComponentId(String id) {
            for (int pos = 0; pos < mComponents.size(); pos++) {
                Component component = mComponents.get(pos);
                String componentId = component.getData().getId();
                if (componentId.equalsIgnoreCase(id)) {
                    return pos;
                }
            }
            return -1;
        }

        private void startRecording(int pos) {
            VoiceSubViewComponent voiceSubViewComponent = (VoiceSubViewComponent) mComponents.get(pos);
            voiceSubViewComponent.startVoiceRecorder();
        }


        private void setComponentId(Component component) {
            checkNotNull(component);

            String componentTag = component.getData().getId();
            int componentId = getComponentId(componentTag);
            component.setComponentId(componentId);
        }

        private int getComponentId(String componentTag) {
            switch (componentTag) {
                case SubViewItem.SUBVIEW_ITEM_MENU:
                    return R.id.component_subview_menu;
                case SubViewItem.SUBVIEW_ITEM_SMILY:
                    return R.id.component_subview_smily;
                case SubViewItem.SUBVIEW_ITEM_SETTING:
                    return R.id.component_subview_setting;
                case SubViewItem.SUBVIEW_ITEM_FEEDBACK:
                    return R.id.component_subview_feedback;
                case SubViewItem.SUBVIEW_ITEM_VOICE:
                    return R.id.component_subview_voice;
                default:
                    throw new IllegalArgumentException("Error: " + componentTag + " is not supported!");
            }
        }
    }
}
