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
    private int pre_size = 0, cur_size = 0;
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
                getColumnSize("temperature");
                //getColumnSize("light");
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

    private void getColumnSize(String name){
        Call<Integer> call = apiService.getColumnSize(name);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("State Wemos", name + "Column Size: " + response.body().toString());
                cur_size = cur_size = Integer.parseInt(response.body().toString());

                if(response.body() == null || pre_size==0){
                    txtStateA.setText("offline");
                    txtStateA.setTextColor(Color.parseColor("#fe3332"));
                    Log.d("State Wemos", "onResponse: offline");
                    Log.d("State Wemos", "length " + name + ": " + pre_size);
                }
                else {
                    cur_size = Integer.parseInt(response.body().toString());
                    if(cur_size != pre_size){
                        txtStateA.setText("online");
                        txtStateA.setTextColor(Color.parseColor("#2cd400"));
                        Log.d("State Wemos", "onResponse: online");
                        Log.d("State Wemos", "length " + name + ": " + pre_size);
                        Log.d("State Wemos", "cur_Tempsize: " + cur_size);
                    }
                    else{
                        txtStateA.setText("offline");
                        txtStateA.setTextColor(Color.parseColor("#fe3332"));
                        Log.d("State Wemos", "onResponse: offline");
                        Log.d("State Wemos", "length " + name + ": " + pre_size);
                        Log.d("State Wemos", "cur_size " + name + ": " + cur_size);
                    }
                }
                pre_size = cur_size;
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Get" + name + " size: ", "onFailure: " + t.toString() );
            }
        });
    }
}