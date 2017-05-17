package com.morfeus.android.mfsdk.ui.widget.editor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.auth.oauth2.AccessToken;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.action.ActionManager;
import com.morfeus.android.mfsdk.ui.action.event.SendEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.widget.audio.WaveformView;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;
import com.morfeus.android.mfsdk.voice.AccessTokenThread;
import com.morfeus.android.mfsdk.voice.SpeechRecognitionAPI;
import com.morfeus.android.mfsdk.voice.VoiceRecorder;

import org.greenrobot.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkNotNull;

//TODO refactor
public class VoiceSubViewComponent extends LinearLayout implements Component {
    public static final int MSG_ACCESS_TOKEN_SUCCESS = 0;
    public static final int MSG_ACCESS_TOKEN_FAIL = -1;
    public static final int MSG_TEXT_RECOGNIZED = 1;
    private static final String TAG = VoiceSubViewComponent.class.getSimpleName();
    private static final int MSG_SET_AMPLITUDE = 2;
    private static final int MSG_CLEAR_VISUALIZER = 3;
    private static final int MSG_SHOW_HITS = 4;

    private static final int REPEAT_INTERVAL = 2;
    private static final long DELAY_INTERVAL = 50;

    private static WaveformView mWaveformView;

    private long mPreviousPostTime = -1;

    private boolean isViewAdded;
    private boolean isRecording = false;

    private ActionManager mActionManager;

    private Handler mHandler = new Handler();

