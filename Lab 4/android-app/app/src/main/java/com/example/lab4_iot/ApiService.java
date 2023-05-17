package com.example.lab4_iot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("wemos/state/{name}")
    Call<stateWeMos> getWeMosState(@Path("name") String nameWeMos);
}
