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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfInfoCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MfInfoCardTemplateView extends TemplateView {

    public static final String INFO_CARD_TEMPLATE_VIEW = "InfoCardTemplate";
    private static Context sContext;

    public MfInfoCardTemplateView(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(INFO_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfInfoCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_with_image_and_caption_layout, this);
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
        return new MfInfoCardTemplateView(context);
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

        private RelativeLayout mRlImageContainerLaout;
        private MfNetworkImageView mIVHeaderImage;
        private MfMediaStatusView mfMediaStatusView;

        private TextView mTVMainHeading;
        private TextView mTVSubHeadingOne;

        private LinearLayout mLlBtnContainer;
        private Button mButtonOne;
        private Button mButtonTwo;
        private Button mButtonThree;
        private Button mButtonFour;
        private Button mButtonFive;

        private SelectableRoundedImageView mSenderImage;

        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_with_image_buttons_and_caption_layout_rly_container
            );
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_Layout);
            this.mIVHeaderImage = (MfNetworkImageView) itemView.findViewById(R.id.iv_header_image);
            this.mIVHeaderImage.setBotId("5w47394784104");
            this.mIVHeaderImage.setDefaultImageResId(R.drawable.cartoon);
            this.mIVHeaderImage.setErrorImageResId(android.R.drawable.ic_delete);
            this.mIVHeaderImage.setVisibility(GONE);
            this.mfMediaStatusView = (MfMediaStatusView) itemView.findViewById(R.id.image_downloader_view);
            this.mRlImageContainerLaout = (RelativeLayout) itemView.findViewById(R.id.rl_image_container);
            mfMediaStatusView.setVisibility(GONE);
            mRlImageContainerLaout.setVisibility(View.GONE);

            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);
            this.mTVSubHeadingOne = (TextView) itemView.findViewById(R.id.tv_sub_heading_one);

            this.mLlBtnContainer = (LinearLayout) itemView.findViewById(R.id.ll_btn_container);
            this.mButtonOne = (Button) itemView.findViewById(R.id.btn_one);
            this.mButtonTwo = (Button) itemView.findViewById(R.id.btn_two);
            this.mButtonThree = (Button) itemView.findViewById(R.id.btn_three);
            this.mButtonFour = (Button) itemView.findViewById(R.id.btn_four);
            this.mButtonFive = (Button) itemView.findViewById(R.id.btn_five);

            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfInfoCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }


            final MfInfoCardTemplateModel infoCardWithButtonsTemplateModel =
                    (MfInfoCardTemplateModel) model;

            applyDefaultCardBgAndBorderColor();
            applyDefaultCardStyle(infoCardWithButtonsTemplateModel.isIncoming());


            if (infoCardWithButtonsTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            mLLContainer.requestLayout();

            List<MfComponentData> mfComponentDatas = infoCardWithButtonsTemplateModel.getComponentDatas();

            boolean isImageItemFound = false;
            boolean isButtonsFound = false;

            if (!model.isShowBotIcon()) {
                this.mSenderImage.setVisibility(GONE);
            } else {
                this.mSenderImage.setVisibility(VISIBLE);
            }

            mTVMainHeading.setTag(null);
            mTVSubHeadingOne.setTag(null);

            for (MfComponentData mfComponentData : mfComponentDatas) {
                MfElementData mfElementData = mfComponentData.getElementData();
                final List<MfElementData> buttonsList = mfComponentData.getElementDataList();

                if (mfElementData != null && "title".equalsIgnoreCase(mfElementData.getId())) {
                    updateTextInfo(mfElementData, mTVMainHeading);
                } else {
                    if (mTVMainHeading.getTag() == null) {
                        mTVMainHeading.setText("");
                        mTVMainHeading.setVisibility(View.GONE);
                    }
                }

                if (mfElementData != null && "subtitle".equalsIgnoreCase(mfElementData.getId())) {
                    updateTextInfo(mfElementData, mTVSubHeadingOne);
                } else {
                    if (mTVSubHeadingOne.getTag() == null) {
                        mTVSubHeadingOne.setText("");
                        mTVSubHeadingOne.setVisibility(View.GONE);
                    }
                }

                if (mfElementData != null && ("cardimage".equalsIgnoreCase(mfElementData.getId())
                        || "image".equalsIgnoreCase(mfElementData.getId()))) {
                    isImageItemFound = true;
                    String imgName = mfElementData.getImageName();
                    String imgUrl = mfElementData.getImageUrl();
                    String[] imgAddress = {imgName, imgUrl};
                    int visible = this.mIVHeaderImage.getVisibility();
                    if (visible == View.GONE || visible == View.INVISIBLE) {
                        mIVHeaderImage.setVisibility(VISIBLE);
                        mfMediaStatusView.setVisibility(View.VISIBLE);
                        mRlImageContainerLaout.setVisibility(View.VISIBLE);
                    }
                    MfImageLoader imageLoader = MFMediaSdk.getInstance().getImageLoader();
                    mfMediaStatusView.setMfMediaStatusCallback(mIVHeaderImage);
                    mIVHeaderImage.setImageUrl(imgAddress, imageLoader, mfMediaStatusView);
                }

                isButtonsFound = showButtons(buttonsList, infoCardWithButtonsTemplateModel);

            }
            if (!isImageItemFound) {
                mIVHeaderImage.setVisibility(GONE);
                mfMediaStatusView.setVisibility(View.GONE);
                mRlImageContainerLaout.setVisibility(View.GONE);
            }

            if (!isButtonsFound) {
                mButtonOne.setVisibility(View.GONE);
                mButtonTwo.setVisibility(View.GONE);
                mButtonThree.setVisibility(View.GONE);
                mButtonFour.setVisibility(View.GONE);
                mButtonFive.setVisibility(View.GONE);
            }

            showMaxButtonSpace(infoCardWithButtonsTemplateModel.getMaxButtonToShow());
        }

        private boolean showButtons(List<MfElementData> buttonsList,
                                    MfInfoCardTemplateModel infoCardWithButtonsTemplateModel) {
            boolean isButtonsFound = false;
            if (buttonsList != null) {
                Button[] buttons = {mButtonOne, mButtonTwo, mButtonThree,
                        mButtonFour, mButtonFive};
                for (int i = 0; i < buttons.length; i++) {
                    if (buttonsList.size() > i) {
                        isButtonsFound = true;
                        updateButtonInfo(buttonsList.get(i), infoCardWithButtonsTemplateModel
                                , buttons[i]);
                    } else {
                        buttons[i].setVisibility(View.GONE);
                    }
                }
            }

            return isButtonsFound;
        }

        private void showMaxButtonSpace(int maxButtonToShow) {
            if (maxButtonToShow != -1) {
                int containerHeight = mButtonOne.getLayoutParams().height * maxButtonToShow;
                mLlBtnContainer.getLayoutParams().height = containerHeight;
            } else {
                mLlBtnContainer.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            }
        }

        private void updateTextInfo(MfElementData mfElementData,
                                    TextView txtView) {
            String title = mfElementData.getText();
            txtView.setVisibility(View.VISIBLE);
            txtView.setText(title);
            txtView.setTag(title);

            if (mfElementData.getStyleData() != null
                    && mfElementData.getStyleData().getTextColor() != null) {
                String color = mfElementData.getStyleData().getTextColor();
                int colorInt = Color.parseColor(color);
                txtView.setTextColor(colorInt);
            }
        }

        private void updateButtonInfo(final MfElementData button,
                                      final MfInfoCardTemplateModel infoCardWithButtonsTemplateModel,
                                      TextView btnTextView) {
            String subTitle = button.getText();
            btnTextView.setVisibility(View.VISIBLE);
            btnTextView.setText(subTitle);


            if (button.getStyleData() != null
                    && button.getStyleData().getTextColor() != null) {
                String color = button.getStyleData().getTextColor();
                int colorInt = Color.parseColor(color);
                btnTextView.setTextColor(colorInt);
            }

            btnTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String payload = button.getPayload();
                    String action = button.getAction();
                    String imageName = button.getImageName();

                    EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                }
            });
        }

        private void applyDefaultCardBgAndBorderColor() {
            Drawable backgroundDrwable = mRLBodyLayout.getBackground();
            if (backgroundDrwable != null) {
                if (backgroundDrwable instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) backgroundDrwable;
                    gradientDrawable.setColor(mContext.getResources().getColor(R.color.white));
                    mRLBodyLayout.setBackgroundDrawable(backgroundDrwable);
                }
            }
        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = INFO_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = INFO_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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

                    if (maxWidth > 0)
                        mIVHeaderImage.setMaxWidth(convertDpToPixel(maxWidth, mContext));

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

                        if (subTitleStyle != null) {
                            String textColor = subTitleStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                mTVSubHeadingOne.setTextColor(colorId);
                            }
                        }

                        if (descStyle != null) {
                            String textColor = descStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                //TODO need to set description color
                            }
                        }


                        if (buttonStyle != null) {
                            String textColor = buttonStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                mButtonOne.setTextColor(colorId);
                                mButtonTwo.setTextColor(colorId);
                            }
                        }
                    }
                }
            } catch (StyleNotFoundException e) {
                e.printStackTrace();
            }

        }

        public TextView getmButtonOne() {
            return mButtonOne;
        }

        public TextView getmButtonTwo() {
            return mButtonTwo;
        }

        public TextView getmTVSubHeadingOne() {
            return mTVSubHeadingOne;
        }

        public TextView getmTVMainHeading() {
            return mTVMainHeading;
        }
    }
}
