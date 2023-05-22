package com.example.lab4_iot;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyXAxisValueFormatter extends ValueFormatter {
    private final String[] labels;

    public MyXAxisValueFormatter(String[] labels) {
        this.labels = labels;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        //axis.setLabelCount(labels.length, true);
        int index = (int) value;
        if (index >= 0 && index < labels.length) {
            return labels[index];
        } else {
            return "";
        }
    }
}
