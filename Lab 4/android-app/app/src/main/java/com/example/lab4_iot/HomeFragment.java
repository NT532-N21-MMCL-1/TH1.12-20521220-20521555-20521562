package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ApiService apiService = ApiClient.getApiService();
        Call<stateWeMos> stateWeMosCall = apiService.getWeMosState("d1A");
        stateWeMosCall.enqueue(new Callback<stateWeMos>() {
            @Override
            public void onResponse(Call<stateWeMos> call, Response<stateWeMos> response) {
                stateWeMos state = response.body();
                Log.d("wemos info", String.valueOf(response.code()));
                Log.d("wemos info", state.toString());
                //Toast.makeText(getActivity(),state.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<stateWeMos> call, Throwable t) {
                Log.d("wemos info", t.toString());
            }
        });

        return view;
    }
}