package com.example.mymap.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mymap.R;
import com.example.mymap.databinding.ActivityMapsBinding;
import com.example.mymap.services.LocationTrackingService;
import com.example.mymap.strorage.LocationStorage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private LocationStorage locationStorage;
    private LatLng pLatLng = new LatLng(0, 0);
    private boolean isDevicePathVisible = false;

    private Marker currentDeviceMarker;

    private Polyline Dpolyline;
    private static final String TAG = "MapsActivity";

    private FusedLocationProviderClient fusedLocationClient;

    private List<LatLng> deviceLatLngList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        deviceLatLngList = new ArrayList<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationStorage = new LocationStorage(this);

        binding.goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationStorage.clearLatLngList();
                if (mMap != null)
                    mMap.clear();

                if (isForegroundServiceRunning(LocationTrackingService.class))
                    stopLocationTrackingService();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        List<LatLng> latLngList = locationStorage.getLatLngList();
        if (latLngList.size() > 0) {
            Log.d(TAG, "onMapReady: latlong list is....." + latLngList.size());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0), 12f));
            PolylineOptions opt = new PolylineOptions().addAll(latLngList).width(5f).color(Color.RED);
            if (Dpolyline != null)
                Dpolyline.remove();
            Dpolyline = mMap.addPolyline(opt);
        }

        binding.btnDrawPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDevicePathVisible) {
                    isDevicePathVisible = false;
                    destroyLocationUpdate();
                    stopLocationTrackingService();
                    deviceLatLngList.clear();

                } else {
                    isDevicePathVisible = true;
                    requestLocationUpdates();
                    startLocationTrackingService();
                }
            }
        });

    }

    private Marker deviceMarker;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult != null) {
                Location location = locationResult.getLastLocation();
                // Handle the new location

                if (mMap != null) {
                    LatLng deviceLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLatLng));
                    if (deviceMarker != null)
                        deviceMarker.remove();
                    deviceMarker = mMap.addMarker(new MarkerOptions().position(deviceLatLng).title("it' Me"));
                }
            }
        }
    };


    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000) // Update interval in milliseconds
                .setFastestInterval(5000); // Fastest update interval in milliseconds


        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }


    private void stopLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
    }

    private void startLocationTrackingService() {
        // Start the location tracking service
        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        startService(serviceIntent);

    }

    private void stopLocationTrackingService() {
        // Stop the location tracking service
        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        stopService(serviceIntent);
    }

    private void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000); // Update interval in milliseconds

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        for (Location location : locationResult.getLocations()) {
                            // Use the obtained location
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Log.d(TAG, "Updated location: " + latitude + ", " + longitude);
                            handleLocationUpdate(location);
                        }
                    }
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            // Location permission not granted
            Log.e(TAG, "Location permission not granted");
        }
    }

    private void destroyLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void handleLocationUpdate(Location location) {
        // Handle the location update
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (latLng.latitude != pLatLng.latitude && latLng.longitude != pLatLng.longitude) {
            Log.d(TAG, "New location: " + location.getLatitude() + ", " + location.getLongitude());
            pLatLng = latLng;
            deviceLatLngList.add(latLng);

//            deviceLatLngList.add(latLng);
//            if (deviceLatLngList.size() > 0) {
//                locationStorage.saveLatLngList(deviceLatLngList);
//            }

            if (mMap != null) {
                LatLng deviceLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLatLng));

                if (deviceMarker != null)
                    deviceMarker.remove();
                deviceMarker = mMap.addMarker(new MarkerOptions().position(deviceLatLng).title("it' Me"));

                if (deviceLatLngList.size() > 0) {
                    PolylineOptions opt = new PolylineOptions().addAll(deviceLatLngList).width(5f).color(Color.RED);
                    if (Dpolyline != null)
                        Dpolyline.remove();
                    Dpolyline = mMap.addPolyline(opt);
                }
            }

        }
    }

    public boolean isForegroundServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);

        if (runningServices != null) {
            for (ActivityManager.RunningServiceInfo service : runningServices) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        // The service is running as a foreground service
                        return true;
                    } else {
                        // The service is running, but not as a foreground service
                        return false;
                    }
                }
            }
        }

        // The service is not running
        return false;
    }

}