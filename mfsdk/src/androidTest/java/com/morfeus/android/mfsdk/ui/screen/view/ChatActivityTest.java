package com.morfeus.android.mfsdk.ui.screen.view;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatPresenter;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatPresenterInjector;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatActivityTest {

    @Rule
    public ChatActivityTestRule<ChatActivity> mActivityRule = new ChatActivityTestRule<>(
            ChatActivity.class);

    private ChatPresenter mockChatPresenter;

    private ChatActivity mChatActivity;

    @org.junit.Before
    public void setUp() throws Exception {
        mChatActivity = (ChatActivity) mActivityRule.getActivity();
    }

    @Test
    public void testOnCreate_loadMFEditorModel() {
        verify(mockChatPresenter, times(1)).loadMFEditorModel();
    }

    @Test
    public void testOnCreate_loadDefaultMessages() {
//        verify(mockChatPresenter, times(1)).loadDefaultMessages();
    }

    @Test
    public void testOnCreate_loadHeaderModel() {
        verify(mockChatPresenter, times(1)).loadHeaderModel();
    }

    @Test
    public void testChatRecycler() {
        onView(withId(R.id.rv_chat)).perform(click());
        Spoon.screenshot(mActivityRule.getActivity(),
                "ChatActivity_chatRecycler");
    }

    @Test
    public void testAddMessages_displaysMessages() {
        final List<TemplateModel> templateModelList = ChatActivityTestHelper.createTemplateModels();

        mChatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mChatActivity.showMessages(templateModelList);
            }
        });

        onView(withId(R.id.tvMainMsg));
        onView(allOf(withId(R.id.tvMainMsg), withText("Welcome to Active AI"))).check(matches(isDisplayed()));
        onView(withId(R.id.tvSubMsg));
        onView(allOf(withId(R.id.tvSubMsg), withText("How can i help you ?"))).check(matches(isDisplayed()));

        Spoon.screenshot(mActivityRule.getActivity(),
                "ChatActivity_addMessage");
    }

    @Test
    public void testAddMessages_displaysListMsg() {
        final List<TemplateModel> templateModelList = ChatActivityTestHelper.createListModels();

        mChatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mChatActivity.showMessages(templateModelList);
            }
        });

//        onView(withId(R.id.tvMainMsg));
//        onView(allOf(withId(R.id.tvMainMsg), withText("Welcome to Active AI"))).check(matches(isDisplayed()));
//        onView(withId(R.id.tvSubMsg));
//        onView(allOf(withId(R.id.tvSubMsg), withText("How can i help you ?"))).check(matches(isDisplayed()));

        Spoon.screenshot(mActivityRule.getActivity(),
                "ChatActivity_addMessage");
    }


    private class ChatActivityTestRule<A extends ChatActivity> extends ActivityTestRule {

        public ChatActivityTestRule(Class activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            mockChatPresenter = mock(ChatPresenter.class);
            ChatPresenterInjector chatPresenterInjector = ChatPresenterInjector.getInstance();
            chatPresenterInjector.setChatPresenter(mockChatPresenter);
        }

        @Override
        protected Intent getActivityIntent() {
            return super.getActivityIntent();
        }
    }
}