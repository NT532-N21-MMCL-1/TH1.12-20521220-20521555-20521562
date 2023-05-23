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
    private static final String TAG = "MQTTAndroid";

    private static String BROKER_URL = "tcp://172.31.9.11:1883";
    private static String CLIENT_ID = "android-app";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView txtStateA, txtStateB;
        txtStateA = view.findViewById(R.id.txtStateA);
        txtStateB = view.findViewById(R.id.txtStateB);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void subscribeToTopic(String topic){
    }
}