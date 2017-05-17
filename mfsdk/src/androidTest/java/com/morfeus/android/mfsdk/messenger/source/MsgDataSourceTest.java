package com.morfeus.android.mfsdk.messenger.source;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.messenger.source.parser.MsgParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MsgDataSourceTest {

    private MsgDataSource mMsgDataSource;

    private MsgParser mockMsgParser;

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getTargetContext();
        mockMsgParser = mock(MsgParser.class);
        mMsgDataSource = MsgDataSource.createInstance(mContext, mockMsgParser);
    }

    @Test
    public void testLoadDefaultMsg_callMsgParser() {
        mMsgDataSource.loadDefaultMsg();
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockMsgParser, times(1)).parse(true, argumentCaptor.capture());
    }

}