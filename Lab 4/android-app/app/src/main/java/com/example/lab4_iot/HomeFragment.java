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
    private int list_length = 0;
    ApiService apiService = ApiClient.getApiService();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtStateA = view.findViewById(R.id.txtStateA);
        txtStateB = view.findViewById(R.id.txtStateB);

        callAllDB();

        return view;
    }

    private void callAllDB(){
        Call<ListSensorValue[]> call = apiService.getListSensorValue();
        call.enqueue(new Callback<ListSensorValue[]>() {
            @Override
            public void onResponse(Call<ListSensorValue[]> call, Response<ListSensorValue[]> response) {
                Log.d("Call allDB", "onResponse: " + response.body().toString());
                ListSensorValue[] list = response.body();
                if (list.length != list_length){
                    txtStateA.setText("online");
                }
                list_length = list.length;
                Log.d("Call allDB", "onResponse: " + list_length);
            }

            @Override
            public void onFailure(Call<ListSensorValue[]> call, Throwable t) {

            }
        });
    }
}