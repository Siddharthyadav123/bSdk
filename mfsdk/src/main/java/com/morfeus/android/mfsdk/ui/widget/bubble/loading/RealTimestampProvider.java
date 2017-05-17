package com.morfeus.android.mfsdk.ui.widget.bubble.loading;

/**
 * Created by User on 27/2/2017.
 */

public class RealTimestampProvider implements TimestampProvider {
    @Override
    public long timestamp() {
        return System.currentTimeMillis();
    }
}
