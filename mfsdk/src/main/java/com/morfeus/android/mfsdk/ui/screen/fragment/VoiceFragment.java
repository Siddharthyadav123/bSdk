package com.morfeus.android.mfsdk.ui.screen.fragment;


import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.auth.oauth2.AccessToken;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.event.SendEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.screen.voice.VoiceContract;
import com.morfeus.android.mfsdk.ui.screen.voice.VoicePresenterInjector;
import com.morfeus.android.mfsdk.ui.widget.audio.WaveformView;
import com.morfeus.android.mfsdk.ui.widget.bubble.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.MfLoginView;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.MfOTPView;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;
import com.morfeus.android.mfsdk.voice.AccessTokenThread;
import com.morfeus.android.mfsdk.voice.SpeechRecognitionAPI;
import com.morfeus.android.mfsdk.voice.VoiceRecorder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class VoiceFragment extends Fragment implements VoiceContract.View {
    private View mRootView;
    private static final String TAG = VoiceFragment.class.getSimpleName();
    public static final int MSG_ACCESS_TOKEN_SUCCESS = 0;
    public static final int MSG_ACCESS_TOKEN_FAIL = -1;
    public static final int MSG_TEXT_RECOGNIZED = 1;
    private static final int MSG_SET_AMPLITUDE = 2;
    private static final int MSG_CLEAR_VISUALIZER = 3;
    private static final int MSG_SHOW_HITS = 4;

    private static final long DELAY_INTERVAL = 30;

    private ImageView mIVRecordAudioImage;
    private TextView mTvVoiceMainHint;
    private WaveformView mWaveformView;
    private LinearLayout mLLCardContainer;

    private AccessToken mActionToken = null;
    private SpeechRecognitionAPI mSpeechAPI;
    private boolean isRecording = false;
    private long mPreviousPostTime = -1;
    private VoiceRecorder mVoiceRecorder;
    private boolean isFragmentShowing = false;
    private TemplateFactory mTemplateFactory;

    private VoicePresenterInjector mVoicePresenterInjector;
    private VoiceContract.Presenter mVoicePresenter;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_ACCESS_TOKEN_SUCCESS:

                    if (isFragmentShowing) {
                        Toast.makeText(getContext(), getResources()
                                        .getString(R.string.text_starting)
                                , Toast.LENGTH_SHORT).show();
                    }

                    mActionToken = (AccessToken) msg.obj;

                    if (mSpeechAPI == null)
                        mSpeechAPI = new SpeechRecognitionAPI();

                    mSpeechAPI.onStart(mSpeechAPIListener);
                    mSpeechAPI.setAccessToken(mActionToken);
                    startVoiceRecorder();
                    break;
                case MSG_TEXT_RECOGNIZED:
                    LogManager.d(TAG, "handleMessage: Text recognized");
                    String message = ((String) msg.obj);
                    //TODO need to process message
//                    if (!TextUtils.isEmpty(message))
//                        sendVoiceMessage(message);
                    break;
                case MSG_SET_AMPLITUDE:
                    double amplitude = (double) msg.obj;
                    if (amplitude > 1) {
                        float amplitRatio = (float) (amplitude * 0.1f / 200);
                        mWaveformView.updateAmplitude(amplitRatio);
                    }
                    break;
                case MSG_CLEAR_VISUALIZER:
                    if (mWaveformView != null)
                        mWaveformView.updateAmplitude(0f);
                    break;
                case MSG_SHOW_HITS:
                    String[] text = (String[]) msg.obj;
                    showVoiceHints(text);
                    sendMesasge(text);
                    break;
                case MSG_ACCESS_TOKEN_FAIL:
                    LogManager.e(TAG, "Error: fail to get access token!");
            }
        }
    };


    private final SpeechRecognitionAPI.Listener mSpeechAPIListener
            = new SpeechRecognitionAPI.Listener() {
        @Override
        public void onSpeechRecognized(String[] text, float confidence, boolean isFinal) {
            if (isFinal) {
                mVoiceCallback.onVoiceEnd();
            }
            mUIHandler.sendMessage(mUIHandler.obtainMessage(MSG_SHOW_HITS, text));

        }
    };

    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {
        @Override
        public void onVoiceStart() {
            isRecording = true;
            mSpeechAPI.startRecognizing(mVoiceRecorder.getSampleRate());
            LogManager.d(TAG, "onVoiceStart: clear amplitude");
            mUIHandler.sendEmptyMessage(MSG_CLEAR_VISUALIZER);
        }

        @Override
        public void onVoice(byte[] data, int size, double amplitude) {
            Log.d(TAG, "onVoice: ");
            if (!isRecording) {
                mUIHandler.sendEmptyMessage(MSG_CLEAR_VISUALIZER);
                return;
            }
            if (mPreviousPostTime == -1
                    || System.currentTimeMillis() > (mPreviousPostTime + DELAY_INTERVAL)) {
                mUIHandler.sendMessage(mUIHandler.obtainMessage(MSG_SET_AMPLITUDE, amplitude));
                LogManager.d(TAG, "onVoice: update amplitude : " + amplitude);
                mPreviousPostTime = System.currentTimeMillis();
            }
            mSpeechAPI.recognize(data, size);
        }

        @Override
        public void onVoiceEnd() {
            stopRecording();
        }
    };

    public VoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeView();
        registerEvents();
        retrieveAccessToken();
        mTemplateFactory = TemplateFactory.getInstance();
        loadVoicePresenter();
    }

    private void loadVoicePresenter() {
        mVoicePresenterInjector = VoicePresenterInjector.getInstance();
        mVoicePresenter = mVoicePresenterInjector.getPresenter(this);
    }

    private void retrieveAccessToken() {
        AccessTokenThread tokenThread = new AccessTokenThread(getContext(), mUIHandler);
        tokenThread.start();
    }

    private void makeView() {
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_voice_layout,
                null, false);
        mIVRecordAudioImage = (ImageView) mRootView.findViewById(R.id.iv_record_image);
        mTvVoiceMainHint = (TextView) mRootView.findViewById(R.id.tv_info_text);
        mWaveformView = (WaveformView) mRootView.findViewById(R.id.waveform_view);
        mLLCardContainer = (LinearLayout) mRootView.findViewById(R.id.ll_card_container);
    }

    private void registerEvents() {
        mIVRecordAudioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    stopRecording();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.text_starting)
                            , Toast.LENGTH_SHORT).show();
                    startVoiceRecorder();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;

    }

    public void startVoiceRecorder() {
        if (isRecording
                || mSpeechAPI == null
                || mActionToken == null
                || !isFragmentShowing) {
            return;
        }
        // If audio record permission not given return
        if (!isAudioPermissionGiven()) {
            Toast.makeText(getContext(), "Please give audio permission from setting",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }


    public void resetVoiceText() {
        mTvVoiceMainHint.setText(LanguageRepository.getInstance()
                .getText("What can i help you with?"));
    }

    public void stopRecording() {
        isRecording = false;
        if (mSpeechAPI != null)
            mSpeechAPI.finishRecognizing();

        if (mUIHandler != null)
            mUIHandler.sendEmptyMessage(MSG_CLEAR_VISUALIZER);

        if (mVoiceRecorder != null) {
            mVoiceRecorder.removeVoiceCallback();
            mVoiceRecorder.stop();
        }
    }

    private void showVoiceHints(String[] text) {
        if (text != null && text.length > 0) {
            mTvVoiceMainHint.setText(LanguageRepository.getInstance()
                    .getText(text[0]));
        }
    }

    private void sendMesasge(String[] text) {
        if (text != null && text.length > 0) {
            SendEvent event = new SendEvent(text[0]);
            EventBus.getDefault().post(event);
        }
    }

    private boolean isAudioPermissionGiven() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRecording();
    }


    @Override
    public void showMessages(List<TemplateModel> templateModels) {
        if (templateModels != null && templateModels.size() == 1) {
            showCard(templateModels.get(0), true);
        } else if (templateModels != null && templateModels.size() > 1) {
            for (int i = 0; i < templateModels.size(); i++) {
                if (i == 0) {
                    showCard(templateModels.get(i), true);
                } else {
                    showCard(templateModels.get(i), false);
                }
            }
        }
    }

    @Override
    public void showMessage(TemplateModel templateModel) {
        if (templateModel != null)
            showCard(templateModel, true);
    }

    @Override
    public void showLoginDialog(LoginModel loginModel) {
        FragmentManager fm = getActivity().getFragmentManager();
        MfLoginView mfLoginView = new MfLoginView();
        mfLoginView.setLoginModel(loginModel);
        mfLoginView.show(fm, "loginDialog");
    }

    @Override
    public void showOTPDialog(OTPModel otpModel) {
        FragmentManager fm = getActivity().getFragmentManager();
        MfOTPView mfOTPView = new MfOTPView();
        mfOTPView.setOTPModel(otpModel);
        mfOTPView.show(fm, "otpDialog");
    }

    private void showCard(TemplateModel templateModel, boolean removePreviousChildCard) {
        String templateID = templateModel.getTemplateID();
        int viewType = mTemplateFactory.getTemplateIntID(templateID);
        BaseView templateView = mTemplateFactory.createTemplate(viewType, getContext());
        TemplateViewHolder viewHolder = templateView.createViewHolder(templateView);
        viewHolder.setData(templateModel);
        if (removePreviousChildCard) {
            mLLCardContainer.removeAllViews();
        }
        mLLCardContainer.addView((View) templateView);
    }


    public boolean isFragmentShowing() {
        return isFragmentShowing;
    }

    public void setFragmentShowing(boolean fragmentShowing) {
        isFragmentShowing = fragmentShowing;
    }

    public void stopPresenter() {
        if (mVoicePresenter != null)
            mVoicePresenter.onStop();
    }

    public void startPresenter() {
        if (mVoicePresenter != null)
            mVoicePresenter.onStart();
    }

    @Override
    public void setPresenter(@NonNull VoiceContract.Presenter presenter) {
        mVoicePresenter = checkNotNull(presenter);
    }
}
