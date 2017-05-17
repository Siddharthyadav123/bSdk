package com.morfeus.android.mfsdk.ui;

import android.content.Context;

import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.widget.bubble.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

/**
 * Interface represent the interface to the MfSDK UI module.
 */
public interface MfSdkUI {

    /**
     * Initialization of {@link MfSdkUI} loads MFUIConfig.json from Assets and lunches
     * the default screen({@link ChatActivity}) with
     * loaded config.
     *
     * @throws MfSdkUIInitializationException thrown when {@link MfSdkUI} initialization fails
     */
    void init() throws MfSdkUIInitializationException;

    /**
     * Opens screen associated with given screen name.
     *
     * @param screenName
     * @param ctx
     * @param language: User can pass null if multi-language not supported
     * @throws ScreenNotFoundException
     */
    void showScreen(String screenName, Context ctx, String language) throws ScreenNotFoundException;

    /**
     * Register new template with unique ID.
     *
     * @param templateView Custom {@link TemplateView} to register.
     * @param templateID   Unique template ID to associate with given {@link TemplateView}.
     */
    void registerTemplate(String templateID, BaseView templateView);

    /**
     * Register {@link TemplateModel} for given Template Id
     *
     * @param templateID    Template Id to register
     * @param templateModel Template model to register
     */
    void registerTemplateModel(String templateID, TemplateModel templateModel);
}
