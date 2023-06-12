package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RainPredictionFragment extends Fragment {

    ApiService apiService = ApiClient.getApiService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_i, container, false);
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("MinTemp", 28);
            requestBody.put("MaxTemp", 38);
            requestBody.put("Humidity9am", 55);
            requestBody.put("Humidity3pm", 60);
            requestBody.put("Temp9am", 34);
            requestBody.put("Temp3pm", 30);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postData(requestBody);

        return view;
    }

    private void postData(JSONObject jsonObject){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<PredictionResponse> call = apiService.postData(requestBody);
        call.enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                Log.d("prediction", "onResponse: " + response.code());
                if(response.isSuccessful()){
                    Log.d("prediction", "onResponse: " + response.body().toString());

                    int predictionResult = response.body().getPredictions()[0];
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {

            }
        });
    }
}