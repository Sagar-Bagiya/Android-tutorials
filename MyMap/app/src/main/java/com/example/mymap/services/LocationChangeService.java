package com.example.mymap.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class LocationChangeService extends Service {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final String TAG = "LocationChangeService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "...Service is started...........");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Call your desired method or perform actions in response to the location change
                handleLocationChange(location);
            }

            // Other methods of LocationListener

            // ...
        };

        // Request location updates
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove location updates when the service is destroyed
        locationManager.removeUpdates(locationListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleLocationChange(Location location) {
        Log.d(TAG, "handleLocationChange: location is . . . " + location.getLatitude());
    }

    // Rest of the Service implementation...
}

