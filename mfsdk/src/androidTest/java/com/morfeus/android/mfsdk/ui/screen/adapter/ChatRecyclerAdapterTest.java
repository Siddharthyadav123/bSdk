package com.morfeus.android.mfsdk.ui.screen.adapter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.ui.widget.bubble.HorizontalTemplateListView;
import com.morfeus.android.mfsdk.ui.widget.bubble.MfInfoCardTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.SimpleImageTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.SimpleTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.TemplateFactory;
import com.morfeus.android.mfsdk.ui.widget.bubble.TickTimeTemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.HorizontalTemplateListModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfInfoCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.SimpleImageTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.SimpleTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TickTimeTemplateModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class ChatRecyclerAdapterTest {

    private static final String TEST_TITLE = "Title";
    private static final String TEST_SIMPLE_TEMPLATE_ID = "temp01";
    private static final String TEST_SIMPLE_IMAGE_TEMPLATE_ID = "temp02";
    private static final String TEST_SUB_TEMPLATE_LIST_ID = "temp_list_01";
    private static final String TEST_TICK_TIME_TEMPLATE_ID = "temp03";
    private static final String TEST_INFO_CARD_TEMPLATE_ID = "InfoCardTemplate";
    private static final String TEST_TIME = "10:00";
    private static final String TEST_PRIMARY_MESSAGE = "Primary Message";
    private static final String TEST_SECONDARY_MESSAGE = "Secondary Messaeg";
    private static TemplateFactory TEMPLATE_FACTORY;

    private static List<TemplateModel> TEST_TEMPLATE_MODEL_LIST =
            new ArrayList<>();

    private static SimpleTemplateModel TEST_SIMPLE_TEMPLATE_MODEL;
    private static SimpleImageTemplateModel TEST_SIMPLE_IMAGE_TEMPLATE_MODEL;
    private static HorizontalTemplateListModel TEST_HORIZONTAL_TEMPLATE_LIST_MODEL;
    private static TickTimeTemplateModel TEST_TICK_TIME_TEMPLATE_MODEL;

    private static MfInfoCardTemplateModel TEST_INFO_CARD_TEMPLATE_MODEL;
    private static MFCardData TEST_INFO_CARD_TEMPLATE_DATA;


    static {
        TEST_SIMPLE_TEMPLATE_MODEL = new SimpleTemplateModel(TEST_TITLE, TEST_SIMPLE_TEMPLATE_ID);
        TEST_SIMPLE_IMAGE_TEMPLATE_MODEL = new SimpleImageTemplateModel(
                TEST_PRIMARY_MESSAGE,
                TEST_SECONDARY_MESSAGE,
                null,
                TEST_SIMPLE_IMAGE_TEMPLATE_ID);

        TEST_TEMPLATE_MODEL_LIST.add(TEST_SIMPLE_TEMPLATE_MODEL);
        TEST_TEMPLATE_MODEL_LIST.add(TEST_SIMPLE_IMAGE_TEMPLATE_MODEL);
        TEST_HORIZONTAL_TEMPLATE_LIST_MODEL = new HorizontalTemplateListModel(
                TEST_TEMPLATE_MODEL_LIST,
                TEST_SUB_TEMPLATE_LIST_ID
        );
        TEST_TEMPLATE_MODEL_LIST.add(TEST_HORIZONTAL_TEMPLATE_LIST_MODEL);
        TEST_TICK_TIME_TEMPLATE_MODEL = new TickTimeTemplateModel(TEST_PRIMARY_MESSAGE, TEST_TIME,
                TEST_TICK_TIME_TEMPLATE_ID);
        TEST_TICK_TIME_TEMPLATE_MODEL.setFirstTick(true);
        TEST_TICK_TIME_TEMPLATE_MODEL.setSecondTick(false);
        TEST_TEMPLATE_MODEL_LIST.add(TEST_TICK_TIME_TEMPLATE_MODEL);

        TEST_INFO_CARD_TEMPLATE_DATA = new MFCardData();
        TEST_INFO_CARD_TEMPLATE_DATA.deserialize(
                ChatRecyclerAdapterTestHelper.TEST_JSON_MF_INFO_CARD_TEMPLATE);

        TEST_INFO_CARD_TEMPLATE_MODEL = new MfInfoCardTemplateModel(
                TEST_INFO_CARD_TEMPLATE_ID,
                TEST_INFO_CARD_TEMPLATE_DATA
        );
        TEST_TEMPLATE_MODEL_LIST.add(TEST_INFO_CARD_TEMPLATE_MODEL);


        TEMPLATE_FACTORY = TemplateFactory.getInstance();
    }

    private ChatRecyclerAdapter mAdapter;
    private Context mMockContext;

    @Before
    public void setUp() throws Exception {
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        mAdapter = new ChatRecyclerAdapter(TEST_TEMPLATE_MODEL_LIST, TEMPLATE_FACTORY);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getItemViewType() throws Exception {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();
        assertEquals(
                (int) TEMPLATE_FACTORY.getTemplateIntID(TEST_SIMPLE_TEMPLATE_ID),
                mAdapter.getItemViewType(0)
        );

        assertEquals(
                (int) TEMPLATE_FACTORY.getTemplateIntID(TEST_SIMPLE_IMAGE_TEMPLATE_ID),
                mAdapter.getItemViewType(1)
        );
    }

    @Test
    public void onCreateViewHolder() throws Exception {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();
        ViewGroup testViewGroup = new LinearLayout(mMockContext);

        TemplateViewHolder actualSimpleTemplateViewHolder = mAdapter.onCreateViewHolder(testViewGroup, 1);
        assertTrue(actualSimpleTemplateViewHolder instanceof SimpleTemplateView.ViewHolder);

        TemplateViewHolder actualSimpleImageTemplateViewHolder = mAdapter.onCreateViewHolder(testViewGroup, 2);
        assertTrue(actualSimpleImageTemplateViewHolder instanceof SimpleImageTemplateView.ViewHolder);
    }

    @Test
    public void onBindViewHolder() throws Exception {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();
        ViewGroup testViewGroup = new LinearLayout(mMockContext);
        TemplateViewHolder simpleTemplateViewHolder = mAdapter.createViewHolder(testViewGroup, 1);

        mAdapter.onBindViewHolder(simpleTemplateViewHolder, 0);

        assertEquals(TEST_TITLE,
                ((SimpleTemplateView.ViewHolder) simpleTemplateViewHolder)
                        .getMessageTextView().getText().toString());
    }

    @Test
    public void getItemCount() throws Exception {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();

        assertEquals(TEST_TEMPLATE_MODEL_LIST.size(), mAdapter.getItemCount());
    }

    private void prepareTemplateFactory() {
        TEMPLATE_FACTORY.registerTemplate(TEST_SIMPLE_TEMPLATE_ID, new SimpleTemplateView(mMockContext));
        TEMPLATE_FACTORY.registerTemplate(TEST_SIMPLE_IMAGE_TEMPLATE_ID, new SimpleImageTemplateView(mMockContext));
        TEMPLATE_FACTORY.registerTemplate(TEST_SUB_TEMPLATE_LIST_ID, new HorizontalTemplateListView(mMockContext));
        TEMPLATE_FACTORY.registerTemplate(TEST_TICK_TIME_TEMPLATE_ID, new TickTimeTemplateView(mMockContext));
        TEMPLATE_FACTORY.registerTemplate(TEST_INFO_CARD_TEMPLATE_ID, new MfInfoCardTemplateView(mMockContext));
    }

    @Test
    public void testAddMessage() {
        mAdapter.addMessage(mock(TemplateModel.class));
        assertEquals(TEST_TEMPLATE_MODEL_LIST.size(), mAdapter.getItemCount());
    }


    @Test
    public void testMfInfoCardTemplate_dispalyMfInfoCardTemplate() {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();

        ViewGroup testViewGroup = new LinearLayout(mMockContext);
        TemplateViewHolder mfInfoCardViewHolder = mAdapter.createViewHolder(testViewGroup, 5);

        mAdapter.onBindViewHolder(mfInfoCardViewHolder, 4);
        assertEquals("Welcome to Active AI",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmTVMainHeading().getText().toString()
        );

        assertEquals("How can i help you ?",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmTVSubHeadingOne().getText().toString());
    }

    @Test
    public void testMfInfoCardTemplate_dispalyMfInfoCardWithButtonTemplate() {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();

        ViewGroup testViewGroup = new LinearLayout(mMockContext);
        TemplateViewHolder mfInfoCardViewHolder = mAdapter.createViewHolder(testViewGroup, 6);

        mAdapter.onBindViewHolder(mfInfoCardViewHolder, 5);

        assertEquals("Savign Account",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmTVMainHeading().getText().toString()
        );

        assertEquals("XXXX-1234",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmTVSubHeadingOne().getText().toString());


        assertEquals("Set As Default",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmButtonOne().getText().toString());

        assertEquals("Last Transaction",
                ((MfInfoCardTemplateView.ViewHolder) mfInfoCardViewHolder)
                        .getmButtonTwo().getText().toString()
        );

    }

    @Test
    public void testHorizontalSubTemplateList_displayHorizontalSubTemplateList() {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();

        ViewGroup testViewGroup = new LinearLayout(mMockContext);
        TemplateViewHolder horizontalViewHolder = mAdapter.createViewHolder(testViewGroup, 3);

        mAdapter.onBindViewHolder(horizontalViewHolder, 2);
        assertEquals(2,
                ((HorizontalTemplateListView.ViewHolder) horizontalViewHolder)
                        .getRecyclerView().getAdapter().getItemCount());
    }

    @Test
    public void testTemplateWithDoubleTick_displayTickTimeTemplate() {
        TEMPLATE_FACTORY.clearTemplates();
        prepareTemplateFactory();

        ViewGroup testViewGroup = new LinearLayout(mMockContext);
        TemplateViewHolder tickTimeViewHolder = mAdapter.createViewHolder(testViewGroup, 4);
        mAdapter.onBindViewHolder(tickTimeViewHolder, 3);

        assertEquals(TEST_PRIMARY_MESSAGE,
                ((TickTimeTemplateView.ViewHolder) tickTimeViewHolder)
                        .getMessageTextView().getText().toString());
        assertEquals(TEST_TIME,
                ((TickTimeTemplateView.ViewHolder) tickTimeViewHolder)
                        .getTimeTextView().getText().toString());

        assertTrue(((TickTimeTemplateView.ViewHolder) tickTimeViewHolder).isFirstTickVisible());
        assertFalse(((TickTimeTemplateView.ViewHolder) tickTimeViewHolder).isSecondTickVisible());
    }
}

