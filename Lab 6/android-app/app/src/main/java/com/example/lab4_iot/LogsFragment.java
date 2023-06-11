package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogsFragment extends Fragment {
    private RecyclerView recyclerView;
    private DataItemAdapter adapter;
    ApiService apiService = ApiClient.getApiService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logs, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        Call<DataResponse> call = apiService.getListSensorValue(10);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if(response.isSuccessful()){
                    DataResponse dataResponse = response.body();
                    if(dataResponse != null){
                        List<DataItem> dataItemList = processData(dataResponse);
                        Log.d("dataItemList", "onResponse: " + dataItemList.toString());
                        adapter = new DataItemAdapter(dataItemList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Log.e("API Error", "Response unsuccessful");
                }
                String responseBody = response.body().toString();
                Log.d("Response Body", responseBody);
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("API Error", "Failure: " + t.getMessage());
            }
        });
        return view;
    }

    private List<DataItem> processData(DataResponse dataResponse){
        List<DataItem> dataItemList = new ArrayList<>();

        for (DHTItem dhtItem : dataResponse.getDHT()){
            dataItemList.add(new DataItem("Wemos D1 DHT",
                    dhtItem.getDht_wemosIP(),
                    dhtItem.getDht_wemosID(),
                    "Temperature",
                    dhtItem.getTemperature(),
                    dhtItem.getDht_timestamp()));

            dataItemList.add(new DataItem("Wemos D1 DHT",
                    dhtItem.getDht_wemosIP(),
                    dhtItem.getDht_wemosID(),
                    "Humidity",
                    dhtItem.getHumidity(),
                    dhtItem.getDht_timestamp()));
        }

        for (BH1750Item bh1750Item : dataResponse.getBH1750()){
            dataItemList.add(new DataItem("Wemos D1 BH1750",
                    bh1750Item.getBh1750_wemosIP(),
                    bh1750Item.getBh1750_wemosID(),
                    "Light",
                    bh1750Item.getLight(),
                    bh1750Item.getBh1750_timestamp()));
        }

        return dataItemList;
    }
}