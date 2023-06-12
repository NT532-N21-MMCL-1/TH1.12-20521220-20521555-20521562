package com.example.lab4_iot;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PredictionResponse {
    @SerializedName("predictions")
    private int[] predictions;

    public int[] getPredictions() {
        return predictions;
    }

    @Override
    public String toString() {
        return "PredictionResponse{" +
                "predictions=" + Arrays.toString(predictions) +
                '}';
    }
}
