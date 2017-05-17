package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TemplateFactoryTest {

    private static final String TEST_TEMPLATE_ID1 = "temp_01";

    private static final String TEST_TEMPLATE_ID2 = "temp_02";

    private TemplateFactory templateFactory;

    @Mock private SimpleTemplateView mockTemplateView1;

    @Mock private SimpleImageTemplateView mockTemplateView2;

    private Context mMockContext;

    @Before
    public void setUp() throws Exception {
        templateFactory = TemplateFactory.getInstance();
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        MockitoAnnotations.initMocks(this);
        when(mockTemplateView1.inflate(mMockContext))
                .thenReturn(mockTemplateView1);

        when(mockTemplateView2.inflate(mMockContext))
                .thenReturn(mockTemplateView2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterTemplate_failOnRegisteringTemplateWithExistingID() {
        templateFactory.clearTemplates();
        templateFactory.registerTemplate(TEST_TEMPLATE_ID1, mockTemplateView1);
        templateFactory.registerTemplate(TEST_TEMPLATE_ID1, mockTemplateView1);
    }

    @Test
    public void testRegisterTemplateWithUniqueID_returnSameTemplateForSameID() {
        templateFactory.clearTemplates();
        templateFactory.registerTemplate(TEST_TEMPLATE_ID1, mockTemplateView1);
        templateFactory.registerTemplate(TEST_TEMPLATE_ID2, mockTemplateView2);

        assertEquals(mockTemplateView1,
                templateFactory.createTemplate(TEST_TEMPLATE_ID1, mMockContext));
        assertEquals(mockTemplateView2,
                templateFactory.createTemplate(TEST_TEMPLATE_ID2, mMockContext));
    }

    @Test
    public void testCreateTemplateWithStrID_returnsValidTemplate() {
        templateFactory.clearTemplates();
        templateFactory.registerTemplate(TEST_TEMPLATE_ID1, mockTemplateView1);
        templateFactory.registerTemplate(TEST_TEMPLATE_ID2, mockTemplateView2);

        Integer templateIntID = templateFactory.getTemplateIntID(TEST_TEMPLATE_ID1);
        assertEquals(mockTemplateView1, templateFactory.createTemplate(templateIntID, mMockContext));
    }

    @After
    public void after() {
        templateFactory.clearTemplates();
    }

}