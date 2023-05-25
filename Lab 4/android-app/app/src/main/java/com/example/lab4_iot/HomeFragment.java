package com.example.lab4_iot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private TextView txtStateA, txtStateB;
    private Handler handler;
    private Runnable apiRunnable;
    private int pre_Tempsize = 0, cur_Tempsize = 0,
                lengthLight = 0, cur_Lightsize = 0;
    ApiService apiService = ApiClient.getApiService();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtStateA = view.findViewById(R.id.txtStateA);
        txtStateB = view.findViewById(R.id.txtStateB);

        handler = new Handler();
        apiRunnable = new Runnable() {
            @Override
            public void run() {
                getTempColSize();
                handler.postDelayed(this, 5000);
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

    private void getTempColSize(){
        Call<Integer> call = apiService.getTempColSize();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("State Wemos", "Temp Column Size: " + response.body().toString());
                cur_Tempsize = cur_Tempsize = Integer.parseInt(response.body().toString());

                if(response.body() == null || pre_Tempsize==0){
                    txtStateA.setText("offline");
                    txtStateA.setTextColor(Color.parseColor("#fe3332"));
                    Log.d("State Wemos", "onResponse: offline");
                    Log.d("State Wemos", "lengthTemp: " + pre_Tempsize);
                }
                else {
                    cur_Tempsize = Integer.parseInt(response.body().toString());
                    if(cur_Tempsize != pre_Tempsize){
                        txtStateA.setText("online");
                        txtStateA.setTextColor(Color.parseColor("#2cd400"));
                        Log.d("State Wemos", "onResponse: online");
                        Log.d("State Wemos", "lengthTemp: " + pre_Tempsize);
                        Log.d("State Wemos", "cur_Tempsize: " + cur_Tempsize);
                    }
                    else{
                        txtStateA.setText("offline");
                        txtStateA.setTextColor(Color.parseColor("#fe3332"));
                        Log.d("State Wemos", "onResponse: offline");
                        Log.d("State Wemos", "lengthTemp: " + pre_Tempsize);
                        Log.d("State Wemos", "cur_Tempsize: " + cur_Tempsize);
                    }
                }
                pre_Tempsize = cur_Tempsize;
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Get Temp Size", "onFailure: " + t.toString() );
            }
        });
    }

    private void getLightColSize(){
        Call<Integer> call = apiService.getLightColSize();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("Light Column Size", "onResponse: " + response.body().toString());

                if(response.body() == null || lengthLight==0){
                    txtStateB.setText("offline");
                    txtStateB.setTextColor(Color.parseColor("#fe3332"));
                    Log.d("State Wemos", "onResponse: offline");
                }
                else {
                    cur_Lightsize = Integer.parseInt(response.body().toString());
                    if(cur_Lightsize != lengthLight){
                        txtStateB.setText("online");
                        txtStateB.setTextColor(Color.parseColor("#2cd400"));
                        Log.d("State Wemos", "onResponse: online");
                    }
                    else{
                        txtStateB.setText("offline");
                        txtStateB.setTextColor(Color.parseColor("#fe3332"));
                        Log.d("State Wemos", "onResponse: offline");
                    }
                }
                lengthLight = cur_Lightsize;
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Get Light Size", "onFailure: " + t.toString() );
            }
        });
    }
}