package com.example.flatbooking.data;

import android.content.Context;

import com.example.flatbooking.models.Item;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class ApiData {

    public static Item getJsonData(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("JsonData.json");
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
