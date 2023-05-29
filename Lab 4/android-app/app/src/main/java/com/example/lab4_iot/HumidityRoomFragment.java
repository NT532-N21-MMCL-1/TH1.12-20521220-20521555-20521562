package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HumidityRoomFragment extends Fragment {
    private TextView tvValue;
    private ProgressBar progressBar;
    private Handler handler;
    private Runnable apiRunnable;
    ApiService apiService = ApiClient.getApiService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_humidity_room, container, false);
        tvValue = view.findViewById(R.id.txtHumValue);

        progressBar = view.findViewById(R.id.humidityCircle);

        handler = new Handler();
        apiRunnable = new Runnable() {
            @Override
            public void run() {
                callCurrentHum("humidity");
                handler.postDelayed(this, 5500); // Gọi lại sau 5 giây
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startAPICalls();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAPICalls();
    }

    private void startAPICalls() {
        handler.postDelayed(apiRunnable, 0);
    }

    private void stopAPICalls() {
        handler.removeCallbacks(apiRunnable);
    }

    private void callCurrentHum(String column_name){
        Call<Float> call = apiService.getCurrentSensorValue(column_name);
        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                Log.d("Call current value", "onHumidityFrag: " + response.body().toString());
                Float res = response.body();

                tvValue.setText(String.valueOf(res));
                float progressData = res;
                int int_data = (int)progressData;
                progressBar.setProgress(int_data);
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                Log.d("Call current value", "onFailure: " + t.toString());
            }
        });
    }
}