    // TODO remove
    Runnable updateVisualizer = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, REPEAT_INTERVAL);
        }
    };

    private AccessToken mActionToken = null;
    private SubViewItemModel mModel;
    private VoiceRecorder mVoiceRecorder;
    private SpeechRecognitionAPI mSpeechAPI;

    private TextView mTvVoiceMainHint;
    private ImageView mTvVoiceMainHintTickImage;
    private RelativeLayout mRlVoiceHintContainer;

    private RelativeLayout mRlVoiceHintLayoutOne;
    private TextView mTvVoiceHintOne;

    private RelativeLayout mRlVoiceHintLayoutTwo;
    private TextView mTvVoiceHintTwo;

    private RelativeLayout mRlVoiceHintLayoutThree;
    private TextView mTvVoiceHintThree;

    private LinearLayout mRootLayout;
    private ImageView mIVRecordAudioImage;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_ACCESS_TOKEN_SUCCESS:
                    Toast.makeText(getContext(), getResources().getString(R.string.text_starting)
                            , Toast.LENGTH_SHORT).show();

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
                    if (!TextUtils.isEmpty(message))
                        sendVoiceMessage(message);
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
                    break;
                case MSG_ACCESS_TOKEN_FAIL:
                    LogManager.e(TAG, "Error: fail to get access token!");
            }
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
    private final SpeechRecognitionAPI.Listener mSpeechAPIListener
            = new SpeechRecognitionAPI.Listener() {
        @Override
        public void onSpeechRecognized(String[] text, float confidence, boolean isFinal) {
            // if confidence level is < 0.90 then display suggestion
            if (isFinal) {
                mVoiceCallback.onVoiceEnd();
            }

            if (confidence >= 0.9f || text.length == 1) {
                mUIHandler.sendMessage(mUIHandler.obtainMessage(MSG_TEXT_RECOGNIZED, text[0]));
            } else {
                mUIHandler.sendMessage(mUIHandler.obtainMessage(MSG_SHOW_HITS, text));
            }

        }
    };

    public VoiceSubViewComponent(Context context,
                                 @NonNull BaseModel baseModel,
                                 @NonNull ActionManager actionManager) {
        super(context);
        mModel = (SubViewItemModel) checkNotNull(baseModel);
        mActionManager = checkNotNull(actionManager);
    }


    public VoiceSubViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        // No-op
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
        switch (text.length) {
            case 2:
                mTvVoiceHintOne.setText(text[0]);
                mTvVoiceHintTwo.setText(text[1]);
                mRlVoiceHintLayoutThree.setVisibility(View.GONE);
                break;
            case 3:
                mTvVoiceHintOne.setText(text[0]);
                mTvVoiceHintTwo.setText(text[1]);
                mTvVoiceHintThree.setText(text[2]);
                mRlVoiceHintLayoutThree.setVisibility(View.VISIBLE);
                break;
            default:
                mTvVoiceHintOne.setText(text[0]);
                mTvVoiceHintTwo.setText(text[1]);
                mTvVoiceHintThree.setText(text[2]);
                mRlVoiceHintLayoutThree.setVisibility(View.VISIBLE);
                break;
        }
        mTvVoiceMainHint.setText(LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_did_you_mean)));
        mTvVoiceMainHint.setTypeface(null, Typeface.BOLD);
        mRlVoiceHintContainer.setVisibility(View.VISIBLE);
        mTvVoiceMainHintTickImage.setVisibility(View.VISIBLE);
        mTvVoiceMainHintTickImage.setImageResource(R.drawable.ic_clear_black_24dp);
    }

    @Override
    public void setStyle(Style style) {
        // No-op
    }

    @Override
    public void setAction(@Action String action) {
        // No-op
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
    public void setData(BaseModel baseModel) {
        mModel = (SubViewItemModel) checkNotNull(baseModel);
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
        try {
            if (!isViewAdded) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mRootLayout = new LinearLayout(getContext());
                layoutParams.gravity = Gravity.CENTER;
                mRootLayout.setOrientation(LinearLayout.HORIZONTAL);
                mRootLayout.setWeightSum(1.0f);
                mRootLayout.setLayoutParams(layoutParams);
                mRootLayout.setId(R.id.component_subview_voice);
                setBgColor();

                setAudioLayout();
                retrieveAccessToken();
                isViewAdded = true;
            }

        } catch (Exception e) {
            Log.e(TAG, "initView: ", e);
        }
    }

    private void setBgColor() {
        SubViewStyle subViewStyle = (SubViewStyle) mModel.getStyle();
        if (subViewStyle != null) {
            String textColor = subViewStyle.getBackgroundColor();
            if (textColor != null && textColor.length() > 0) {
                int colorId = Color.parseColor(textColor);
                setBackgroundColor(colorId);
            }
        }
    }

    private void retrieveAccessToken() {
        AccessTokenThread tokenThread = new AccessTokenThread(getContext(), mUIHandler);
        tokenThread.start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mSpeechAPI.finishRecognizing();
    }

    private void setAudioLayout() {
        View voiceView
                = LayoutInflater.from(getContext()).inflate(R.layout.voice_sub_view_layout, null);

        mIVRecordAudioImage = (ImageView) voiceView.findViewById(R.id.iv_record_image);
        mTvVoiceMainHint = (TextView) voiceView.findViewById(R.id.tv_info_text);
        mWaveformView = (WaveformView) voiceView.findViewById(R.id.waveform_view);
        mTvVoiceMainHintTickImage = (ImageView) voiceView.findViewById(R.id.iv_tick_image);

        mRlVoiceHintContainer = (RelativeLayout) voiceView.findViewById(R.id.rl_hint_container);

        mRlVoiceHintLayoutOne = (RelativeLayout) voiceView.findViewById(R.id.rl_hint_one_layout);
        mTvVoiceHintOne = (TextView) voiceView.findViewById(R.id.tv_info_text_hint_one);
        mRlVoiceHintLayoutTwo = (RelativeLayout) voiceView.findViewById(R.id.rl_hint_two_layout);
        mTvVoiceHintTwo = (TextView) voiceView.findViewById(R.id.tv_info_text_hint_two);
        mRlVoiceHintLayoutThree = (RelativeLayout) voiceView.findViewById(R.id.rl_hint_three_layout);
        mTvVoiceHintThree = (TextView) voiceView.findViewById(R.id.tv_info_text_hint_three);

        mTvVoiceMainHint.setText(LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_what_can_i_help_you_with)));

        mHandler.removeCallbacks(updateVisualizer);

        mIVRecordAudioImage.setOnClickListener(new OnClickListener() {
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


        mTvVoiceMainHintTickImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRlVoiceHintContainer.getVisibility() == View.VISIBLE) {
                    resetHintUI();
                } else {
                    sendMessageAndRemoveVoiceHint(mTvVoiceMainHint.getText().toString().trim());
                }

            }
        });
        mRlVoiceHintLayoutOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageAndRemoveVoiceHint(mTvVoiceHintOne.getText().toString().trim());
            }
        });
        mRlVoiceHintLayoutTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageAndRemoveVoiceHint(mTvVoiceHintTwo.getText().toString().trim());
            }
        });
        mRlVoiceHintLayoutThree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageAndRemoveVoiceHint(mTvVoiceHintThree.getText().toString().trim());
            }
        });

        mRootLayout.addView(voiceView);
        addView(mRootLayout);
    }

    private void resetHintUI() {
        mRlVoiceHintContainer.setVisibility(View.GONE);
        mTvVoiceMainHint.setTypeface(null, Typeface.NORMAL);
        mTvVoiceMainHintTickImage.setVisibility(View.GONE);
        mTvVoiceMainHint.setText(LanguageRepository.getInstance()
                .getText(getResources().getString(R.string.text_what_can_i_help_you_with)));
    }

    private void sendMessageAndRemoveVoiceHint(String voiceTextMsg) {
        resetHintUI();
        sendVoiceMessage(voiceTextMsg);
    }

    private void sendVoiceMessage(String voiceTextMsg) {
        SendEvent event = new SendEvent(voiceTextMsg);
        event.setTextToSpeechMsg(true);
        EventBus.getDefault().post(event);
    }

    public void startVoiceRecorder() {
        if (isRecording || mSpeechAPI == null || mActionToken == null) {
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

    private boolean isAudioPermissionGiven() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public Component inflate(Context context, ViewGroup viewGroup, boolean attach) {
        return null;
    }

}
