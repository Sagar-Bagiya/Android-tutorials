package com.example.mymap.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.mymap.R;
import com.example.mymap.activity.MapsActivity;
import com.example.mymap.strorage.LocationStorage;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationTrackingService extends Service {

    private static Context context;

    private static final String TAG = "LocationTrackingService";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "location_tracking_channel";
    private static final int LOCATION_REQUEST_CODE = 1001;

    private LatLng pLatLng = new LatLng(0, 0);

    private LocationStorage locationStorage;

    private List<LatLng> deviceLatLngList;

    private FusedLocationProviderClient fusedLocationClient;

    LocationCallback locationCallback;

    MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();

        deviceLatLngList = new ArrayList<LatLng>();
        locationStorage = new LocationStorage(this);
        locationStorage.clearLatLngList();
        context = this;

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mp.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification(this));

        mp.start();
        requestLocationUpdates();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        destroyLocationUpdate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleLocationUpdate(Location location) {
        // Handle the location update
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (latLng.latitude != pLatLng.latitude && latLng.longitude != pLatLng.longitude) {
            Log.d(TAG, "New location: " + location.getLatitude() + ", " + location.getLongitude());
            pLatLng = latLng;
            deviceLatLngList.add(latLng);
            if (deviceLatLngList.size() > 0) {
                locationStorage.saveLatLngList(deviceLatLngList);
            }
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Location Tracking",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification(Context context) {
        Intent notificationIntent = new Intent(this, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 1, new Intent[]{notificationIntent}, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Location Tracking")
                .setContentText("Tracking device location")
                .setSmallIcon(R.drawable.baseline_notification_add_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        return builder.build();
    }


    private void getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Use the obtained location
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.d(TAG, "Last known location: " + latitude + ", " + longitude);
                    } else {
                        // Location is not available
                        Log.e(TAG, "Last known location is null");
                    }
                }
            });
        } else {
            // Location permission not granted
            Log.e(TAG, "Location permission not granted");
        }
    }

    private void requestLocationUpdates()  {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000); // Update interval in milliseconds

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            // Location permission not granted
            Log.e(TAG, "Location permission not granted");
        }
    }

    private void destroyLocationUpdate() {
        if (locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
    }



}
