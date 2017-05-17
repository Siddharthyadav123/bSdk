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
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;

import org.greenrobot.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkNotNull;

public final class FeedbackSubViewComponent extends LinearLayout implements Component {

    private LinearLayout mRootLayout;
    private SubViewItemModel mModel;
    private ActionManager mActionManager;

    public FeedbackSubViewComponent(Context context,
                                    @NonNull BaseModel baseModel,
                                    @NonNull ActionManager actionManager) {
        super(context);
        mModel = (SubViewItemModel) checkNotNull(baseModel);
        mActionManager = checkNotNull(actionManager);

    }

    public FeedbackSubViewComponent(Context context, AttributeSet attrs) {
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
            setGravity(Gravity.CENTER);
            setBgColor();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            mRootLayout = new LinearLayout(getContext());
            layoutParams.gravity = Gravity.CENTER;
            mRootLayout.setOrientation(LinearLayout.HORIZONTAL);
            mRootLayout.setLayoutParams(layoutParams);
            mRootLayout.setId(R.id.component_subview_feedback);
            mRootLayout.setGravity(Gravity.CENTER);
            feedImages();
            addView(mRootLayout);
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

    private void feedImages() throws Exception {
        if (mModel != null) {
            if (mModel.getContents() != null && mModel.getContents().size() > 0) {
                for (int i = 0; i < mModel.getContents().size(); i++) {
                    final SubViewItemModel.Content content = mModel.getContents().get(i);

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    int margin = convertDpToPixel(40);
                    param.setMargins(margin, margin, margin, margin);

                    ImageViewComponent likeDisLikeView = new ImageViewComponent(getContext(),
                            null, null, content.getAction(), mActionManager);
                    likeDisLikeView.setLayoutParams(param);
                    likeDisLikeView.setImageResource(getResourceId(content.getImage()));

                    likeDisLikeView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PayloadEvent payloadEvent = new PayloadEvent(
                                    content.getPayload(),
                                    content.getAction(),
                                    content.getImage()
                            );
                            EventBus.getDefault().post(payloadEvent);
                        }
                    });

                    mRootLayout.addView(likeDisLikeView, param);
                }
            }
        }
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
//        LikeSubViewComponent likeSubViewComponent = (LikeSubViewComponent) LayoutInflater.from(getContext())
//                .inflate(R.layout.component_subview_like_dislike, viewGroup, attach);
        return null;
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    private int convertDpToPixel(float dp) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }
}
