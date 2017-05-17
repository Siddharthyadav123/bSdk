package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TickTimeTemplateModel;

import static com.google.common.base.Preconditions.checkNotNull;

@Deprecated
public class TickTimeTemplateView extends TemplateView {
    public static final String TICK_TIME_TEMPLATE = "tickTimeTemplate";

    private String mMessage;

    private ImageView mIVFirstTick;

    private ImageView mIVSecondTick;

    private TextView mTVTime;

    private TextView mTVMessage;

    public TickTimeTemplateView(Context context) {
        super(context);
    }

    public TickTimeTemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TickTimeTemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(TICK_TIME_TEMPLATE, this);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SimpleTemplateView, 0, 0);
        try {
            mMessage = typedArray.getString(R.styleable.SimpleTemplateView_message);
        } finally {
            typedArray.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.simple_text_bubble, this);
        mTVMessage = (TextView) this.findViewById(R.id.tv_msg);
        mTVMessage.setText(mMessage);
        mTVTime = (TextView) this.findViewById(R.id.tv_time);
        mIVFirstTick = (ImageView) this.findViewById(R.id.iv_tick1);
        mIVSecondTick = (ImageView) this.findViewById(R.id.iv_tick2);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (TickTimeTemplateView)LayoutInflater.from(context)
                .inflate(R.layout.tick_time_bubble, this);
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
        return new TickTimeTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private TextView tvMessage;

        private TextView tvTime;

        private ImageView ivFirstTick;

        private ImageView ivSecondTick;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvMessage = (TextView) itemView.findViewById(R.id.tv_msg);
            this.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.ivFirstTick = (ImageView) itemView.findViewById(R.id.iv_tick1);
            this.ivSecondTick = (ImageView) itemView.findViewById(R.id.iv_tick2);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof TickTimeTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            if (((TickTimeTemplateModel)model).getMessage() != null)
                this.tvMessage.setText(((TickTimeTemplateModel) model).getMessage());

            if (((TickTimeTemplateModel)model).getTime() != null)
                this.tvTime.setText(((TickTimeTemplateModel) model).getTime());

            if (((TickTimeTemplateModel)model).getFirstTick())
                this.ivFirstTick.setVisibility(VISIBLE);
            else
                this.ivFirstTick.setVisibility(INVISIBLE);

            if (((TickTimeTemplateModel)model).getSecondTick())
                this.ivSecondTick.setVisibility(VISIBLE);
            else
                this.ivSecondTick.setVisibility(INVISIBLE);

        }

        public TextView getMessageTextView() {
            return this.tvMessage;
        }

        public TextView getTimeTextView() {
            return this.tvTime;
        }

        public ImageView getFirstTickImageView() {
            return this.ivFirstTick;
        }

        public boolean isFirstTickVisible() {
            return this.ivFirstTick.getVisibility() == VISIBLE;
        }

        public boolean isSecondTickVisible() {
            return this.ivSecondTick.getVisibility() == VISIBLE;
        }

        public ImageView getSecondTickImageView() {
            return this.ivSecondTick;
        }

    }
}
