package com.example.steven.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tyler on 3/5/2018.
 */

@SuppressWarnings("ALL")
public class InteractiveMap extends FragmentActivity implements OnMapReadyCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleMap mGoogleMap;
    List<Marker> mMarkerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactive_map);

        // check to make sure the correct build for location services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(
                R.id.interactive_map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // add markers for buildings and plants
        addMapMarkers(googleMap);

        // remove google places icons
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        // check to ensure user has granted permission for location
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api, no need to prompt
            googleMap.setMyLocationEnabled(true);
        }

        // create LatLngBounds for the CI campus
        LatLngBounds CI = new LatLngBounds(new LatLng(34.161593, -119.049312),
                new LatLng(34.163393, -119.038105));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(34.162081, -119.043616)).zoom(16).build();

        // constrain the camera target to the CI campus bounds.
        googleMap.setLatLngBoundsForCameraTarget(CI);
        googleMap.setMinZoomPreference((float) 16);

        // set other flags
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                for (Marker m : mMarkerList) {
                    m.setVisible(cameraPosition.zoom >= 16 && cameraPosition.zoom <= 19);
                }
            }
        });
    }

    public void addMapMarkers(GoogleMap googleMap) {
        int numberOfBuildings = 26;
        String[] buildingNames =
                {"Aliso Hall", "Arroyo Hall", "Bell Tower", "Bell Tower East", "Bell Tower West",
                        "Broome Library", "Chaparral Hall", "Del Norte Hall", "El Dorodo Hall",
                        "Ironwood Hall", "Islands Cafe", "Lindero Hall", "Madera Hall",
                        "Malibu Hall", "Manzanita Hall", "Martin V Smith", "Modoc Hall",
                        "Napa Hall", "Ojai Hall", "Placer Hall", "Sage Hall", "Sierra Hall",
                        "Solano Hall", "Student Union Building",
                        "Topanga Hall",
                        "University Hall", "Yuba Hall"};
        List<LatLng> buildingList = new ArrayList<>();

        LatLng ALISO_HALL = new LatLng(34.161318, -119.045174);
        buildingList.add(ALISO_HALL);
        LatLng ARROYO_HALL = new LatLng(34.160347, -119.044755);
        buildingList.add(ARROYO_HALL);
        LatLng BELL_TOWER = new LatLng(34.161290, -119.043218);
        buildingList.add(BELL_TOWER);
        LatLng BELL_TOWER_EAST = new LatLng(34.161288, -119.041975);
        buildingList.add(BELL_TOWER_EAST);
        LatLng BELL_TOWER_WEST = new LatLng(34.160681, -119.044335);
        buildingList.add(BELL_TOWER_WEST);
        LatLng BROOME_LIBRARY = new LatLng(34.162629, -119.041144);
        buildingList.add(BROOME_LIBRARY);
        LatLng CHAPARRAL_HALL = new LatLng(34.162314, -119.045588);
        buildingList.add(CHAPARRAL_HALL);
        LatLng DEL_NORTE_HALL = new LatLng(34.163187, -119.043969);
        buildingList.add(DEL_NORTE_HALL);
        LatLng EL_DORODO_HALL = new LatLng(34.164221, -119.047091);
        buildingList.add(EL_DORODO_HALL);
        LatLng IRONWOOD_HALL = new LatLng(34.162705, -119.045746);
        buildingList.add(IRONWOOD_HALL);
        LatLng ISLANDS_CAFE = new LatLng(34.160399, -119.042005);
        buildingList.add(ISLANDS_CAFE);
        LatLng LINDERO_HALL = new LatLng(34.159569, -119.041212);
        buildingList.add(LINDERO_HALL);
        LatLng MADERA_HALL = new LatLng(34.162804, -119.044328);
        buildingList.add(MADERA_HALL);
        LatLng MALIBU_HALL = new LatLng(34.161165, -119.041187);
        buildingList.add(MALIBU_HALL);
        LatLng MANZANITA_HALL = new LatLng(34.162681, -119.045144);
        buildingList.add(MANZANITA_HALL);
        LatLng MARTIN_V_SMITH = new LatLng(34.163921, -119.043126);
        buildingList.add(MARTIN_V_SMITH);
        LatLng MODOC_HALL = new LatLng(34.164007, -119.048285);
        buildingList.add(MODOC_HALL);
        LatLng NAPA_HALL = new LatLng(34.163723, -119.045433);
        buildingList.add(NAPA_HALL);
        LatLng OJAI_HALL = new LatLng(34.161817, -119.042355);
        buildingList.add(OJAI_HALL);
        LatLng PLACER_HALL = new LatLng(34.163263, -119.042980);
        buildingList.add(PLACER_HALL);
        LatLng SAGE_HALL = new LatLng(34.164073, -119.042303);
        buildingList.add(SAGE_HALL);
        LatLng SIERRA_HALL = new LatLng(34.162208, -119.044685);
        buildingList.add(SIERRA_HALL);
        LatLng SOLANO_HALL = new LatLng(34.163303, -119.045333);
        buildingList.add(SOLANO_HALL);
        LatLng STUDENT_UNION_BUILDING = new LatLng(34.161412, -119.044627);
        buildingList.add(STUDENT_UNION_BUILDING);
        LatLng TOPANGA_HALL = new LatLng(34.160027, -119.041502);
        buildingList.add(TOPANGA_HALL);
        LatLng UNIVERSITY_HALL = new LatLng(34.162586, -119.043704);
        buildingList.add(UNIVERSITY_HALL);
        LatLng YUBA_HALL = new LatLng(34.163986, -119.041088);
        buildingList.add(YUBA_HALL);

        for (int buildingIndex = 0; buildingIndex < numberOfBuildings; buildingIndex++) {
            LatLng building = buildingList.get(buildingIndex);
            String buildingName = buildingNames[buildingIndex];
            Marker marker = googleMap.addMarker(
                    new MarkerOptions().position(building).title(buildingName));
            mMarkerList.add(marker);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    // permission denied,Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
