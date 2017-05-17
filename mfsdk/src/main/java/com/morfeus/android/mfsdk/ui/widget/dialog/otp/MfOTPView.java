package com.morfeus.android.mfsdk.ui.widget.dialog.otp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.event.otp.OTPEvent;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.pinpad.PinpadUI;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import org.greenrobot.eventbus.EventBus;

public class MfOTPView extends DialogFragment implements OTPView {
    private static final String TAG = MfOTPView.class.getName();

    private View mRootView = null;
    private RelativeLayout mRlParentLayout;
    private LinearLayout mLlHeaderContainer;
    private ImageView mIvHeaderImage;
    private TextView mTvHeaderText;
    private TextView mTvSubHeaderText;
    private LinearLayout mLlBodyContainer;
    private LinearLayout mLlFooterContainer;
    private TextView mTvCancelBtn;
    private TextView mTvSendBtn;
    private PinpadUI mPinPadUI;
    private OTPModel mOtpModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(getActivity(), R.style.DialogFragmentStyle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_otp_view_layout, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        initView();
        return mRootView;
    }

    @Override
    public void initView() {
        if (mRootView != null) {
            mRlParentLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_parent_layout);
            mLlHeaderContainer = (LinearLayout) mRootView.findViewById(R.id.ll_header_container);
            mIvHeaderImage = (ImageView) mRootView.findViewById(R.id.iv_header_image);
            mTvHeaderText = (TextView) mRootView.findViewById(R.id.tv_header_text);
            mLlBodyContainer = (LinearLayout) mRootView.findViewById(R.id.ll_body_container);
            mLlFooterContainer = (LinearLayout) mRootView.findViewById(R.id.ll_footer_container);
            mTvCancelBtn = (TextView) mRootView.findViewById(R.id.tv_cancel_btn);
            mTvSendBtn = (TextView) mRootView.findViewById(R.id.tv_send_btn);
            mTvSubHeaderText = (TextView) mRootView.findViewById(R.id.tv_sub_header_text);
            addPinPadView(6);
            registerEvents();
            setData();
        }
    }


    private void registerEvents() {
        mTvSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPinPadUI.isAllDigitEntered()) {
                    Toast.makeText(getActivity(), getString(R.string.err_empty_otp), Toast.LENGTH_SHORT).show();
                } else {
                    performOTPRequest();
                }
            }
        });

        mTvCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void performOTPRequest() {
        long otp = Long.parseLong(mPinPadUI.getPinString());
        EventBus.getDefault().post(new OTPEvent(otp));
        mTvSendBtn.setText("Verifying OTP...");
    }

    private void addPinPadView(int numberOfPinSlots) {
        mPinPadUI = new PinpadUI(getActivity(), numberOfPinSlots);
        LinearLayout.LayoutParams pinpadParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        mPinPadUI.setLayoutParams(pinpadParams);
        mPinPadUI.setGravity(Gravity.CENTER);
        mLlBodyContainer.addView(mPinPadUI);
    }

    @Override
    public void setOTPModel(OTPModel otpModel) {
        if (otpModel != null)
            mOtpModel = otpModel;
    }

    public void setData() {
        if (mOtpModel == null) {
            return;
        }

        if (mOtpModel.getHeader() != null) {
            processHeader(mOtpModel.getHeader());
        }

        if (mOtpModel.getBody() != null) {
            processBody(mOtpModel.getBody());
        }

        if (mOtpModel.getFooter() != null) {
            processFooter(mOtpModel.getFooter());
        }
    }

    private void processHeader(OTPModel.Header header) {
        if (header.getStyle() != null) {
            String backgroundImage = header.getStyle().getBackgroundImage();
            String backgroundColor = header.getStyle().getBackgroundColor();
            String borderColor = header.getStyle().getBorderColor();

            try {
                int bgColor = Color.parseColor(backgroundColor);
                mLlHeaderContainer.setBackgroundColor(bgColor);
            } catch (Exception e) {
                String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                LogManager.e(TAG, msg);
            }

            if (backgroundImage != null) {
                if (!(TextUtils.isEmpty(backgroundImage))) {
                    int resourceId = 0;
                    try {
                        resourceId = getResourceId(backgroundImage);
                    } catch (Exception e) {
                        String msg = (e.getMessage() == null) ? "resource failed to load!" : e.getMessage();
                        LogManager.e(TAG, msg);
                    }
                    if (resourceId != 0) {
                        mLlHeaderContainer.setBackgroundResource(resourceId);
                    }
                }
            }
        }

        if (header.getTextModel() != null) {
            String text = header.getTextModel().getText();
            MfDialogStyle style = header.getTextModel().getStyle();
            mTvHeaderText.setText(text);
            if (style != null) {
                String textColor = header.getStyle().getTextColor();
                String horizontalAlignment = header.getStyle().getHorizontalAlignment();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvHeaderText.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }
    }

    private void processFooter(OTPModel.Footer footer) {
        if (footer.getStyle() != null) {
            String backgroundColor = footer.getStyle().getBackgroundColor();
            String borderColor = footer.getStyle().getBorderColor();
            try {
                int bgColor = Color.parseColor(backgroundColor);
                mLlFooterContainer.setBackgroundColor(bgColor);
            } catch (Exception e) {
                String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                LogManager.e(TAG, msg);
            }
        }

        if (footer.getButtonModel("sendbutton") != null) {
            ButtonModel sendbuttonModel = footer.getButtonModel("sendbutton");
            mTvSendBtn.setText(sendbuttonModel.getText());

            if (sendbuttonModel.getStyle() != null) {
                String textColor = sendbuttonModel.getStyle().getTextColor();
                String backgroundColor = sendbuttonModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvSendBtn.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
                try {
                    int bgColor = Color.parseColor(backgroundColor);
                    mTvSendBtn.setBackgroundColor(bgColor);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }

        if (footer.getButtonModel("cancelbutton") != null) {
            ButtonModel cancelbuttonModel = footer.getButtonModel("cancelbutton");
            mTvCancelBtn.setText(cancelbuttonModel.getText());

            if (cancelbuttonModel.getStyle() != null) {
                String textColor = cancelbuttonModel.getStyle().getTextColor();
                String backgroundColor = cancelbuttonModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvCancelBtn.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
                try {
                    int bgColor = Color.parseColor(backgroundColor);
                    mTvCancelBtn.setBackgroundColor(bgColor);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }
    }

    private void processBody(OTPModel.Body body) {
        if (body.getStyle() != null) {
            String backgroundImage = body.getStyle().getBackgroundImage();
            String backgroundColor = body.getStyle().getBackgroundColor();

            try {
                int bgColor = Color.parseColor(backgroundColor);
                mLlBodyContainer.setBackgroundColor(bgColor);
            } catch (Exception e) {
                String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                LogManager.e(TAG, msg);
            }

            if (!(TextUtils.isEmpty(backgroundImage))) {
                int resourceId = 0;
                try {
                    resourceId = getResourceId(backgroundImage);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "BG resource failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
                if (resourceId != 0) {
                    mLlBodyContainer.setBackgroundResource(resourceId);
                }
            }
        }

        if (body.getSubheaderText() != null) {
            String text = body.getSubheaderText().getText();
            MfDialogStyle style = body.getSubheaderText().getStyle();
            mTvSubHeaderText.setText(text);
            if (style != null) {
                String textColor = body.getStyle().getTextColor();
                String horizontalAlignment = body.getStyle().getHorizontalAlignment();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvSubHeaderText.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }

        if (body.getPinpadModel() != null) {
            int numberOfCharater = body.getPinpadModel().getNumberOfCharater();
            mLlBodyContainer.removeView(mPinPadUI);
            addPinPadView(numberOfCharater);
            if (body.getPinpadModel().getStyle() != null) {
                String maskImage = body.getPinpadModel().getStyle().getMaskImage();
                String unmaskImage = body.getPinpadModel().getStyle().getUnmaskImage();
                //TODO need to discuss this mask and unmask image
            }
        }


    }

    private int getResourceId(String imageName) {
        Resources resources = getActivity().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getActivity().getPackageName());
    }

}
