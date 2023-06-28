package com.example.mymap.strorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocationStorage {
    private static final String PREF_NAME = "LatLngStorage";
    private static final String KEY_LATLNG_LIST = "latLngList";
    private static SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private static Context context;

    public LocationStorage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveLatLngList(List<LatLng> latLngList) {
        clearLatLngList();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String json = gson.toJson(latLngList);

        editor.putString(KEY_LATLNG_LIST, json);
        editor.apply();
    }

    public List<LatLng> getLatLngList() {
        String json = sharedPreferences.getString(KEY_LATLNG_LIST, null);

        if (json != null) {
            Type type = new TypeToken<List<LatLng>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>(); // Return an empty list if no LatLng data is found
        }
    }

    public  void clearLatLngList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_LATLNG_LIST);
        editor.apply();
    }

}
