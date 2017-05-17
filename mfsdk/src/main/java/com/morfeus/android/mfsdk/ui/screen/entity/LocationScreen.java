package com.morfeus.android.mfsdk.ui.screen.entity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocationScreen implements Screen {

    private Context mParentContext;
    private LocationScreenModel mLocationScreenModel;

    public LocationScreen(Context context, LocationScreenModel locationScreenModel) {
        mParentContext = checkNotNull(context);
        mLocationScreenModel = locationScreenModel;
    }

    public LocationScreenModel getLocationScreenModel() {
        return mLocationScreenModel;
    }

    public Context getParentContext() {
        return mParentContext;
    }

    @Override
    public void setParentContext(Context ctx) {
        mParentContext = ctx;
    }

    @NonNull
    @Override
    public String getType() {
        return Screen.LOCATION_SCREEN;
    }
}
