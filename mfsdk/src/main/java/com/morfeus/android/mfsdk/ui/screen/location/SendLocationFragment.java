package com.morfeus.android.mfsdk.ui.screen.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManager;
import com.morfeus.android.mfsdk.ui.config.ConfigManagerImpl;
import com.morfeus.android.mfsdk.ui.config.exception.ScreenNotFoundException;
import com.morfeus.android.mfsdk.ui.lang.LanguageRepository;
import com.morfeus.android.mfsdk.ui.screen.entity.LocationScreen;
import com.morfeus.android.mfsdk.ui.screen.entity.Screen;
import com.morfeus.android.mfsdk.ui.widget.editor.model.LocationScreenModel;
import com.morfeus.android.mfsdk.ui.widget.editor.style.LocationScreenStyle;

import java.util.HashMap;

public class SendLocationFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = ShowLocationFragment.class.getSimpleName();

    private View mRootView;
    private RelativeLayout mMapBodyRelativeLayout;
    private RelativeLayout mFooterRelativeLayout;
    private ImageView mLeftImage;
    private TextView mLeftText;
    private ImageView mRightImage;
    private LinearLayout mActinBarLinearLayout;
    private ImageView mActionbarLeftButton;
    private TextView mActionbarHeaderText;

    private LatLng mMyLatLong;
    private String mMapTypeString;

    protected GoogleMap mMap;

    public SendLocationFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        makeView();
        setScreenStyle();
        setUpMap();
        return mRootView;
    }


    private void makeView() {
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_location_map_layout,
                null, false);

        mMapBodyRelativeLayout = (RelativeLayout) mRootView.findViewById(R.id.rl_body_layout);
        mFooterRelativeLayout = (RelativeLayout) mRootView.findViewById(R.id.footer_rel_layout);
        mLeftImage = (ImageView) mRootView.findViewById(R.id.iv_left_image);
        mLeftText = (TextView) mRootView.findViewById(R.id.tv_left_text);
        mRightImage = (ImageView) mRootView.findViewById(R.id.iv_right_image);
        mActinBarLinearLayout = (LinearLayout) mRootView.findViewById(R.id.lly_actionbar);
        mActionbarLeftButton = (ImageView) mRootView.findViewById(R.id.ivLeftButton);
        mActionbarHeaderText = (TextView) mRootView.findViewById(R.id.tvHeaderText);
        mRightImage.setVisibility(View.GONE);

        mActionbarLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        mRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMyLatLong != null) {
                    Intent intent = new Intent();
                    intent.putExtra(LocationMapActivity.KEY_LOCATION_RESULT, mMyLatLong);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Unable to find your location.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setScreenStyle() {
        try {
            ConfigManager configManager = ConfigManagerImpl.getInstance();
            LocationScreen locationScreen = (LocationScreen) configManager.getScreen(Screen.LOCATION_SCREEN);
            LocationScreenModel locationScreenModel = locationScreen.getLocationScreenModel();
            if (locationScreenModel != null) {
                LocationScreenModel.Header header = locationScreenModel.getHeader();
                LocationScreenModel.Body body = locationScreenModel.getBody();
                LocationScreenModel.Footer footer = locationScreenModel.getFooter();
                styleHeader(header);
                styleBody(body);
                styleFooter(footer);
            }
        } catch (ScreenNotFoundException e) {
            LogManager.e(TAG, e.getMessage());
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }

    }

    private void styleHeader(LocationScreenModel.Header header) {
        try {
            if (header != null) {
                LocationScreenStyle style = header.getStyle();
                LocationScreenModel.ImageModel imageModel = header.getImageModel();
                LocationScreenModel.TextModel textModel = header.getTextModel();

                if (style != null) {
                    String headerBGColor = style.getBackgroundColor();
                    if (headerBGColor != null) {
                        int bgColor = Color.parseColor(headerBGColor);
                        mActinBarLinearLayout.setBackgroundColor(bgColor);
                        ((LocationMapActivity) getActivity()).setStatusBarColor(bgColor);
                    }
                }

                if (imageModel != null) {
                    mActionbarLeftButton.setImageResource(getResourceId(imageModel.getImage()));
                }

                if (textModel != null) {
                    mActionbarHeaderText.setText(LanguageRepository.getInstance()
                            .getText(textModel.getText()));

                    if (textModel.getStyle() != null) {
                        LocationScreenStyle headerStyle = header.getStyle();
                        mActionbarHeaderText.setTextColor(Color.parseColor(headerStyle.getTextColor()));
                    }
                }
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    private void styleBody(LocationScreenModel.Body body) {
        try {
            if (body != null) {
                LocationScreenStyle style = body.getStyle();
                LocationScreenModel.Map map = body.getMap();
                if (style != null) {
                    String bGColor = style.getBackgroundColor();
                    if (bGColor != null) {
                        int bgColor = Color.parseColor(bGColor);
                        mMapBodyRelativeLayout.setBackgroundColor(bgColor);
                    }
                }
                if (map != null) {
                    mMapTypeString = map.getType();
                }
            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    private void styleFooter(LocationScreenModel.Footer footer) {
        try {
            if (footer != null) {
                LocationScreenStyle style = footer.getStyle();
                HashMap<String, LocationScreenModel.FooterStrip> fotterStripMap
                        = footer.getFotterStripMap();

                if (style != null) {
                    String footerBGColor = style.getBackgroundColor();
                    if (footerBGColor != null) {
                        int bgColor = Color.parseColor(footerBGColor);
                        mFooterRelativeLayout.setBackgroundColor(bgColor);
                    }
                }

                if (fotterStripMap != null) {
                    LocationScreenModel.FooterStrip sendLocationFooterStrip
                            = fotterStripMap.get("sendlocation");
                    if (sendLocationFooterStrip != null) {
                        LocationScreenModel.ImageModel leftImageModel
                                = sendLocationFooterStrip.getLeftImageModel();
                        LocationScreenModel.ImageModel rightImageModel
                                = sendLocationFooterStrip.getRightImageModel();
                        LocationScreenModel.TextModel textModel
                                = sendLocationFooterStrip.getTextModel();

                        if (leftImageModel != null) {
                            mLeftImage.setImageResource(getResourceId(leftImageModel.getImage()));
                        }

                        if (rightImageModel != null) {
                            mRightImage.setImageResource(getResourceId(rightImageModel.getImage()));
                        }

                        if (textModel != null) {
                            mLeftText.setText(LanguageRepository.getInstance()
                                    .getText(textModel.getText()));
                            if (textModel.getStyle() != null) {
                                LocationScreenStyle headerStyle = textModel.getStyle();
                                mLeftText.setTextColor(Color.parseColor(headerStyle.getTextColor()));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            LogManager.e(TAG, e.getMessage());
        }
    }

    private int getResourceId(String imageName) {
        try {
            Resources resources = getActivity().getResources();
            return resources.getIdentifier(imageName, "drawable",
                    getActivity().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_camera_off;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int mapType = getMapType(mMapTypeString);
        mMap.setMapType(mapType);

        if (ActivityCompat.checkSelfPermission(getActivity()
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        showCurrentLocation();
        enableMyLocation();
    }

    public void showCurrentLocation() {
        Location location = getLocation();
        if (location != null && mMap != null) {
            mMyLatLong = new LatLng(location.getLatitude()
                    , location.getLongitude());
            mRightImage.setVisibility(View.VISIBLE);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLatLong, 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    private void enableMyLocation() {
        mMap.setMyLocationEnabled(true);
        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private int getMapType(String mapTypeString) {
        if (mapTypeString != null) {
            if ("MAP_TYPE_NONE".equals(mapTypeString)) {
                return GoogleMap.MAP_TYPE_NONE;
            } else if ("MAP_TYPE_NORMAL".equals(mapTypeString)) {
                return GoogleMap.MAP_TYPE_NORMAL;
            } else if ("MAP_TYPE_SATELLITE".equals(mapTypeString)) {
                return GoogleMap.MAP_TYPE_SATELLITE;
            } else if ("MAP_TYPE_TERRAIN".equals(mapTypeString)) {
                return GoogleMap.MAP_TYPE_TERRAIN;
            } else if ("MAP_TYPE_HYBRID".equals(mapTypeString)) {
                return GoogleMap.MAP_TYPE_HYBRID;
            }
        }
        return GoogleMap.MAP_TYPE_NORMAL;
    }

    private Location getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        String provider_info = null;
        if (isNetworkEnabled) {
            provider_info = LocationManager.NETWORK_PROVIDER;
        } else if (isGPSEnabled) {
            provider_info = LocationManager.GPS_PROVIDER;
        }
        if (locationManager != null && provider_info != null) {
            return locationManager.getLastKnownLocation(provider_info);
        }
        return null;
    }

}
