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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateWithCardInfoModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplateWithCardInfo extends TemplateView {
    public static final String BUTTON_TEMPLATE_WITH_CARD_INFO_VIEW = "ButtonCardTemplateWithCardInfo";
    private static Context sContext;

    public MfButtonCardTemplateWithCardInfo(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(BUTTON_TEMPLATE_WITH_CARD_INFO_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplateWithCardInfo) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_button_with_card_info_template_layout, this);
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
        return new MfButtonCardTemplateWithCardInfo(context);
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

        private GridView mGVCards;
        private TextView mTVBlockCards;
        private RelativeLayout mRLGridContainer;
        private View mSepratorOne;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_with_card_info_layout_lin_container
            );
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);

            this.mGVCards = (GridView) itemView.findViewById(R.id.gv_cards);
            this.mTVBlockCards = (TextView) itemView.findViewById(R.id.tv_block_card_button);
            this.mRLGridContainer = (RelativeLayout) itemView.findViewById(R.id.rl_grid_container);
            this.mSepratorOne = itemView.findViewById(R.id.seprator_one);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateWithCardInfoModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfButtonCardTemplateWithCardInfoModel mfButtonCardTemplateWithCardInfoModel = (MfButtonCardTemplateWithCardInfoModel) model;
            applyDefaultCardStyle(mfButtonCardTemplateWithCardInfoModel.isIncoming());


            if (mfButtonCardTemplateWithCardInfoModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            List<MfListContentData> mfListContentDatas = mfButtonCardTemplateWithCardInfoModel.getMfListContentDatas();
            for (int i = 0; i < mfListContentDatas.size(); i++) {
                MfListContentData mfListContentData = mfListContentDatas.get(i);
                List<MfComponentData> mfComponentDatas = mfListContentData.getElementsList();
                switch (i) {
                    case 0:
                        feedHeader(mfComponentDatas, mfListContentData.getStyle());
                        break;
                    case 1:
                        feedCards(mfComponentDatas.get(0).getElementDataListOfArray(), mfListContentData.getStyle());
                        break;
                    case 2:
                        feedFooter(mfComponentDatas.get(0).getElementDataList(), mfListContentData.getStyle(),
                                mfButtonCardTemplateWithCardInfoModel);
                        break;
                }

            }
        }

        private void decideGridHeight(ArrayList<MfElementData[]> elementDataListOfArray) {
            if (elementDataListOfArray != null) {
                mGVCards.setVisibility(View.VISIBLE);
                if (elementDataListOfArray.size() == 0) {
                    mGVCards.setVisibility(View.GONE);
                } else if (elementDataListOfArray.size() > 3) {
                    LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, convertDpToPixel(190, mContext));
                    int marginLeftRight = convertDpToPixel(10, mContext);
                    layoutParams.setMargins(marginLeftRight, 0, marginLeftRight, 0);
                    mGVCards.setLayoutParams(layoutParams);
                } else {
                    LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, convertDpToPixel(95, mContext));
                    int marginLeftRight = convertDpToPixel(10, mContext);
                    layoutParams.setMargins(marginLeftRight, 0, marginLeftRight, 0);
                    mGVCards.setLayoutParams(layoutParams);
                }
            }
        }


        private void feedCards(ArrayList<MfElementData[]> elementDataListOfArray, MfElementStyleData style) {
            decideGridHeight(elementDataListOfArray);
            CardGridAdapter cardGridAdapter = new CardGridAdapter(mContext, elementDataListOfArray);
            mGVCards.setAdapter(cardGridAdapter);

            if (style != null) {
                try {
                    String color = style.getBackgroundColor();
                    int colorInt = Color.parseColor(color);
                    mRLGridContainer.setBackgroundColor(colorInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public class CardGridAdapter extends BaseAdapter {
            private Context context;
            private ArrayList<MfElementData[]> elementDataListOfArray;

            public CardGridAdapter(Context context, ArrayList<MfElementData[]> elementDataListOfArray) {
                this.context = context;
                this.elementDataListOfArray = elementDataListOfArray;
            }

            @Override
            public int getCount() {
                if (elementDataListOfArray != null)
                    return elementDataListOfArray.size();
                return 0;
            }

            @Override
            public Object getItem(int i) {
                return elementDataListOfArray.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int pos, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.button_with_info_grid_item, null);
                }
                ImageView cardImageView = (ImageView) view.findViewById(R.id.iv_card_image);
                TextView cardNumberTextView = (TextView) view.findViewById(R.id.tv_card_number);

                MfElementData[] mfElementDataArray = elementDataListOfArray.get(pos);

                if (mfElementDataArray != null) {
                    for (int i = 0; i < mfElementDataArray.length; i++) {
                        MfElementData cardElement = mfElementDataArray[i];

                        if (cardElement != null && cardElement.getId().equals("title")) {
                            String title = cardElement.getText();
                            String color = cardElement.getStyleData().getTextColor();
                            int colorInt = Color.parseColor(color);
                            cardNumberTextView.setText(title);
                            cardNumberTextView.setTextColor(colorInt);
                            cardNumberTextView.setVisibility(View.VISIBLE);
                        }

                        if (cardElement != null && cardElement.getId().equals("image")) {
                            int visible = cardImageView.getVisibility();
                            if (visible == View.GONE || visible == View.INVISIBLE) {
                                cardImageView.setVisibility(VISIBLE);
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
                                    cardImageView.setImageResource(resourceId);
                                } else {
                                    cardImageView.setImageResource(R.drawable.visa_card);
                                }
                            } else {
                                cardImageView.setImageResource(R.drawable.visa_card);
                            }
                        }
                    }
                }
                return view;
            }
        }

        private void feedFooter(final List<MfElementData> buttonsList, MfElementStyleData style,
                                final MfButtonCardTemplateWithCardInfoModel mfButtonCardTemplateWithCardInfoModel) {

            if (buttonsList != null && buttonsList.size() > 0) {
                String subTitle = buttonsList.get(0).getText();
                this.mTVBlockCards.setVisibility(View.VISIBLE);
                this.mSepratorOne.setVisibility(View.VISIBLE);
                this.mTVBlockCards.setText(subTitle);

                if (buttonsList.get(0).getStyleData() != null
                        && buttonsList.get(0).getStyleData().getTextColor() != null) {
                    String color = buttonsList.get(0).getStyleData().getTextColor();
                    int colorInt = Color.parseColor(color);
                    this.mTVBlockCards.setTextColor(colorInt);
                }

                mTVBlockCards.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MfElementData elementData = buttonsList.get(0);
                        String payload = elementData.getPayload();
                        String action = elementData.getAction();
                        String imageName = elementData.getImageName();
                        EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                    }
                });

            } else {
                this.mTVBlockCards.setVisibility(View.GONE);
                this.mSepratorOne.setVisibility(View.GONE);
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


        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = BUTTON_TEMPLATE_WITH_CARD_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_TEMPLATE_WITH_CARD_INFO_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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

