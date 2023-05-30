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

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightRoomFragment extends Fragment {
    private TextView tvValue;
    private ProgressBar progressBar;
    private Timer timer;
    ApiService apiService = ApiClient.getApiService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light_room, container, false);
        tvValue = view.findViewById(R.id.txtLightValue);
        progressBar = view.findViewById(R.id.lightCircle);

        startAPITimer();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startAPITimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAPITimer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void stopAPITimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startAPITimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callCurrentTemp("light");
            }
        }, 0, 5000); // Gọi API sau mỗi 5 giây
    }

    private void callCurrentTemp(String column_name){
        Call<Float> call = apiService.getCurrentSensorValue(column_name);
        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if (response.body() != null){
                    Log.d("Call current value", "onLightFrag: " + response.body().toString());
                    Float res = response.body();

                    long roundedNumber = Math.round(res);
                    tvValue.setText(String.valueOf(roundedNumber));
                    float progressData = res;
                    int int_data = (int)progressData;
                    progressBar.setProgress(int_data);
                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                Log.e("Call current value", "onFailure: " + t.toString());
            }
        });
    }
}