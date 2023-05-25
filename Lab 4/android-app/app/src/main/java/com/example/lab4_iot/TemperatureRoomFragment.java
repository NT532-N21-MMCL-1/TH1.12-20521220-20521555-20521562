package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemperatureRoomFragment extends Fragment {
    private TextView tvValue;
    private ProgressBar progressBar;
    private Handler handler;
    private Runnable apiRunnable;
    ApiService apiService = ApiClient.getApiService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_room, container, false);
        tvValue = view.findViewById(R.id.txtTempValue);
        progressBar = view.findViewById(R.id.temperatureCircle);

        handler = new Handler();
        apiRunnable = new Runnable() {
            @Override
            public void run() {
                callCurrentTemp();
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

    private void callCurrentTemp(){
        Call<ListSensorValue> call = apiService.getCurrentSensorValue();
        call.enqueue(new Callback<ListSensorValue>() {
            @Override
            public void onResponse(Call<ListSensorValue> call, Response<ListSensorValue> response) {
                Log.d("Call current value", "onTemperatureFrag: " + response.body().toString());
                ListSensorValue res = response.body();

                tvValue.setText(String.valueOf(res.getTemperature()));
                float progressData = res.getTemperature();
                int int_data = (int)progressData;
                progressBar.setProgress(int_data);
            }

            @Override
            public void onFailure(Call<ListSensorValue> call, Throwable t) {
                Log.e("Call current value", "onFailure: " + t.toString());
            }
        });
    }
}