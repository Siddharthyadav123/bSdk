package com.morfeus.android.mfsdk.ui.widget.editor;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringDef;
import android.view.ViewGroup;

import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Custom mf-editor component.
 */
public interface Component {

    String ACTION_GALLERY = "galleryAction";
    String ACTION_IMAGE_PICKER = "imagePickerAction";
    String ACTION_CAMERA = "cameraAction";
    String ACTION_OPEN_MENU_VIEW = "openMenuViewAction";
    String ACTION_OPEN_SMILEY_VIEW = "openSmileyViewAction";
    String ACTION_OPEN_SETTING_VIEW = "openSettingViewAction";
    String ACTION_OPEN_FEEDBACK_VIEW = "openFeedbackViewAction";
    // Primitive component
    String COMPONENT_IMAGE_BUTTON = "buttonComponent";
    String COMPONENT_IMAGE = "imageComponent";
    String COMPONENT_SUGGESTION_BUTTON = "suggestionButtonComponent";
    // Composite component
    String COMPONENT_TABLAYOUT = "toolbarview";
    String COMPONENT_SUBVIEW = "subview";
    String COMPONENT_EDITTEXT = "textinput";
    String COMPONENT_INPUT_VIEW = "inputview";
    String COMPONENT_INPUT_VIEW_SEND_BUTTON = "sendButton";
    String COMPONENT_SUGGESTION_VIEW = "shortcutview";
    //    String COMPONENT_TOOLBAR_VIEW = "toolbarViewComponent";
    String COMPONENT_SUBVIEW_PAGER = "subViewPagerComponent";
    String COMPONENT_SUBVIEW_ITEM_MENU = "menuview";
    String COMPONENT_SUBVIEW_ITEM_SMILY = "smileyview";
    String COMPONENT_SUBVIEW_ITEM_SETTING = "settingsview";
    String COMPONENT_SUBVIEW_ITEM_FEEDBACK = "feedbackview";
    String COMPONENT_SUBVIEW_ITEM_VOICE = "voiceview";
    // Main container component
    String COMPONENT_MF_EDITOR = "mfEditorComponent";

    void setStyle(Style style);

    void setAction(@Action String action);

    void setComponentId(@IdRes int id);

    BaseModel getData();

    void setData(BaseModel baseModel);

    void show(boolean show);

    ;

    void initView() throws ComponentException;

    Component inflate(Context context, ViewGroup viewGroup, boolean attach);

    /**
     * List of Action can be performed by Mf-Editor.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            ACTION_GALLERY,
            ACTION_IMAGE_PICKER,
            ACTION_CAMERA,
            ACTION_OPEN_MENU_VIEW,
            ACTION_OPEN_SMILEY_VIEW,
            ACTION_OPEN_SETTING_VIEW,
            ACTION_OPEN_FEEDBACK_VIEW
    })
    @interface Action {
    }

    /**
     * List of component used inside Mf-Editor.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            COMPONENT_MF_EDITOR,
            COMPONENT_IMAGE_BUTTON,
            COMPONENT_IMAGE,
            COMPONENT_SUBVIEW,
            COMPONENT_SUGGESTION_BUTTON,
            COMPONENT_EDITTEXT,
            COMPONENT_INPUT_VIEW,
            COMPONENT_SUGGESTION_VIEW,
//            COMPONENT_TOOLBAR_VIEW,
            COMPONENT_SUBVIEW_PAGER,
            COMPONENT_SUBVIEW_ITEM_MENU,
            COMPONENT_SUBVIEW_ITEM_SMILY,
            COMPONENT_SUBVIEW_ITEM_SETTING,
            COMPONENT_SUBVIEW_ITEM_FEEDBACK,
            COMPONENT_TABLAYOUT,
            COMPONENT_SUBVIEW_ITEM_VOICE
    })
    @interface Type {
    }
}
