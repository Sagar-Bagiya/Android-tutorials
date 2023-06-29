package com.example.flatbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.flatbooking.adpters.SpecificationAdpter;
import com.example.flatbooking.data.ApiData;
import com.example.flatbooking.databinding.ActivityMakePackageBinding;
import com.example.flatbooking.models.Item;

public class MakePackageActivity extends AppCompatActivity {
    Item myData;

    ActivityMakePackageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakePackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myData = ApiData.getJsonData(this);

        binding.myRecyclerView.setAdapter(new SpecificationAdpter(this,myData.getSpecifications()));
        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}