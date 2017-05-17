package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TabLayoutComponent extends LinearLayout implements Component {

    private TabLayoutModel mModel;

    private Style mStyle;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    private HashMap<String, BaseModel> mMapTabWithSubComponent = new HashMap<>();

    private List<OnTabChangeListener> mTabChangeListener = new ArrayList<>();

    public TabLayoutComponent(Context context,
                              @NonNull ComponentFactory componentFactory,
                              @NonNull Style style,
                              @NonNull BaseModel baseModel,
                              @NonNull ActionManager actionManager) {
        super(context);

        mComponentFactory = checkNotNull(componentFactory);
        mStyle = checkNotNull(style);
        mModel = (TabLayoutModel) checkNotNull(baseModel);
        mActionManager = checkNotNull(actionManager);
    }

    public TabLayoutComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        //No-op
    }

    @Override
    public void setStyle(Style style) {
        mStyle = checkNotNull(style);
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
        mModel = (TabLayoutModel) checkNotNull(baseModel);
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
        final TabLayout tabLayout = (TabLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.component_tablayout, this, false);
        tabLayout.setSelectedTabIndicatorHeight(0);

        for (BaseModel model : mModel.getBaseModels()) {
            if (model instanceof ImageButtonModel) {
                // TODO refactor
                mMapTabWithSubComponent.put(model.getId(), model);
                int resourceId = getResourceId(((ImageButtonModel) model).getImage());
                TabLayout.Tab tab = tabLayout.newTab();
                tab.setIcon(resourceId);
                tab.setTag(model.getId());
                tabLayout.addTab(tab);
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CharSequence tabId = (CharSequence) tab.getTag();
                showTabSelection(tabLayout, tab);
                notifyTabListener(tabId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                CharSequence tabId = (CharSequence) tab.getTag();
                showTabSelection(tabLayout, tab);
                notifyTabListener(tabId);
            }
        });

        addView(tabLayout);
    }

    public void removeTabSelection() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < mModel.getBaseModels().size(); i++) {
            BaseModel model = mModel.getBaseModels().get(i);
            if (model instanceof ImageButtonModel) {
                int unselectedResourceId = getResourceId(((ImageButtonModel) model).getImage());
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setIcon(unselectedResourceId);
            }
        }
    }


//    public void changeTabSelectionOnPageChange(int pageNo) {
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        TabLayout.Tab selectedTab = tabLayout.getTabAt(pageNo);
//
//        for (int i = 0; i < mModel.getBaseModels().size(); i++) {
//            BaseModel model = mModel.getBaseModels().get(i);
//            if (model instanceof ImageButtonModel) {
//                int unselectedResourceId = getResourceId(((ImageButtonModel) model).getImage());
//                int selectedResourceId = getResourceId(((ImageButtonModel) model).getImageSel());
//                TabLayout.Tab tab = tabLayout.getTabAt(i);
//                if (tab == selectedTab) {
//                    tab.setIcon(selectedResourceId);
//                } else {
//                    tab.setIcon(unselectedResourceId);
//                }
//
//            }
//        }
//    }

    private void showTabSelection(TabLayout tabLayout, TabLayout.Tab selectedTab) {
        for (int i = 0; i < mModel.getBaseModels().size(); i++) {
            BaseModel model = mModel.getBaseModels().get(i);
            if (model instanceof ImageButtonModel) {
                int unselectedResourceId = getResourceId(((ImageButtonModel) model).getImage());
                int selectedResourceId = getResourceId(((ImageButtonModel) model).getImageSel());
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab == selectedTab) {
                    tab.setIcon(selectedResourceId);
                } else {
                    tab.setIcon(unselectedResourceId);
                }

            }
        }
    }

    private void notifyTabListener(CharSequence tabId) {
        for (OnTabChangeListener listener : mTabChangeListener) {
            assert tabId != null;
            listener.onClick(tabId.toString());
        }
    }

    public void registerTabListener(@NonNull OnTabChangeListener listener) {
        checkNotNull(listener);
        if (!mTabChangeListener.contains(listener))
            mTabChangeListener.add(listener);
    }

    public void unregisterListener(@NonNull OnTabChangeListener listener) {
        checkNotNull(listener);
        if (mTabChangeListener.contains(listener))
            mTabChangeListener.remove(listener);
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return null;
    }

    public interface OnTabChangeListener {
        void onClick(@NonNull String tabId);
    }

}
