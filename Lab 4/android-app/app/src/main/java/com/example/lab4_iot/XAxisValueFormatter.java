package com.example.lab4_iot;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class XAxisValueFormatter extends IndexAxisValueFormatter {
    private String[] mValues;

    public XAxisValueFormatter(String[] values) {
        mValues = values;
    }

    @Override
        public String getFormattedValue(float value) {
        int index = (int) value;
        if (index >= 0 && index < mValues.length) {
            return mValues[index];
        } else {
            return "";
        }
    }
}
