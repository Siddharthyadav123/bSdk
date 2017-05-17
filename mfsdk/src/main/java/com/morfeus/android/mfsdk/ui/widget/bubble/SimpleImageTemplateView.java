package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.SimpleImageTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link SimpleImageTemplateView} displays image and text message inside the
 * chat bubble.
 */
@Deprecated
public class SimpleImageTemplateView extends TemplateView {

    public static final String SIMPLE_IMAGE_BUBBLE = "SIMPLE_IMAGE_BUBBLE";
    private String mPrimaryMessage;
    private String mSecondaryMessage;
    private Drawable mDrawable;
    private ImageView mIVImg;
    private TextView mTVPrimaryMessage;
    private TextView mTVSecondaryMessage;

    public SimpleImageTemplateView(Context context) {
        super(context);
    }

    public SimpleImageTemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SimpleImageTemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SimpleImageTemplateView, 0, 0);


        try {
            mPrimaryMessage = a.getString(R.styleable.SimpleImageTemplateView_primaryMessage);
            mSecondaryMessage = a.getString(R.styleable.SimpleImageTemplateView_secondaryMessage);
            mDrawable = a.getDrawable(R.styleable.SimpleImageTemplateView_defaultImage);
        } finally {
            a.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.simple_text2_bubble, this);
        mTVPrimaryMessage =  (TextView) this.findViewById(R.id.tv_primary_msg);
        mTVPrimaryMessage.setText(mPrimaryMessage);
        mTVSecondaryMessage = (TextView) this.findViewById(R.id.tv_secondary_msg);
        mTVSecondaryMessage.setText(mSecondaryMessage);
        mIVImg = (ImageView) this.findViewById(R.id.iv_img);
        mIVImg.setImageDrawable(mDrawable);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (SimpleImageTemplateView)LayoutInflater.from(context)
                .inflate(R.layout.simple_text2_bubble, this);
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
        return new SimpleImageTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        if (mIVImg != null) {
            mIVImg.setImageDrawable(drawable);
        }
    }

    public void setImageBitmap(Bitmap bm) {
        if (mIVImg != null) {
            mIVImg.setImageBitmap(bm);
        }
    }

    public void setPrimaryMessage(String primaryMessage) {
        if (mTVPrimaryMessage != null) {
            mTVPrimaryMessage.setText(primaryMessage);
        }
    }

    public void setSecondaryMessage(String secondaryMessage) {
        if (mTVSecondaryMessage !=null) {
            mTVSecondaryMessage.setText(secondaryMessage);
        }
    }

    public static class ViewHolder extends TemplateViewHolder {
        private ImageView ivImg;

        private TextView tvPrimaryMessage;

        private TextView tvSecondaryMessage;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvPrimaryMessage =  (TextView) itemView.findViewById(R.id.tv_primary_msg);
            this.tvSecondaryMessage = (TextView) itemView.findViewById(R.id.tv_secondary_msg);
            this.ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof SimpleImageTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            if (((SimpleImageTemplateModel)model).getPrimaryMessage() != null)
                this.tvPrimaryMessage.setText(((SimpleImageTemplateModel) model).getPrimaryMessage());

            if (((SimpleImageTemplateModel)model).getSecondaryMessage() != null)
                this.tvSecondaryMessage.setText(((SimpleImageTemplateModel) model).getSecondaryMessage());

            if (((SimpleImageTemplateModel)model).getImageBitmap() != null)
                ivImg.setImageBitmap(((SimpleImageTemplateModel) model).getImageBitmap());
        }

    }

}
