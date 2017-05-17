package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.loading.LoadingView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

/**
 * Loading card template.
 */

public class MfLoadingTemplateView extends TemplateView {
    public static final String LOADING_CARD_TEMPLATE_VIEW = "LoadingCardTemplate";
    private static Context sContext;

    public MfLoadingTemplateView(Context context) {
        super(context);
        sContext = context;
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {
        TemplateFactory.getInstance().registerTemplate(LOADING_CARD_TEMPLATE_VIEW, this);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MfLoadingTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.chat_item_loading_template_layout, this);
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
        return new MfLoadingTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new ViewHolder((View) view);
    }

    public static class ViewHolder extends TemplateViewHolder {
        private LoadingView mLoadingView;

        public ViewHolder(View itemView) {
            super(itemView);
            mLoadingView = (LoadingView) itemView.findViewById(R.id.loading_view);
        }

        @Override
        public void setData(@NonNull TemplateModel model) {
            // No-op
        }
    }
}
