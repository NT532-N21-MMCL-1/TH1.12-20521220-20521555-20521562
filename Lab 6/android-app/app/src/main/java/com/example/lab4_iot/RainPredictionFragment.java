package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RainPredictionFragment extends Fragment {

    ApiService apiService = ApiClient.getApiService();
    TextView tvRainForeCast;
    ImageView ivWeather;
    private static final String BROKER_URL = "tcp://172.31.9.11:1883";
    private static final String CLIENT_ID = "client_android";
    private MqttHandler mqttHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rain_prediction, container, false);

        tvRainForeCast = view.findViewById(R.id.tvRainForecast);
        ivWeather = view.findViewById(R.id.ivWeather);

        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL,CLIENT_ID);

        PredictionBody predictionBody = new PredictionBody();

        Log.d("predictionBody", "onCreateView: " + predictionBody.getRequestBody().toString());
        postData(predictionBody.getRequestBody());

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

                    if (predictionResult == 0){
                        sendMQTT("sunny");
                        tvRainForeCast.setText("Sunny on tomorrow");
                        ivWeather.setImageResource(R.drawable.sunny_illustration);

                    } else {
                        sendMQTT("rainy");
                        tvRainForeCast.setText("Rainy on tomorrow");
                        ivWeather.setImageResource(R.drawable.rainy_illustration);
                    }
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {

            }
        });
    }

    void sendMQTT(String message){
        mqttHandler.publish("mmcl/nhom12/prediction/rainy", message);
    }
}