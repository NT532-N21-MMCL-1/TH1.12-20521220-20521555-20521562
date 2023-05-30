package com.example.lab4_iot;

public class BH1750Item {
    private String bh1750_timestamp;
    private int bh1750_wemosID;
    private String bh1750_wemosIP;
    private float light;

    public String getBh1750_timestamp() {
        return bh1750_timestamp;
    }

    public int getBh1750_wemosID() {
        return bh1750_wemosID;
    }

    public String getBh1750_wemosIP() {
        return bh1750_wemosIP;
    }

    public float getLight() {
        return light;
    }

    @Override
    public String toString() {
        return "BH1750Item{" +
                "bh1750_timestamp='" + bh1750_timestamp + '\'' +
                ", bh1750_wemosID=" + bh1750_wemosID +
                ", bh1750_wemosIP='" + bh1750_wemosIP + '\'' +
                ", light=" + light +
                '}';
    }
}
