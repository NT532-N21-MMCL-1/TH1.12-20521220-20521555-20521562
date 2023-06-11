package com.example.lab4_iot;

import com.google.gson.annotations.SerializedName;

public class ListSensorValue {
    @SerializedName("id")
    private int id;

    @SerializedName("dht_timestamp")
    private String dht_timestamp;

    @SerializedName("temperature")
    private float temperature;

    @SerializedName("humidity")
    private float humidity;

    @SerializedName("bh1750_timestamp")
    private String bh1750_timestamp;

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

    public String getDht_timestamp() {
        return dht_timestamp;
    }

    public String getBh1750_timestamp() {
        return bh1750_timestamp;
    }

    @Override
    public String toString() {
        return "ListSensorValue{" +
                "id=" + id +
                ", dht_timestamp='" + dht_timestamp + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", bh1750_timestamp='" + bh1750_timestamp + '\'' +
                ", light=" + light +
                '}';
    }
}
