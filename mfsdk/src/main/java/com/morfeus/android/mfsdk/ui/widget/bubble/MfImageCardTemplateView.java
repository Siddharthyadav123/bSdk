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
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfImageCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfImageCardTemplateView extends TemplateView {

    public static final String IMAGE_CARD = "image";
    private static Context sContext;

    public MfImageCardTemplateView(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(IMAGE_CARD, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfImageCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_image_layout, this);
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
        return new MfImageCardTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private static final String TAG = ViewHolder.class.getName();
        private Context mContext;
        private TextView mTVTextMessage;
        private ImageView mIVImage;
        private LinearLayout mLLContainer;
        private RelativeLayout mRLBodyLayout;
        private SelectableRoundedImageView mSenderImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mTVTextMessage = (TextView) itemView.findViewById(R.id.chat_item_image_layout_tv_text);
            this.mIVImage = (ImageView) itemView.findViewById(R.id.chat_item_image_layout_iv_image);
            this.mLLContainer = (LinearLayout) itemView.findViewById(R.id.chat_item_image_layout_rly_container);
            this.mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rlBodyLayout);
            this.mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof MfImageCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            MfImageCardTemplateModel imageCardTemplateModel = (MfImageCardTemplateModel) model;

            applyDefaultCardStyle(imageCardTemplateModel.isIncoming());

            String message = imageCardTemplateModel.getTextMessage();
            String image = imageCardTemplateModel.getImage();
            if (imageCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END);
                mSenderImage.setVisibility(View.GONE);
            }

            if (message != null && message.length() > 0) {
                mTVTextMessage.setVisibility(View.VISIBLE);
                mTVTextMessage.setText(message);
            } else {
                mTVTextMessage.setVisibility(View.GONE);
            }

            if (mIVImage.getVisibility() == INVISIBLE || mIVImage.getVisibility() == GONE) {
                mIVImage.setVisibility(VISIBLE);
            }

            if (!TextUtils.isEmpty(image)) {
                int resourceId = 0;
                try {
                    resourceId = getResourceId(image);
                } catch (Exception e) {
                    LogManager.e(TAG, e.getMessage());
                }
                if (resourceId != 0) {
                    mIVImage.setImageResource(resourceId);
                } else {
                    mIVImage.setImageResource(R.drawable.banner2);
                }
            } else {
                mIVImage.setImageResource(R.drawable.banner2);
            }
        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = IMAGE_CARD + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = IMAGE_CARD + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
                    String messagSenderColor = defaultStyle.getMessageSenderColor();
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
                        mTVTextMessage.setMaxWidth(convertDpToPixel(maxWidth, mContext));

                    if (incoming) {
                        if (backgroundImage != null && backgroundImage.length() > 0) {
                            //TODO set background image based on bubble
                        }
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

                        if (messagSenderColor != null && messagSenderColor.length() > 0) {
                            //TODO set message time color
                        }
                    } else {
                        mLLContainer.setPadding(mLLContainer.getPaddingLeft(), mLLContainer.getPaddingTop(),
                                convertDpToPixel(paddingRight, mContext), mLLContainer.getPaddingBottom());
                    }


                    if (componentStyle != null && componentStyle.size() > 0) {
                        Style titleStyle = null;
                        for (int i = 0; i < componentStyle.size(); i++) {
                            if (componentStyle.get(i).getTemplateType().equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_TITLE)) {
                                titleStyle = componentStyle.get(i);
                                break;
                            }
                        }
                        if (titleStyle != null) {
                            String textColor = titleStyle.getTextColor();
                            if (textColor != null && textColor.length() > 0) {
                                int colorId = Color.parseColor(textColor);
                                mTVTextMessage.setTextColor(colorId);
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
