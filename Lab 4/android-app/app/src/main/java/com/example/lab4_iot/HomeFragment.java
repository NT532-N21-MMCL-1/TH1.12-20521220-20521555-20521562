package com.example.lab4_iot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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
    ApiService apiService = ApiClient.getApiService();
    private WebSocket webSocket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView txtStateA, txtStateB;
        txtStateA = view.findViewById(R.id.txtStateA);
        txtStateB = view.findViewById(R.id.txtStateB);

//        callStateAPI("d1A", txtStateA);
//        callStateAPI("d1B", txtStateB);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder().url("ws://192.168.1.25:8080").build();
        webSocket = client.newWebSocket(request, new MyWebSocketListener());
        return view;
    }

    private class MyWebSocketListener extends WebSocketListener{
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response){
            super.onOpen(webSocket, response);
            Log.d("WebSocket", "Connected");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text){
            super.onMessage(webSocket, text);
            Log.d("WebSocket", "Received message: " + text);
        }

        @Override
        public void onClosed(WebSocket webSocket,int code, String reason){
            super.onClosed(webSocket, code, reason);
            Log.d("WebSocket", "Disconnected");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            super.onFailure(webSocket, t, response);
            // Xử lý khi gặp lỗi kết nối WebSocket
            Log.e("WebSocket", "Error: " + t.getMessage());
        }
    }

    private void callStateAPI(String name, TextView txtState){
        Call<stateWeMos> stateWeMosCall = apiService.getWeMosState(name);
        stateWeMosCall.enqueue(new Callback<stateWeMos>() {
            @Override
            public void onResponse(Call<stateWeMos> call, Response<stateWeMos> response) {
                stateWeMos state = response.body();
                Log.d("wemos info", String.valueOf(response.code()));
                Log.d("wemos info", state.toString());

                if (state.state.equals("online")) txtState.setTextColor(Color.parseColor("#2cd400"));
                if (state.state.equals("offline")) txtState.setTextColor(Color.parseColor("#fe3332"));
                txtState.setText(state.state.toString());

                refresh(1000, name, txtState);
            }

            @Override
            public void onFailure(Call<stateWeMos> call, Throwable t) {
                Log.d("wemos info", t.toString());
            }
        });
    }

    private void refresh(int milliseconds, String name, TextView txtState){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callStateAPI(name, txtState);
            }
        };

        handler.postDelayed(runnable, milliseconds);
    }
}