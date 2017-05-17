package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.PayloadEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactory;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SuggestionViewStyle;

import org.greenrobot.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkNotNull;

public class SuggestionButtonComponent extends LinearLayout implements Component {
    private static final String TAG = SuggestionButtonComponent.class.getSimpleName();
    private String mAction;

    private SuggestionViewStyle mStyle;

    private ActionManager mActionManager;

    private ComponentFactory mComponentFactory;

    private SuggestionButtonModel mModel;

    public SuggestionButtonComponent(Context context,
                                     @NonNull String action,
                                     @NonNull ComponentFactory componentFactory,
                                     @NonNull Style style,
                                     @NonNull ActionManager actionManager,
                                     @NonNull BaseModel model) {
        super(context);

        mAction = checkNotNull(action);
        mComponentFactory = checkNotNull(componentFactory);
        mActionManager = checkNotNull(actionManager);
        mModel = (SuggestionButtonModel) checkNotNull(model);
        mStyle = (SuggestionViewStyle) checkNotNull(style);
    }

    public SuggestionButtonComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    @Override
    public void setStyle(Style style) {
        mStyle = (SuggestionViewStyle) checkNotNull(style);
    }

    @Override
    public void setAction(@Action String action) {
        mAction = checkNotNull(action);
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
    public void setData(BaseModel model) {
        mModel = (SuggestionButtonModel) checkNotNull(model);
    }

    @Override
    public void show(boolean show) {
        if (show)
            setVisibility(VISIBLE);
        else
            setVisibility(GONE);
    }

    @Override
    public void initView() throws ComponentException {
        // TODO refine layout parameter and style
        setBackgroundResource(R.drawable.suggestion_button_shape);
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                getPxFromDP(mStyle.getHeight())
        );

        int paddingLeftRight = getPxFromDP(7);
        setPadding(paddingLeftRight, 0, paddingLeftRight, 0);
        int margin = getPxFromDP(4);
        layoutParams.setMargins(margin, margin, margin, margin);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);

        addSuggestionImageIfRequired();
        addSuggestionText();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PayloadEvent payloadEvent = new PayloadEvent(mModel.getPayload(), mModel.getAction(), null);
                    EventBus.getDefault().post(payloadEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addSuggestionText() {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = getPxFromDP(3);
        textParams.setMargins(margin, 0, margin, 0);
        textView.setLayoutParams(textParams);
        textView.setAllCaps(false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTypeface(null, Typeface.NORMAL);

        if (mModel.getLabel() != null && mModel.getLabel().length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(LanguageRepository.getInstance().getText(mModel.getLabel()));
        } else {
            textView.setVisibility(View.GONE);
        }

        try {
            if (mModel.getStyle() != null) {
                SuggestionViewStyle suggestionViewStyle = (SuggestionViewStyle) mModel.getStyle();
                String labelColor = suggestionViewStyle.getButtonStyle().getLabelColor();
                if (labelColor != null && labelColor.length() > 0) {
                    int colorId = Color.parseColor(labelColor);
                    textView.setTextColor(colorId);
                }

                String borderColor = suggestionViewStyle.getButtonStyle().getBorderColor();
                Drawable backgroundDrwable = getBackground();
                if (backgroundDrwable != null && borderColor != null) {
                    if (backgroundDrwable instanceof GradientDrawable) {
                        GradientDrawable myGrad = (GradientDrawable) backgroundDrwable;
                        int colorId = Color.parseColor(borderColor);
                        myGrad.setStroke(dpToPixel(1), colorId);
                    }
                }
            }
        } catch (Exception e) {
            LogManager.e(TAG, "addSuggestionText: " + e.getMessage());
        }

        addView(textView);
    }

    private void addSuggestionImageIfRequired() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                getPxFromDP(mStyle.getHeight() - 13),
                getPxFromDP(mStyle.getHeight() - 13)
        );
        int margin = getPxFromDP(1);
        imageParams.setMargins(margin, 0, 0, 0);
        imageView.setLayoutParams(imageParams);
        addView(imageView);
        if (!TextUtils.isEmpty(mModel.getImage())) {
            int resourceId = 0;
            try {
                resourceId = getResourceId(mModel.getImage());
            } catch (Exception e) {
                LogManager.e(TAG, e.getMessage());
            }
            if (resourceId != 0) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(resourceId);
            } else {
                imageView.setVisibility(View.GONE);
            }
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private int getPxFromDP(int dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics()
        );
        return (int) px;
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return null;
    }

    private int getResourceId(String imageName) {
        Resources resources = getContext().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getContext().getPackageName());
    }

    protected int dpToPixel(float dp) {
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }


}
