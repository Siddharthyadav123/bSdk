package com.morfeus.android.mfsdk.ui.screen.fragment;


import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.push.model.PushModel;
import com.morfeus.android.mfsdk.ui.action.ActionManagerImpl;
import com.morfeus.android.mfsdk.ui.action.event.ActionbarCopyStateEvent;
import com.morfeus.android.mfsdk.ui.action.event.MessageEvent;
import com.morfeus.android.mfsdk.ui.action.event.SendEvent;
import com.morfeus.android.mfsdk.ui.action.event.UIMessageEvent;
import com.morfeus.android.mfsdk.ui.action.event.chat.TypingIndicatorEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.adapter.ChatRecyclerAdapter;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatContract;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatPresenterInjector;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfLocationCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfTextCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.StickerTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.LoginModel;
import com.morfeus.android.mfsdk.ui.widget.dialog.login.MfLoginView;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.MfOTPView;
import com.morfeus.android.mfsdk.ui.widget.dialog.otp.OTPModel;
import com.morfeus.android.mfsdk.ui.widget.editor.EditTextComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.ImageButtonComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.InputViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.MFEditorViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SubViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.SuggestionViewComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.TabLayoutComponent;
import com.morfeus.android.mfsdk.ui.widget.editor.exception.ComponentException;
import com.morfeus.android.mfsdk.ui.widget.editor.factory.ComponentFactoryProvider;
import com.morfeus.android.mfsdk.ui.widget.editor.model.BaseModel;
import com.morfeus.android.mfsdk.ui.widget.header.ActionbarHeaderView;
import com.morfeus.android.mfsdk.ui.widget.header.model.ActionbarModel;
import com.morfeus.android.mfsdk.ui.widget.loading.ChatLoadingView;
import com.morfeus.android.mfsdk.ui.widget.loading.model.LoadingModel;
import com.morfeus.android.mfsdk.voice.TextToSpeechManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatFragment extends Fragment implements ChatContract.View {
    private static final String TAG = ChatFragment.class.getSimpleName();
    public static final boolean ALLOW_MSG_TO_SEND_OFFLINE = false;
    private final String STICKER_CARD_TEMPLATE_ID = "SimleyCardTemplate";
    private final String TEXT_CARD_TEMPLATE_ID = "text";

    private View mRootView;
    private LinearLayout mLlyRootView;
    private RecyclerView mRecyclerViewChat;
    private ChatLoadingView mChatLoadingView;
    private ChatRecyclerAdapter mChatAdapter;
    private MFEditorViewComponent mToolbarComponent;
    private SuggestionViewComponent mSuggestionViewComponent;
    private SubViewComponent mSubViewComponent;
    private TabLayoutComponent mTabLayoutComponent;
    private EditText mMessageEditText;
    private ActionbarHeaderView mHeaderView;
    private LinearLayout mActionbarContainerLinearLayout;

    private LanguageRepository mLanguageRepository;
    private TextToSpeechManager mTextToSpeechManager;
    private List<TemplateModel> mMessages = null;
    private TemplateFactory mTemaplteFactory;
    private PushModel mPushModel;
    private ChatPresenterInjector mPresenterInjector;
    private boolean mKeyboardVisible;

    private ChatContract.Presenter mChatPresenter;

    public ChatFragment() {
        // Required empty public constructor
    }


    private final TabLayoutComponent.OnTabChangeListener mTextTabListener
            = new TabLayoutComponent.OnTabChangeListener() {
        @Override
        public void onClick(@NonNull String tabId) {
            if (tabId.equalsIgnoreCase("textview")) {
                if (mKeyboardVisible) {
                    showSoftKeyboard(false);
                    showSuggestionViewComponent(true);
                    showToolbarMenuViewComponent(true);
                    hideTabSelection();
                } else {
                    showSoftKeyboard(true);
                    showSuggestionViewComponent(false);
                    showToolbarMenuViewComponent(false);
                }
            } else {
                if (mKeyboardVisible) {
                    showSoftKeyboard(false);
                    showSuggestionViewComponent(true);
                    showToolbarMenuViewComponent(true);
                }

                if (mSubViewComponent.getVisibility() == View.GONE) {
                    mTabLayoutComponent.removeTabSelection();
                } else {
                    showSuggestionViewComponent(false);
                }
            }
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMembers();
        registerEvents();
        loadChatPresenter();
        setChatAdapter();
    }

    private void loadChatPresenter() {
        mPresenterInjector = ChatPresenterInjector.getInstance();
        mChatPresenter = mPresenterInjector.getPresenter(this);
        mChatPresenter.onStart();
        mChatPresenter.loadLoadingModel();
        mChatPresenter.loadHeaderModel();
        mChatPresenter.loadMFEditorModel();
        if (mPushModel != null) {
            mChatPresenter.loadInitMessages(mPushModel);
        } else {
            mChatPresenter.loadInitMessages(null);
        }
    }


    private void initMembers() {
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_chat_layout, null, false);
        mLlyRootView = (LinearLayout) mRootView.findViewById(R.id.activity_chat);
        mRecyclerViewChat = (RecyclerView) mRootView.findViewById(R.id.rv_chat);
        mChatLoadingView = (ChatLoadingView) mRootView.findViewById(R.id.rl_chat_loading);
        mActionbarContainerLinearLayout = (LinearLayout) mRootView.findViewById(R.id.lly_actionbar);

        mLanguageRepository = LanguageRepository.getInstance();
        mTextToSpeechManager = new TextToSpeechManager(getContext());
    }

    private void setChatAdapter() {
        mChatAdapter = new ChatRecyclerAdapter(mMessages, mTemaplteFactory);
        mRecyclerViewChat.setAdapter(mChatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerViewChat.setLayoutManager(linearLayoutManager);
        VerticalSpaceItemDecoration spaceItemDecoration = new VerticalSpaceItemDecoration(16);
        mRecyclerViewChat.addItemDecoration(spaceItemDecoration);
    }

    private void registerEvents() {
        mLlyRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                mLlyRootView.getWindowVisibleDisplayFrame(rect);
                int actualHeight = mLlyRootView.getRootView().getHeight();
                int rootViewHeight = rect.height();
                int heightDiff = actualHeight - rootViewHeight;

                mKeyboardVisible = heightDiff > actualHeight / 4;

                if (mKeyboardVisible) {
                    showToolbarMenuViewComponent(false);
                    showSuggestionViewComponent(false);
                    scrollToLast();
                } else {
                    showToolbarMenuViewComponent(true);
                    if (mSubViewComponent != null &&
                            mSubViewComponent.getVisibility() == View.GONE) {
                        showSuggestionViewComponent(true);
                    }
                }
            }
        });

        mRecyclerViewChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSoftKeyboard(false);
                showSuggestionViewComponent(true);
                showToolbarMenuViewComponent(true);
                hideTabSelection();
                hideSubView();
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;

    }

    public void showSoftKeyboard(boolean show) {
        if (mMessageEditText == null) {
            LogManager.e(TAG, "Error: " + "mMessageEditText " + "can't be null!"
                    + "\n" + "Fail to toggle softKeyboard opening!"
            );
        } else {
            InputMethodManager inputManager
                    = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

            if (show) {
                inputManager.showSoftInput(mMessageEditText, 0);
            } else {
                inputManager.hideSoftInputFromWindow(mMessageEditText.getWindowToken(), 0);
            }
        }
    }

    private void showSuggestionViewComponent(boolean show) {
        if (mSuggestionViewComponent == null) {
            LogManager.e(TAG, "Error: " + "mSuggestionViewComponent " + "can't be null!"
                    + "\n" + "Fail to toggle softKeyboard opening!"
            );
        } else {
            mSuggestionViewComponent.show(show);
        }
    }

    private void showToolbarMenuViewComponent(boolean show) {
        if (mTabLayoutComponent == null) {
            LogManager.e(TAG, "Error: " + "mTabLayoutComponent " + "can't be null!"
                    + "\n" + "Fail to toggle softKeyboard opening!"
            );
        } else {
            mTabLayoutComponent.show(show);
        }
    }

    private void hideTabSelection() {
        mTabLayoutComponent.removeTabSelection();
    }

    public boolean hideSubView() {
        if (mSubViewComponent != null
                && (mSubViewComponent.getVisibility() == View.VISIBLE)) {
            mSubViewComponent.setVisibility(View.GONE);
            mTabLayoutComponent.removeTabSelection();
            return true;
        }
        return false;
    }

    private ActionbarHeaderView prepareActionbar(ActionbarModel actionbarModel) {
        ActionbarHeaderView headerView = new ActionbarHeaderView(
                getContext(),
                actionbarModel,
                ActionManagerImpl.getInstance());
        headerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        headerView.setOrientation(LinearLayout.HORIZONTAL);
        headerView.initView();
        return headerView;
    }

    @Override
    public void showHeader(@NonNull ActionbarModel actionbarModel) {
        checkNotNull(actionbarModel);
        mHeaderView = prepareActionbar(actionbarModel);
        mActionbarContainerLinearLayout.addView(mHeaderView);
    }

    @Override
    public void showMessages(List<TemplateModel> templateModels) {
        mChatAdapter.addMessages(templateModels);
        scrollToLast();
    }

    @Override
    public void showMessage(TemplateModel templateModel) {
        mChatAdapter.addMessage(templateModel);
        scrollToLast();
    }

    @Override
    public void textToSpeechMsg(String text) {
        mTextToSpeechManager.speakText(text);
    }

    @Override
    public void showMFEditor(BaseModel mfEditorModel) {
        try {
            mToolbarComponent = prepareMFEditor(mfEditorModel);

            mSuggestionViewComponent = (SuggestionViewComponent) mToolbarComponent.findViewById(
                    R.id.component_suggestion_view);

            mSubViewComponent = (SubViewComponent) mToolbarComponent.findViewById(R.id.component_subview);
            mSubViewComponent.setVisibility(View.GONE);

            mTabLayoutComponent
                    = (TabLayoutComponent) mToolbarComponent.findViewById(R.id.component_tablayout);
            mTabLayoutComponent.registerTabListener(mSubViewComponent.getTabChangeListener());
            mTabLayoutComponent.registerTabListener(mTextTabListener);


            InputViewComponent inputViewComponent
                    = (InputViewComponent) mToolbarComponent.findViewById(R.id.component_inputview);
            EditTextComponent editTextComponent
                    = (EditTextComponent) inputViewComponent.findViewById(R.id.component_edittext);
            mMessageEditText
                    = (EditText) editTextComponent.findViewById(R.id.component_edittext_et);
            mMessageEditText.requestFocus();
            mMessageEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideSubView();
                }
            });

            ImageButtonComponent sendButton
                    = (ImageButtonComponent) inputViewComponent.findViewById(
                    R.id.component_inputview_ibc_send
            );

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (mMessageEditText.getText().toString().trim().length() > 0) {
                            SendEvent event = new SendEvent(mMessageEditText.getText().toString());
                            EventBus.getDefault().post(event);
                            mMessageEditText.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            mLlyRootView.addView(mToolbarComponent);
        } catch (ComponentException e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    private MFEditorViewComponent prepareMFEditor(BaseModel baseModel)
            throws ComponentException {
        MFEditorViewComponent mfec = new MFEditorViewComponent(
                getContext(),
                baseModel,
                ComponentFactoryProvider.getFactory(ComponentFactoryProvider.PRIMITIVE_COMPONENT_FACTORY),
                ActionManagerImpl.getInstance()
        );
        mfec.setComponentId(R.id.component_mf_editor);
        mfec.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        mfec.initView();
        return mfec;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void configLoadingProgress(@NonNull LoadingModel loadingModel) {
        if (mChatLoadingView != null)
            mChatLoadingView.setData(loadingModel);
    }

    @Override
    public void showProgressIndicator(boolean showLoading) {
        if (showLoading) {
            mChatLoadingView.show();
            mToolbarComponent.disableEditorToolbar();
        } else {
            mChatLoadingView.hide();
            mToolbarComponent.enabledEditorToolbar();
        }
    }

    @Override
    public void scrollToLast() {
        mRecyclerViewChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
    }

    @Override
    public void showInitError() {
        String title = mLanguageRepository.getText(
                getResources().getString(R.string.err_init_title));
        String err_init_msg = mLanguageRepository.getText(
                getResources().getString(R.string.err_init_msg));
        String retry = mLanguageRepository.getText(
                getResources().getString(R.string.retry));
        String cancel = mLanguageRepository.getText(
                getResources().getString(R.string.text_cancel));


        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(err_init_msg)
                .setCancelable(false)
                .setPositiveButton(retry,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mChatPresenter.loadInitMessages(mPushModel);
                                dialogInterface.dismiss();
                            }
                        })
                .setNegativeButton(cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                getActivity().finish();
                            }
                        })
                .show();
    }

    @Override
    public void showTyping(boolean show) {
        mChatAdapter.showTyping(show);
        scrollToLast();
    }

    @Override
    public void updateMessageStatus(String msgId, int status) {
        if (mMessages != null && msgId != null) {
            TemplateModel msgToUpdate = null;
            for (int i = mMessages.size() - 1; i >= 0; i--) {
                String messageSpecificId = mMessages.get(i).getMsgId();
                if (messageSpecificId != null && msgId.equals(messageSpecificId)) {
                    msgToUpdate = mMessages.get(i);
                    break;
                }
            }
            if (msgToUpdate != null && !msgToUpdate.isIncoming()) {
                msgToUpdate.setMsgStatus(status);
                mChatAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateNetworkStatus(boolean isNetworkConnected) {
        if (isNetworkConnected) {
            mHeaderView.setStatusText(getResources().getString(R.string.text_online));
            mToolbarComponent.enabledEditorToolbar();
        } else {
            mHeaderView.setStatusText(getResources().getString(R.string.text_waiting_for_network));
            if (!ALLOW_MSG_TO_SEND_OFFLINE) {
                mToolbarComponent.disableEditorToolbar();
            }
        }
    }

    @Override
    public void setPresenter(@NonNull ChatContract.Presenter presenter) {
        mChatPresenter = checkNotNull(presenter);
    }

    private static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    public boolean isActiobarCopyStateEnable() {
        if (mHeaderView.getCurrentState() == ActionbarHeaderView.COPY) {
            return true;
        }
        return false;
    }

    public void setActionBarState(int state) {
        mHeaderView.setState(state);
    }


    @SuppressWarnings("unused")
    @Subscribe
    public void onActionbarCopyStateEvent(ActionbarCopyStateEvent actionbarCopyStateEvent) {
        mHeaderView.setCopyText(actionbarCopyStateEvent.getText());
        mHeaderView.setState(ActionbarHeaderView.COPY);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMsg() != null) {
            sendTextMessage(messageEvent.getMsg(), messageEvent.getmMsgId());

        } else if (messageEvent.getImage() != null) {
            String image = messageEvent.getImage();
            if (image != null) {
                sendEmojiMessage(image, messageEvent.getmMsgId());
            }

        } else if (messageEvent.getPayload() != null) {
            String action = messageEvent.getAction();
            String payload = messageEvent.getPayload();

            if (action != null) {
                if (action.equalsIgnoreCase("sendmessage") || action.equalsIgnoreCase("IMAGEPICKER")) {
                    sendTextMessage(payload, messageEvent.getmMsgId());
                }
            }

        } else if (messageEvent.getAction() != null) {
            String action = messageEvent.getAction();

            if (action.equalsIgnoreCase("ic_arrow_back")) {
                mTemaplteFactory.clearInstance();
                getActivity().finish();
            } else if (action.equalsIgnoreCase("HIDESUBTITLE")) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                int cameraRequestCode = 1001;
                startActivityForResult(cameraIntent, cameraRequestCode);
            } else if (action.equalsIgnoreCase("LOGOUT")) {
                Toast.makeText(getActivity(), "Info clicked.", Toast.LENGTH_SHORT).show();
            }

        }
        scrollToLast();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onTypingIndicatorEvent(TypingIndicatorEvent event) {
        showTyping(event.isShow());
    }

    private void sendEmojiMessage(String image, String msgId) {
        LogManager.i(TAG, "SEND EMOJI: MESSAGE_ID = " + msgId);
        StickerTemplateModel stickerTemplateModel = new StickerTemplateModel(STICKER_CARD_TEMPLATE_ID);
        stickerTemplateModel.setTo("user123");
        stickerTemplateModel.setFrom("self");
        stickerTemplateModel.setMsgId(msgId);
        stickerTemplateModel.setMsgStatus(MessageStatus.UNSEND);
        stickerTemplateModel.setImage(image);
        boolean isIncoming = isIncoming(stickerTemplateModel.getFrom());
        stickerTemplateModel.setIncoming(isIncoming);

        mChatAdapter.addMessage(stickerTemplateModel);
        showTyping(true);
    }

    private void sendTextMessage(String msg, String msgId) {
        LogManager.i(TAG, "SEND TEXT: MESSAGE = " + msg);
        TemplateModel textCardTemplateModel = new MfTextCardTemplateModel(TEXT_CARD_TEMPLATE_ID);
        textCardTemplateModel.setTo("user123");
        textCardTemplateModel.setFrom("self");
        textCardTemplateModel.setMsgId(msgId);
        textCardTemplateModel.setMsgStatus(MessageStatus.UNSEND);

        msg = mLanguageRepository.getText(msg);
        ((MfTextCardTemplateModel) textCardTemplateModel).setTextMessage(msg);
        boolean isIncoming = isIncoming(textCardTemplateModel.getFrom());
        textCardTemplateModel.setIncoming(isIncoming);

        mChatAdapter.addMessage(textCardTemplateModel);
        showTyping(true);
    }

    public void sendLocationMessage(LatLng myLatLong) {
        String msg = "Your Location";
        String msgId = System.currentTimeMillis() + "";
        MfLocationCardTemplateModel locationCardTemplateModel
                = new MfLocationCardTemplateModel(ChatActivity.LOCATION_CARD_TEMPLATE_ID);
        locationCardTemplateModel.setTo("user123");
        locationCardTemplateModel.setFrom("self");
        locationCardTemplateModel.setMsgId(msgId);
        locationCardTemplateModel.setMsgStatus(MessageStatus.UNSEND);
        msg = mLanguageRepository.getText(msg);
        locationCardTemplateModel.setTextMessage(msg);
        locationCardTemplateModel.setLatitude(myLatLong.latitude);
        locationCardTemplateModel.setLongitude(myLatLong.longitude);

        boolean isIncoming = isIncoming(locationCardTemplateModel.getFrom());
        locationCardTemplateModel.setIncoming(isIncoming);
        mChatAdapter.addMessage(locationCardTemplateModel);
        showTyping(true);

        UIMessageEvent uiMessageEvent = new UIMessageEvent(
                null,
                null,
                msg,
                null,
                "location",
                msgId
        );
        ModuleEventBus.getDefault().post(uiMessageEvent);
    }

    private boolean isIncoming(String userName) {
        return userName.equalsIgnoreCase("morfues_bot");
    }


    public void setTemaplteFactory(TemplateFactory temaplteFactory) {
        this.mTemaplteFactory = temaplteFactory;
    }

    public void setPushModel(PushModel pushModel) {
        this.mPushModel = pushModel;
    }

    public void setMessages(List<TemplateModel> messages) {
        this.mMessages = messages;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        registerEventBus(false);
    }

    public void registerEventBus(boolean register) {
        if (register) {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this);
        } else {
            if (EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this);
        }
    }


    public void stopRecording() {
        if (mSubViewComponent != null) {
            mSubViewComponent.stopRecording();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPresenter();

        if (mTextToSpeechManager != null)
            mTextToSpeechManager.stop();
    }

    public void stopPresenter() {
        if (mChatPresenter != null)
            mChatPresenter.onStop();
    }
    public void startPresenter() {
        if (mChatPresenter != null)
            mChatPresenter.onStart();
    }
}
