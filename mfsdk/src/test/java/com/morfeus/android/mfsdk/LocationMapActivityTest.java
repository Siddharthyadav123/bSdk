package com.morfeus.android.mfsdk;

import com.morfeus.android.BuildConfig;
import com.morfeus.android.mfsdk.ui.screen.location.LocationMapActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class LocationMapActivityTest {
    private LocationMapActivity mLocationMapActivity;

    @Before
    public void setUp() throws Exception {
        mLocationMapActivity = Robolectric.buildActivity(LocationMapActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull("Unable to launch Location Map Activity", mLocationMapActivity);
    }


}
