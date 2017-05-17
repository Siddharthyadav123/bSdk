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
import com.morfeus.android.mfsdk.ui.action.event.PostbackEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfComponentData;
import com.morfeus.android.mfsdk.ui.screen.parser.entity.MfElementData;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfButtonCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfButtonCardTemplate extends TemplateView {
    public static final String BUTTON_CARD_TEMPLATE_VIEW = "ButtonCardTemplate";
    private static Context sContext;

    public MfButtonCardTemplate(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(BUTTON_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfButtonCardTemplate) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_btn_template_layout, this);
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
        return new MfButtonCardTemplate(context);
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
        private TextView mTVSubHeading;
        private TextView mTVMainHeading;
        private Button mButtonOne;
        private Button mButtonTwo;

        private SelectableRoundedImageView mSenderImage;


        ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mLLContainer = (LinearLayout) itemView.findViewById(
                    R.id.chat_item_btn_temp_layout_lin_container
            );
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            this.mTVMainHeading = (TextView) itemView.findViewById(R.id.tv_main_heading);
            this.mTVSubHeading = (TextView) itemView.findViewById(R.id.tv_sub_heading);
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);

            this.mButtonOne = (Button) itemView.findViewById(R.id.btn_one);
            this.mButtonTwo = (Button) itemView.findViewById(R.id.btn_two);
        }


        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfButtonCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            final MfButtonCardTemplateModel mfButtonCardTemplateModel =
                    (MfButtonCardTemplateModel) model;

            applyDefaultCardBgAndBorderColor();
            applyDefaultCardStyle(mfButtonCardTemplateModel.isIncoming());

            if (mfButtonCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            List<MfComponentData> mfComponentDatas = mfButtonCardTemplateModel.getComponentDatas();

            mTVMainHeading.setTag(null);
            mTVSubHeading.setTag(null);

            boolean isTitleFound = false;
            boolean isSubTitleFound = false;
            for (MfComponentData mfComponentData : mfComponentDatas) {
                MfElementData mfElementData = mfComponentData.getElementData();
                final List<MfElementData> buttonsList = mfComponentData.getElementDataList();

                if (mfElementData != null && "title".equalsIgnoreCase(mfElementData.getId())) {
                    isTitleFound = true;
                    String title = mfElementData.getText();
                    mTVMainHeading.setVisibility(View.VISIBLE);
                    mTVMainHeading.setText(title);
                    mTVMainHeading.setTag(title);

                    if (mfElementData.getStyleData() != null && mfElementData.getStyleData().getTextColor() != null) {
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        mTVMainHeading.setTextColor(colorInt);
                    }
                } else {
                    if (mTVMainHeading.getTag() == null) {
                        mTVMainHeading.setText("");
                        mTVMainHeading.setVisibility(View.GONE);
                    }
                }

                if (mfElementData != null && "subtitle".equalsIgnoreCase(mfElementData.getId())) {
                    isSubTitleFound = true;
                    String subTitle = mfElementData.getText();
                    mTVSubHeading.setVisibility(View.VISIBLE);
                    mTVSubHeading.setText(subTitle);
                    mTVSubHeading.setTag(subTitle);

                    if (mfElementData.getStyleData() != null && mfElementData.getStyleData().getTextColor() != null) {
                        String color = mfElementData.getStyleData().getTextColor();
                        int colorInt = Color.parseColor(color);
                        mTVSubHeading.setTextColor(colorInt);
                    }
                } else {
                    if (mTVSubHeading.getTag() == null) {
                        mTVSubHeading.setText("");
                        mTVSubHeading.setVisibility(View.GONE);
                    }
                }

                TextView[] btnArray = {mButtonOne, mButtonTwo};

                if (buttonsList != null) {
                    for (int i = 0; i < btnArray.length; i++) {
                        if (buttonsList.size() > i) {
                            String subTitle = buttonsList.get(i).getText();
                            btnArray[i].setVisibility(View.VISIBLE);
                            btnArray[i].setText(subTitle);

                            if (buttonsList.get(i).getStyleData() != null
                                    && buttonsList.get(i).getStyleData().getTextColor() != null) {
                                String color = buttonsList.get(i).getStyleData().getTextColor();
                                int colorInt = Color.parseColor(color);
                                btnArray[i].setTextColor(colorInt);
                            }

                            if (buttonsList.get(i).getStyleData() != null
                                    && buttonsList.get(i).getStyleData().getBackgroundColor() != null) {
                                String bgColor = buttonsList.get(i).getStyleData().getBackgroundColor();
                                int colorInt = Color.parseColor(bgColor);
                                btnArray[i].setBackgroundColor(colorInt);
                            }


                            final int finalI = i;
                            btnArray[i].setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    MfElementData elementData = buttonsList.get(finalI);
                                    String payload = elementData.getPayload();
                                    String action = elementData.getAction();
                                    String imageName = elementData.getImageName();

                                    EventBus.getDefault().post(new PostbackEvent(payload, action, imageName));
                                }
                            });
                        } else {
                            btnArray[i].setVisibility(View.GONE);
                        }
                    }
                } else {
                    for (int i = 0; i < btnArray.length; i++) {
                        btnArray[i].setVisibility(View.GONE);
                    }
                }
            }

            if (!isTitleFound) {
                mTVMainHeading.setVisibility(View.GONE);
            }
            if (!isSubTitleFound) {
                mTVSubHeading.setVisibility(View.GONE);
            }

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
                styleTemplateId = BUTTON_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = BUTTON_CARD_TEMPLATE_VIEW + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
                        Style buttonStyle = null;

                        for (int i = 0; i < componentStyle.size(); i++) {
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_TITLE)) {
                                titleStyle = componentStyle.get(i);
                            }
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_SUB_TITLE)) {
                                subTitleStyle = componentStyle.get(i);
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
                                mTVSubHeading.setTextColor(colorId);
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

    }
}
