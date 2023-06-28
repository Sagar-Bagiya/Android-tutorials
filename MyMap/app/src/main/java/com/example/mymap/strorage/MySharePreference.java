package com.example.mymap.strorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MySharePreference {

    public final static String DEVICE_LATLONG_LIST = "deviceLatLongList";
    private Context context;
    private  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    private  Gson gson = new Gson();


    public MySharePreference(Context context) {
        this.context = context;
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key) {
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
