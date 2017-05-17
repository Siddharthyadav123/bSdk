package com.morfeus.android.mfsdk.ui.widget.bubble;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.StickerTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;

import static com.google.common.base.Preconditions.checkNotNull;

public class StickerTemplateView extends TemplateView {

    public static final String STICKER_CARD = "SimleyCardTemplate";
    private static Context sContext;

    public StickerTemplateView(Context context) {
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
        TemplateFactory.getInstance().registerTemplate(STICKER_CARD, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (StickerTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_sticker, this);
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
        return new StickerTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private static final String TAG = ViewHolder.class.getName();

        private Context mContext;
        private ImageView mIVStickerImage;
        private LinearLayout mLLParentLayout;
        private ImageView mIvTickImage;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mIVStickerImage = (ImageView) itemView.findViewById(R.id.ivStickerImage);
            this.mLLParentLayout = (LinearLayout) itemView.findViewById(R.id.llParentLayout);
            this.mIvTickImage = (ImageView) itemView.findViewById(R.id.iv_tick_image);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);

            if (!(model instanceof StickerTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            StickerTemplateModel stickerTemplateModel = (StickerTemplateModel) model;
            applyDefaultCardStyle(stickerTemplateModel.isIncoming());

            String image = stickerTemplateModel.getImage();

            if (stickerTemplateModel.isIncoming()) {
                mLLParentLayout.setGravity(Gravity.START | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.GONE);
            } else {
                mLLParentLayout.setGravity(Gravity.END | Gravity.BOTTOM);
                mIvTickImage.setVisibility(View.VISIBLE);
            }

            setMsgStatus(stickerTemplateModel.getStatus());

            if (!TextUtils.isEmpty(image)) {
                int resourceId = 0;
                try {
                    resourceId = getResourceId(image);
                } catch (Exception e) {
                    LogManager.e(TAG, e.getMessage());
                }
                if (resourceId != 0) {
                    if (mIVStickerImage.getVisibility() == INVISIBLE || mIVStickerImage.getVisibility() == GONE) {
                        mIVStickerImage.setVisibility(VISIBLE);
                    }
                    mIVStickerImage.setImageResource(resourceId);
                }
            }
        }

        public void setMsgStatus(int msgStatus) {
            switch (msgStatus) {
                case MessageStatus.UNSEND:
                    this.mIvTickImage.setImageResource(R.drawable.tick_msg_pending_or_failed);
                    break;
                case MessageStatus.SEND:
                    this.mIvTickImage.setImageResource(R.drawable.tick_msg_sent_read);
                    break;
                case MessageStatus.FAILED:
                    this.mIvTickImage.setImageResource(R.drawable.tick_msg_pending_or_failed);
                    break;
            }
        }

        private void applyDefaultCardStyle(boolean incoming) {
            String styleTemplateId = STICKER_CARD;

            try {
                Style defaultStyle = StyleFactory.getInstance().getStyle(styleTemplateId);
                if (defaultStyle != null) {
                    boolean isShownAsBig = defaultStyle.isShowAsBig();

                    if (isShownAsBig) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(convertDpToPixel(110, mContext), convertDpToPixel(110, mContext));
                        this.mIVStickerImage.setLayoutParams(layoutParams);
                    } else {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(convertDpToPixel(85, mContext), convertDpToPixel(85, mContext));
                        this.mIVStickerImage.setLayoutParams(layoutParams);
                    }

                }
//                Drawable backgroundDrwable = mLLTickContainer.getBackground();
//                if (backgroundDrwable != null) {
//                    if (backgroundDrwable instanceof GradientDrawable) {
//                        GradientDrawable gradientDrawable = (GradientDrawable) backgroundDrwable;
//                        gradientDrawable.setColor(mContext.getResources().getColor(R.color.fbChatBlue));
//                        mLLTickContainer.setBackgroundDrawable(backgroundDrwable);
//                    }
//                }

            } catch (StyleNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
