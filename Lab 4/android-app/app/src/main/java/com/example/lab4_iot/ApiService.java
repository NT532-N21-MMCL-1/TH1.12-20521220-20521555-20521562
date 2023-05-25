package com.example.lab4_iot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("sqlite/retrieve_all")
    Call<ListSensorValue[]> getListSensorValue();

    @GET("sqlite/retrieve_current")
    Call<ListSensorValue> getCurrentSensorValue();
    @GET("sqlite/temperature_column")
    Call<Object[]> getTempColumn();
    @GET("sqlite/temperature_column_size")
    Call<Integer> getTempColSize();

    @GET("sqlite/light_column_size")
    Call<Integer> getLightColSize();
}
