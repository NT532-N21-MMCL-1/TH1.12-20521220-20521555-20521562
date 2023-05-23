package com.example.lab4_iot;

import com.google.gson.annotations.SerializedName;

public class ListSensorValue {
    @SerializedName("id")
    private int id;

    @SerializedName("temperature")
    private float temperature;

    @SerializedName("humidity")
    private float humidity;

    @SerializedName("light")
    private float light;

    public int getId() {
        return id;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getLight() {
        return light;
    }

    @Override
    public String toString() {
        return "ListSensorValue{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", light=" + light +
                '}';
    }
}
