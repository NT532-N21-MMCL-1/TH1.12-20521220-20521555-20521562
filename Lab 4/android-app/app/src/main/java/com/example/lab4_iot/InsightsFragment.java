package com.example.lab4_iot;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    private LineChart temperatureChart, lightChart;
    private BarChart humidityChart;
    private TextView charTitle;
    private Handler handler;
    private Runnable apiRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insights, container, false);

        temperatureChart = view.findViewById(R.id.temperatureChart);
        humidityChart = view.findViewById(R.id.humidityChart);
        lightChart = view.findViewById(R.id.lightChart);

        charTitle = view.findViewById(R.id.chartTitle);

        handler = new Handler();
        apiRunnable = new Runnable() {
            @Override
            public void run() {
                getSensorValues("7");
                handler.postDelayed(this, 5000);
            }
        };

        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_chart_select, item);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                charTitle.setText(item);
                charTitle.setVisibility(View.VISIBLE);

                switch (i){
                    case 0:
                        temperatureChart.setVisibility(View.VISIBLE);
                        humidityChart.setVisibility(View.GONE);
                        lightChart.setVisibility(View.GONE);
                        break;
                    case 1:
                        temperatureChart.setVisibility(View.GONE);
                        humidityChart.setVisibility(View.VISIBLE);
                        lightChart.setVisibility(View.GONE);
                        break;
                    case 2:
                        temperatureChart.setVisibility(View.GONE);
                        humidityChart.setVisibility(View.GONE);
                        lightChart.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        return view;
    }

    private void getSensorValues(String num){
        Call<ListSensorValue[]> numOfDataCall = apiService.getNumOfData(num);
        numOfDataCall.enqueue(new Callback<ListSensorValue[]>() {
            @Override
            public void onResponse(Call<ListSensorValue[]> call, Response<ListSensorValue[]> response) {
                ListSensorValue[] apiResponses = response.body();
                importToTemperatureChart(temperatureChart, apiResponses);
                importToHumidityChart(humidityChart, apiResponses);
                //importToLightChart(lightChart, apiResponses);

                Gson gson = new Gson();
                String jsonString = gson.toJson(apiResponses);
                Log.d("API Response", jsonString);
                Log.d("API Response", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<ListSensorValue[]> call, Throwable t) {
                Log.e("API Response", "onFailure: " + t.toString());
            }
        });
    }

    private void importToTemperatureChart(LineChart lineChart, ListSensorValue[] listLineChartData){
        lineChart.setDrawGridBackground(false);

        String[] xLabels = new String[listLineChartData.length];
        ArrayList<Entry> entries = new ArrayList<>();

        for(int i=0; i<listLineChartData.length; i++){
            xLabels[i] = listLineChartData[i].getDht_timestamp();
            entries.add(new Entry(i, listLineChartData[i].getTemperature()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature value");
        dataSet.setColor(Color.parseColor("#f5be49"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);
        dataSet.setLineWidth(5f);
        dataSet.setCircleRadius(5);
        dataSet.setCircleHoleRadius(4);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setLabelCount(7, true);
        xAxis.setGranularity(1f);

        Legend legend = lineChart.getLegend();
        legend.setTextSize(12);

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    private void importToHumidityChart(BarChart barChart, ListSensorValue[] listBarCharData){
        barChart.setDrawGridBackground(false);
        String[] xLabels = new String[listBarCharData.length];
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i<listBarCharData.length; i++){
            entries.add(new BarEntry(i, listBarCharData[i].getHumidity()));
            xLabels[i] = listBarCharData[i].getDht_timestamp();
        }

        BarDataSet dataSet = new BarDataSet(entries, "Humidity value");
        dataSet.setColor(Color.parseColor("#F266AB"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barData.setBarWidth(0.5f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7, true);
        xAxis.setGranularity(1f);

        Legend legend = barChart.getLegend();
        legend.setTextSize(12);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }

    private void importToLightChart(LineChart lineChart, ListSensorValue[] listLineChartData){
        lineChart.setDrawGridBackground(false);
        String[] xLabels = new String[listLineChartData.length];
        ArrayList<Entry> entries = new ArrayList<>();

        for(int i=0; i<listLineChartData.length; i++){
            entries.add(new Entry(i, listLineChartData[i].getLight()));
            xLabels[i] = listLineChartData[i].getBh1750_timestamp();
        }

        LineDataSet dataSet = new LineDataSet(entries, "Light value");
        dataSet.setColor(Color.parseColor("#bef8fc"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16);
        dataSet.setLineWidth(5f);
        dataSet.setCircleRadius(5);
        dataSet.setCircleHoleRadius(4);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_line_chart);
        dataSet.setFillDrawable(drawable);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7, true);
        xAxis.setGranularity(1f);

        Legend legend = lineChart.getLegend();
        legend.setTextSize(12);

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
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
}