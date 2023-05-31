package com.example.lab4_iot;

public class DHTItem {
    private int dht_wemosID;
    private String dht_timestamp;
    private float humidity;
    private float temperature;
    private String dht_wemosIP;

    public int getDht_wemosID() {
        return dht_wemosID;
    }

    public String getDht_timestamp() {
        return dht_timestamp;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getDht_wemosIP() {
        return dht_wemosIP;
    }

    @Override
    public String toString() {
        return "DHTItem{" +
                "dht_wemosID=" + dht_wemosID +
                ", dht_timestamp='" + dht_timestamp + '\'' +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", dht_wemosIP='" + dht_wemosIP + '\'' +
                '}';
    }
}
