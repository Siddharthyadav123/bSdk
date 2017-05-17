package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.widget.PagerSlidingTabStrip;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Component view to show emojis
 */
public class EmojiSubViewComponent extends LinearLayout implements Component {

    private SubViewItemModel mModel;

    private ActionManager mActionManager;

    private LinearLayout mRootLayout;

    private HorizontalScrollView mHorizontalScrollView;
    private boolean isViewAdded;

    public EmojiSubViewComponent(Context context,
                                 @NonNull BaseModel baseModel,
                                 @NonNull ActionManager actionManager) {
        super(context);

        mModel = (SubViewItemModel) checkNotNull(baseModel);
        mActionManager = checkNotNull(actionManager);
    }

    public EmojiSubViewComponent(Context context, AttributeSet attrs) {
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
        mModel = (SubViewItemModel) checkNotNull(baseModel);
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
        try {
            if (!isViewAdded) {
                setBgColor();
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext())
                        .inflate(R.layout.emoji_subview_layout, null);
                ViewPager viewPager = (ViewPager) linearLayout.findViewById(R.id.viewpager);
                PagerSlidingTabStrip tabLayout = (PagerSlidingTabStrip) linearLayout.findViewById(R.id.tabs);
                updateViewPagerAndTabs(viewPager, tabLayout);
                addView(linearLayout);
                isViewAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBgColor() {
        SubViewStyle subViewStyle = (SubViewStyle) mModel.getStyle();
        if (subViewStyle != null) {
            String textColor = subViewStyle.getBackgroundColor();
            if (textColor != null && textColor.length() > 0) {
                int colorId = Color.parseColor(textColor);
                setBackgroundColor(colorId);
            }
        }
    }

    private void updateViewPagerAndTabs(ViewPager viewPager, PagerSlidingTabStrip tabLayout) {
        if (mModel != null) {
            ArrayList<SubViewItemModel.SmileySubViewModel> smileySubViewModelList
                    = (ArrayList<SubViewItemModel.SmileySubViewModel>)
                    mModel.getSmileySubViewModelList();

            if (smileySubViewModelList != null) {
                //if there is only single list in item we hide the tabs
                if (smileySubViewModelList.size() == 1) {
                    tabLayout.setVisibility(View.GONE);
                } else {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                ViewPagerAdapter adapter = new ViewPagerAdapter(smileySubViewModelList);
                viewPager.setAdapter(adapter);
                tabLayout.setViewPager(viewPager);
            }

        }

    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        EmojiSubViewComponent emojiSubViewComponent = (EmojiSubViewComponent) LayoutInflater.from(getContext())
                .inflate(R.layout.component_subview_emoji, viewGroup, attach);
        return emojiSubViewComponent;
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    protected int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    /**
     * Emoji Pager Adapter inner class
     */
    private class ViewPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        ArrayList<SubViewItemModel.SmileySubViewModel> mSmileySubViewModelList = null;

        public ViewPagerAdapter(ArrayList<SubViewItemModel.SmileySubViewModel>
                                        smileySubViewModelList) {
            this.mSmileySubViewModelList = smileySubViewModelList;
        }

        @Override
        public int getPageIconResId(int position) {
            int resourceId = getResourceId(mSmileySubViewModelList.get(position).getImage());
            return resourceId;
        }

        @Override
        public int getCount() {
            return mSmileySubViewModelList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {


            LinearLayout parent = new LinearLayout(getContext());
            parent.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LinearLayout parent = new LinearLayout(getContext());
            LayoutParams parentParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            parent.setLayoutParams(parentParams);
            parent.setGravity(Gravity.CENTER);

            GridView grid = new GridView(getContext());
            LayoutParams gridPrams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            int topMargin = convertDpToPixel(5, getContext());
            gridPrams.setMargins(0, topMargin, 0, 0);
            grid.setLayoutParams(gridPrams);
            grid.setNumColumns(4);
            grid.setColumnWidth(GridView.AUTO_FIT);

            int verticalSpacing = convertDpToPixel(12, getContext());
            int horizontalSpacing = convertDpToPixel(5, getContext());
            grid.setVerticalSpacing(verticalSpacing);
            grid.setHorizontalSpacing(horizontalSpacing);
            grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            grid.setGravity(Gravity.CENTER);
            parent.addView(grid);

            List<SubViewItemModel.Content> contents
                    = mSmileySubViewModelList.get(position).getContents();
            EmojiGridAdapter emojiGridAdapter = new EmojiGridAdapter(
                    (ArrayList<SubViewItemModel.Content>) contents);
            grid.setAdapter(emojiGridAdapter);

            container.addView(parent);
            return parent;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    /**
     * Emoji Grid adapter inner class
     */
    private class EmojiGridAdapter extends BaseAdapter {
        private final String TAG = EmojiGridAdapter.class.getName();
        private ArrayList<SubViewItemModel.Content> mEmojiContents;

        public EmojiGridAdapter(ArrayList<SubViewItemModel.Content> emojiContents) {
            this.mEmojiContents = emojiContents;
        }

        public int getCount() {
            if (mEmojiContents != null)
                return mEmojiContents.size();
            else
                return 0;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(getContext());
                int imageHeightWidth = convertDpToPixel(60, getContext());
                LayoutParams imagePrams = new LayoutParams(
                        imageHeightWidth, imageHeightWidth);
                imageView.setLayoutParams(imagePrams);
            } else {
                imageView = (ImageView) convertView;
            }

            final SubViewItemModel.Content content = mEmojiContents.get(position);
            try {
                int resourceId = getResourceId(content.getImage());
                imageView.setImageResource(resourceId);
            } catch (Exception e) {
                Log.d(TAG, "getView: " + e.getMessage());
            }

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        PayloadEvent payloadEvent = new PayloadEvent(content.getPayload(),
                                content.getAction(), content.getImage());
                        EventBus.getDefault().post(payloadEvent);
                    } catch (Exception e) {
                        Log.d(TAG, "getView: " + e.getMessage());
                    }
                }
            });

            return imageView;
        }
    }


}
