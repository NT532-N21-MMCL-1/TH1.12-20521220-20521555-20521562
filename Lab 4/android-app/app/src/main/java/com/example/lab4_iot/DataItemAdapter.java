package com.example.lab4_iot;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.DataItemViewHolder> {

    private List<DataItem> dataItems;

    public DataItemAdapter(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public DataItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new DataItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataItemViewHolder holder, int position) {
        DataItem dataItem = dataItems.get(position);
        Log.d("adapter dataItem", "onBindViewHolder: " + dataItem.toString());

        String strValue = String.valueOf(dataItem.getValue());
        if(dataItem.getTypeValue() == "Light"){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            strValue = decimalFormat.format(dataItem.getValue());
        }

        holder.nameWemos.setText(dataItem.getNameWM());
        holder.ipWemos.setText("IP: " + dataItem.getIpWM());
        holder.idWemos.setText("ID: " + String.valueOf(dataItem.getIdWM()));
        holder.typeValue.setText(dataItem.getTypeValue());
        holder.value.setText(strValue);
        holder.timestamp.setText(dataItem.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class DataItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameWemos, ipWemos, idWemos, typeValue, value, timestamp;

        public DataItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameWemos = itemView.findViewById(R.id.nameWemos);
            ipWemos = itemView.findViewById(R.id.ipWemos);
            idWemos = itemView.findViewById(R.id.idWemos);
            typeValue = itemView.findViewById(R.id.typeValue);
            value = itemView.findViewById(R.id.value);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
