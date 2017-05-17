package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;


/**
 * Abstract class for Chat bubble view. Extend this class to create
 * new {@link TemplateView}.
 */
public abstract class TemplateView extends RelativeLayout implements BaseView {

    @TemplateView.TemplateType
    int type = INCOMING;

    public TemplateView(Context context) {
        super(context);
    }

    public TemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO can be removed
    public abstract void initView(Context context, AttributeSet attrs);

    /**
     * Inflate and return appropriate view.
     *
     * @return returns inflated view.
     */
    public abstract TemplateView inflate(Context context);

    /**
     * Set chat bubble type {@link TemplateType}. Default chat bubble type is
     * {@code INCOMING}.
     *
     * @param type {@link TemplateType} to set for chat bubble.
     */
    public void setType(@TemplateType int type) {
        this.type = type;
    }

    /**
     * Set background of the {@link TemplateView}.
     */
    public abstract void setBackground();

    /**
     * Create view holder for view.
     *
     * @return return viewholder for view.
     */
    public abstract TemplateViewHolder createViewHolder(BaseView view);


    protected static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

}
