package com.example.mymap.api;



import com.example.mymap.directionEntities.DirectionResults;
import com.example.mymap.entities.Predictions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapAPI {
    @GET("place/autocomplete/json")
    public Call<Predictions> getPlacesAutoComplete(
            @Query("input") String input,
            @Query("types") String types,
            @Query("language") String language,
            @Query("key") String key
    );

    @GET("directions/json")
    public Call<DirectionResults>  getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String key
           );

}