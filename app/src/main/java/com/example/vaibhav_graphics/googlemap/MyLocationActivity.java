package com.example.vaibhav_graphics.googlemap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vaibhav_graphics.R;
import com.example.vaibhav_graphics.databinding.ActivityMylocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMylocationBinding binding;

    LocationManager locationManager;
    public final int REQUEST_LOCATION_SERVICE_CODE = 999;

    double latitude, longitude;
    Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMylocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.fabLayers.setOnClickListener(v -> showMapTypeDialog());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_SERVICE_CODE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
            startLocationUpdates();
        }
    }

    private void startLocationUpdates()
    {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                5,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        LatLng myLocation = new LatLng(latitude, longitude);

                        if (currentMarker == null) {
                            currentMarker = mMap.addMarker(new MarkerOptions()
                                    .position(myLocation)
                                    .title("My Location")
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        } else {
                            currentMarker.setPosition(myLocation);
                        }

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(myLocation)
                                .zoom(18)
                                .tilt(60)
                                .bearing(30)
                                .build();

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
        );
    }

    private void showMapTypeDialog()
    {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottum_map, null);

        LinearLayout defaultMap = view.findViewById(R.id.layoutDefault);
        LinearLayout satelliteMap = view.findViewById(R.id.layoutSatellite);
        LinearLayout terrainMap = view.findViewById(R.id.layoutTerrain);

        defaultMap.setOnClickListener(v ->
        {
               mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                 dialog.dismiss();
        });

        satelliteMap.setOnClickListener(v ->
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            dialog.dismiss();
        });

        terrainMap.setOnClickListener(v ->
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }
}