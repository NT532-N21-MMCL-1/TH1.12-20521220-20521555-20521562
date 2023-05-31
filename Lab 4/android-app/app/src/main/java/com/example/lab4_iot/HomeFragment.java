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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private int pre_sizeTemp = 0, pre_sizeLight = 0, cur_size = 0;
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
                getColumnSize("temperature"); //get size of temperature column
                getColumnSize("light"); // get size of light column
                handler.postDelayed(this, 5000); //get size after 5 seconds
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss, MMM d", Locale.ENGLISH);
        String currentTime = timeFormat.format(calendar.getTime());
        Log.d("current time", currentTime);

        Call<Integer> call = apiService.getColumnSize(name);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Log.d("State Wemos", name + "Column Size: " + response.body().toString());
                    cur_size = Integer.parseInt(response.body().toString());
                    if(name.equals("temperature")){
                        if(pre_sizeTemp != 0){
                            if(cur_size != pre_sizeTemp){
                                txtStateA.setVisibility(View.VISIBLE);
                                txtStateA.setText("online " + currentTime);
                                txtStateA.setTextColor(Color.parseColor("#2cd400"));
                                Log.d("State Wemos DHT", "onResponse: online");
                            } else {
                                txtStateA.setVisibility(View.VISIBLE);
                                txtStateA.setText("offline " + currentTime);
                                txtStateA.setTextColor(Color.parseColor("#fe3332"));
                                Log.d("State Wemos DHT", "onResponse: offline");
                            }
                            pre_sizeTemp = cur_size;
                        }
                        pre_sizeTemp = cur_size;
                    } else if (name.equals("light")){
                        if(pre_sizeLight != 0){
                            if(cur_size != pre_sizeLight){
                                txtStateB.setVisibility(View.VISIBLE);
                                txtStateB.setText("online " + currentTime);
                                txtStateB.setTextColor(Color.parseColor("#2cd400"));
                                Log.d("State Wemos BH1750", "onResponse: online");
                            } else {
                                txtStateB.setVisibility(View.VISIBLE);
                                txtStateB.setText("offline " + currentTime);
                                txtStateB.setTextColor(Color.parseColor("#fe3332"));
                                Log.d("State Wemos BH1750", "onResponse: offline");
                            }
                            pre_sizeLight = cur_size;
                        }
                        pre_sizeLight = cur_size;
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Get" + name + " size: ", "onFailure: " + t.toString() );
            }
        });
    }
}