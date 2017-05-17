package com.morfeus.android.mfsdk.ui.widget.header.model;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.morfeus.android.mfsdk.ui.widget.header.style.ActionbarStyle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ActionbarModelTest {
    private static final String TEST_ACTIONBAR_ID = "actionbar";
    private static final ActionbarModel.Text TEST_TITLE_TEXT = new ActionbarModel.Text();
    private static final ActionbarModel.Text TEST_SUBTITLE_TEXT = new ActionbarModel.Text();
    private ActionbarModel mActionbarModel;

    @Before
    public void setUp() throws Exception {
        mActionbarModel = new ActionbarModel();
        mActionbarModel.setId(TEST_ACTIONBAR_ID);
        mActionbarModel.setButtons(new ArrayList<ActionbarModel.Button>());
        mActionbarModel.setProfile(new ActionbarModel.Profile());
        mActionbarModel.setStyle(new ActionbarStyle());
        TEST_TITLE_TEXT.setId("title");
        TEST_SUBTITLE_TEXT.setId("subTitle");
        List<ActionbarModel.Text> textList = new ArrayList<ActionbarModel.Text>();
        textList.add(TEST_TITLE_TEXT);
        textList.add(TEST_SUBTITLE_TEXT);

        mActionbarModel.setTexts(textList);
    }

    @Test
    public void testActionbarModel_parcelable() {
        Parcel parcel = Parcel.obtain();
        mActionbarModel.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        ActionbarModel modelFromParcel = ActionbarModel.CREATOR.createFromParcel(parcel);
        assertEquals(mActionbarModel, modelFromParcel);
    }

}