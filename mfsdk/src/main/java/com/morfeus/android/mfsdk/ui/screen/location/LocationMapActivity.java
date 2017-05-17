package com.morfeus.android.mfsdk.ui.screen.location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.morfeus.android.R;

public class LocationMapActivity extends AppCompatActivity {
    public static final String KEY_SCREEN_MODE = "screenMode";
    public static final String KEY_LOCATION_TO_SHOW = "locationToShowKey";
    public static final String KEY_LOCATION_RESULT = "locationResultKey";

    public static final int SCREEN_MODE_SEND_LOCATION = 0;
    public static final int SCREEN_MODE_SHOW_LOCATION = 1;
    public static final int REQUEST_LOCATION = 111;

    private int mCurrentScreenMode;
    private Fragment mCurrentFragment;
    private LatLng mLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        mCurrentScreenMode = getIntent()
                .getIntExtra(KEY_SCREEN_MODE, SCREEN_MODE_SEND_LOCATION);
        mLatLong = getIntent()
                .getParcelableExtra(KEY_LOCATION_TO_SHOW);
        checkIfHavingLocationPermission();
        enableGPS();
        launchFragmentAsPerMode();
    }

    private void launchFragmentAsPerMode() {
        switch (mCurrentScreenMode) {
            case SCREEN_MODE_SEND_LOCATION:
                mCurrentFragment = new SendLocationFragment();
                break;
            case SCREEN_MODE_SHOW_LOCATION:
                mCurrentFragment = new ShowLocationFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ShowLocationFragment.KEY_LOCATION, mLatLong);
                mCurrentFragment.setArguments(bundle);
                break;
        }

        if (mCurrentFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout,
                            mCurrentFragment,
                            mCurrentFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private void checkIfHavingLocationPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length >= 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mCurrentFragment instanceof SendLocationFragment) {
                        ((SendLocationFragment) mCurrentFragment)
                                .showCurrentLocation();
                    }
                } else {
                    Toast.makeText(LocationMapActivity.this,
                            "Location Permission is must.", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


    public void setStatusBarColor(int colorCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorCode);
        }
    }

    private void enableGPS() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.enableGPSText));
            builder.setMessage(getResources().getString(R.string.enableGPSBody));
            builder.setPositiveButton(getResources().getString(R.string.settingsGPSText)
                    , new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LocationMapActivity.this
                                    .startActivity(new Intent(android.provider.Settings
                                            .ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            builder.setNegativeButton(getResources().getString(R.string.text_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.setCancelable(false);
            builder.create().show();
        }

    }
}
