package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.ui.action.event.ActionbarCopyStateEvent;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParser.CardStyleKey;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.inject.Injection;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfTextCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MfTextCardTemplateView extends TemplateView {
    public static final String TEXT_CARD = "text";
    private static Context sContext;

    public MfTextCardTemplateView(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(TEXT_CARD, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfTextCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_text_layout, this);
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
        return new MfTextCardTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private static final String TAG = ViewHolder.class.getName();
        private Context mContext;
        private TextView mTVTextMessage;
        private LinearLayout mRLContainer;
        private RelativeLayout mRLBodyLayout;
        private SelectableRoundedImageView mSenderImage;
        private ImageView mIvTickImage;
        private StyleFactory mStyleFactory;

        ViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mStyleFactory = Injection.provideStyleFactory();
            mTVTextMessage
                    = (TextView) itemView.findViewById(R.id.chat_item_text_layout_tv_text);
            mRLContainer
                    = (LinearLayout) itemView.findViewById(R.id.chat_item_text_layout_rly_container);
            mRLBodyLayout
                    = (RelativeLayout) itemView.findViewById(R.id.rl_body_layout);
            mSenderImage
                    = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_sender_image);
            mIvTickImage
                    = (ImageView) itemView.findViewById(R.id.iv_tick_image);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof MfTextCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }
            MfTextCardTemplateModel textCardTemplateModel = (MfTextCardTemplateModel) model;
            applyDefaultCardStyle(textCardTemplateModel.isIncoming());

            if (textCardTemplateModel.isIncoming()) {
                mRLContainer.setGravity(Gravity.START | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.GONE);
                mSenderImage.setVisibility(View.VISIBLE);
            } else {
                mRLContainer.setGravity(Gravity.END | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.VISIBLE);
                mSenderImage.setVisibility(View.GONE);
            }
            setMsgStatus(textCardTemplateModel.getStatus());

            final String message = textCardTemplateModel.getTextMessage();
            if (!TextUtils.isEmpty(message)) {
                mTVTextMessage.setText(message);

                mRLContainer.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        ActionbarCopyStateEvent copyClipboardEvent = new ActionbarCopyStateEvent(message);
                        EventBus.getDefault().post(copyClipboardEvent);
                        return false;
                    }
                });
            }
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


        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId;

            if (incoming) {
                styleTemplateId = TEXT_CARD + "-" + CardStyleKey.KEY_INCOMING;
            } else {
                styleTemplateId = TEXT_CARD + "-" + CardStyleKey.KEY_OUTGOING;
            }

            try {
                Style defaultStyle = mStyleFactory.getStyle(styleTemplateId);

                if (defaultStyle != null) {
                    int paddingLeft = defaultStyle.getPaddingLeft();
                    int paddingRight = defaultStyle.getPaddingRight();
                    int maxWidth = defaultStyle.getMaxWidth();

                    String botImage = defaultStyle.getBotImage();

                    List<Style> componentStyle = defaultStyle.getComponentStyle();

                    if (incoming) {
                        mRLBodyLayout.setBackgroundResource(R.drawable.bubble_incoming_text);
                    } else {
                        mRLBodyLayout.setBackgroundResource(R.drawable.bubble_outgoing_text);
                    }

                    if (maxWidth > 0)
                        mTVTextMessage.setMaxWidth(convertDpToPixel(maxWidth, mContext));

                    int pdLeft;
                    int pdTop;
                    int pdRight;
                    int pdBottom;

                    if (incoming) {

                        //set padding
                        if (paddingLeft > 0) {
                            pdLeft = convertDpToPixel(paddingLeft, mContext);
                            pdTop = mRLBodyLayout.getPaddingTop();
                            pdRight = mRLBodyLayout.getPaddingRight();
                            pdBottom = mRLBodyLayout.getPaddingBottom();

                            mRLBodyLayout.setPadding(pdLeft, pdTop, pdRight, pdBottom);

                        } else {
                            mRLBodyLayout.setPadding(0, 0, 0, 0);
                        }

                        if (hasBotImage(botImage)) {
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
                        if (paddingRight > 0) {
                            pdLeft = mRLBodyLayout.getPaddingLeft();
                            pdTop = mRLBodyLayout.getPaddingTop();
                            pdRight = convertDpToPixel(paddingRight, mContext);
                            pdBottom = mRLBodyLayout.getPaddingBottom();

                            mRLBodyLayout.setPadding(pdLeft, pdTop, pdRight, pdBottom);
                        } else {
                            mRLBodyLayout.setPadding(0, 0, 0, 0);
                        }
                    }


                    if (componentStyle != null && componentStyle.size() > 0) {
                        Style titleStyle = null;
                        for (int i = 0; i < componentStyle.size(); i++) {
                            String templateType = componentStyle.get(i).getTemplateType();
                            if (CardStyleKey.KEY_TITLE.equalsIgnoreCase(templateType)) {
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
                Log.e(TAG, "applyDefaultCardStyle: ", e);
            }

        }

        private boolean hasBotImage(String botImage) {
            return botImage != null && botImage.length() > 0;
        }


    }
}
