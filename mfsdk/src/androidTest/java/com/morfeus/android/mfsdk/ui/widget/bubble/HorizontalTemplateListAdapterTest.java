package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.morfeus.android.mfsdk.messenger.source.entity.MFCardData;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.MfInfoCardTemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class HorizontalTemplateListAdapterTest {

    private static final String TEST_INFO_CARD_TEMPLATE_ID = "InfoCardTemplate";
    private static List<TemplateModel> testTemplateModels =
            new ArrayList<>();

    private TemplateFactory mMockTemplateFactory;
    private HorizontalTemplateListAdapter mAdapter;
    private Context mMockContext;
    private TemplateView mMockTemplateView;
    private TemplateViewHolder mMockTemplateVH;

    @Before
    public void setUp() throws Exception {
        mMockContext = InstrumentationRegistry.getTargetContext();
        mMockTemplateFactory = mock(TemplateFactory.class);
        mMockTemplateView = mock(TemplateView.class);
        mMockTemplateVH = mock(TemplateViewHolder.class);

        MFCardData mockMFCardData = mock(MFCardData.class);
        MfInfoCardTemplateModel TEST_INFO_CARD_TEMPLATE_MODEL = new MfInfoCardTemplateModel(
                TEST_INFO_CARD_TEMPLATE_ID,
                mockMFCardData
        );
        testTemplateModels.add(TEST_INFO_CARD_TEMPLATE_MODEL);

        mAdapter = new HorizontalTemplateListAdapter(testTemplateModels, mMockTemplateFactory);
    }

    @Test
    public void testGetItemViewType() throws Exception {
        int testPosition = 0;
        int expectedTemplateIntId = 1;

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        when(mMockTemplateFactory
                .getTemplateIntID(argumentCaptor.capture())).thenReturn(expectedTemplateIntId);

        int actualTemplateId = mAdapter.getItemViewType(testPosition);

        assertThat(argumentCaptor.getValue(), equalTo(TEST_INFO_CARD_TEMPLATE_ID));
        assertThat(actualTemplateId, equalTo(expectedTemplateIntId));
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {
        Integer testTemplateType = 1;
        ViewGroup mockVG = new LinearLayout(mMockContext);

        when(mMockTemplateFactory.createTemplate(anyInt(), any(Context.class)))
                .thenReturn(mMockTemplateView);

        mAdapter.onCreateViewHolder(mockVG, testTemplateType);

        verify(mMockTemplateFactory, times(1)).createTemplate(anyInt(), any(Context.class));
        verify(mMockTemplateView, times(1)).createViewHolder(any(BaseView.class));
    }

    @Test
    public void testOnBindViewHolder() throws Exception {
        int testPosition = 0;
        mAdapter.onBindViewHolder(mMockTemplateVH, testPosition);

        ArgumentCaptor<TemplateModel> argumentCaptor = ArgumentCaptor.forClass(TemplateModel.class);
        verify(mMockTemplateVH, times(1)).setData(argumentCaptor.capture());
    }

    @Test
    public void getItemCount() throws Exception {
        assertEquals(testTemplateModels.size(), mAdapter.getItemCount());
    }

    @Test
    public void testAddMessage() {
        mAdapter.addMessage(mock(TemplateModel.class));
        assertEquals(testTemplateModels.size(), mAdapter.getItemCount());
    }

}