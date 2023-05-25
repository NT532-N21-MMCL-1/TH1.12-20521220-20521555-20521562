package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LogsFragment extends Fragment {
    private ListView listView;
    private TextView logTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logs, container, false);
        listView = view.findViewById(R.id.list_view);

        // Tạo một mảng dữ liệu giả để hiển thị trong ListView
        String[] data = {"Mục 1 - Text 1", "Mục 2 - Text 2", "Mục 3 - Text 3", "Mục 4 - Text 4", "Mục 5 - Text 5"};

        // Tạo một ArrayAdapter để hiển thị dữ liệu trong ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                String item = getItem(position);
                String[] parts = item.split(" - ");

                TextView text1 = view.findViewById(R.id.logTemp);

                text1.setText(parts[0]);

                return view;
            }
        };

        // Gắn adapter vào ListView
        listView.setAdapter(adapter);

        return view;
    }
}