package com.morfeus.android.mfsdk;

import com.google.common.eventbus.EventBus;
import com.morfeus.android.mfsdk.messenger.source.entity.MfMsgModel;
import com.morfeus.android.mfsdk.ui.action.event.MessageReceiveEvent;
import com.morfeus.android.mfsdk.ui.action.event.login.LoginDialogEvent;
import com.morfeus.android.mfsdk.ui.action.event.otp.OTPDialogEvent;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.exception.ConfigLoadException;
import com.morfeus.android.mfsdk.ui.screen.parser.TemplateModelParser;
import com.morfeus.android.mfsdk.ui.screen.voice.VoiceContract;
import com.morfeus.android.mfsdk.ui.screen.voice.VoicePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TemplateModelParser.class})
public class VoicePresenterTest {

    private VoiceContract.Presenter mVoicePresenter;
    private ConfigManager mMockConfigManager;
    private VoiceContract.View mMockVoiceView;
    private TemplateModelParser mMockModelParser;

    @Before
    public void setUpVoicePresenter() throws ConfigLoadException {
        mMockConfigManager = PowerMockito.mock(ConfigManager.class);
        mMockVoiceView = mock(VoiceContract.View.class);
        mMockModelParser = PowerMockito.mock(TemplateModelParser.class);

        mVoicePresenter = new VoicePresenter(
                mMockConfigManager,
                mMockVoiceView,
                mMockModelParser);
    }

    @Test
    public void testOTPDialog() {
        EventBus eventBus = PowerMockito.mock(EventBus.class);
        OTPDialogEvent otpDialogEvent = new OTPDialogEvent(true);
        eventBus.post(otpDialogEvent);
        verify(mMockVoiceView, times(1)).showOTPDialog(null);
    }

    @Test
    public void testLoginDialog() {
        EventBus eventBus = PowerMockito.mock(EventBus.class);
        eventBus.post(new LoginDialogEvent(true, "Login"));
    }

    @Test
    public void testSingleMessage() {
        MfMsgModel mfMsgModel = PowerMockito.mock(MfMsgModel.class);
        EventBus eventBus = PowerMockito.mock(EventBus.class);
        eventBus.post(new MessageReceiveEvent(mfMsgModel));
    }


}
