package com.morfeus.android.mfsdk.messenger.source.net;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.messenger.session.MfTimeoutWorkerThread;
import com.morfeus.android.mfsdk.volley.Request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@Config(constants = BuildConfig.class)
@PrepareForTest(RestClient.class)
public class MfRemoteServiceTest {
    private RestClient mockRestClient;
    private MfTimeoutWorkerThread mockTimeoutWorkerThread;
    private Request mockRequest;

    @Before
    public void setUp() throws Exception {
        mockRestClient = PowerMockito.mock(RestClient.class);
        mockTimeoutWorkerThread = mock(MfTimeoutWorkerThread.class);
        mockRequest = mock(Request.class);
    }

    @Test
    public void testInitRequest_addToRequestQueue() {
        MfRemoteService.getInstance(mockRestClient, mockTimeoutWorkerThread)
                .init(mockRequest);
        verify(mockRestClient).addToRequestQueue(mockRequest);
        MfRemoteService.destroyInstance();
    }

    @Test
    public void testSendMessageRequest_addToRequestQueue() {
        MfRemoteService.getInstance(mockRestClient, mockTimeoutWorkerThread)
                .sendMessage(mockRequest);
        verify(mockRestClient).addToRequestQueue(mockRequest);
        MfRemoteService.destroyInstance();
    }

}