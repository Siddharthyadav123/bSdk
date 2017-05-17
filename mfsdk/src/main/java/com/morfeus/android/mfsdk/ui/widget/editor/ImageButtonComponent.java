package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import org.greenrobot.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class ImageButtonComponent extends ImageButton implements Component {

    @Action
    private String mAction;

    private ActionManager mActionManager;

    private ImageButtonModel mModel;

    private Style mStyle;

    @SuppressWarnings("WrongConstant")
    public ImageButtonComponent(Context context,
                                @NonNull BaseModel model,
                                @Nullable Style style,
                                @Nullable @Action String action,
                                @NonNull ActionManager actionManager) {
        super(context);
//        checkArgument(model instanceof ImageButtonModel);

        mStyle = style;
        mModel = (ImageButtonModel) checkNotNull(model);
        mAction = action;
        mActionManager = checkNotNull(actionManager);
    }

    public ImageButtonComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    public ImageButtonComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // No-op
    }

    @Override
    public void initView() {
        setImage(mModel.getImage());
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO handle action
            }
        });
    }

    @Override
    public void setStyle(Style style) {
        mStyle = checkNotNull(style);
        setImage(style.getBackgroundImage());
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public void setAction(@Action String action) {
        mAction = checkNotNull(action);
    }

    private void setImage(String imageName) {
        checkNotNull(imageName);
        int resourceId = getResourceId(imageName);
        setBackgroundResource(resourceId);
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    public BaseModel getData() {
        return mModel;
    }

    @Override
    public void setData(BaseModel baseModel) {
        checkArgument(baseModel instanceof ImageButtonModel);
        mModel = (ImageButtonModel) checkNotNull(baseModel);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PayloadEvent payloadEvent = new PayloadEvent(null, mModel.getAction(), mModel.getImage());
                    EventBus.getDefault().post(payloadEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void setComponentId(@IdRes int id) {
        setId(id);
    }

    @Override
    public void show(boolean show) {
        if (show)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);
    }

//    @Override
//    public void initView(Context context, AttributeSet attrs) {
//        // No-op
//    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return (ImageButtonComponent) LayoutInflater.from(context)
                .inflate(R.layout.component_image_button, viewGroup, attach);
    }

}
