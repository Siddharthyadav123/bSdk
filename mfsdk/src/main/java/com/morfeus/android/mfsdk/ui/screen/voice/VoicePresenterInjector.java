package com.morfeus.android.mfsdk.ui.screen.voice;

import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManagerImpl;
import com.morfeus.android.mfsdk.ui.screen.BaseView;
import com.morfeus.android.mfsdk.ui.screen.mapper.MessageModelMapper;
import com.morfeus.android.mfsdk.ui.screen.parser.TemplateModelParser;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModelFactory;

public class VoicePresenterInjector {
    private static VoicePresenterInjector INSTANCE;

    private VoicePresenterInjector() {
    }

    public static VoicePresenterInjector getInstance() {
        if (INSTANCE == null)
            INSTANCE = new VoicePresenterInjector();
        return INSTANCE;
    }

    public VoiceContract.Presenter getPresenter(BaseView baseView) {
        ConfigManager configManager = ConfigManagerImpl.getInstance();
        TemplateModelFactory templateModelFactory = TemplateModelFactory.getInstance();
        MessageModelMapper modelMapper = MessageModelMapper.createInstance(templateModelFactory);
        TemplateModelParser templateParser = TemplateModelParser.getInstance(templateModelFactory);
        return new VoicePresenter(
                configManager,
                (VoiceContract.View) baseView,
                templateParser);
    }

    public void setVoicePresenter(VoicePresenter voicePresenter) {
        // No-op
    }
}