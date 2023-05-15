package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class TemperatureRoomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_room, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        int progressData = 50; // Giá trị data
        progressBar.setProgress(progressData);
        return view;
    }
}