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
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateWithTransactionInfoModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplateWithTransactionInfo extends TemplateView {
    public static final String BUTTON_TEMPLATE_WITH_TRANSACTION_INFO_VIEW = "ButtonCardTemplateWithTransactionInfo";
    private static Context sContext;

    public MfButtonCardTemplateWithTransactionInfo(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(BUTTON_TEMPLATE_WITH_TRANSACTION_INFO_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplateWithTransactionInfo) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_button_with_transaction_info_template_layout, this);
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
        return new MfButtonCardTemplateWithTransactionInfo(context);
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

        private ImageView mIvMoneyInImage;
        private TextView mTvMoneyIn;
        private TextView mTvMoneyInLabel;

        private ImageView mIvMoneyOutImage;
        private TextView mTvMoneyOut;
        private TextView mTvMoneyOutLabel;

        private View mSepratorOne;
        private View mSepratorTwo;

        private TextView mTvBtnOne;
        private TextView mTvBtnTwo;

        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_with_transaction_info_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);

            this.mIvMoneyInImage = (ImageView) itemView.findViewById(R.id.iv_money_in_image);
            this.mTvMoneyIn = (TextView) itemView.findViewById(R.id.tv_money_in_text);
            this.mTvMoneyInLabel = (TextView) itemView.findViewById(R.id.tv_money_in_label);

            this.mIvMoneyOutImage = (ImageView) itemView.findViewById(R.id.iv_money_out_image);
            this.mTvMoneyOut = (TextView) itemView.findViewById(R.id.tv_money_out_text);
            this.mTvMoneyOutLabel = (TextView) itemView.findViewById(R.id.tv_money_out_label);

            this.mSepratorOne = itemView.findViewById(R.id.seprator_one);
            this.mSepratorTwo = itemView.findViewById(R.id.seprator_two);
            this.mTvBtnOne = (TextView) itemView.findViewById(R.id.tv_btn_one);
            this.mTvBtnTwo = (TextView) itemView.findViewById(R.id.tv_btn_two);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateWithTransactionInfoModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfButtonCardTemplateWithTransactionInfoModel mfButtonCardTemplateWithTransactionInfoModel = (MfButtonCardTemplateWithTransactionInfoModel) model;
            applyDefaultCardStyle(mfButtonCardTemplateWithTransactionInfoModel.isIncoming());


            if (mfButtonCardTemplateWithTransactionInfoModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            List<MfListContentData> mfListContentDatas = mfButtonCardTemplateWithTransactionInfoModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();

                switch (i) {
                    case 0:
                        feedHeader(mfComponentDatas);
                        break;
                    case 1:
                        feedMoneyInOutDetails(mfComponentDatas.get(0).getElementDataListOfArray());
                        break;
                    case 2:
                        feedFooter(mfComponentDatas.get(0).getElementDataList(),
                                mfButtonCardTemplateWithTransactionInfoModel);
                        break;
                }

            }
        }

        private void feedMoneyInOutDetails(ArrayList<MfElementData[]> elementDataListOfArray) {
            ImageView[] moneyInImageArray = {mIvMoneyInImage, mIvMoneyOutImage};
            TextView[] moneyInTextArray = {mTvMoneyIn, mTvMoneyOut};
            TextView[] moneyInLabelArray = {mTvMoneyInLabel, mTvMoneyOutLabel};

            if (elementDataListOfArray != null) {
                for (int i = 0; i < elementDataListOfArray.size(); i++) {
                    MfElementData[] mfElementDatasArray = elementDataListOfArray.get(i);

                    for (int j = 0; j < mfElementDatasArray.length; j++) {
                        MfElementData elementData = mfElementDatasArray[j];

                        if (elementData.getId().equals("title")) {
                            String title = elementData.getText();
                            String color = elementData.getStyleData().getTextColor();
                            int colorInt = Color.parseColor(color);
                            moneyInTextArray[i].setText(title);
                            moneyInTextArray[i].setTextColor(colorInt);
                            moneyInTextArray[i].setVisibility(View.VISIBLE);
                        }


                        if (elementData.getId().equals("subtitle")) {
                            String title = elementData.getText();
                            String color = elementData.getStyleData().getTextColor();
                            int colorInt = Color.parseColor(color);
                            moneyInLabelArray[i].setText(title);
                            moneyInLabelArray[i].setTextColor(colorInt);
                            moneyInLabelArray[i].setVisibility(View.VISIBLE);
                        }

                        if (elementData.getId().equals("image")) {
                            int visible = moneyInImageArray[i].getVisibility();
                            if (visible == View.GONE || visible == View.INVISIBLE) {
                                moneyInImageArray[i].setVisibility(VISIBLE);
                            }
                            String image = elementData.getImageName();
                            if (!(TextUtils.isEmpty(image))) {
                                int resourceId = 0;
                                try {
                                    resourceId = getResourceId(image);
                                } catch (Exception e) {
                                    LogManager.e(TAG, e.getMessage());
                                }
                                if (resourceId != 0) {
                                    moneyInImageArray[i].setImageResource(resourceId);
                                }
                            }
                        }

                    }


                }

            }
        }

        private void feedFooter(List<MfElementData> buttonsList,
                                MfButtonCardTemplateWithTransactionInfoModel mfButtonCardTemplateWithTransactionInfoModel) {
            TextView[] btnArray = {mTvBtnOne, mTvBtnTwo};
            View[] sepratorViewArray = {mSepratorOne, mSepratorTwo};

            if (buttonsList != null) {
                for (int i = 0; i < btnArray.length; i++) {
                    if (buttonsList.size() > i) {
                        updateButtonInfo(buttonsList.get(i), mfButtonCardTemplateWithTransactionInfoModel,
                                btnArray[i], sepratorViewArray[i]);
                    } else {
                        btnArray[i].setVisibility(View.GONE);
                        sepratorViewArray[i].setVisibility(View.GONE);
                    }
                }
            }
        }

        private void updateButtonInfo(final MfElementData elementData,
                                      final MfButtonCardTemplateWithTransactionInfoModel mfButtonCardTemplateWithTransactionInfoModel,
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
                styleTemplateId = BUTTON_TEMPLATE_WITH_TRANSACTION_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_TEMPLATE_WITH_TRANSACTION_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
