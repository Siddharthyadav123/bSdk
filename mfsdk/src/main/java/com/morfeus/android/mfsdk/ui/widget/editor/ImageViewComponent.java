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
import android.widget.ImageView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImageViewComponent extends ImageView implements Component {

    @Action
    String mAction;

    private BaseModel mModel;

    private Style mStyle;

    private ActionManager mActionManager;

    @SuppressWarnings("WrongConstant")
    public ImageViewComponent(Context context,
                              @Nullable BaseModel model,
                              @Nullable Style style,
                              @NonNull String action,
                              @NonNull ActionManager actionManager) {
        super(context);

        mStyle = style;
        mModel = model;
        mAction = checkNotNull(action);
        mActionManager = checkNotNull(actionManager);
    }

    public ImageViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    public ImageViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // No-op
    }

    @Override
    public void initView() {
        setImage(null);
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

    @SuppressWarnings("WrongConstant")
    @Override
    public void setAction(@Action String action) {
        mAction = checkNotNull(action);
    }

    @Override
    public void setData(BaseModel baseModel) {

    }

    @Override
    public void setComponentId(@IdRes int id) {
        setId(id);
    }

    @Override
    public BaseModel getData() {
        return mModel;
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
//
//    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return (ImageViewComponent) LayoutInflater.from(context)
                .inflate(R.layout.component_imageview, viewGroup, attach);
    }

}
