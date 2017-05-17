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
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementStyleData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfRecieptCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfRecieptCardTemplate extends TemplateView {
    public static final String RECIEPT_CARD_TEMPLATE_VIEW = "RecieptCardTemplate";
    private static Context sContext;

    public MfRecieptCardTemplate(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(RECIEPT_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfRecieptCardTemplate) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_reciept_card_template_layout, this);
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
        return new MfRecieptCardTemplate(context);
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
        private View mBtnOneSepratorView;

        private LinearLayout mOrderLayout;
        private LinearLayout mOrderDetailsLayout;
        private RelativeLayout mRLFooterLayout;
        private TextView mTVFooterTitle;
        private TextView mTVFooterSubTitle;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_recipt_temp_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);
            this.mBtnOneSepratorView = itemView.findViewById(R.id.seprator_one);

            this.mRLFooterLayout = (RelativeLayout) itemView.findViewById(R.id.rl_footer_layout);
            this.mOrderLayout = (LinearLayout) itemView.findViewById(R.id.ll_order_items);
            this.mOrderDetailsLayout = (LinearLayout) itemView.findViewById(R.id.ll_order_details);
            this.mTVFooterTitle = (TextView) itemView.findViewById(R.id.tv_footer_title);
            this.mTVFooterSubTitle = (TextView) itemView.findViewById(R.id.tv_footer_subtitle);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfRecieptCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfRecieptCardTemplateModel mfRecieptCardTemplateModel = (MfRecieptCardTemplateModel) model;
            applyDefaultCardStyle(mfRecieptCardTemplateModel.isIncoming());


            if (mfRecieptCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            this.mOrderLayout.removeAllViews();
            this.mOrderDetailsLayout.removeAllViews();

            List<MfListContentData> mfListContentDatas = mfRecieptCardTemplateModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();

                if (mfListContentData.getElementStyle() != null
                        && mfListContentData.getElementStyle().equalsIgnoreCase("rowHeader")) {

                    feedHeader(mfComponentDatas, mfListContentData.getStyle());

                } else if (mfListContentData.getElementStyle() != null
                        && mfListContentData.getElementStyle().equalsIgnoreCase("recieptDetails")) {

                    feedReceiptDetailsOrdersList(mfComponentDatas, mfListContentData.getStyle());

                } else if (mfListContentData.getElementStyle() != null
                        && mfListContentData.getElementStyle().equalsIgnoreCase("footer")) {

                    feedFooter(mfComponentDatas, mfListContentData.getStyle());

                } else {
                    //items
                    feedReceiptItemsOrdersList(mfComponentDatas, mfListContentData.getStyle());
                }

            }
        }

        private void feedFooter(List<MfComponentData> mfComponentDatas, MfElementStyleData style) {
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

            if (style != null) {
                try {
                    String color = style.getBackgroundColor();
                    int colorInt = Color.parseColor(color);
                    mRLFooterLayout.setBackgroundColor(colorInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private void feedHeader(List<MfComponentData> mfComponentDatas, MfElementStyleData style) {
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

            if (style != null) {
                try {
                    String color = style.getBackgroundColor();
                    int colorInt = Color.parseColor(color);
                    mTVMainHeading.setBackgroundColor(colorInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void feedReceiptDetailsOrdersList(List<MfComponentData> childListComponents, MfElementStyleData
                style) {
            checkNotNull(childListComponents);

            if (childListComponents != null) {
                LinearLayout listCardItem = (LinearLayout)
                        LayoutInflater.from(itemView.getContext())
                                .inflate(R.layout.receipt_detail_item_layout, null);

                TextView labelTextView = (TextView) listCardItem.findViewById(R.id.tv_label);
                TextView detailTextView = (TextView) listCardItem.findViewById(R.id.tv_detail);


                LinearLayout listCardItem2 = (LinearLayout)
                        LayoutInflater.from(itemView.getContext())
                                .inflate(R.layout.receipt_detail_item_layout, null);

                TextView labelTextView2 = (TextView) listCardItem2.findViewById(R.id.tv_label);
                TextView detailTextView2 = (TextView) listCardItem2.findViewById(R.id.tv_detail);

                for (int i = 0; i < childListComponents.size(); i++) {
                    MfComponentData mfComponentData = childListComponents.get(i);
                    final MfElementData mfElementData = mfComponentData.getElementData();

                    if (mfElementData != null && mfElementData.getId().equals("label1")) {
                        String title = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        labelTextView.setText(title);
                        labelTextView.setTextColor(colorInt);
                        labelTextView.setVisibility(View.VISIBLE);
                        labelTextView.setTag(mfElementData.getId());
                    } else {
                        if (labelTextView.getTag() == null) {
                            labelTextView.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("details1")) {
                        String title = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        detailTextView.setText(title);
                        detailTextView.setTextColor(colorInt);
                        detailTextView.setVisibility(View.VISIBLE);
                        detailTextView.setTag(mfElementData.getId());
                    } else {
                        if (detailTextView.getTag() == null) {
                            detailTextView.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("label2")) {
                        String title = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        labelTextView2.setText(title);
                        labelTextView2.setTextColor(colorInt);
                        labelTextView2.setVisibility(View.VISIBLE);
                        labelTextView2.setTag(mfElementData.getId());
                    } else {
                        if (labelTextView2.getTag() == null) {
                            labelTextView2.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("details2")) {
                        String title = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        detailTextView2.setText(title);
                        detailTextView2.setTextColor(colorInt);
                        detailTextView2.setVisibility(View.VISIBLE);
                        detailTextView2.setTag(mfElementData.getId());
                    } else {
                        if (detailTextView2.getTag() == null) {
                            detailTextView2.setVisibility(View.GONE);
                        }
                    }

                }

                if (style != null) {
                    try {
                        String color = style.getBackgroundColor();
                        int colorInt = Color.parseColor(color);
                        listCardItem.setBackgroundColor(colorInt);
                        listCardItem2.setBackgroundColor(colorInt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                listCardItem.setLayoutParams(layoutParams);
                listCardItem2.setLayoutParams(layoutParams);
                this.mOrderLayout.addView(listCardItem);
                this.mOrderLayout.addView(listCardItem2);
            }

        }

        public void feedReceiptItemsOrdersList(@NonNull List<MfComponentData> childListComponents,
                                               MfElementStyleData style) {
            checkNotNull(childListComponents);

            if (childListComponents != null) {
                RelativeLayout listCardItem = (RelativeLayout)
                        LayoutInflater.from(itemView.getContext())
                                .inflate(R.layout.receipt_order_item_layout, null);

                ImageView itemImageView = (ImageView) listCardItem.findViewById(R.id.iv_order_image);
                TextView orderTitleTextView = (TextView) listCardItem.findViewById(R.id.tv_order_title);
                TextView orderDetailsTextView = (TextView) listCardItem.findViewById(R.id.tv_order_details);
                TextView quantitiyTextView = (TextView) listCardItem.findViewById(R.id.tv_order_quantity);

                for (int i = 0; i < childListComponents.size(); i++) {
                    MfComponentData mfComponentData = childListComponents.get(i);
                    final MfElementData mfElementData = mfComponentData.getElementData();

                    if (mfElementData != null && mfElementData.getId().equals("title")) {
                        String title = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        orderTitleTextView.setText(title);
                        orderTitleTextView.setTextColor(colorInt);
                        orderTitleTextView.setVisibility(View.VISIBLE);
                        orderTitleTextView.setTag(mfElementData.getId());
                    } else {
                        if (orderTitleTextView.getTag() == null) {
                            orderTitleTextView.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                        String subTitle = mfElementData.getText();
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        orderDetailsTextView.setText(subTitle);
                        orderDetailsTextView.setTextColor(colorInt);
                        orderDetailsTextView.setVisibility(View.VISIBLE);
                        orderDetailsTextView.setTag(mfElementData.getId());
                    } else {
                        if (orderDetailsTextView.getTag() == null) {
                            orderDetailsTextView.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("image")) {
                        int visible = itemImageView.getVisibility();
                        if (visible == View.GONE || visible == View.INVISIBLE) {
                            itemImageView.setVisibility(VISIBLE);
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
                                itemImageView.setImageResource(resourceId);
                            } else {
                                itemImageView.setImageResource(R.drawable.cartoon);
                            }
                        } else {
                            itemImageView.setImageResource(R.drawable.cartoon);
                        }
                    }
                }

                if (style != null) {
                    try {
                        String color = style.getBackgroundColor();
                        int colorInt = Color.parseColor(color);
                        listCardItem.setBackgroundColor(colorInt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                listCardItem.setLayoutParams(layoutParams);
                this.mOrderLayout.addView(listCardItem);
            }

        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = RECIEPT_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = RECIEPT_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
