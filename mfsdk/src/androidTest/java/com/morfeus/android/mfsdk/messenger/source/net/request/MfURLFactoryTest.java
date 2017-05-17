package com.morfeus.android.mfsdk.messenger.source.net.request;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.messenger.source.MfService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MfURLFactoryTest {
    private MfURLFactory mFactory;

    private Uri.Builder mMockUriBuilder;

    @Before
    public void setUp() throws Exception {
        mMockUriBuilder = new Uri.Builder();
        mFactory = MfURLFactory.getInstance();
    }

    @Test
    public void testGetUrl_returnUrlStr() {
        MfURLModel urlModel = new MfURLModel();
        urlModel.addPath("botId", "232342423");
        String url = mFactory.getUrl(MfService.INIT_POST_REQUEST, urlModel);
        System.out.println("url>> " + url);
        assertNotNull(url);
        assertTrue(url.length() > 0);
        String expectedUrl
                = "https://morfeus-demo.active.ai/morfeus/v1/channels/232342423/init";
        assertEquals(expectedUrl, url);

        url = mFactory.getUrl(MfService.MESSAGE_POST_REQUEST, urlModel);
        expectedUrl
                = "https://morfeus-demo.active.ai/morfeus/v1/channels/232342423/message";
        assertNotNull(url);
        assertTrue(url.length() > 0);
        assertEquals(expectedUrl, url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUrl_throwException() {
        mFactory.getUrl(-1, new MfURLModel());
    }
}