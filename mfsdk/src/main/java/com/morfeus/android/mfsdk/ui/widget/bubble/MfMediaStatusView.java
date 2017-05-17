package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.morfeus.android.R;

public class MfMediaStatusView extends RelativeLayout {
    private Context mContext;

    private FrameLayout mControlFrameLayout;
    private ProgressBar mProgressBar;
    private ImageView mIvCancelDownload;
    private ImageView mIvDownload;
    private MfMediaStatusCallback mfMediaStatusCallback;

    public MfMediaStatusView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public MfMediaStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public MfMediaStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.media_status_view_layout, this, true);
        initViews();
        registerEvents();
    }

    private void registerEvents() {
        mIvCancelDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showRetryState();
                if (mfMediaStatusCallback != null) {
                    mfMediaStatusCallback.onMediaCancelDownloadClick();
                }
            }
        });
        mIvDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDownloadingState();
                if (mfMediaStatusCallback != null) {
                    mfMediaStatusCallback.onMediaDownloadBtnClick();
                }
            }
        });
    }

    public void showRetryState() {
        setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(GONE);
        mIvCancelDownload.setVisibility(GONE);
        mIvDownload.setVisibility(VISIBLE);
    }

    public void showDownloadingState() {
        setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mIvCancelDownload.setVisibility(VISIBLE);
        mIvDownload.setVisibility(GONE);
    }


    /**
     * This need to call in downloading complete
     */
    public void hideView() {
        setVisibility(View.GONE);
    }

    private void initViews() {
        mControlFrameLayout = (FrameLayout) findViewById(R.id.fl_control_frame);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress_bar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(android.graphics.Color.WHITE,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mIvCancelDownload = (ImageView) findViewById(R.id.iv_cancel_download);
        mIvDownload = (ImageView) findViewById(R.id.iv_download);
    }

    public void setMfMediaStatusCallback(MfMediaStatusCallback mfMediaStatusCallback) {
        this.mfMediaStatusCallback = mfMediaStatusCallback;
    }

    public interface MfMediaStatusCallback {
        public void onMediaDownloadBtnClick();

        public void onMediaCancelDownloadClick();
    }
}
