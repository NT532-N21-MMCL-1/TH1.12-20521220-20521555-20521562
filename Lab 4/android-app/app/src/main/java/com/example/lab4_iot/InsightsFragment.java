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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightsFragment extends Fragment {
    ApiService apiService = ApiClient.getApiService();
    String[] item = {"Temperature", "Humidity", "Light"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private LineChart temperatureChart, humidityChart, lightChart;
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
                getSensorValues("temperature", "5");
                getSensorValues("humidity", "5");
                getSensorValues("light", "5");
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

    private void getSensorValues(String name, String num){
        Call<ResponseBody> numOfDataCall = apiService.getNumOfData(name, num);
        numOfDataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    switch(name){
                        case "temperature":
                            importToTemperatureChart(temperatureChart, responseBody, num);
                            break;
                        case "humidity":
                            importToHumidityChart(humidityChart, responseBody, num);
                            break;
                        case "light":
                            importToLightChart(lightChart, responseBody, num);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API Response", "onFailure: " + t.toString());
            }
        });
    }

    private void importToTemperatureChart(LineChart lineChart, ResponseBody responseBody, String num){
        lineChart.setDrawGridBackground(false);
        String[] xLabels = new String[Integer.parseInt(num)];
        ArrayList<Entry> entries = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(responseBody.string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray item = jsonArray.getJSONArray(i);
                String time = item.getString(0);
                double value = item.getDouble(1);

                xLabels[i] = time;
                entries.add(new Entry(i, (float) value));
            }
        }
        catch (JSONException | IOException e){
            e.printStackTrace();
        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature value");
        dataSet.setColor(Color.parseColor("#ffca74"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_temperature_chart);
        dataSet.setFillDrawable(drawable);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setLabelCount(Integer.parseInt(num), true);
        xAxis.setGranularity(1f);

        Legend legend = lineChart.getLegend();
        legend.setTextSize(12);

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    private void importToHumidityChart(LineChart lineChart,ResponseBody responseBody, String num){
        lineChart.setDrawGridBackground(false);
        String[] xLabels = new String[Integer.parseInt(num)];
        ArrayList<Entry> entries = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(responseBody.string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray item = jsonArray.getJSONArray(i);
                String time = item.getString(0);
                double value = item.getDouble(1);
                xLabels[i] = time;
                entries.add(new Entry(i, (float) value));
            }
        }
        catch (JSONException | IOException e){
            e.printStackTrace();
        }

        LineDataSet dataSet = new LineDataSet(entries, "Humidity value");
        dataSet.setColor(Color.parseColor("#f585bc"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_humidity_chart);
        dataSet.setFillDrawable(drawable);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(Integer.parseInt(num), true);
        xAxis.setGranularity(1f);

        Legend legend = lineChart.getLegend();
        legend.setTextSize(12);

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }

    private void importToLightChart(LineChart lineChart, ResponseBody responseBody, String num){
        lineChart.setDrawGridBackground(false);
        String[] xLabels = new String[Integer.parseInt(num)];
        ArrayList<Entry> entries = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(responseBody.string());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray item = jsonArray.getJSONArray(i);
                String time = item.getString(0);
                double value = item.getDouble(1);
                xLabels[i] = time;
                entries.add(new Entry(i, (float) value));
            }
        }
        catch (JSONException | IOException e){
            e.printStackTrace();
        }

        LineDataSet dataSet = new LineDataSet(entries, "Light value");
        dataSet.setColor(Color.parseColor("#82f7ff"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_light_chart);
        dataSet.setFillDrawable(drawable);
        dataSet.setDrawFilled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new XAxisValueFormatter(xLabels));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(Integer.parseInt(num), true);
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