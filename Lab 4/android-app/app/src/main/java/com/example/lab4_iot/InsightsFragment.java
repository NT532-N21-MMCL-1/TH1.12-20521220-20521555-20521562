package com.example.lab4_iot;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightsFragment extends Fragment {
    ApiService apiService = ApiClient.getApiService();
    String[] item = {"Temperature", "Humidity", "Light"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private LineChart lineChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insights, container, false);

        lineChart = view.findViewById(R.id.lineChart);

        getListSensorValues();

        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_chart_select, item);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void getListSensorValues(){
        Call<ListSensorValue[]> listSensorValueCall = apiService.getListSensorValue();
        listSensorValueCall.enqueue(new Callback<ListSensorValue[]>() {
            @Override
            public void onResponse(Call<ListSensorValue[]> call, Response<ListSensorValue[]> response) {
                ListSensorValue[] apiResponses = response.body();
                importToLineChart(lineChart, apiResponses);

                Gson gson = new Gson();
                String jsonString = gson.toJson(apiResponses);
                Log.d("API Response", jsonString);
            }

            @Override
            public void onFailure(Call<ListSensorValue[]> call, Throwable t) {

            }
        });
    }

    private void importToLineChart(LineChart lineChart, ListSensorValue[] listLineChartData){
        lineChart.setDrawGridBackground(false);

        String[] xLabels = new String[listLineChartData.length];
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i=0; i<listLineChartData.length; i++){
            entries.add(new Entry(i, listLineChartData[i].getTemperature()));
            xLabels[i] = listLineChartData[i].getTimestamp();
        }

        for(int i=0; i<xLabels.length; i++){
            Log.d("xLabels", xLabels[i]);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Data");
        dataSet.setColor(Color.parseColor("#007dfe"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(3f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xLabels));
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();

        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(listLineChartData.length, true);
        xAxis.setGranularity(1f);

        lineChart.setVisibleXRangeMinimum(2f);
        lineChart.invalidate();
    }
}