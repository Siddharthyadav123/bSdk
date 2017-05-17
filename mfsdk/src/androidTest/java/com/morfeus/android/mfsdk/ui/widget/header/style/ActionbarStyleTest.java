package com.morfeus.android.mfsdk.ui.widget.header.style;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ActionbarStyleTest {

    private static final String TEST_BACKGROUND_COLOR = "BLUE";
    private static final String TEST_BACKGROUND_IMAGE = "BackgroundImage.png";
    private ActionbarStyle mActionbarStyle;

    @Before
    public void setUp() throws Exception {
        mActionbarStyle = new ActionbarStyle();
        mActionbarStyle.setBackgroundColor(TEST_BACKGROUND_COLOR);
        mActionbarStyle.setBackgroundImage(TEST_BACKGROUND_IMAGE);
    }

    @Test
    public void testActionbarStyle_parcelable() {
        Parcel parcel = Parcel.obtain();
        mActionbarStyle.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        ActionbarStyle parceledModel = ActionbarStyle.CREATOR.createFromParcel(parcel);
        assertEquals(mActionbarStyle, parceledModel);
    }

}