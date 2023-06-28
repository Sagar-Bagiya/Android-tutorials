package com.example.flatbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.flatbooking.models.Item;
import com.example.flatbooking.models.Specification;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Item myData = getJsonData();



    }

    public Item getJsonData() {
        try {
            InputStream inputStream = getAssets().open("JsonData.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, "UTF-8");
            return new Gson().fromJson(jsonString, Item.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}