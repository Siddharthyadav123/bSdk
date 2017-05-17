package com.morfeus.android.mfsdk.ui.screen.chat;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.model.LatLng;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.messenger.ModuleEventBus;
import com.morfeus.android.mfsdk.messenger.event.NotificationEvent;
import com.morfeus.android.mfsdk.push.NotificationServiceHelper;
import com.morfeus.android.mfsdk.push.model.PushModel;
import com.morfeus.android.mfsdk.ui.action.event.BackEvent;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.entity.ChatScreen;
import com.morfeus.android.mfsdk.ui.screen.fragment.ChatFragment;
import com.morfeus.android.mfsdk.ui.screen.fragment.VoiceFragment;
import com.morfeus.android.mfsdk.ui.screen.location.LocationMapActivity;
import com.morfeus.android.mfsdk.ui.widget.bubble.HorizontalTemplateListView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplate;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithCardInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithImageInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithLimit;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithLoanInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithReminderInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithStatusInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithTransactionInfo;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfButtonCardTemplateWithVideoTips;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfImageCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfInfoCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfListCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfLoadingTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfLocationCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfMapCardTemplateWithDetails;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfRecieptCardTemplate;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfTextCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfTouchIdCardTemplate;
import com.morfeus.android.mfsdk.ui.widget.bubble.StickerTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.header.ActionbarHeaderView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    public static final int REQUEST_CODE_LOCATION = 1001;
    private static final int LOADER_ACCESS_TOKEN = 101;

    private static final String INFO_CARD_TEMPLATE_ID = "InfoCardTemplate";
    private static final String LIST_CARD_TEMPLATE = "ListCardTemplate";
    private static final String TEXT_CARD_TEMPLATE_ID = "text";
    private static final String IMAGE_CARD_TEMPLATE_ID = "image";
    private static final String HORIZONTAL_TEMPLATES_ID = "HorizontalTemplates";
    private static final String STICKER_CARD_TEMPLATE_ID = "SimleyCardTemplate";
    private static final String BUTTON_CARD_TEMPLATES_ID = "ButtonCardTemplate";
    private static final String RECIEPT_CARD_TEMPLATES_ID = "RecieptCardTemplate";
    private static final String TOUCH_ID_CARD_TEMPLATES_ID = "TouchIdCardTemplate";
    private static final String BUTTON_WITH_LIMI_CARD_TEMPLATES_ID = "ButtonCardTemplateWithLimit";
    private static final String BUTTON_WITH_CARD_INFO_TEMPLATES_ID = "ButtonCardTemplateWithCardInfo";
    private static final String BUTTON_WITH_STATUS_INFO_TEMPLATES_ID = "ButtonCardTemplateWithStatusInfo";
    private static final String BUTTON_WITH_REMINDER_INFO_TEMPLATES_ID = "ButtonCardTemplateWithReminderInfo";
    private static final String BUTTON_WITH_TRANSATION_INFO_TEMPLATES_ID = "ButtonCardTemplateWithTransactionInfo";
    private static final String BUTTON_WITH_IMAGE_INFO_TEMPLATES_ID = "ButtonCardTemplateWithImageInfo";
    private static final String BUTTON_WITH_VIDEO_TIPS_TEMPLATES_ID = "ButtonCardTemplateWithVideoTips";
    private static final String BUTTON_WITH_LOAN_INFO_TEMPLATES_ID = "ButtonCardTemplateWithLoanInfo";
    private static final String MAP_CARD_WITH_DETAILS_TEMPLATES_ID = "MapCardTemplateWithDetails";
    private static final String LOADING_VIEW_TEMPLATE_ID = "LoadingCardTemplate";
    public static final String LOCATION_CARD_TEMPLATE_ID = "location";

    private static final String TAG = ChatActivity.class.getSimpleName();
    private final List<TemplateModel> mMessages = new ArrayList<>();
    private final NotificationServiceHelper.NotificationListener mNotificationListener
            = new NotificationServiceHelper.NotificationListener() {
        @Override
        public void onMessageReceived(PushModel pushModel) {
            LogManager.d(TAG, "onMessageReceived: " + pushModel.toString());
            postNotificationMessage(pushModel);
        }
    };

    private PushModel mPushModel;

    private boolean mIsBound;
    private NotificationServiceHelper mNotificationService;
    private NotificationServiceConnection mNotificationServiceConnection
            = new NotificationServiceConnection();

    private TemplateFactory mTemaplteFactory;
    private List<PushModel> mPendingPushModels = new ArrayList<>();
    private boolean isForergound;

    private LanguageRepository mLanguageRepository;

    private ChatFragment mChatFragment;
    private VoiceFragment mVoiceFragment;


    public static void start(@NonNull Context context,
                             @Nullable String welcomeMessage,
                             @Nullable String secondaryMessage,
                             @NonNull ChatScreenModel chatScreenModel) {
        checkNotNull(chatScreenModel);
        checkNotNull(context);

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(
                ChatScreen.EXTRA_MSG_WELCOME,
                welcomeMessage
        );
        intent.putExtra(
                ChatScreen.EXTRA_MSG_SECONDARY,
                secondaryMessage
        );
        intent.putExtra(ChatScreen.EXTRA_MSG_MF_EDITOR_MODEL,
                chatScreenModel
        );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTaskRoot()) {
            // TODO open launcher activity.
//            PushModel pushModel = processExtra(getIntent());
//            Intent intent = getLauncherIntent(pushModel);
//            startActivity(intent);
        }

        mLanguageRepository = LanguageRepository.getInstance();
        mPendingPushModels.clear();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_chat);
        mTemaplteFactory = TemplateFactory.getInstance();
        registerDefaultTemplates();
        processExtra(getIntent());
        initPagerFragments();
        setChatPagerFragmentAdapter();

        mIsBound = bindService(new Intent(getApplicationContext(), NotificationServiceHelper.class),
                mNotificationServiceConnection,
                Service.BIND_AUTO_CREATE);
    }

    private void initPagerFragments() {
        mChatFragment = new ChatFragment();
        mChatFragment.setMessages(mMessages);
        mChatFragment.setPushModel(mPushModel);
        mChatFragment.setTemaplteFactory(mTemaplteFactory);

        mVoiceFragment = new VoiceFragment();
    }

    private void setChatPagerFragmentAdapter() {
        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_pager);
        ChatPagerAdapter chatPagerAdapter = new ChatPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(chatPagerAdapter);

        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mChatFragment.showSoftKeyboard(false);
                    mChatFragment.stopRecording();
                    mChatFragment.registerEventBus(false);
                    mChatFragment.stopPresenter();

                    mVoiceFragment.setFragmentShowing(true);
                    mVoiceFragment.startVoiceRecorder();
                    mVoiceFragment.startPresenter();
                } else {
                    mChatFragment.registerEventBus(true);
                    mChatFragment.startPresenter();

                    mVoiceFragment.setFragmentShowing(false);
                    mVoiceFragment.resetVoiceText();
                    mVoiceFragment.stopRecording();
                    mVoiceFragment.stopPresenter();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @NonNull
    private Intent getLauncherIntent(PushModel pushModel) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(getPackageName());
        intent.putExtra("extra_push_model", pushModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    private PushModel processExtra(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
//                    mPushModel = bundle.getParcelable("extra_push_model");
//                    Log.d(TAG, "onCreate: pushModel > " + mPushModel.toString());
                } catch (Exception e) {
                    // No-op
                    //TODO check for proper solution
                }
            }
        }
        return mPushModel;
    }

    private void registerDefaultTemplates() {
        if (mTemaplteFactory == null) {
            throw new IllegalStateException("Error: TemplateFactory can't be null!");
        }

        mTemaplteFactory.registerTemplate(INFO_CARD_TEMPLATE_ID,
                new MfInfoCardTemplateView(this));
        mTemaplteFactory.registerTemplate(HORIZONTAL_TEMPLATES_ID,
                new HorizontalTemplateListView(this));
        mTemaplteFactory.registerTemplate(LIST_CARD_TEMPLATE,
                new MfListCardTemplateView(this));
        mTemaplteFactory.registerTemplate(TEXT_CARD_TEMPLATE_ID,
                new MfTextCardTemplateView(this));
        mTemaplteFactory.registerTemplate(IMAGE_CARD_TEMPLATE_ID,
                new MfImageCardTemplateView(this));
        mTemaplteFactory.registerTemplate(STICKER_CARD_TEMPLATE_ID,
                new StickerTemplateView(this));
        mTemaplteFactory.registerTemplate(BUTTON_CARD_TEMPLATES_ID,
                new MfButtonCardTemplate(this));
        mTemaplteFactory.registerTemplate(RECIEPT_CARD_TEMPLATES_ID,
                new MfRecieptCardTemplate(this));
        mTemaplteFactory.registerTemplate(TOUCH_ID_CARD_TEMPLATES_ID,
                new MfTouchIdCardTemplate(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_LIMI_CARD_TEMPLATES_ID,
                new MfButtonCardTemplateWithLimit(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_CARD_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithCardInfo(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_STATUS_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithStatusInfo(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_REMINDER_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithReminderInfo(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_TRANSATION_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithTransactionInfo(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_IMAGE_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithImageInfo(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_VIDEO_TIPS_TEMPLATES_ID,
                new MfButtonCardTemplateWithVideoTips(this));
        mTemaplteFactory.registerTemplate(BUTTON_WITH_LOAN_INFO_TEMPLATES_ID,
                new MfButtonCardTemplateWithLoanInfo(this));
        mTemaplteFactory.registerTemplate(MAP_CARD_WITH_DETAILS_TEMPLATES_ID,
                new MfMapCardTemplateWithDetails(this));
        mTemaplteFactory.registerTemplate(LOADING_VIEW_TEMPLATE_ID,
                new MfLoadingTemplateView(this));
        mTemaplteFactory.registerTemplate(LOCATION_CARD_TEMPLATE_ID,
                new MfLocationCardTemplateView(this));
        LogManager.i(TAG, "TEMPLATE REGISTERED");
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAudioPermission();
            }
        }, 2000);
    }

    private void getAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only for gingerbread and newer versions
            if (hasAudioPermission()) {
                // No-op
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                showPermissionMessageDialog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_AUDIO_PERMISSION);
            }
        }
    }

    private boolean hasAudioPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForergound = true;
        showPendingNotificationMessage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (resultCode == RESULT_OK && data != null) {
                    LatLng myLatLong = data.getParcelableExtra(
                            LocationMapActivity.KEY_LOCATION_RESULT);
                    if (myLatLong != null) {
                        mChatFragment.sendLocationMessage(myLatLong);
                    }
                }
                break;
        }
    }

    private void showPendingNotificationMessage() {
        for (PushModel pushModel : mPendingPushModels) {
            postNotificationMessage(pushModel);
            mPendingPushModels.remove(pushModel);
        }
    }

    private void showPermissionMessageDialog() {
        new AlertDialog.Builder(this)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
//                        ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
//                                REQUEST_RECORD_AUDIO_PERMISSION);
                    }
                })
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
                                REQUEST_RECORD_AUDIO_PERMISSION);
                        dialogInterface.dismiss();
                    }
                })
                .setMessage(getString(R.string.permission_message))
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForergound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTemaplteFactory.clearTemplates();
    }

    @Override
    protected void onStop() {
        if (mIsBound) {
            unbindService(mNotificationServiceConnection);
            mIsBound = false;
        }
        super.onStop();
    }

    public void setStatusBarColor(int colorCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorCode);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mChatFragment.hideSubView()) {
            if (mChatFragment.isActiobarCopyStateEnable()) {
                mChatFragment.setActionBarState(ActionbarHeaderView.NORMAL);
            } else {
                super.onBackPressed();
                EventBus.getDefault().post(new BackEvent("ic_arrow_back"));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (permissions.length == 1 && grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // No-op
            } else {
                showPermissionMessageDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            PushModel pushModel = intent.getExtras().getParcelable("extra_push_model");
            if (pushModel != null) {
                if (!isForergound) {
                    mPendingPushModels.add(pushModel);
                } else {
                    postNotificationMessage(pushModel);
                }
            }
            LogManager.d(TAG, "onNewIntent: pushModel > " + pushModel);
        }

    }

    private class NotificationServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mNotificationService = ((NotificationServiceHelper.ServiceBinder) iBinder).getService();
            mNotificationService.register(mNotificationListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mNotificationService.unregister(mNotificationListener);
            mNotificationService = null;
        }
    }

    private void postNotificationMessage(PushModel pushModel) {
        ModuleEventBus.getDefault().post(new NotificationEvent(pushModel.getCard()));
    }


    public void launchLocationSelectionActivity() {
        Intent intent = new Intent(this, LocationMapActivity.class);
        intent.putExtra(LocationMapActivity.KEY_SCREEN_MODE
                , LocationMapActivity.SCREEN_MODE_SEND_LOCATION);
        startActivityForResult(intent,
                ChatActivity.REQUEST_CODE_LOCATION);
    }

    private boolean isIncoming(String userName) {
        return userName.equalsIgnoreCase("morfues_bot");
    }

    private class ChatPagerAdapter extends FragmentPagerAdapter {

        public ChatPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mChatFragment;
                case 1:
                    return mVoiceFragment;
                default:
                    return null;
            }
        }

    }
}
