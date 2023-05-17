package com.example.lab4_iot;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("http://127.0.0.1:8000/")
    Call<stateWeMos> getWeMosState(String name);
}
