package com.morfeus.android.mfsdk.ui.widget.dialog.login;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.event.MessageReceiveEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.ButtonModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.style.MfDialogStyle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MfLoginView extends DialogFragment implements LoginView {
    private static final String TAG = MfLoginView.class.getName();

    public final int LOGIN_SUCCESS_AUTO_CLOSE_POP_TIME_IN_MILLIS = 2000;

    private View mRootView = null;
    private RelativeLayout mRlParentLayout;

    private LinearLayout mLlHeaderContainer;
    private ImageView mIvHeaderImage;
    private TextView mTvHeaderText;

    private LinearLayout mLlBodyContainer;
    private EditText mEtUserName;
    private EditText mEtPwd;

    private LinearLayout mLlFooterContainer;
    private TextView mTvCancelBtn;
    private TextView mTvLoginBtn;

    private LoginModel mLoginModel;

    private TextView mTvErrorInfoText;
    private ProgressDialog mProgressBar;

    private Handler mAutoDismissHandler;
    private AlertDialog mLoginSuccessDialog;
    private Runnable autoDismissRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLoginSuccessDialog != null) {
                mLoginSuccessDialog.dismiss();
            }
            dismiss();
        }
    };

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
        mRootView = inflater.inflate(R.layout.dialog_login_view_layout, container,
                false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        initView();
        return mRootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        if (mRootView != null) {
            mRlParentLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_parent_layout);
            mLlHeaderContainer = (LinearLayout) mRootView.findViewById(R.id.ll_header_container);
            mIvHeaderImage = (ImageView) mRootView.findViewById(R.id.iv_header_image);
            mTvHeaderText = (TextView) mRootView.findViewById(R.id.tv_header_text);
            mLlBodyContainer = (LinearLayout) mRootView.findViewById(R.id.ll_body_container);
            mEtUserName = (EditText) mRootView.findViewById(R.id.et_username_text);
            mEtPwd = (EditText) mRootView.findViewById(R.id.et_pwd_text);
            mLlFooterContainer = (LinearLayout) mRootView.findViewById(R.id.ll_footer_container);
            mTvCancelBtn = (TextView) mRootView.findViewById(R.id.tv_cancel_btn);
            mTvLoginBtn = (TextView) mRootView.findViewById(R.id.tv_login_btn);
            mTvErrorInfoText = (TextView) mRootView.findViewById(R.id.tv_error_info);

            mTvLoginBtn.setText(LanguageRepository.getInstance()
                    .getText(getString(R.string.text_login)));
            mTvCancelBtn.setText(LanguageRepository.getInstance()
                    .getText(getString(R.string.text_cancel)));
            mEtUserName.setHint(LanguageRepository.getInstance()
                    .getText(getString(R.string.text_user_name)));
            mEtPwd.setHint(LanguageRepository.getInstance()
                    .getText(getString(R.string.text_password)));

            registerEvents();
            setData();
        }
    }

    private void registerEvents() {
        mTvCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEtUserName.getText().toString();
                String password = mEtPwd.getText().toString();
                boolean isValid = true;

                if (TextUtils.isEmpty(username)) {
                    mEtUserName.setError(LanguageRepository.getInstance()
                            .getText(getString(R.string.err_empty_username)));
                    isValid = false;
                }

                if (TextUtils.isEmpty(password)) {
                    mEtPwd.setError(LanguageRepository.getInstance()
                            .getText(getString(R.string.err_empty_password)));
                    isValid = false;
                }

                if (isValid) {
                    hideSoftKeyboard();
                    performLogin(username, password);
                }
            }
        });
        EventBus.getDefault().register(this);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mRootView.getWindowToken(), 0);
    }

    private void performLogin(String username, String password) {
        String token = mLoginModel.getToken();
        if (TextUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Error: Login token can't be null");
        }
        showProgressDialog();
        EventBus.getDefault().post(new LoginEvent(username, password, token));
        mTvLoginBtn.setText(LanguageRepository.getInstance().getText("Login..."));
        mTvLoginBtn.setEnabled(false);
        mTvErrorInfoText.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        mProgressBar = ProgressDialog.show(getActivity(), "",
                "Logging you in...", true);
        mProgressBar.setCanceledOnTouchOutside(false);
        mProgressBar.show();
    }

    private void dismissProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void setLoginModel(LoginModel loginModel) {
        if (loginModel != null)
            mLoginModel = loginModel;
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onLoginResponse(LoginEvent.Response response) {
        if (response.isSuccess()) {
            showAlert(LanguageRepository.getInstance().getText("Login Successful."));
            startAutoDismissClock();
            EventBus.getDefault().post(new MessageReceiveEvent(response.getMfMsgModel()));
        } else {
            showError();
            mTvLoginBtn.setText(LanguageRepository.getInstance()
                    .getText(getString(R.string.text_login)));
            mTvLoginBtn.setEnabled(true);
        }
        dismissProgressBar();
    }

    private void startAutoDismissClock() {
        mAutoDismissHandler = new Handler();
        mAutoDismissHandler.postDelayed(autoDismissRunnable, LOGIN_SUCCESS_AUTO_CLOSE_POP_TIME_IN_MILLIS);
    }

    private void showError() {
        mTvErrorInfoText.setVisibility(View.VISIBLE);
        mTvErrorInfoText.setText(LanguageRepository.getInstance()
                .getText("Fail to login, please try again later."));
    }

    private void showAlert(String body) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(body);
        alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mAutoDismissHandler != null) {
                    mAutoDismissHandler.removeCallbacks(autoDismissRunnable);
                }
                dismiss();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mAutoDismissHandler != null) {
                    mAutoDismissHandler.removeCallbacks(autoDismissRunnable);
                }
                dismiss();
            }
        });
        mLoginSuccessDialog = alertDialog.show();
    }

    private void setData() {
        if (mLoginModel == null) {
            return;
        }

        if (mLoginModel.getHeader() != null) {
            processHeader(mLoginModel.getHeader());
        }

        if (mLoginModel.getBody() != null) {
            processBody(mLoginModel.getBody());
        }

        if (mLoginModel.getFooter() != null) {
            processFooter(mLoginModel.getFooter());
        }
    }

    private void processHeader(LoginModel.Header header) {
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
                        LogManager.e(TAG, e.getMessage());
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
            mTvHeaderText.setText(LanguageRepository.getInstance().getText(text));
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

    private void processBody(LoginModel.Body body) {
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
                    LogManager.e(TAG, e.getMessage());
                }
                if (resourceId != 0) {
                    mLlBodyContainer.setBackgroundResource(resourceId);
                }
            }
        }


        if (body.getEditTextModel("username") != null) {
            EditTextModel userNameEditTextModel = body.getEditTextModel("username");
            mEtUserName.setHint(LanguageRepository.getInstance()
                    .getText(userNameEditTextModel.getPlaceHolderText()));

            if (userNameEditTextModel.getStyle() != null) {
                String textColor = userNameEditTextModel.getStyle().getTextColor();
                String backgroundColor = userNameEditTextModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mEtUserName.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }

        if (body.getEditTextModel("password") != null) {
            EditTextModel pwdEditTextModel = body.getEditTextModel("password");
            mEtPwd.setHint(LanguageRepository.getInstance()
                    .getText(pwdEditTextModel.getPlaceHolderText()));

            if (pwdEditTextModel.getStyle() != null) {
                String textColor = pwdEditTextModel.getStyle().getTextColor();
                String backgroundColor = pwdEditTextModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mEtPwd.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }


    }

    private void processFooter(LoginModel.Footer footer) {
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
            mTvLoginBtn.setText(LanguageRepository.getInstance()
                    .getText(sendbuttonModel.getText()));

            if (sendbuttonModel.getStyle() != null) {
                String textColor = sendbuttonModel.getStyle().getTextColor();
                String backgroundColor = sendbuttonModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvLoginBtn.setTextColor(textColorId);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "Text color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
                try {
                    int bgColor = Color.parseColor(backgroundColor);
                    mTvLoginBtn.setBackgroundColor(bgColor);
                } catch (Exception e) {
                    String msg = (e.getMessage() == null) ? "BG color failed to load!" : e.getMessage();
                    LogManager.e(TAG, msg);
                }
            }
        }

        if (footer.getButtonModel("cancelbutton") != null) {
            ButtonModel cancelbuttonModel = footer.getButtonModel("cancelbutton");
            mTvCancelBtn.setText(LanguageRepository.getInstance()
                    .getText(cancelbuttonModel.getText()));

            if (cancelbuttonModel.getStyle() != null) {
                String textColor = cancelbuttonModel.getStyle().getTextColor();
                String backgroundColor = cancelbuttonModel.getStyle().getBackgroundColor();
                try {
                    int textColorId = Color.parseColor(textColor);
                    mTvCancelBtn.setTextColor(textColorId);
                } catch (Exception e) {
                    LogManager.e(TAG, e.getMessage());
                }
                try {
                    int bgColor = Color.parseColor(backgroundColor);
                    mTvCancelBtn.setBackgroundColor(bgColor);
                } catch (Exception e) {
                    LogManager.e(TAG, e.getMessage());
                }
            }
        }

    }

    private int getResourceId(String imageName) {
        Resources resources = getActivity().getResources();
        return resources.getIdentifier(imageName, "drawable",
                getActivity().getPackageName());
    }


}
