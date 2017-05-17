package com.morfeus.android.mfsdk.ui.widget.loading;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;
import com.morfeus.android.mfsdk.ui.widget.loading.style.LoadingTitleStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatLoadingView extends RelativeLayout implements LoadingView {
    private Context mContext;
    private ProgressBar mProgressBar;
    private ImageView mHeaderImageView;
    private ImageView mBlackShadowImageView;
    private TextView mLoadingTextView;
    private RelativeLayout mParentRelLayout;
    private RelativeLayout mBodyRelLayout;
    private RelativeLayout mRootView;

    private LoadingModel mLoadingModel;

    public ChatLoadingView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChatLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public ChatLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    public void show() {
        showParentLayoutWithAnim();
    }

    public void hide() {
        if (mParentRelLayout.getVisibility() == View.VISIBLE) {
            hideParentLayoutWithAnim();
        }
    }


    private void showParentLayoutWithAnim() {
        Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        mParentRelLayout.setVisibility(View.VISIBLE);
        mParentRelLayout.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mParentRelLayout.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void hideParentLayoutWithAnim() {
        Animation fadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        mParentRelLayout.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mParentRelLayout.setVisibility(View.GONE);
                mParentRelLayout.clearAnimation();
                mHeaderImageView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.chat_default_loading_layout, null);

        mParentRelLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_parent_layout);
        mBodyRelLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_body_layout);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.pb_loading_bar);
        mHeaderImageView = (ImageView) mRootView.findViewById(R.id.iv_loading_image);
        mBlackShadowImageView = (ImageView) mRootView.findViewById(R.id.iv_black_shadow);
        mLoadingTextView = (TextView) mRootView.findViewById(R.id.tv_loading_text);

        mLoadingTextView.setText(LanguageRepository.getInstance().getText(
                getResources().getString(R.string.text_loading)));

        addView(mRootView);
        if (mLoadingModel != null) {
            if (mLoadingModel.getLogo() != null) {
                String imageName = mLoadingModel.getLogo().getImageName();
                if (imageName != null) {
                    mHeaderImageView.setImageResource(getResourceId(imageName));
                }
            }

            if (mLoadingModel.getLoadingText() != null) {
                String text = mLoadingModel.getLoadingText().getLabel();
                if (text != null) {
                    mLoadingTextView.setText(LanguageRepository.getInstance().getText(text));
                }

                if (mLoadingModel.getLoadingText().getStyle() != null) {
                    LoadingTitleStyle loadingTitleStyle = (LoadingTitleStyle) mLoadingModel.getLoadingText().getStyle();
                    if (loadingTitleStyle.getLoadingTextColor() != null) {
                        mLoadingTextView.setTextColor(Color.parseColor(loadingTitleStyle.getLoadingTextColor()));
                    }
                }
            }

            if (mLoadingModel.getProgressView() != null) {
                String color = mLoadingModel.getProgressView().getColor();
                if (color != null) {
                    mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
                }
            }

        }
    }


    @Override
    public void setData(@NonNull LoadingModel model) {
        mLoadingModel = checkNotNull(model);
        invalidate();
        initView();
    }

    @Override
    public LoadingModel getData() {
        return mLoadingModel;
    }

    @Override
    public void setLoadingText(@NonNull String title) {
        // No-op
    }

    @Override
    public void setLoadingImage(@NonNull String imageName) {
        // No-op
    }

    private int getResourceId(String imageName) {
        try {
            Resources resources = mContext.getResources();
            return resources.getIdentifier(imageName, "drawable",
                    mContext.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_camera_off;
        }
    }
}
