package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MenuSubViewComponent extends LinearLayout implements Component {

    private SubViewItemModel mModel;
    private ActionManager mActionManager;
    private GridView mGridViewMenus;
    private boolean isViewAdded;

    public MenuSubViewComponent(Context context,
                                @NonNull BaseModel baseModel,
                                @NonNull ActionManager actionManager) {
        super(context);

        mModel = (SubViewItemModel) checkNotNull(baseModel);
        mActionManager = checkNotNull(actionManager);
    }

    public MenuSubViewComponent(Context context, AttributeSet attrs) {
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
                mGridViewMenus = new GridView(getContext());
                mGridViewMenus.setLayoutParams(new GridView.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
//                mGridViewMenus.setBackgroundColor(Color.WHITE);
                mGridViewMenus.setNumColumns(3);
                mGridViewMenus.setGravity(Gravity.CENTER);
                mGridViewMenus.setId(R.id.component_subview_menu);
                List<SubViewItemModel.Content> contents = mModel.getContents();

                MenuAdapter adapter = new MenuAdapter(
                        getContext(),
                        (ArrayList<SubViewItemModel.Content>) contents,
                        mActionManager);
                mGridViewMenus.setAdapter(adapter);
                setGravity(Gravity.CENTER);
                addView(mGridViewMenus);
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

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return null;
    }

    private static class MenuAdapter extends ArrayAdapter<SubViewItemModel.Content> {

        private ActionManager mActionManager;
        private LanguageRepository languageRepository;

        public MenuAdapter(Context context, ArrayList<SubViewItemModel.Content> contents,
                           ActionManager actionManager) {
            super(context, 0, contents);
            this.mActionManager = actionManager;
            languageRepository = LanguageRepository.getInstance();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final SubViewItemModel.Content content = getItem(position);

            ImageViewComponent imageViewComponent = null;
            TextView footerLabel = null;

            if (convertView == null) {
                imageViewComponent = new ImageViewComponent(
                        getContext(),
                        null,
                        null,
                        content.getAction(),
                        this.mActionManager);
                imageViewComponent.setId(R.id.component_menu_subview_image);

                int topPadding = convertDpToPixel(12, getContext());
                int bottomPadding = convertDpToPixel(6, getContext());
                imageViewComponent.setPadding(0, topPadding, 0, bottomPadding);

                footerLabel = new TextView(getContext());
                footerLabel.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                footerLabel.setId(R.id.component_menu_subview_label);
                footerLabel.setTextSize(12);
                LinearLayout parentLayout = new LinearLayout(getContext());
                parentLayout.setId(R.id.component_menu_subview);
                parentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                parentLayout.setOrientation(LinearLayout.VERTICAL);
                parentLayout.setGravity(Gravity.CENTER);

                parentLayout.addView(imageViewComponent);
                parentLayout.addView(footerLabel);

                convertView = parentLayout;
                convertView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            } else {
                imageViewComponent = (ImageViewComponent) convertView.findViewById(R.id.component_menu_subview_image);
                footerLabel = (TextView) convertView.findViewById(R.id.component_menu_subview_label);
            }

            int imageResourceId = getResourceId(content.getImage());
            imageViewComponent.setImageResource(imageResourceId);
            String label = content.getLabel();
            footerLabel.setText(languageRepository.getText(label));


            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    PayloadEvent payloadEvent = new PayloadEvent(content.getPayload(), content.getAction(), null);
                    EventBus.getDefault().post(payloadEvent);
                }
            });
            return convertView;
        }

        protected int convertDpToPixel(float dp, Context context) {
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return (int) px;
        }


        private int getResourceId(String imageName) {
            Resources resources = getContext().getResources();
            return resources.getIdentifier(imageName, "drawable",
                    getContext().getPackageName());
        }
    }

}
