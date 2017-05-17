package com.morfeus.android;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentTransaction;

import com.morfeus.android.mfsdk.TestActivity;
import com.morfeus.android.mfsdk.ui.screen.fragment.VoiceFragment;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class VoiceFullScreenTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            TestActivity.class);

    @Test
    public void fragment_can_be_instantiated() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VoiceFragment voiceFragment = startVoiceFragment();
            }
        });
        // Then use Espresso to test the Fragment
        onView(withId(R.id.iv_record_image)).check(matches(isDisplayed()));
    }

    private VoiceFragment startVoiceFragment() {
        TestActivity activity = (TestActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        VoiceFragment voiceFragment = new VoiceFragment();
        transaction.add(voiceFragment, "voiceFragment");
        transaction.commit();
        return voiceFragment;
    }


}
