package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.SimpleTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SimpleBubbleView displays simple text message inside the chat bubble.
 */
@Deprecated
//TODO write sample unit test.
public class SimpleTemplateView extends TemplateView {

    public static final String SIMPLE_BUBBLE = "SIMPLE_BUBBLE";

    private String mMessage;

    private TextView mTVMessage;

    public SimpleTemplateView(Context context) {
        super(context);
    }

    public SimpleTemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SimpleTemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(SIMPLE_BUBBLE, this);
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
    }

    @Override
    public TemplateView inflate(Context context) {
        return (SimpleTemplateView)LayoutInflater.from(context)
                .inflate(R.layout.simple_text_bubble, this);
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
        return new SimpleTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    private void setMessage(@NonNull String message) {
        mMessage = message;
        mTVMessage.setText(mMessage);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private TextView tvMessage;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvMessage = (TextView) itemView.findViewById(R.id.tv_msg);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            checkNotNull(model);
            if (!(model instanceof SimpleTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            if (((SimpleTemplateModel)model).getTitle() != null)
                this.tvMessage.setText(((SimpleTemplateModel) model).getTitle());
        }

        public TextView getMessageTextView() {
            return this.tvMessage;
        }

    }
}
