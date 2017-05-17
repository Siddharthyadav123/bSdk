package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.morfeus.android.R;

public class CurveRelativeLayout extends RelativeLayout {
    private static final String TAG = CurveRelativeLayout.class.getSimpleName();

    private final Path mPath = new Path();
    private Paint paint = new Paint();
    float round = getResources().getDimension(R.dimen.roundEdges);


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CurveRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CurveRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CurveRelativeLayout(Context context) {
        super(context);
        init(null);
    }

    @SuppressLint("NewApi")
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            fetchCustomAttributsFromXML(attrs);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        paint.setAntiAlias(true);
    }

    private void fetchCustomAttributsFromXML(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CurveRelativeLayout);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CurveRelativeLayout_cr_corner_size) {
                round = a.getDimension(attr, getResources().getDimension(R.dimen.roundEdges));
                break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        mPath.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom()), round, round, Path.Direction.CW);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
    }

}
