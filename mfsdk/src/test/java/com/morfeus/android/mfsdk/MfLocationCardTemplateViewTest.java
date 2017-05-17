package com.morfeus.android.mfsdk;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.messenger.message.MessageStatus;
import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.ui.config.parser.CardStyleParser;
import com.morfeus.android.mfsdk.ui.config.parser.ConfigParserHelper;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfLocationCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfNetworkImageView;
import com.morfeus.android.mfsdk.ui.widget.bubble.SelectableRoundedImageView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfLocationCardTemplateModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(constants = BuildConfig.class, sdk = 22)
@RunWith(RobolectricTestRunner.class)
public class MfLocationCardTemplateViewTest {
    private MfLocationCardTemplateView mfLocationCardTemplateView;
    private TemplateViewHolder mViewHolder;

    private Activity mActivity;
    private View mRootView;

    private TextView mTVTextMessage;
    private SelectableRoundedImageView mSenderImage;
    private MfNetworkImageView mIVHeaderImage;
    private ImageView mIvTickImage;
    private RelativeLayout mRlImageContainerLaout;

    private MfLocationCardTemplateModel mMfLocationCardTemplateModel;

    @Before
    public void setUp() throws Exception {
        ActivityController<Activity> activityController = Robolectric.buildActivity(Activity.class);
        mActivity = activityController.get();
        mfLocationCardTemplateView = new MfLocationCardTemplateView(mActivity);
        BaseView baseView = mfLocationCardTemplateView.inflate(mActivity);
        mViewHolder = mfLocationCardTemplateView.createViewHolder(baseView);

        mRootView = (View) baseView;
        mTVTextMessage = (TextView) mRootView
                .findViewById(R.id.chat_item_location_layout_tv_text);
        mSenderImage = (SelectableRoundedImageView) mRootView.findViewById(R.id.iv_sender_image);
        mIvTickImage = (ImageView) mRootView.findViewById(R.id.iv_tick_image);
        mIVHeaderImage = (MfNetworkImageView) mRootView.findViewById(R.id.iv_header_image);
        mIVHeaderImage.setBotId("5w47394784104");
        mIVHeaderImage.setDefaultImageResId(R.drawable.cartoon);
        mIVHeaderImage.setErrorImageResId(android.R.drawable.ic_delete);
        mIVHeaderImage.setVisibility(View.GONE);
        mRlImageContainerLaout = (RelativeLayout) mRootView
                .findViewById(R.id.rl_image_container);

        CardStyleParser.getInstance().parse(ConfigParserHelper.TEST_CARD_STYLE_JSON_STR);
        MFMediaSdk.createInstance(mActivity);
        setData();
    }

    private void setData() {
        mMfLocationCardTemplateModel = new MfLocationCardTemplateModel("location");
        mMfLocationCardTemplateModel.setTextMessage("Your Location");
        mMfLocationCardTemplateModel.setIncoming(false);
        mMfLocationCardTemplateModel.setMsgStatus(MessageStatus.SEND);
        mMfLocationCardTemplateModel.setLatitude(21.145800);
        mMfLocationCardTemplateModel.setLongitude(79.0881550);
        mViewHolder.setData(mMfLocationCardTemplateModel);
    }

    @Test
    public void testViewsVisibility() {
        assertThat(mIvTickImage.getVisibility(), equalTo(View.VISIBLE));
        assertThat(mSenderImage.getVisibility(), equalTo(View.GONE));
        assertThat(mIVHeaderImage.getVisibility(), equalTo(View.VISIBLE));
        assertThat(mTVTextMessage.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testLocationText() {
        assertThat(mTVTextMessage.getText().toString(), equalTo("Your Location"));
    }

    @Test
    public void testTickImage() {
        int drawableResId = Shadows.shadowOf(mIvTickImage.getDrawable()).getCreatedFromResId();
        assertThat("Error: wrong image drawable", R.drawable.tick_msg_sent_read, equalTo(drawableResId));
    }


}
