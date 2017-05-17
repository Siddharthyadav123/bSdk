package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.maps.model.LatLng;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.location.LocationMapActivity;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfLocationCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfLocationCardTemplateView extends TemplateView {
    public static final String LOCATION_CARD = "location";
    private static Context sContext;

    public MfLocationCardTemplateView(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(LOCATION_CARD, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfLocationCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_location_layout, this);
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
        return new MfLocationCardTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private static final String TAG = ViewHolder.class.getName();
        private Context mContext;
        private TextView mTVTextMessage;
        private LinearLayout mLLContainer;
        private RelativeLayout mRLBodyLayout;
        private SelectableRoundedImageView mSenderImage;

        private MfNetworkImageView mIVHeaderImage;
        private MfMediaStatusView mfMediaStatusView;
        private RelativeLayout mRlImageContainerLaout;
        private ImageView mIvTickImage;


        public ViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mTVTextMessage = (TextView) itemView
                    .findViewById(R.id.chat_item_location_layout_tv_text);
            mLLContainer = (LinearLayout) itemView
                    .findViewById(R.id.chat_item_location_layout_rly_container);
            mRLBodyLayout = (RelativeLayout) itemView.findViewById(R.id.rl_body_Layout);
            mSenderImage = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            mIvTickImage = (ImageView) itemView.findViewById(R.id.iv_tick_image);

            mIVHeaderImage = (MfNetworkImageView) itemView.findViewById(R.id.iv_header_image);
            mIVHeaderImage.setBotId("5w47394784104");
            mIVHeaderImage.setDefaultImageResId(R.drawable.cartoon);
            mIVHeaderImage.setErrorImageResId(android.R.drawable.ic_delete);
            mIVHeaderImage.setVisibility(GONE);

            mfMediaStatusView = (MfMediaStatusView) itemView
                    .findViewById(R.id.image_downloader_view);
            mRlImageContainerLaout = (RelativeLayout) itemView
                    .findViewById(R.id.rl_image_container);
            mfMediaStatusView.setVisibility(GONE);
            mRlImageContainerLaout.setVisibility(View.GONE);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof MfLocationCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            MfLocationCardTemplateModel locationCardTemplateModel
                    = (MfLocationCardTemplateModel) model;
            applyDefaultCardStyle(locationCardTemplateModel.isIncoming());

            String message = locationCardTemplateModel.getTextMessage();
            if (locationCardTemplateModel.isIncoming()) {
                mLLContainer.setGravity(Gravity.START | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.GONE);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mLLContainer.setGravity(Gravity.END | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.VISIBLE);
                mSenderImage.setVisibility(View.GONE);
            }
            setMsgStatus(locationCardTemplateModel.getStatus());

            if (message != null && message.length() > 0) {
                mTVTextMessage.setVisibility(View.VISIBLE);
                mTVTextMessage.setText(message);
            } else {
                mTVTextMessage.setVisibility(View.GONE);
            }
            updateLocationImage(locationCardTemplateModel);
        }

        private void setMsgStatus(int msgStatus) {
            switch (msgStatus) {
                case MessageStatus.UNSEND:
                    mIvTickImage.setImageResource(R.drawable.tick_msg_pending_or_failed);
                    break;
                case MessageStatus.SEND:
                    mIvTickImage.setImageResource(R.drawable.tick_msg_sent_read);
                    break;
                case MessageStatus.FAILED:
                    mIvTickImage.setImageResource(R.drawable.tick_msg_pending_or_failed);
                    break;
            }
        }

        private void updateLocationImage(final MfLocationCardTemplateModel locationCardTemplateModel) {
            boolean isImageItemFound = false;
            String url = locationCardTemplateModel.getUrl();
            String imageName = locationCardTemplateModel.getImageName();
            if (url != null) {
                isImageItemFound = true;
                String[] imgAddress = {imageName, url};
                int visible = this.mIVHeaderImage.getVisibility();
                if (visible == View.GONE || visible == View.INVISIBLE) {
                    mIVHeaderImage.setVisibility(VISIBLE);
                    mfMediaStatusView.setVisibility(View.VISIBLE);
                    mRlImageContainerLaout.setVisibility(View.VISIBLE);
                }
                MfImageLoader imageLoader = MFMediaSdk.getInstance().getImageLoader();
                mfMediaStatusView.setMfMediaStatusCallback(mIVHeaderImage);
                mIVHeaderImage.setImageUrl(imgAddress, imageLoader, mfMediaStatusView);

                mRlImageContainerLaout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCardClickEvent(locationCardTemplateModel);
                    }
                });
            }

            if (!isImageItemFound) {
                mIVHeaderImage.setVisibility(GONE);
                mfMediaStatusView.setVisibility(View.GONE);
                mRlImageContainerLaout.setVisibility(View.GONE);
            }
        }

        private void onCardClickEvent(MfLocationCardTemplateModel locationCardTemplateModel) {
            LatLng latLng = new LatLng(locationCardTemplateModel.getLatitude(),
                    locationCardTemplateModel.getLongitude());

            Intent intent = new Intent(mContext, LocationMapActivity.class);
            intent.putExtra(LocationMapActivity.KEY_SCREEN_MODE
                    , LocationMapActivity.SCREEN_MODE_SHOW_LOCATION);
            intent.putExtra(LocationMapActivity.KEY_LOCATION_TO_SHOW, latLng);
            mContext.startActivity(intent);
        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = null;
            if (incoming) {
                styleTemplateId = LOCATION_CARD + "-" + ConfigParser.CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = LOCATION_CARD + "-" + ConfigParser.CardStyleKey.KEY_OUTGOING;
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
                        mLLContainer.setPadding(mLLContainer.getPaddingLeft(),
                                mLLContainer.getPaddingTop(),
                                convertDpToPixel(paddingRight, mContext),
                                mLLContainer.getPaddingBottom());
                    }


                    if (componentStyle != null && componentStyle.size() > 0) {
                        Style titleStyle = null;
                        for (int i = 0; i < componentStyle.size(); i++) {
                            if (componentStyle.get(i).getTemplateType()
                                    .equalsIgnoreCase(ConfigParser.CardStyleKey.KEY_TITLE)) {
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
