package com.example.mymap.services;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class LocationChangeServiceForground extends Service {

    private static final int NOTIFICATION_ID = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final String TAG = "LocationChangeServiceFo";

    @Override
    public void onCreate() {
        super.onCreate();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle the location change
                handleLocationChange(location);
            }

            // Other methods of LocationListener

            // ...
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleLocationChange(Location location) {

        Log.d(TAG, "handleLocationChange: ,,,,,,,, location is ......."+location.getLatitude());
        // Implement your logic here to handle the location change
        // This method will be called whenever the device's location changes
    }

    private Notification createNotification() {
        // Create a notification with a relevant title, content, and icon
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Location Change Service")
                .setContentText("Service is running");

        return builder.build();
    }



}
