package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfTouchIdCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfTouchIdCardTemplate extends TemplateView {
    public static final String TOUCH_ID_CARD_TEMPLATE_VIEW = "TouchIdCardTemplate";
    private static Context sContext;

    public MfTouchIdCardTemplate(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(TOUCH_ID_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfTouchIdCardTemplate) LayoutInflater.from(context)
                .inflate(R.layout.chat_itme_touch_id_card_template_layout, this);
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
        return new MfTouchIdCardTemplate(context);
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

        private ImageView mIVThumbImage;
        private ImageView mIVPersonImage;
        private TextView mTVPersonName;
        private TextView mTVAmount;
        private TextView mTVFooterTitle;
        private TextView mTVFooterSubTitle;

        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_touch_temp_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);
            this.mIVThumbImage = (ImageView) itemView.findViewById(R.id.iv_thumb);
            this.mIVPersonImage = (ImageView) itemView.findViewById(R.id.iv_person_image);
            this.mTVPersonName = (TextView) itemView.findViewById(R.id.tv_person_name);
            this.mTVAmount = (TextView) itemView.findViewById(R.id.tv_amount);

            this.mTVFooterTitle = (TextView) itemView.findViewById(R.id.tv_footer_title);
            this.mTVFooterSubTitle = (TextView) itemView.findViewById(R.id.tv_footer_subtitle);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfTouchIdCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfTouchIdCardTemplateModel mfTouchIdCardTemplateModel = (MfTouchIdCardTemplateModel) model;
            applyDefaultCardStyle(mfTouchIdCardTemplateModel.isIncoming());

            if (mfTouchIdCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            List<MfListContentData> mfListContentDatas = mfTouchIdCardTemplateModel.getMfListContentDatas();

            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                switch (i) {
                    case 0:
                        feedTitle(mfComponentDatas);
                        break;
                    case 1:
                        feedTouchImage(mfComponentDatas);
                        break;
                    case 2:
                        feedProfileImageAndPaymentDetails(mfComponentDatas);
                        break;
                    case 3:
                        feedFooterCardDetails(mfComponentDatas);
                        break;

                }
            }
        }

        private void feedFooterCardDetails(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVFooterTitle.setText(title);
                    mTVFooterTitle.setTextColor(colorInt);
                    mTVFooterTitle.setVisibility(View.VISIBLE);
                    mTVFooterTitle.setTag(mfElementData.getId());
                } else {
                    if (mTVFooterTitle.getTag() == null) {
                        mTVFooterTitle.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVFooterTitle.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVFooterSubTitle.setText(title);
                    mTVFooterSubTitle.setTextColor(colorInt);
                    mTVFooterSubTitle.setVisibility(View.VISIBLE);
                    mTVFooterSubTitle.setTag(mfElementData.getId());
                } else {
                    if (mTVFooterSubTitle.getTag() == null) {
                        mTVFooterSubTitle.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVFooterSubTitle.setVisibility(View.GONE);
            }
        }

        private void feedProfileImageAndPaymentDetails(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVPersonName.setText(title);
                    mTVPersonName.setTextColor(colorInt);
                    mTVPersonName.setVisibility(View.VISIBLE);
                    mTVPersonName.setTag(mfElementData.getId());
                } else {
                    if (mTVPersonName.getTag() == null) {
                        mTVPersonName.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVPersonName.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTVAmount.setText(title);
                    mTVAmount.setTextColor(colorInt);
                    mTVAmount.setVisibility(View.VISIBLE);
                    mTVAmount.setTag(mfElementData.getId());
                } else {
                    if (mTVAmount.getTag() == null) {
                        mTVAmount.setVisibility(View.GONE);
                    }
                }
            } else {
                mTVAmount.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 2) {
                MfComponentData mfComponentData = mfComponentDatas.get(2);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("image")) {
                    int visible = mIVPersonImage.getVisibility();
                    if (visible == View.GONE || visible == View.INVISIBLE) {
                        mIVPersonImage.setVisibility(VISIBLE);
                    }
                    String image = mfElementData.getImageName();
                    if (!(TextUtils.isEmpty(image))) {
                        int resourceId = 0;
                        try {
                            resourceId = getResourceId(image);
                        } catch (Exception e) {
                            LogManager.e(TAG, e.getMessage());
                        }
                        if (resourceId != 0) {
                            mIVPersonImage.setImageResource(resourceId);
                        } else {
//                            mIVPersonImage.setImageResource(R.drawable.ic_emoji_off);
                        }
                    } else {
//                        mIVPersonImage.setImageResource(R.drawable.ic_emoji_off);
                    }
                }
            }
        }

        private void feedTouchImage(List<MfComponentData> mfComponentDatas) {
            MfComponentData mfComponentData = mfComponentDatas.get(0);
            final MfElementData mfElementData = mfComponentData.getElementData();

            if (mfElementData != null && mfElementData.getId().equals("image")) {
                int visible = mIVThumbImage.getVisibility();
                if (visible == View.GONE || visible == View.INVISIBLE) {
                    mIVThumbImage.setVisibility(VISIBLE);
                }
                String image = mfElementData.getImageName();
                if (!(TextUtils.isEmpty(image))) {
                    int resourceId = 0;
                    try {
                        resourceId = getResourceId(image);
                    } catch (Exception e) {
                        LogManager.e(TAG, e.getMessage());
                    }
                    if (resourceId != 0) {
                        mIVThumbImage.setImageResource(resourceId);
                    } else {
                        mIVThumbImage.setImageResource(R.drawable.index);
                    }
                } else {
                    mIVThumbImage.setImageResource(R.drawable.index);
                }
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
                styleTemplateId = TOUCH_ID_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = TOUCH_ID_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
