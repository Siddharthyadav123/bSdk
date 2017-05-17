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
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementStyleData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfListContentData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfListCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MfListCardTemplateView extends TemplateView {
    public static final String LIST_CARD_TEMPLATE = "ListCardTemplate";

    private static Context sContext;

    public MfListCardTemplateView(Context context) {
        super(context);
        this.sContext = context;
    }

    private static int getResourceId(String imageName) {
        Resources resources = sContext.getResources();
        return resources.getIdentifier(imageName, "drawable",
                sContext.getPackageName());
    }


    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(LIST_CARD_TEMPLATE, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfListCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_with_list_layout, this);
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
        return new MfListCardTemplateView(context);
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
        private View mItemView;
        private ImageView mIVMainImage;
        private TextView mTVChatHeaderMain;
        private TextView mTVChatHeaderSub;
        private TextView mTVReadMoreBtn;
        private LinearLayout mLLListContainer;
        private RelativeLayout mRLHeader;
        private SelectableRoundedImageView mSenderImage;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mItemView = itemView;
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_with_list_layout_rly_container
            );
            this.mIVMainImage = (ImageView) itemView.findViewById(R.id.ivMainImage);
            this.mTVChatHeaderMain = (TextView) itemView.findViewById(R.id.tvChatHeaderMain);
            this.mTVChatHeaderSub = (TextView) itemView.findViewById(R.id.tvChatHeaderSub);
            this.mTVReadMoreBtn = (TextView) itemView.findViewById(R.id.tvReadMoreBtn);
            this.mLLListContainer = (LinearLayout) itemView.findViewById(R.id.LlListContainer);
            this.mRLHeader = (RelativeLayout) itemView.findViewById(R.id.rlHeader);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rlBodyLayout);
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfListCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfListCardTemplateModel listCardTemplateModel = (MfListCardTemplateModel) model;
            applyDefaultCardStyle(listCardTemplateModel.isIncoming());


            if (listCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }


            this.mLLListContainer.removeAllViews();

            List<MfListContentData> mfListContentDatas = listCardTemplateModel.getMfListContentDatas();

            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);

                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                if (mfListContentData.getElementStyle() == null
                        || mfListContentData.getElementStyle().equalsIgnoreCase("rowLarge")) {

                    feedChildListItems(mfComponentDatas, mfListContentData.getStyle(), listCardTemplateModel);

                } else if (mfListContentData.getElementStyle() != null
                        && mfListContentData.getElementStyle().equalsIgnoreCase("rowButton")) {
                    if (mfComponentDatas.size() > 0) {
                        showMoreButton(mfComponentDatas.get(0), listCardTemplateModel);
                    }
                }

            }

            //TODO hiding header as we don't have anything to shown
            this.mRLHeader.setVisibility(View.GONE);


        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = LIST_CARD_TEMPLATE + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = LIST_CARD_TEMPLATE + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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

                    if (maxWidth > 0)
                        mIVMainImage.setMaxWidth(convertDpToPixel(maxWidth, mContext));

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
                                mTVChatHeaderMain.setTextColor(colorId);
                            }
                        }

                        if (subTitleStyle != null) {
                            String textColor = subTitleStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                mTVChatHeaderSub.setTextColor(colorId);
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
                                mTVReadMoreBtn.setTextColor(colorId);
                            }
                        }
                    }
                }
            } catch (StyleNotFoundException e) {
                e.printStackTrace();
            }

        }

        private void showMoreButton(MfComponentData moreButton, final MfListCardTemplateModel listCardTemplateModel) {
            TextView moreBtnTextView = (TextView)
                    LayoutInflater.from(itemView.getContext())
                            .inflate(R.layout.chat_item_more_button, null);

            final MfElementData mfElementData = moreButton.getElementData();

            if (mfElementData != null) {
                try {
                    String subTitle = mfElementData.getText();
                    MfElementStyleData styleData = mfElementData.getStyleData();
                    if (styleData != null) {
                        String color = styleData.getTextColor();
                        int colorInt = Color.parseColor(color);
                        moreBtnTextView.setTextColor(colorInt);
                    }
                    moreBtnTextView.setText(subTitle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            moreBtnTextView.setLayoutParams(layoutParams);


            moreBtnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String payload = mfElementData.getPayload();
                    String action = mfElementData.getAction();
                    String image = mfElementData.getImageName();
                    EventBus.getDefault().post(new PostbackEvent(payload, action, image));
                }
            });

            this.mLLListContainer.addView(moreBtnTextView);

        }

        public void feedChildListItems(@NonNull List<MfComponentData> childListComponents, MfElementStyleData style, final MfListCardTemplateModel listCardTemplateModel) {
            checkNotNull(childListComponents);

            if (childListComponents != null) {
                RelativeLayout listCardItem = (RelativeLayout)
                        LayoutInflater.from(itemView.getContext())
                                .inflate(R.layout.chat_list_item_layout, null);

                TextView tvHeaderText = (TextView) listCardItem.findViewById(R.id.tvHeaderText);
                ImageView ivChatImage = (ImageView) listCardItem.findViewById(R.id.ivChatImage);
                TextView tvSubHeaderText = (TextView) listCardItem.findViewById(R.id.tvSubHeaderText);
                TextView btnReadMore = (TextView) listCardItem.findViewById(R.id.btnReadMore);

                for (int i = 0; i < childListComponents.size(); i++) {
                    MfComponentData mfComponentData = childListComponents.get(i);
                    final MfElementData mfElementData = mfComponentData.getElementData();

                    if (mfElementData != null && mfElementData.getId().equals("title")) {
                        String title = mfElementData.getText();
                        MfElementStyleData styleData = mfElementData.getStyleData();
                        if (styleData != null) {
                            String color = styleData.getTextColor();
                            int colorInt = Color.parseColor(color);
                            tvHeaderText.setTextColor(colorInt);
                        }
                        tvHeaderText.setText(title);
                        tvHeaderText.setVisibility(View.VISIBLE);
                        tvHeaderText.setTag(mfElementData.getId());
                    } else {
                        if (tvHeaderText.getTag() == null) {
                            tvHeaderText.setVisibility(View.GONE);
                        }
                    }


                    if (mfElementData != null && mfElementData.getId().equals("subtitle")) {
                        String subTitle = mfElementData.getText();
                        MfElementStyleData styleData = mfElementData.getStyleData();
                        if (styleData != null) {
                            String color = styleData.getTextColor();
                            int colorInt = Color.parseColor(color);
                            tvSubHeaderText.setTextColor(colorInt);
                        }
                        tvSubHeaderText.setText(subTitle);
                        tvSubHeaderText.setVisibility(View.VISIBLE);
                        tvSubHeaderText.setTag(mfElementData.getId());
                    } else {
                        if (tvSubHeaderText.getTag() == null) {
                            tvSubHeaderText.setVisibility(View.GONE);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("description")) {
                        //TODO need to description.. need to more info where to put this
                    }

                    if (mfElementData != null && mfElementData.getId().equals("image")) {
//                        ivChatImage.setImageResource(R.drawable.cartoon);
                        int visible = ivChatImage.getVisibility();
                        if (visible == View.GONE || visible == View.INVISIBLE) {
                            ivChatImage.setVisibility(VISIBLE);
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
                                ivChatImage.setImageResource(resourceId);
                            } else {
                                ivChatImage.setImageResource(R.drawable.cartoon);
                            }
                        } else {
                            ivChatImage.setImageResource(R.drawable.cartoon);
                        }
                    }

                    if (mfElementData != null && mfElementData.getId().equals("buttons")) {
                        try {
                            String subTitle = mfElementData.getText();
                            MfElementStyleData styleData = mfElementData.getStyleData();
                            if (styleData != null) {
                                String color = styleData.getTextColor();
                                int colorInt = Color.parseColor(color);
                                btnReadMore.setTextColor(colorInt);
                            }
                            btnReadMore.setText(subTitle);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        btnReadMore.setVisibility(View.VISIBLE);
                        btnReadMore.setTag(mfElementData.getId());

                        btnReadMore.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String payload = mfElementData.getPayload();
                                String action = mfElementData.getAction();
                                String imageName = mfElementData.getImageName();
                                EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                            }
                        });

                    } else {
                        if (btnReadMore.getTag() == null) {
                            btnReadMore.setVisibility(View.GONE);
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
                this.mLLListContainer.addView(listCardItem);
            }

        }

    }
}
