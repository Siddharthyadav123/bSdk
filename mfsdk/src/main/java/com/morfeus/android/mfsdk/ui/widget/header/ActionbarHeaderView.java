package com.morfeus.android.mfsdk.ui.widget.header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.BackEvent;
import com.morfeus.android.mfsdk.ui.action.event.CameraEvent;
import com.morfeus.android.mfsdk.ui.action.event.InfoEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.header.style.ActionbarStyle;
import com.morfeus.android.mfsdk.ui.widget.header.style.Style;
import com.morfeus.android.mfsdk.ui.widget.header.style.TitleStyle;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActionbarHeaderView extends LinearLayout implements HeaderView {
    public static final int NORMAL = 1;
    public static final int COPY = 2;
    private ActionbarModel mModel;
    private Context mContext;
    private Style mStyle;
    private LinearLayout mRootView;
    private TextView mTvLogoutBtn;
    private String mCopyText;

    private int mCurrentState = NORMAL;

    public ActionbarHeaderView(Context context, ActionbarModel model, ActionManager actionManager) {
        super(context);
        mContext = context;
        mModel = model;
    }

    public ActionbarHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // No-op
    }

    @Override
    public void initView() {
        mRootView = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.action_header_toolbar_layout, this,
                false);

        ImageView ivLeftButton = (ImageView) mRootView.findViewById(R.id.ivLeftButton);
        ImageView ivProfileImage = (ImageView) mRootView.findViewById(R.id.ivProfileImage);
        TextView tvHeaderText = (TextView) mRootView.findViewById(R.id.tvHeaderText);
        TextView tvSubHeaderText = (TextView) mRootView.findViewById(R.id.tvSubHeaderText);
        ImageView ivRightBtnOne = (ImageView) mRootView.findViewById(R.id.ivRightBtnOne);
        ImageView ivRightBtnTwo = (ImageView) mRootView.findViewById(R.id.ivRightBtnTwo);
        mTvLogoutBtn = (TextView) mRootView.findViewById(R.id.tvLogout);
        mTvLogoutBtn.setVisibility(View.GONE);
        mTvLogoutBtn.setText(LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_logout)));

        mTvLogoutBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(LanguageRepository.getInstance()
                        .getText(getResources().getString(R.string.text_logout_info)));
            }
        });

        addView(mRootView);

        if (mModel != null) {
            //setting actionbar base style
            if (mModel.getStyle() != null) {
                defineBackgroundStyle(mModel.getStyle(), mRootView);
            }

            //setting buttons property
            if (mModel.getButtons() != null) {
                List<ActionbarModel.Button> buttonList = mModel.getButtons();
                if (buttonList.size() > 0) {
                    ActionbarModel.Button leftBtnModel = mModel.getButtons().get(0);
                    ivLeftButton.setImageResource(getResourceId(leftBtnModel.getImage()));
                    ivLeftButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                ActionbarModel.Button button = mModel.getButtons().get(0);
                                String action = button.getAction();
                                EventBus.getDefault().post(new BackEvent(action));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    ivLeftButton.setVisibility(View.GONE);
                }

                if (buttonList.size() > 1) {
                    ActionbarModel.Button rightButton1 = mModel.getButtons().get(1);
                    ivRightBtnOne.setImageResource(getResourceId(rightButton1.getImage()));
                    ivRightBtnOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                ActionbarModel.Button button = mModel.getButtons().get(1);
                                String action = button.getAction();
                                EventBus.getDefault().post(new CameraEvent(action));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    ivRightBtnOne.setVisibility(View.GONE);
                }

                if (buttonList.size() > 2) {
                    ActionbarModel.Button rightButton2 = mModel.getButtons().get(2);
                    ivRightBtnTwo.setImageResource(getResourceId(rightButton2.getImage()));
                    ivRightBtnTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                ActionbarModel.Button button = mModel.getButtons().get(2);
                                String action = button.getAction();
                                EventBus.getDefault().post(new InfoEvent(action));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    ivRightBtnTwo.setVisibility(View.GONE);
                }

            }

            //setting text
            if (mModel.getTexts() != null) {
                List<ActionbarModel.Text> texts = mModel.getTexts();

                if (texts.size() > 0) {
                    ActionbarModel.Text headerText = mModel.getTexts().get(0);
                    tvHeaderText.setText(LanguageRepository.getInstance().getText(headerText.getLabel()));

                    if (headerText.getStyle() != null) {
                        TitleStyle headerStyle = (TitleStyle) headerText.getStyle();
                        tvHeaderText.setTextColor(Color.parseColor(headerStyle.getTextColor()));
                    }

                } else {
                    tvHeaderText.setVisibility(View.GONE);
                }

                if (texts.size() > 1) {
                    ActionbarModel.Text subHeaderText = mModel.getTexts().get(1);
                    tvSubHeaderText.setText(LanguageRepository.getInstance().getText(subHeaderText.getLabel()));

                    if (subHeaderText.getStyle() != null) {
                        TitleStyle subHeaderTextStyle = (TitleStyle) subHeaderText.getStyle();
                        tvSubHeaderText.setTextColor(Color.parseColor(subHeaderTextStyle.getTextColor()));
                    }

                } else {
                    tvSubHeaderText.setVisibility(View.GONE);
                }
            }

            //setting image
            if (mModel.getProfile() != null) {
                ivProfileImage.setImageResource(getResourceId(mModel.getProfile().getImage()));
            } else {
                ivProfileImage.setVisibility(View.GONE);
            }
        }

    }

    private void showAlert(String body) {
        String logoutText = LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_logout));
        String cancelText = LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_cancel));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(body);
        alertDialog.setPositiveButton(logoutText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO hit logout API
            }
        });
        alertDialog.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void showLogoutBtn() {
        mTvLogoutBtn.setVisibility(View.VISIBLE);
    }

    public void hideLogoutBtn() {
        mTvLogoutBtn.setVisibility(View.GONE);
    }

    public void setStatusText(String statusText) {
        TextView tvSubHeaderText = (TextView) mRootView.findViewById(R.id.tvSubHeaderText);
        tvSubHeaderText.setText(LanguageRepository.getInstance().getText(statusText));
    }

    private void defineBackgroundStyle(ActionbarStyle style, View view) {
        try {
            String backgroundColor = style.getBackgroundColor();

            if (backgroundColor != null) {
                int bgColor = Color.parseColor(backgroundColor);
                view.setBackgroundColor(bgColor);
                ((ChatActivity) getContext()).setStatusBarColor(bgColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setStyle(Style style) {
        mStyle = checkNotNull(style);
        defineBackgroundStyle((ActionbarStyle) mStyle, mRootView);
    }

    @Override
    public ActionbarModel getData() {
        return mModel;
    }

    @Override
    public void setData(@NonNull ActionbarModel model) {
        mModel = checkNotNull(model);
        invalidate();
        initView();
    }

    @Override
    public void setHeaderTitle(@NonNull String title) {
        // No-op
    }

    @Override
    public void setHeaderSubTitle(@NonNull String subTitle) {
        // No-op
    }

    @Override
    public void setProfileImage(@NonNull String imageName) {
        // No-op
    }

    @Override
    public void setState(@State int state) {
        switch (state) {
            case NORMAL:
                updateStateToNormal();
                break;
            case COPY:
                updateStateToCopy();
                break;
            default:
                throw new IllegalArgumentException("Error: " + state + " is not supported!");
        }
    }

    private void updateStateToNormal() {
        mCurrentState = NORMAL;
        removeAllViews();
        addView(mRootView);
    }

    private void updateStateToCopy() {
        mCurrentState = COPY;
        removeAllViews();

        View copyView = LayoutInflater.from(getContext())
                .inflate(R.layout.action_header_copy_layout, null);
        copyView.setLayoutParams(mRootView.getLayoutParams());
        ImageView ivLeftButton = (ImageView) copyView.findViewById(R.id.ivLeftButton);
        ImageView ivCopyToClipboard = (ImageView) copyView.findViewById(R.id.ivCopyToClipboard);
        ImageView ivShare = (ImageView) copyView.findViewById(R.id.ivShare);

        if (mModel != null) {
            if (mModel.getStyle() != null) {
                defineBackgroundStyle(mModel.getStyle(), copyView);
            }
            if (mModel.getButtons() != null) {
                List<ActionbarModel.Button> buttonList = mModel.getButtons();
                if (buttonList.size() > 0) {
                    ActionbarModel.Button leftBtnModel = mModel.getButtons().get(0);
                    ivLeftButton.setImageResource(getResourceId(leftBtnModel.getImage()));
                }
            }
        }

        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStateToNormal();
            }
        });

        ivCopyToClipboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(getContext(), mCopyText);
                updateStateToNormal();
            }
        });

        ivShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareCompat.IntentBuilder
                        .from((Activity) mContext)
                        .setText(mCopyText)
                        .setType("text/plain")
                        .setChooserTitle("Share")
                        .startChooser();
                updateStateToNormal();
            }
        });
        addView(copyView);
    }

    private void copyToClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getContext(), LanguageRepository.getInstance()
                        .getText("Copied text to clipboard."),
                Toast.LENGTH_SHORT).show();
    }

    private int getResourceId(String imageName) {
        try {
            Resources resources = mContext.getResources();
            return resources.getIdentifier(imageName, "drawable",
                    mContext.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_camera_off;
        }
    }


    public void setCopyText(String copyText) {
        this.mCopyText = copyText;
    }


    public int getCurrentState() {
        return mCurrentState;
    }

    /**
     * List of state available for actionbar.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            NORMAL,
            COPY
    })
    @interface State {
    }

}
