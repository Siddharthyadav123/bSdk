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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateWithLoanInfoModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplateWithLoanInfo extends TemplateView {
    public static final String BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW = "ButtonCardTemplateWithLoanInfo";
    private static Context sContext;

    public MfButtonCardTemplateWithLoanInfo(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplateWithLoanInfo) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_button_card_with_loan_info_layout, this);
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
        return new MfButtonCardTemplateWithLoanInfo(context);
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

        private SelectableRoundedImageView mSenderImage;

        private ProgressBar mPaidPercentageProgressBar;
        private TextView mProgressPercentTextView;
        private TextView mProgressMsgTextView;
        private ImageView mLoanAmountDueImage;
        private TextView mLoanAmountDueTextView;
        private TextView mLoanAmountDueLabelTextView;
        private View mSepratorOne;
        private View mSepratorTwo;
        private TextView mBtnOne;
        private TextView mBtnTwo;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_with_loan_info_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);

            this.mPaidPercentageProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_paid);
            this.mProgressPercentTextView = (TextView) itemView.findViewById(R.id.tv_progress_percentage);
            this.mProgressMsgTextView = (TextView) itemView.findViewById(R.id.tv_progress_msg);
            this.mLoanAmountDueImage = (ImageView) itemView.findViewById(R.id.iv_loan_amount_image);
            this.mLoanAmountDueTextView = (TextView) itemView.findViewById(R.id.tv_loan_amount_due_text);
            this.mLoanAmountDueLabelTextView = (TextView) itemView.findViewById(R.id.tv_loan_amount_due_label_text);
            this.mSepratorOne = itemView.findViewById(R.id.seprator_one);
            this.mSepratorTwo = itemView.findViewById(R.id.seprator_two);
            this.mBtnOne = (TextView) itemView.findViewById(R.id.tv_btn_one);
            this.mBtnTwo = (TextView) itemView.findViewById(R.id.tv_btn_two);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateWithLoanInfoModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfButtonCardTemplateWithLoanInfoModel mfButtonCardTemplateWithLoanInfoModel = (MfButtonCardTemplateWithLoanInfoModel) model;
            applyDefaultCardStyle(mfButtonCardTemplateWithLoanInfoModel.isIncoming());


            if (mfButtonCardTemplateWithLoanInfoModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            List<MfListContentData> mfListContentDatas = mfButtonCardTemplateWithLoanInfoModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                formLoadCardAndFeed(mfComponentDatas, mfButtonCardTemplateWithLoanInfoModel);
            }
        }

        private void formLoadCardAndFeed(List<MfComponentData> mfComponentDatas,
                                         MfButtonCardTemplateWithLoanInfoModel mfButtonCardTemplateWithLoanInfoModel) {
            for (int i = 0; i < mfComponentDatas.size(); i++) {
                MfComponentData mfComponentData = mfComponentDatas.get(i);
                MfElementData mfElementData = mfComponentData.getElementData();
                List<MfElementData> buttonsList = mfComponentData.getElementDataList();

                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mLoanAmountDueTextView.setText(title);
                    mLoanAmountDueTextView.setVisibility(View.VISIBLE);
                    mLoanAmountDueTextView.setTag(title);
                    mLoanAmountDueTextView.setTextColor(colorInt);
                } else {
                    if (mLoanAmountDueTextView.getTag() == null) {
                        mLoanAmountDueTextView.setVisibility(View.GONE);
                    }
                }

                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mLoanAmountDueLabelTextView.setText(title);
                    mLoanAmountDueLabelTextView.setVisibility(View.VISIBLE);
                    mLoanAmountDueLabelTextView.setTag(title);
                    mLoanAmountDueLabelTextView.setTextColor(colorInt);
                } else {
                    if (mLoanAmountDueLabelTextView.getTag() == null) {
                        mLoanAmountDueLabelTextView.setVisibility(View.GONE);
                    }
                }

                if (mfElementData != null && mfElementData.getId().equals("image")) {
                    int visible = mLoanAmountDueImage.getVisibility();
                    if (visible == View.GONE || visible == View.INVISIBLE) {
                        mLoanAmountDueImage.setVisibility(VISIBLE);
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
                            mLoanAmountDueImage.setImageResource(resourceId);
                        } else {
                            mLoanAmountDueImage.setImageResource(R.drawable.red_weel);
                        }
                    }
                }

                if (mfElementData != null && mfElementData.getId().equals("gauge")) {
                    int coverPercentage = mfElementData.getCoverPercentage();
                    String subText = mfElementData.getSubText();

                    mPaidPercentageProgressBar.setProgress(coverPercentage);
                    mProgressPercentTextView.setText(coverPercentage + "%");
                    mProgressMsgTextView.setText(subText);

                    if (mfElementData.getStyleData() != null) {
                        String bgColor = mfElementData.getStyleData().getBackgroundColor();
                        String textColor = mfElementData.getStyleData().getTextColor();
                        //TODO not sure about above two properties. means where to set?

                        String fillColor = mfElementData.getStyleData().getFillColor();
                        if (fillColor != null) {
                            try {
                                int colorInt = Color.parseColor(fillColor);
                                mPaidPercentageProgressBar.getProgressDrawable().setColorFilter(colorInt, PorterDuff.Mode.MULTIPLY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (buttonsList != null) {
                    feedFooter(buttonsList, mBtnOne, mBtnTwo,
                            mSepratorOne, mSepratorTwo,
                            mfButtonCardTemplateWithLoanInfoModel);
                }

            }
        }

        private void feedFooter(List<MfElementData> buttonsList, TextView btnOne, TextView btnTwo,
                                View sepratorOne, View sepratorTwo,
                                MfButtonCardTemplateWithLoanInfoModel mfButtonCardTemplateWithLoanInfoModel) {

            TextView[] btnArray = {btnOne, btnTwo,};
            View[] sepratorViewArray = {sepratorOne, sepratorTwo};

            if (buttonsList != null) {
                for (int i = 0; i < btnArray.length; i++) {
                    if (buttonsList.size() > i) {
                        updateButtonInfo(buttonsList.get(i), mfButtonCardTemplateWithLoanInfoModel,
                                btnArray[i], sepratorViewArray[i]);
                    } else {
                        btnArray[i].setVisibility(View.GONE);
                        sepratorViewArray[i].setVisibility(View.GONE);
                    }
                }
            }
        }

        private void updateButtonInfo(final MfElementData elementData,
                                      final MfButtonCardTemplateWithLoanInfoModel mfButtonCardTemplateWithLoanInfoModel,
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


        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_CARD_WITH_LOAN_INFO_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
