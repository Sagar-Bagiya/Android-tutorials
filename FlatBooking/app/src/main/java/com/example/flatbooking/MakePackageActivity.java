package com.example.flatbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flatbooking.data.ApiData;
import com.example.flatbooking.models.Item;


public class MakePackageActivity extends AppCompatActivity {

    Item myData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_package);
        myData = ApiData.getJsonData(this);

    }
}