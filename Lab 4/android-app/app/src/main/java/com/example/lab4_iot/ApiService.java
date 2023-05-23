package com.example.lab4_iot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("wemos/sqlite/retrieve_all")
    Call<ListSensorValue[]> getListSensorValue();

    @GET("wemos/sqlite/retrieve_current")
    Call<ListSensorValue> getCurrentSensorValue();
}
