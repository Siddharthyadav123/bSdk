package com.morfeus.android.mfsdk.ui.widget.editor.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivityTest {

    // TODO change

//    @Rule
//    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(
//            TestActivity.class);
@Rule
public ActivityTestRule<ChatActivity> mActivityRule = new ActivityTestRule<>(
        ChatActivity.class);

    Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void imageButtonComponent_displayed() {
        onView(withId(R.id.ibc));
        Spoon.screenshot(mActivityRule.getActivity(),
                "imageButtonComponent");
    }

    @Test
    public void editTextComponent_withImageButtons() {
        onView(withId(R.id.etc));
        onView(withId(R.id.component_edittext_leftbutton)).perform(click());
        onView(withId(R.id.component_edittext_rightbutton1)).perform(click());
//        onView(withId(R.id.component_edittext_rightbutton2)).perform(click());
        onView(withId(R.id.component_edittext_et)).perform(typeText("Hi"));
        Spoon.screenshot(mActivityRule.getActivity(),
                "editTextComponent");
    }

    @Test
    public void inputViewComponent_withEditTextComponentAndImageButtonComponent() {
        onView(withId(R.id.component_inputview)).perform(click());
        onView(withId(R.id.component_edittext)).perform(click());
        onView(withId(R.id.component_edittext_et)).perform(typeText("Hi"));
        onView(withId(R.id.component_edittext_rightbutton1)).perform(click());
//        onView(withId(R.id.component_inputview_ibc_send)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "inputViewComponent");
    }

    @Test
    public void toolbarViewComponent_showImageButtonComponents() throws Exception {
        onView(withId(R.id.component_toolbarview)).perform(click());
        onView(withId(R.id.component_toolbarview_ibc_menu)).perform(click());
        onView(withId(R.id.component_toolbarview_ibc_emoji)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "toolbarViewComponent");
    }

    @Test
    public void subViewComponent_showSubViewItem() {
        onView(withId(R.id.component_subview)).perform(click());
        onView(withId(R.id.component_subview_emoji_iv_temp)).check(matches(isDisplayed()));

        Spoon.screenshot(mActivityRule.getActivity(),
                "subViewComponent");
    }

    @Test
    public void tabLayoutComponent_showsTabsAndPerformAction() {
        onView(withId(R.id.component_tablayout)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "tabLayoutComponent");
    }

    @Test
    public void mfEditorComponent_showMFEditorWithAllRequireComponent() {
        onView(withId(R.id.component_mf_editor)).perform(click());
        onView(withId(R.id.component_tablayout)).perform(click());
        onView(withId(R.id.component_inputview)).perform(click());
        onView(withId(R.id.component_edittext_et)).perform(typeText("Hello World"));
        onView(withId(R.id.component_edittext_leftbutton)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "mfEditorComponent");
        onView(withId(R.id.component_subview)).perform(click());
        onView(withId(R.id.component_edittext_leftbutton)).perform(click());

    }

    @Test
    public void suggestionViewComponent_showSuggestionView() {
        onView(withId(R.id.component_suggestion_view)).perform(click());
    }

    @Test
    public void feedbackSubViewComponent_showFeedbackContent() {
        onView(withId(R.id.component_subview_feedback)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "feedbackSubView");
    }

    @Test
    public void menuSubViewComponent_showMenuContent() {
        onView(withId(R.id.component_subview_menu)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "menuSubView");
    }

    @Test
    public void voiceSubViewComponent_showVoiceContent() {
        onView(withId(R.id.component_subview_voice)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "voiceSubView");
    }

    @Test
    public void testActionbar_showActionbar() {
        onView(withId(R.id.tvHeaderText)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "actionbarView");
    }

    @Test
    public void test_listCardTemplate() {

        onView(withId(R.id.tvHeaderText)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "actionbarView");
    }
}