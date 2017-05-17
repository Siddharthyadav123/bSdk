package com.morfeus.android.mfsdk.ui.widget.editor.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.common.collect.Lists;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.action.ActionManagerImpl;
import com.morfeus.android.mfsdk.ui.widget.editor.Component;
import com.morfeus.android.mfsdk.ui.widget.editor.EditTextComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.FeedbackSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.ImageButtonComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.InputViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.MFEditorViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.MenuSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SuggestionViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.TabLayoutComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.VoiceSubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactoryProvider;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.EditTextModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.ImageButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.InputViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.MFEditorViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewItemModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SubViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionButtonModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.SuggestionViewModel;
import com.morfeus.android.mfsdk.ui.widget.editor.model.TabLayoutModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.EditTextStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.ImageButtonStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.InputViewStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.Style;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SubViewStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.SuggestionViewStyle;
import com.morfeus.android.mfsdk.ui.widget.editor.style.ToolbarViewStyle;
import com.morfeus.android.mfsdk.ui.widget.header.ActionbarHeaderView;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.header.style.ActionbarStyle;
import com.morfeus.android.mfsdk.ui.widget.header.style.TitleStyle;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private static final String LEFT_BUTTON = "leftbutton";
    private static final String RIGHT_BUTTON_1 = "rightbutton1";
    private static final String RIGHT_BUTTON_2 = "rightbutton2";
    private static final String TOOLBAR_ITEM_MENU_VIEW = "menuview";
    private static final String TOOLBAR_ITEM_TEXT_VIEW = "textview";
    private static final String TOOLBAR_ITEM_EMOJI_VIEW = "smileyview";
    private static final String TOOLBAR_ITEM_VOICE_VIEW = "voiceview";
    private static final String TOOLBAR_ITEM_LIKE_VIEW = "feedbackview";
    private LinearLayout lly;
    private ActionbarHeaderView actionbarHeaderView;
    private BaseModel actionbarModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lly = (LinearLayout) findViewById(R.id.activity_test);
        final MFEditorViewComponent mfec;
        try {
            // MFEditorComponent
//            mfec = getMFEditorViewComponent(lly);
//            lly.addView(mfec);
//
//            final SubViewComponent subViewComponent = (SubViewComponent) mfec.findViewById(R.id.component_subview);
//            subViewComponent.setVisibility(View.GONE);
//
//            TabLayoutComponent tabLayoutComponent = (TabLayoutComponent) mfec.findViewById(R.id.component_tablayout);
//            tabLayoutComponent.registerTabListener(subViewComponent.getTabChangeListener());
//
//
//            ImageButtonComponent imageButtonComponent = (ImageButtonComponent) mfec.findViewById(R.id.component_edittext_leftbutton);
//            imageButtonComponent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (subViewComponent.getVisibility() == View.VISIBLE)
//                        subViewComponent.setVisibility(View.GONE);
//                    else
//                        subViewComponent.setVisibility(View.VISIBLE);
//                }
//            });

            // Actionbar view
//            ActionbarHeaderView ahv = getActionbarHeaderView();
//            ahv.initView();
//            lly.addView(ahv);

//            // VoiceSubViewComponent
//            VoiceSubViewComponent vsc = getVoiceSubViewComponent();
//            lly.addView(vsc);
//            // MenuSubViewComponent
//            MenuSubViewComponent msc = getMenuSubViewComponent();
//            lly.addView(msc);

//            // FeedbackSubViewComponent
//            FeedbackSubViewComponent fsc = getFeedbackSubViewComponent();
//            lly.addView(fsc);

//            // SuggestionViewComponent
//            SuggestionViewComponent svc = getSuggestionViewComponent();
//            lly.addView(svc);

            // TabLayoutComponent
//            TabLayoutComponent tlc = getTabLayoutComponent();
//            lly.addView(tlc);
//            // Inputview
//            InputViewComponent ivc = getInputViewComponent(lly);
//            lly.addView(ivc);

//            // Toolbar
//            ToolbarViewComponent tvc = getToolbarViewComponent(lly);
//            lly.addView(tvc);
//
//            // Subview
//            SubViewComponent svc = getSubViewComponent(lly);
//            lly.addView(svc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // EditTextModel
//        EditTextComponent etc = getEditTextComponent(lly);
//        lly.addView(etc);

//        // ImageButtonComponent
//        ImageButtonComponent ibc = getImageButtonComponent(lly);
//        lly.addView(ibc);

    }

    private void testConfigLoading() {
//        MfSdk mfSdk = MfSdkKit.getInstance(this);
//        try {
//            mfSdk.init();
//        } catch (MfSdkInitializationException e) {
//            e.printStackTrace();
//        }
    }

    private MFEditorViewComponent getMFEditorViewComponent(LinearLayout lly)
            throws ComponentException {
        MFEditorViewComponent mfec = new MFEditorViewComponent(
                this,
                createMFEditorViewModel(),
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                ActionManagerImpl.getInstance()
        );

        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 250,
                getResources().getDisplayMetrics()
        );

        mfec.setComponentId(R.id.component_mf_editor);
        mfec.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

