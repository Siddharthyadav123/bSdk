package com.morfeus.android.mfsdk.ui.widget.bubble;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateWithLimitModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplateWithLimit extends TemplateView {

    public static final String BUTTON_LIMIT_CARD_TEMPLATE_VIEW = "ButtonCardTemplateWithLimit";
    private static Context sContext;

    public MfButtonCardTemplateWithLimit(Context context) {
        super(context);
        sContext = context;
    }

    private static int getResourceId(String imageName) {
        Resources resources = sContext.getResources();
        return resources.getIdentifier(imageName, "drawable",
                sContext.getPackageName());
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(BUTTON_LIMIT_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplateWithLimit) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_button_with_limit_template_layout, this);
    }

    @Override
    public void setBackground() {
        switch (type) {
            case INCOMING:

                break;
            case OUTGOING:

                break;
            default:
                throw new IllegalArgumentException("Error: Invalid template type!");
        }
    }

    @Override
    public BaseView create(Context context) {
        return new MfButtonCardTemplateWithLimit(context);
    }


    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private static final String TAG = ViewHolder.class.getName();
        private Context mContext;
        private LinearLayout mLLContainer;
        private RelativeLayout mRLBodyLayout;
        private TextView mTVMainHeading;

        private SelectableRoundedImageView mSenderImage;

        private TextView mTVLimitLabel;
        private TextView mTVSelectedLimit;
        private SeekBar mSBLimitBar;
        private TextView mTVIncreateLimitBtn;

        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_with_limit_temp_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);

            this.mTVLimitLabel = (TextView) itemView.findViewById(R.id.tv_label_limit);
            this.mTVSelectedLimit = (TextView) itemView.findViewById(R.id.tv_selected_limit);
            this.mSBLimitBar = (SeekBar) itemView.findViewById(R.id.sb_limit_bar);
            this.mTVIncreateLimitBtn = (TextView) itemView.findViewById(R.id.tv_limit_button);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateWithLimitModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfButtonCardTemplateWithLimitModel mfButtonCardTemplateWithLimitModel = (MfButtonCardTemplateWithLimitModel) model;
            applyDefaultCardStyle(mfButtonCardTemplateWithLimitModel.isIncoming());

            if (mfButtonCardTemplateWithLimitModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            List<MfListContentData> mfListContentDatas = mfButtonCardTemplateWithLimitModel.getMfListContentDatas();

            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                switch (i) {
                    case 0:
                        feedTitle(mfComponentDatas);
                        break;
                    case 1:
                        feedSliderDetails(mfComponentDatas);
                        break;
                    case 2:
                        feedFooterCardDetails(mfComponentDatas, mfButtonCardTemplateWithLimitModel);
                        break;

                }
            }
        }

        private void feedFooterCardDetails(List<MfComponentData> mfComponentDatas,
                                           final MfButtonCardTemplateWithLimitModel mfButtonCardTemplateWithLimitModel) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("buttons")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVIncreateLimitBtn.setText(title);
                    mTVIncreateLimitBtn.setTextColor(colorInt);
                    mTVIncreateLimitBtn.setVisibility(View.VISIBLE);
                    mTVIncreateLimitBtn.setTag(mfElementData.getId());

                    mTVIncreateLimitBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String payload = mfElementData.getPayload();
                            String action = mfElementData.getAction();
                            String imageName = mfElementData.getImageName();
                            EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                        }
                    });
                } else {
                    if (mTVIncreateLimitBtn.getTag() == null) {
                        mTVIncreateLimitBtn.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVIncreateLimitBtn.setVisibility(View.GONE);
            }
        }


        private void feedSliderDetails(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVLimitLabel.setText(title);
                    mTVLimitLabel.setTextColor(colorInt);
                    mTVLimitLabel.setVisibility(View.VISIBLE);
                    mTVLimitLabel.setTag(mfElementData.getId());
                } else {
                    if (mTVLimitLabel.getTag() == null) {
                        mTVLimitLabel.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVLimitLabel.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVSelectedLimit.setText(title);
                    mTVSelectedLimit.setTextColor(colorInt);
                    mTVSelectedLimit.setVisibility(View.VISIBLE);
                    mTVSelectedLimit.setTag(mfElementData.getId());
                } else {
                    if (mTVSelectedLimit.getTag() == null) {
                        mTVSelectedLimit.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVSelectedLimit.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 2) {
                MfComponentData mfComponentData = mfComponentDatas.get(2);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("slider")) {
                    int minimumValue = mfElementData.getMinimumValue();
                    int maximumValue = mfElementData.getMaximumValue();
                    int interval = mfElementData.getInterval();
                    mSBLimitBar.setMax(maximumValue);
                    mSBLimitBar.setProgress(interval);

                    if (mfElementData.getStyleData() != null) {
                        String thumbImage = mfElementData.getStyleData().getThumbImage();
                        String sliderColor = mfElementData.getStyleData().getSliderColor();
                        if (sliderColor != null) {
                            try {
                                int colorInt = Color.parseColor(sliderColor);
                                mSBLimitBar.getProgressDrawable().setColorFilter(colorInt, PorterDuff.Mode.MULTIPLY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (!(TextUtils.isEmpty(thumbImage))) {
                            int resourceId = 0;
                            try {
                                resourceId = getResourceId(thumbImage);
                                if (resourceId != 0) {
                                    this.mSBLimitBar.setThumb(mContext.getResources().getDrawable(resourceId));
                                }
                            } catch (Exception e) {
                                LogManager.e(TAG, e.getMessage());
                            }
                        }
                    }
                } else {
                    if (mSBLimitBar.getTag() == null) {
                        mSBLimitBar.setVisibility(View.GONE);
                    }
                }
            } else {
                mSBLimitBar.setVisibility(View.GONE);
            }
        }

        private void feedTitle(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVMainHeading.setText(title);
                    mTVMainHeading.setTextColor(colorInt);
                    mTVMainHeading.setVisibility(View.VISIBLE);
                    mTVMainHeading.setTag(mfElementData.getId());
                } else {
                    if (mTVMainHeading.getTag() == null) {
                        mTVMainHeading.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVMainHeading.setVisibility(View.GONE);
            }
        }


        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = BUTTON_LIMIT_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_LIMIT_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
            }

            try {
                Style defaultStyle = StyleFactory.getInstance().getStyle(styleTemplateId);
                if (defaultStyle != null) {
                    String backgroundColor = defaultStyle.getBackgroundColor();
                    String backgroundImage = defaultStyle.getBackgroundImage();
                    String backgroundColorShape = defaultStyle.getBackgroundColorShape();
                    int paddingLeft = defaultStyle.getPaddingLeft();
                    int paddingRight = defaultStyle.getPaddingRight();
                    String messageTimeColor = defaultStyle.getMessageTimeColor();
                    String botImage = defaultStyle.getBotImage();
                    int maxWidth = defaultStyle.getMaxWidth();
                    List<Style> componentStyle = defaultStyle.getComponentStyle();

                    //setting common properties
                    if (backgroundColor != null && backgroundColor.length() > 0) {
                        Drawable backgroundDrwable = mRLBodyLayout.getBackground();
                        if (backgroundDrwable != null) {
                            if (backgroundDrwable instanceof GradientDrawable) {
                                // cast to 'ShapeDrawable'
                                GradientDrawable gradientDrawable = (GradientDrawable) backgroundDrwable;
                                int colorId = Color.parseColor(backgroundColor);
                                gradientDrawable.setColor(colorId);
                                mRLBodyLayout.setBackgroundDrawable(backgroundDrwable);
                            }
                        }
                    }

                    if (backgroundColorShape != null && backgroundColorShape.length() > 0) {
                        //TODO set background color shape based on bubble
                    }

                    if (messageTimeColor != null && messageTimeColor.length() > 0) {
                        //TODO set message time color
                    }


                    if (incoming) {
                        if (backgroundImage != null && backgroundImage.length() > 0) {
                            //TODO set background image based on bubble
                        }
                        //set padding
                        mLLContainer.setPadding(convertDpToPixel(paddingLeft, mContext), mLLContainer.getPaddingTop(),
                                mLLContainer.getPaddingRight(), mLLContainer.getPaddingBottom());

                        if (botImage != null && botImage.length() > 0) {
                            if (!(TextUtils.isEmpty(botImage))) {
                                int resourceId = 0;
                                try {
                                    resourceId = getResourceId(botImage);
                                } catch (Exception e) {
                                    LogManager.e(TAG, e.getMessage());
                                }

                                if (resourceId != 0) {
                                    mSenderImage.setImageResource(resourceId);
                                } else {
                                    mSenderImage.setImageResource(R.drawable.bot);
                                }
                            } else {
                                mSenderImage.setImageResource(R.drawable.bot);
                            }
                        }

                    } else {
                        //set padding
                        mLLContainer.setPadding(mLLContainer.getPaddingLeft(), mLLContainer.getPaddingTop(),
                                convertDpToPixel(paddingRight, mContext), mLLContainer.getPaddingBottom());
                    }


                    if (componentStyle != null && componentStyle.size() > 0) {
                        Style titleStyle = null;
                        Style subTitleStyle = null;
                        Style descStyle = null;
                        Style buttonStyle = null;

                        for (int i = 0; i < componentStyle.size(); i++) {
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_TITLE)) {
                                titleStyle = componentStyle.get(i);
                            }
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_SUB_TITLE)) {
                                subTitleStyle = componentStyle.get(i);
                            }
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_DESCRIPTION)) {
                                descStyle = componentStyle.get(i);
                            }
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_BUTTON)) {
                                buttonStyle = componentStyle.get(i);
                            }
                        }

                        if (titleStyle != null) {
                            String textColor = titleStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                mTVMainHeading.setTextColor(colorId);
                            }
                        }

                    }
                }
            } catch (StyleNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
