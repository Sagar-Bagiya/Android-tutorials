package com.example.mymap.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationUpdateReceiver extends BroadcastReceiver {

    private static final String TAG = "LocationUpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
            // Check if the location provider is enabled or disabled
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isLocationEnabled) {
                // Start a service or perform any other necessary actions to handle location updates
                Intent serviceIntent = new Intent(context, LocationTrackingService.class);
                context.startService(serviceIntent);
                Log.d(TAG, "Location tracking service started");
            } else {
                // Stop the service or perform any other necessary actions
                Intent serviceIntent = new Intent(context, LocationTrackingService.class);
                context.stopService(serviceIntent);
                Log.d(TAG, "Location tracking service stopped");
            }
        }
    }
}
