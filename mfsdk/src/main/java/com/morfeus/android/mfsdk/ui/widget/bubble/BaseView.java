package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.annotation.IntDef;

import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * BaseView tobe implemented by all template view.
 */
public interface BaseView {
    int INCOMING = 0;
    int OUTGOING = 1;

    /**
     * Inflate and return appropriate view.
     * @return returns inflated view.
     */
    TemplateView inflate(Context context);

    /**
     * Create view holder for view.
     * @return return viewholder for view.
     */
    TemplateViewHolder createViewHolder(BaseView view);

    /**
     * Set chat bubble type {@link TemplateView.TemplateType}. Default chat bubble type is
     * {@code INCOMING}.
     *
     * @param type {@link TemplateView.TemplateType} to set for chat bubble.
     */
    void setType(@TemplateView.TemplateType int type);

    /**
     * Set background of the {@link TemplateView}.
     */
    void setBackground();

    /**
     * Create the instance
     *
     * @return return instance
     */
    BaseView create(Context context);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INCOMING, OUTGOING})
    public @interface TemplateType {
    }
}