//        mfec.setOrientation(LinearLayout.VERTICAL);
        mfec.initView();

        return mfec;
    }

    private SuggestionViewComponent getSuggestionViewComponent() throws ComponentException {
        SuggestionViewComponent svc = new SuggestionViewComponent(
                this,
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                createSuggestionViewStyle(),
                createSuggestionViewModel(),
                ActionManagerImpl.getInstance()
        );
        svc.setId(R.id.component_suggestion_view);
        svc.initView();
        svc.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return svc;
    }

    private SuggestionViewModel createSuggestionViewModel() {
        SuggestionViewModel suggestionViewModel = new SuggestionViewModel(
                Component.COMPONENT_SUGGESTION_VIEW
        );
        suggestionViewModel.setStyle(createSuggestionViewStyle());
        suggestionViewModel.setSubComponentModels(createSuggestionButtonModels());
        return suggestionViewModel;
    }

    private List<BaseModel> createSuggestionButtonModels() {
        List<BaseModel> baseModels = new ArrayList<>();

        SuggestionButtonModel suggestionButtonModel1 = new SuggestionButtonModel("accountSummary");
        suggestionButtonModel1.setAction("sendmessage");
        suggestionButtonModel1.setLabel("Account Summary");
        suggestionButtonModel1.setPayload("Account Summary");


        SuggestionButtonModel suggestionButtonModel2 = new SuggestionButtonModel("recharge");
        suggestionButtonModel2.setLabel("Recharge");
        suggestionButtonModel2.setAction("sendMessage");
        suggestionButtonModel2.setPayload("Account Summary");

        SuggestionButtonModel suggestionButtonModel3 = new SuggestionButtonModel("accountSummary");
        suggestionButtonModel3.setAction("sendmessage");
        suggestionButtonModel3.setLabel("Account Summary");
        suggestionButtonModel3.setPayload("Account Summary");


        SuggestionButtonModel suggestionButtonModel4 = new SuggestionButtonModel("recharge");
        suggestionButtonModel4.setLabel("Recharge");
        suggestionButtonModel4.setAction("sendMessage");
        suggestionButtonModel4.setPayload("Account Summary");


        baseModels.add(suggestionButtonModel1);
        baseModels.add(suggestionButtonModel2);
        baseModels.add(suggestionButtonModel3);
        baseModels.add(suggestionButtonModel4);
        return baseModels;
    }

    private Style createSuggestionViewStyle() {
        SuggestionViewStyle suggestionViewStyle = new SuggestionViewStyle(createSuggestionButtonStyle());
//        suggestionViewStyle.setBackgroundColor("#432442");
        suggestionViewStyle.setHeight(35);
        return suggestionViewStyle;
    }

    private SuggestionViewStyle.ButtonStyle createSuggestionButtonStyle() {
        SuggestionViewStyle.ButtonStyle buttonStyle = new SuggestionViewStyle.ButtonStyle();
        buttonStyle.setBackgroundColor("#887888");
        buttonStyle.setLabelColor("#333333");
        return buttonStyle;
    }

    private BaseModel createMFEditorViewModel() {
        MFEditorViewModel mfEditorViewModel = new MFEditorViewModel(
                "mfEditor",
                createMFEditorSubModel());
        return mfEditorViewModel;
    }

    private List<BaseModel> createMFEditorSubModel() {
        List<BaseModel> baseModels = new ArrayList<>();
        baseModels.add(createSuggestionViewModel());
        baseModels.add(createInputViewModel());
        baseModels.add(createTabLayoutModel());
        baseModels.add(createSubViewModel());
        return baseModels;
    }

    public void addComponentView(Component component) {
        component.inflate(this, lly, false);
        lly.addView((View) component);
    }

    private InputViewComponent getInputViewComponent(LinearLayout rly) throws Exception {
        InputViewStyle inputViewStyle = new InputViewStyle();
        inputViewStyle.setBackgroundColor("#bebebe");

        InputViewComponent ivc = new InputViewComponent(
                this,
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                inputViewStyle,
                createInputViewModel(),
                ActionManagerImpl.getInstance()
        );

        ivc.setComponentId(R.id.component_inputview);
        ivc.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        ivc.initView();
//        ivc.inflate(this, rly, false);
        return ivc;
    }

    private ImageButtonComponent getImageButtonComponent(LinearLayout rly) {
        ImageButtonComponent ibc = new ImageButtonComponent(
                this,
                createModel(LEFT_BUTTON),
                new ImageButtonStyle(),
                Component.ACTION_CAMERA,
                ActionManagerImpl.getInstance()
        );

        ibc.initView();
        ibc.setId(R.id.ibc);
        ibc.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
//        ibc.inflate(this,rly, false);
        return ibc;
    }

    private TabLayoutComponent getTabLayoutComponent() throws ComponentException {
        TabLayoutComponent tlc = new TabLayoutComponent(
                this,
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                createToolbarViewStyle(),
                createTabLayoutModel(),
                ActionManagerImpl.getInstance()
        );
        tlc.setComponentId(R.id.component_tablayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tlc.setLayoutParams(layoutParams);
        tlc.initView();
        return tlc;
    }

    private SubViewComponent getSubViewComponent(LinearLayout rly) throws ComponentException {
        SubViewComponent svc = new SubViewComponent(
                this,
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                createSubViewModel(),
                ActionManagerImpl.getInstance()
        );

        svc.setComponentId(R.id.component_subview);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        svc.setLayoutParams(layoutParams);
        svc.initView();
        return svc;
    }

    private BaseModel createSubViewModel() {
        SubViewItemModel.Content content1 = new SubViewItemModel.Content(
                "ic_funds",
                "abc",
                Component.ACTION_CAMERA,
                "payload");

        SubViewItemModel.Content content2 = new SubViewItemModel.Content(
                "ic_reset_pin",
                "abc",
                Component.ACTION_CAMERA,
                "payload");

        SubViewItemModel emojiSubViewItemModel =
                new SubViewItemModel(Component.COMPONENT_SUBVIEW_ITEM_SMILY);
        emojiSubViewItemModel.setContents(Lists.newArrayList(
                content1,
                content2));

        SubViewItemModel feedbacksubViewItemModel =
                new SubViewItemModel(Component.COMPONENT_SUBVIEW_ITEM_FEEDBACK);
        feedbacksubViewItemModel.setContents(createFeedbackContent());
        SubViewStyle style = new SubViewStyle();
//        style.setBackgroundColor("#765544");

        SubViewItemModel menuSubViewItemModel =
                new SubViewItemModel(Component.COMPONENT_SUBVIEW_ITEM_MENU);
        menuSubViewItemModel.setContents(createMenuContent());

        return new SubViewModel(
                Component.COMPONENT_SUBVIEW,
                style,
                Lists.<BaseModel>newArrayList(
                        menuSubViewItemModel,
                        emojiSubViewItemModel,
                        createVoiceSubViewModel(),
                        feedbacksubViewItemModel));
    }

    private BaseModel createTabLayoutModel() {
        ImageButtonModel emojiImageButtonModel =
                new ImageButtonModel(TOOLBAR_ITEM_EMOJI_VIEW);
        emojiImageButtonModel.setImage("ic_emoji_off");

        ImageButtonModel menuImageButtonModel =
                new ImageButtonModel(TOOLBAR_ITEM_MENU_VIEW);
        menuImageButtonModel.setImage("ic_menu_off");

        ImageButtonModel textImageButtonModel =
                new ImageButtonModel(TOOLBAR_ITEM_TEXT_VIEW);
        textImageButtonModel.setImage("ic_text_off");

        ImageButtonModel likeImageButtonModel =
                new ImageButtonModel(TOOLBAR_ITEM_LIKE_VIEW);
        likeImageButtonModel.setImage("ic_like_off");
        likeImageButtonModel.setAction(Component.ACTION_CAMERA);
        ImageButtonModel voiceImageButtonModel =
                new ImageButtonModel(TOOLBAR_ITEM_VOICE_VIEW);
        voiceImageButtonModel.setImage("ic_record_off");

        List<BaseModel> baseModels = new ArrayList<>();
        baseModels.add(menuImageButtonModel);
        baseModels.add(emojiImageButtonModel);
        baseModels.add(textImageButtonModel);
        baseModels.add(voiceImageButtonModel);
        baseModels.add(likeImageButtonModel);

        TabLayoutModel tabLayoutModel =
                new TabLayoutModel(
                        Component.COMPONENT_TABLAYOUT,
                        "default",
                        createToolbarViewStyle(),
                        baseModels);
        return tabLayoutModel;
    }

    private Style createToolbarViewStyle() {
        return new ToolbarViewStyle("#bebebe", null);
    }

    @Nullable
    private EditTextComponent getEditTextComponent(LinearLayout rly) {
        EditTextComponent etc = null;
        try {
            etc = new EditTextComponent(
                    this,
                    ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                    createEditTextStyle(),
                    createEditTextModel(),
                    Component.ACTION_CAMERA,
                    ActionManagerImpl.getInstance()
            );
            etc.setId(R.id.etc);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            etc.setLayoutParams(layoutParams);
            etc.initView();
            etc.inflate(this, rly, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etc;
    }

    private BaseModel createInputViewModel() {
        ImageButtonModel imageButtonModel = new ImageButtonModel(Component.COMPONENT_INPUT_VIEW_SEND_BUTTON);
        imageButtonModel.setImage("ic_send");

        List<BaseModel> subComponentModels = new ArrayList<>();
        subComponentModels.add(imageButtonModel);
        subComponentModels.add(createEditTextModel());

        InputViewModel inputViewModel = new InputViewModel(
                Component.COMPONENT_INPUT_VIEW,
                createStyle(),
                subComponentModels);
        return inputViewModel;
    }

    private BaseModel createEditTextModel() {
        EditTextModel editTextModel = new EditTextModel(
                Component.COMPONENT_EDITTEXT,
                createStyle(),
                createSubComponentModels(),
                "");
        return editTextModel;
    }

    private List<BaseModel> createSubComponentModels() {

        List<BaseModel> baseModels = new ArrayList<>();
        baseModels.add(createModel(LEFT_BUTTON));
//        baseModels.add(createModel(RIGHT_BUTTON_1));
//        baseModels.add(createModel(RIGHT_BUTTON_2));
        return baseModels;
    }

    private Style createEditTextStyle() {
        EditTextStyle editTextStyle = new EditTextStyle();
        editTextStyle.setBackgroundColor("#bebebe");
        return editTextStyle;

    }

    private BaseModel createModel(String position) {
        ImageButtonModel imageButtonModel;
        switch (position) {
            case LEFT_BUTTON:
                imageButtonModel = new ImageButtonModel(LEFT_BUTTON);
                imageButtonModel.setImage("ic_menu_off");
                break;
            case RIGHT_BUTTON_1:
                imageButtonModel = new ImageButtonModel(RIGHT_BUTTON_1);
                imageButtonModel.setImage("ic_emoji_off");
                break;
            case RIGHT_BUTTON_2:
                imageButtonModel = new ImageButtonModel(RIGHT_BUTTON_2);
                imageButtonModel.setImage("ic_like_off");
                break;
            default:
                imageButtonModel = new ImageButtonModel(LEFT_BUTTON);
                imageButtonModel.setImage("ic_default");
                break;

        }
        return imageButtonModel;
    }

    private Style createStyle() {
        ImageButtonStyle style = new ImageButtonStyle();
        style.setBackgroundImage("ic_like_off");
        return style;
    }

    public FeedbackSubViewComponent getFeedbackSubViewComponent() throws ComponentException {
        FeedbackSubViewComponent fsv = new FeedbackSubViewComponent(
                this,
                createFeedbackModel(),
                ActionManagerImpl.getInstance());
        fsv.initView();
        return fsv;
    }

    private BaseModel createFeedbackModel() {
        SubViewItemModel feedbackItemModel = new SubViewItemModel("feedbackview");
        feedbackItemModel.setContents(createFeedbackContent());
        return feedbackItemModel;
    }

    private List<SubViewItemModel.Content> createFeedbackContent() {
        List<SubViewItemModel.Content> contents = new ArrayList<>();
        SubViewItemModel.Content likeContent = new SubViewItemModel.Content(
                "ic_like",
                "like",
                "sendMessage",
                "Balance"
        );

        SubViewItemModel.Content dislikeContent = new SubViewItemModel.Content(
                "ic_dislike",
                "like",
                "sendMessage",
                "Balance"
        );
        contents.add(likeContent);
        contents.add(dislikeContent);
        return contents;
    }

    public MenuSubViewComponent getMenuSubViewComponent() throws ComponentException {
        MenuSubViewComponent msc = new MenuSubViewComponent(
                this,
                createMenuSubViewModel(),
                ActionManagerImpl.getInstance());
        msc.initView();
        return msc;
    }

    private BaseModel createMenuSubViewModel() {
        SubViewItemModel menuItemModel = new SubViewItemModel("menuview");
        menuItemModel.setContents(createMenuContent());
        return menuItemModel;
    }

    private List<SubViewItemModel.Content> createMenuContent() {
        List<SubViewItemModel.Content> contents = new ArrayList<>();
        SubViewItemModel.Content acBalanceMenuContent = new SubViewItemModel.Content(
                "ic_menu_acbalance",
                "like",
                "sendMessage",
                "Balance"
        );

        SubViewItemModel.Content billpayMenuContent = new SubViewItemModel.Content(
                "ic_menu_billpay",
                "like",
                "sendMessage",
                "Balance"
        );

        SubViewItemModel.Content dollarMenuContent = new SubViewItemModel.Content(
                "ic_menu_dollar_inactive",
                "like",
                "sendMessage",
                "Balance"
        );

        SubViewItemModel.Content rechargeMenuContent = new SubViewItemModel.Content(
                "ic_menu_recharge",
                "like",
                "sendMessage",
                "Balance"
        );

        SubViewItemModel.Content resetPinMenuContent = new SubViewItemModel.Content(
                "ic_menu_reset_pin",
                "like",
                "sendMessage",
                "Balance"
        );
        contents.add(acBalanceMenuContent);
        contents.add(billpayMenuContent);
        contents.add(dollarMenuContent);
        contents.add(rechargeMenuContent);
        contents.add(resetPinMenuContent);
        return contents;
    }

    public VoiceSubViewComponent getVoiceSubViewComponent() throws ComponentException {
        VoiceSubViewComponent vsc = new VoiceSubViewComponent(
                this,
                createVoiceSubViewModel(),
                ActionManagerImpl.getInstance());
        vsc.initView();
        return vsc;
    }

    private BaseModel createVoiceSubViewModel() {
        SubViewItemModel voiceItemModel = new SubViewItemModel("voiceview");
        voiceItemModel.setContents(createVoiceContent());
        return voiceItemModel;
    }

    private List<SubViewItemModel.Content> createVoiceContent() {
        SubViewItemModel.Content voiceContent = new SubViewItemModel.Content(
                "ic_voice",
                "like",
                "sendMessage",
                "Balance"
        );

        List<SubViewItemModel.Content> contents = new ArrayList<>();
        contents.add(voiceContent);

        return contents;
    }

    public ActionbarHeaderView getActionbarHeaderView() {
        ActionbarHeaderView actionbarHeaderView = new ActionbarHeaderView(
                this,
                getActionbarModel(),
                ActionManagerImpl.getInstance());
        return actionbarHeaderView;
    }

    public ActionbarModel getActionbarModel() {
        ActionbarModel actionbarModel = new ActionbarModel();

        String id = "header";
        actionbarModel.setId(id);

        ActionbarStyle style = new ActionbarStyle();
        style.setBackgroundColor("#2222FF");
        actionbarModel.setStyle(style);

//        ActionbarModel.Profile profile = new ActionbarModel.Profile();
//        profile.setImage("cartoon.jpg");
//        actionbarModel.setProfile(profile);


        List<ActionbarModel.Button> buttons = new ArrayList<>();
        ActionbarModel.Button cameraBtn = new ActionbarModel.Button();
        cameraBtn.setId("rightbuttons");
        cameraBtn.setImage("ic_camera_off.png");
        cameraBtn.setAction("Camera");
        buttons.add(cameraBtn);

        ActionbarModel.Button audioBtn = new ActionbarModel.Button();
        cameraBtn.setId("leftbuttons");
        cameraBtn.setImage("ic_record_off.png");
        cameraBtn.setAction("Audio");
        buttons.add(audioBtn);

        actionbarModel.setButtons(buttons);
        TitleStyle titleStyle = new TitleStyle();
        titleStyle.setTextColor("#FFFFFF");

        List<ActionbarModel.Text> texts = new ArrayList<>();
        ActionbarModel.Text text = new ActionbarModel.Text();
        text.setId("headerText");
        text.setLabel("Title");
        text.setStyle(titleStyle);
        texts.add(text);

        actionbarModel.setTexts(texts);

        return actionbarModel;
    }
}
