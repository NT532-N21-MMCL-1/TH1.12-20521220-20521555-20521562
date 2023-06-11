package com.example.lab4_iot;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("sqlite/retrieve/all_data/{num}")
    Call<DataResponse> getListSensorValue(@Path("num") Integer num);

    @GET("sqlite/retrieve/current_data/{column_name}")
    Call<Float> getCurrentSensorValue(@Path("column_name") String name);

    @GET("sqlite/retrieve/column_size/{column_name}")
    Call<Integer> getColumnSize(@Path("column_name") String name);

    @GET("sqlite/retrieve/amount_of_data/{column_name}/{num}")
    Call<ResponseBody> getNumOfData(@Path("column_name") String name, @Path("num") String num);
}
