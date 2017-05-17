package com.morfeus.android.mfsdk.voice;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;

import com.morfeus.android.mfsdk.log.LogManager;

import java.util.Locale;

public class TextToSpeechManager implements
        TextToSpeech.OnInitListener {
    private static final String TAG = TextToSpeechManager.class.getSimpleName();
    private Context mContext;
    private TextToSpeech mTextToSpeech;

    public TextToSpeechManager(Context context) {
        this.mContext = context;
        initTextToSpeech();
    }

    private void initTextToSpeech() {
        LogManager.i(TAG, "T2S: INITIALIZED");
        mTextToSpeech = new TextToSpeech(mContext, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LogManager.i(TAG, "T2S: This Language is not supported");
            }
        } else {
            // Initialization failed.
            LogManager.i(TAG, "T2S: 404 Could not initialize TextToSpeech.");
            // May be its not installed so we prompt it to be installed
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            mContext.startActivity(installIntent);
        }
    }

    public void speakText(String text) {
        LogManager.i(TAG, "T2S: TEXT =" + text);
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    /**
     * Call this method from onDestory of actvity
     */
    public void stop() {
        if (mTextToSpeech != null) {
            LogManager.i(TAG, "T2S: STOPPED");
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }
}
