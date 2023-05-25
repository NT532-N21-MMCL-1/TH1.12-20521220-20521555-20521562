package com.example.lab4_iot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("sqlite/retrieve/all_data")
    Call<ListSensorValue[]> getListSensorValue();

    @GET("sqlite/retrieve/current_data")
    Call<ListSensorValue> getCurrentSensorValue();

    @GET("sqlite/retrieve/column_size/{column_name}")
    Call<Integer> getColumnSize(@Path("column_name") String name);

    @GET("sqlite/retrieve/amount_of_data/{num}")
    Call<ListSensorValue[]> getNumOfData(@Path("num") String num);
}
