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
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateWithReminderInfoModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplateWithReminderInfo extends TemplateView {
    public static final String BUTTON_TEMPLATE_WITH_REMINDER_INFO_VIEW = "ButtonCardTemplateWithReminderInfo";
    private static Context sContext;

    public MfButtonCardTemplateWithReminderInfo(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(BUTTON_TEMPLATE_WITH_REMINDER_INFO_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplateWithReminderInfo) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_button_with_reminder_info_template_layout, this);
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
        return new MfButtonCardTemplateWithReminderInfo(context);
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

        private ImageView mIvCardImage;
        private TextView mTvCardName;
        private TextView mTvDueDetails;

        private View mSepratorOne;
        private View mSepratorTwo;
        private View mSepratorThree;
        private View mSepratorFour;
        private View mSepratorFive;
        private TextView mBtnOne;
        private TextView mBtnTwo;
        private TextView mBtnThree;
        private TextView mBtnFour;
        private TextView mBtnFive;

        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_with_reminder_info_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);

            this.mIvCardImage = (ImageView) itemView.findViewById(R.id.iv_card_image);
            this.mTvCardName = (TextView) itemView.findViewById(R.id.tv_card_name);
            this.mTvDueDetails = (TextView) itemView.findViewById(R.id.tv_due_details);

            this.mSepratorOne = itemView.findViewById(R.id.seprator_one);
            this.mBtnOne = (TextView) itemView.findViewById(R.id.tv_btn_one);
            this.mSepratorTwo = itemView.findViewById(R.id.seprator_two);
            this.mBtnTwo = (TextView) itemView.findViewById(R.id.tv_btn_two);
            this.mSepratorThree = itemView.findViewById(R.id.seprator_three);
            this.mBtnThree = (TextView) itemView.findViewById(R.id.tv_btn_three);
            this.mSepratorFour = itemView.findViewById(R.id.seprator_four);
            this.mBtnFour = (TextView) itemView.findViewById(R.id.tv_btn_four);
            this.mSepratorFive = itemView.findViewById(R.id.seprator_five);
            this.mBtnFive = (TextView) itemView.findViewById(R.id.tv_btn_five);

        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateWithReminderInfoModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfButtonCardTemplateWithReminderInfoModel mfButtonCardTemplateWithReminderInfoModel
                    = (MfButtonCardTemplateWithReminderInfoModel) model;
            applyDefaultCardStyle(mfButtonCardTemplateWithReminderInfoModel.isIncoming());


            if (mfButtonCardTemplateWithReminderInfoModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            List<MfListContentData> mfListContentDatas = mfButtonCardTemplateWithReminderInfoModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                switch (i) {
                    case 0:
                        feedHeader(mfComponentDatas);
                        break;
                    case 1:
                        feedCardInfo(mfComponentDatas);
                        break;
                    case 2:
                        feedFooter(mfComponentDatas.get(0).getElementDataList(),
                                mfButtonCardTemplateWithReminderInfoModel);
                        break;
                }

            }
        }

        private void feedCardInfo(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvCardName.setText(title);
                    mTvCardName.setTextColor(colorInt);
                    mTvCardName.setVisibility(View.VISIBLE);
                } else {
                    mTvCardName.setVisibility(View.GONE);
                }
            } else {
                mTvCardName.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvDueDetails.setText(title);
                    mTvDueDetails.setTextColor(colorInt);
                    mTvDueDetails.setVisibility(View.VISIBLE);
                } else {
                    mTvDueDetails.setVisibility(View.GONE);
                }
            } else {
                mTvDueDetails.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 2) {
                MfComponentData mfComponentData = mfComponentDatas.get(2);
                final MfElementData cardElement = mfComponentData.getElementData();
                if (cardElement != null && cardElement.getId().equals("image")) {
                    int visible = mIvCardImage.getVisibility();
                    if (visible == View.GONE || visible == View.INVISIBLE) {
                        mIvCardImage.setVisibility(VISIBLE);
                    }
                    String image = cardElement.getImageName();
                    if (!(TextUtils.isEmpty(image))) {
                        int resourceId = 0;
                        try {
                            resourceId = getResourceId(image);
                        } catch (Exception e) {
                            LogManager.e(TAG, e.getMessage());
                        }
                        if (resourceId != 0) {
                            mIvCardImage.setImageResource(resourceId);
                        } else {
                            mIvCardImage.setImageResource(R.drawable.visa_card);
                        }
                    } else {
                        mIvCardImage.setImageResource(R.drawable.visa_card);
                    }
                }
            }


        }


        private void updateButtonInfo(final MfElementData elementData,
                                      final MfButtonCardTemplateWithReminderInfoModel mfButtonCardTemplateWithReminderInfoModel,
                                      TextView btnTextView, View sepratorView) {
            String subTitle = elementData.getText();
            btnTextView.setVisibility(View.VISIBLE);
            sepratorView.setVisibility(View.VISIBLE);
            btnTextView.setText(subTitle);

            if (elementData.getStyleData() != null
                    && elementData.getStyleData().getTextColor() != null) {
                String color = elementData.getStyleData().getTextColor();
                int colorInt = Color.parseColor(color);
                btnTextView.setTextColor(colorInt);
            }
            btnTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String payload = elementData.getPayload();
                    String action = elementData.getAction();
                    String imageName = elementData.getImageName();
                    EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                }
            });
        }


        private void feedFooter(List<MfElementData> buttonsList,
                                MfButtonCardTemplateWithReminderInfoModel mfButtonCardTemplateWithReminderInfoModel) {
            TextView[] btnArray = {mBtnOne, mBtnTwo, mBtnThree, mBtnFour, mBtnFive};
            View[] sepratorViewArray = {mSepratorOne, mSepratorTwo, mSepratorThree, mSepratorFour, mSepratorFive};

            if (buttonsList != null) {
                for (int i = 0; i < btnArray.length; i++) {
                    if (buttonsList.size() > i) {
                        updateButtonInfo(buttonsList.get(i), mfButtonCardTemplateWithReminderInfoModel,
                                btnArray[i], sepratorViewArray[i]);
                    } else {
                        btnArray[i].setVisibility(View.GONE);
                        sepratorViewArray[i].setVisibility(View.GONE);
                    }
                }
            }
        }


        private void feedHeader(List<MfComponentData> mfComponentDatas) {
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
                styleTemplateId = BUTTON_TEMPLATE_WITH_REMINDER_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_TEMPLATE_WITH_REMINDER_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
