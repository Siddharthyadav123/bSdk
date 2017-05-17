package com.morfeus.android.mfsdk.ui.widget.bubble;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfMapCardTemplateWithDetailsModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfMapCardTemplateWithDetails extends TemplateView {
    public static final String MAP_CARD_TEMPLATE_WITH_DETAILS_VIEW = "MapCardTemplateWithDetails";
    private static Context sContext;

    public MfMapCardTemplateWithDetails(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(MAP_CARD_TEMPLATE_WITH_DETAILS_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfMapCardTemplateWithDetails) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_map_card_template_with_details_layout, this);
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
        return new MfMapCardTemplateWithDetails(context);
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

        private ImageView mIvPersonImage;
        private TextView mTvPersonName;
        private TextView mTvAmount;

        private TextView mTvPersonAccountNumber;
        private TextView mTvPersonCardNumber;

        private ImageView mIvStartOne;
        private ImageView mIvStartTwo;
        private ImageView mIvStartThree;
        private ImageView mIvStartFour;
        private ImageView mIvStartFive;

        private TextView mTvRaingTitle;

        private MfNetworkImageView mTvMapImage;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_map_card_liner_layout_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);

            this.mIvPersonImage = (ImageView) itemView.findViewById(R.id.iv_person_image);
            this.mTvPersonName = (TextView) itemView.findViewById(R.id.tv_person_name);
            this.mTvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            this.mTvPersonAccountNumber = (TextView) itemView.findViewById(R.id.tv_person_account_num);
            this.mTvPersonCardNumber = (TextView) itemView.findViewById(R.id.tv_person_card_num);

            this.mIvStartOne = (ImageView) itemView.findViewById(R.id.iv_star1);
            this.mIvStartTwo = (ImageView) itemView.findViewById(R.id.iv_star2);
            this.mIvStartThree = (ImageView) itemView.findViewById(R.id.iv_star3);
            this.mIvStartFour = (ImageView) itemView.findViewById(R.id.iv_star4);
            this.mIvStartFive = (ImageView) itemView.findViewById(R.id.iv_star5);
            this.mTvRaingTitle = (TextView) itemView.findViewById(R.id.tv_rating_title);
            this.mTvMapImage = (MfNetworkImageView) itemView.findViewById(R.id.iv_map_image);

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

            if (!(model instanceof MfMapCardTemplateWithDetailsModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfMapCardTemplateWithDetailsModel mfMapCardTemplateWithDetailsModel
                    = (MfMapCardTemplateWithDetailsModel) model;
            applyDefaultCardStyle(mfMapCardTemplateWithDetailsModel.isIncoming());


            if (mfMapCardTemplateWithDetailsModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            List<MfListContentData> mfListContentDatas = mfMapCardTemplateWithDetailsModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                switch (i) {
                    case 0:
                        feedHeader(mfComponentDatas);
                        break;
                    case 1:
                        feedPersonDetails(mfComponentDatas);
                        break;
                    case 2:
                        feedAccountDetails(mfComponentDatas);
                        break;
                    case 3:
                        feedRatingAndStaticMap(mfComponentDatas);
                        break;
                    case 4:
                        feedFooter(mfComponentDatas.get(0).getElementDataList(),
                                mfMapCardTemplateWithDetailsModel);
                        break;
                }

            }
        }

        private void feedRatingAndStaticMap(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvRaingTitle.setText(title);
                    mTvRaingTitle.setTextColor(colorInt);
                    mTvRaingTitle.setVisibility(View.VISIBLE);
                } else {
                    mTvRaingTitle.setVisibility(View.GONE);
                }
            } else {
                mTvRaingTitle.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                int rateOffResId = R.drawable.star_blank;
                int rateIconResID = R.drawable.start_filled;
                double ratingValue = 0;
                if (mfElementData != null && mfElementData.getId().equals("rate")) {
                    ratingValue = mfElementData.getValue();
                    if (mfElementData.getStyleData() != null) {
                        String rateOff = mfElementData.getStyleData().getRateOff();
                        String rateIcon = mfElementData.getStyleData().getRateIcon();
                        if (!(TextUtils.isEmpty(rateOff))) {
                            try {
                                rateOffResId = getResourceId(rateOff);
                            } catch (Exception e) {
                                LogManager.e(TAG, e.getMessage());
                            }
                        }
                        if (!(TextUtils.isEmpty(rateOff))) {
                            try {
                                rateIconResID = getResourceId(rateIcon);
                            } catch (Exception e) {
                                LogManager.e(TAG, e.getMessage());
                            }
                        }
                    }
                }
                feedRatingStarts(ratingValue, rateIconResID, rateOffResId);
            }


            if (mfComponentDatas != null && mfComponentDatas.size() > 2) {
                MfComponentData mfComponentData = mfComponentDatas.get(2);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("map")) {
                    final double latitude = mfElementData.getLatitude();
                    final double longitude = mfElementData.getLongitude();

                    if (mfElementData.getStyleData() != null) {
                        String pinImage = mfElementData.getStyleData().getPinImage();
                    }
                    String staticMapURL = "https://maps.googleapis.com/maps/api/staticmap?center="
                            + latitude + "," + longitude + "&" + "zoom=13&scale=false&size=600x300" +
                            "&maptype=roadmap&format=png&visual_refresh=true&" + "markers=size:" +
                            "mid%7Ccolor:0xff0000%7Clabel:1%7C" + latitude + "," + longitude;

                    String[] imgAddress = {"map", staticMapURL};
                    MfImageLoader imageLoader = MFMediaSdk.getInstance().getImageLoader();
                    mTvMapImage.setImageUrl(imgAddress, imageLoader);

                    mTvMapImage.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openDeviceMap(latitude, longitude);
                        }
                    });

                }
            }

        }

        private void openDeviceMap(double latitude, double longitude) {
            Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mContext.startActivity(mapIntent);
        }

        private Bitmap makeHalfBitmap(int resId) {
            Bitmap originalBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
            Bitmap halfStarBitmap = Bitmap.createBitmap(originalBitmap, 0, 0,
                    originalBitmap.getWidth() - (originalBitmap.getWidth() / 2), originalBitmap.getHeight());
            return halfStarBitmap;
        }


        private void feedRatingStarts(double rating, int rateIcon, int rateOff) {
            if (rateIcon == 0) {
                rateIcon = R.drawable.start_filled;
                rateOff = R.drawable.star_blank;
            }

            mIvStartOne.setBackgroundResource(rateOff);
            mIvStartTwo.setBackgroundResource(rateOff);
            mIvStartThree.setBackgroundResource(rateOff);
            mIvStartFour.setBackgroundResource(rateOff);
            mIvStartFive.setBackgroundResource(rateOff);

            Bitmap halfStarBitmap = makeHalfBitmap(R.drawable.start_filled);
            if (rating == 1.0f) {
                mIvStartOne.setImageResource(rateIcon);
            } else if (rating == 1.5f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageBitmap(halfStarBitmap);
            } else if (rating == 2.0f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
            } else if (rating == 2.5f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageBitmap(halfStarBitmap);
            } else if (rating == 3.0f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageResource(rateIcon);
            } else if (rating == 3.5f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageResource(rateIcon);
                mIvStartFour.setImageBitmap(halfStarBitmap);
            } else if (rating == 4.0f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageResource(rateIcon);
                mIvStartFour.setImageResource(rateIcon);
            } else if (rating == 4.5f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageResource(rateIcon);
                mIvStartFour.setImageResource(rateIcon);
                mIvStartFive.setImageBitmap(halfStarBitmap);
            } else if (rating == 5.0f) {
                mIvStartOne.setImageResource(rateIcon);
                mIvStartTwo.setImageResource(rateIcon);
                mIvStartThree.setImageResource(rateIcon);
                mIvStartFour.setImageResource(rateIcon);
                mIvStartFive.setImageResource(rateIcon);
            }
        }

        private void feedAccountDetails(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvPersonAccountNumber.setText(title);
                    mTvPersonAccountNumber.setTextColor(colorInt);
                    mTvPersonAccountNumber.setVisibility(View.VISIBLE);
                } else {
                    mTvPersonAccountNumber.setVisibility(View.GONE);
                }
            } else {
                mTvPersonAccountNumber.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvPersonCardNumber.setText(title);
                    mTvPersonCardNumber.setTextColor(colorInt);
                    mTvPersonCardNumber.setVisibility(View.VISIBLE);
                } else {
                    mTvPersonCardNumber.setVisibility(View.GONE);
                }
            } else {
                mTvPersonCardNumber.setVisibility(View.GONE);
            }
        }

        private void feedPersonDetails(List<MfComponentData> mfComponentDatas) {
            if (mfComponentDatas != null && mfComponentDatas.size() > 0) {
                MfComponentData mfComponentData = mfComponentDatas.get(0);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("title")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvPersonName.setText(title);
                    mTvPersonName.setTextColor(colorInt);
                    mTvPersonName.setVisibility(View.VISIBLE);
                } else {
                    mTvPersonName.setVisibility(View.GONE);
                }
            } else {
                mTvPersonName.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 1) {
                MfComponentData mfComponentData = mfComponentDatas.get(1);
                final MfElementData mfElementData = mfComponentData.getElementData();
                if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                    String title = mfElementData.getText();
                    String color = mfElementData.getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    mTvAmount.setText(title);
                    mTvAmount.setTextColor(colorInt);
                    mTvAmount.setVisibility(View.VISIBLE);
                } else {
                    mTvAmount.setVisibility(View.GONE);
                }
            } else {
                mTvAmount.setVisibility(View.GONE);
            }

            if (mfComponentDatas != null && mfComponentDatas.size() > 2) {
                MfComponentData mfComponentData = mfComponentDatas.get(2);
                final MfElementData cardElement = mfComponentData.getElementData();
                if (cardElement != null && cardElement.getId().equals("image")) {
                    int visible = mIvPersonImage.getVisibility();
                    if (visible == View.GONE || visible == View.INVISIBLE) {
                        mIvPersonImage.setVisibility(VISIBLE);
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
                            mIvPersonImage.setImageResource(resourceId);
                        } else {
//                            mIvPersonImage.setImageResource(R.drawable.ic_menu_billpay);
                        }
                    } else {
//                        mIvPersonImage.setImageResource(R.drawable.ic_menu_dollar_inactive);
                    }
                }
            }
        }


        private void updateButtonInfo(final MfElementData elementData,
                                      final MfMapCardTemplateWithDetailsModel mfMapCardTemplateWithDetailsModel,
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
                                MfMapCardTemplateWithDetailsModel mfMapCardTemplateWithDetailsModel) {
            TextView[] btnArray = {mBtnOne, mBtnTwo, mBtnThree, mBtnFour, mBtnFive};
            View[] sepratorViewArray = {mSepratorOne, mSepratorTwo, mSepratorThree, mSepratorFour, mSepratorFive};

            if (buttonsList != null) {
                for (int i = 0; i < btnArray.length; i++) {
                    if (buttonsList.size() > i) {
                        updateButtonInfo(buttonsList.get(i), mfMapCardTemplateWithDetailsModel,
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
                styleTemplateId = MAP_CARD_TEMPLATE_WITH_DETAILS_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = MAP_CARD_TEMPLATE_WITH_DETAILS_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
