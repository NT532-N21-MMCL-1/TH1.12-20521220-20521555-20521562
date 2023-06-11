package com.example.lab4_iot;

import java.util.List;

public class DataResponse {
    private List<DHTItem> DHT;
    private List<BH1750Item> BH1750;

    public List<DHTItem> getDHT() {
        return DHT;
    }

    public List<BH1750Item> getBH1750() {
        return BH1750;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "DHT=" + DHT +
                ", BH1750=" + BH1750 +
                '}';
    }
}
