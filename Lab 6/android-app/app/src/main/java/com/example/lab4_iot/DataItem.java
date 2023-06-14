package com.example.lab4_iot;

public class DataItem {
    private String nameWM;
    private String ipWM;
    private int idWM;
    private String typeValue;
    private float value;
    private String timestamp;

    public DataItem(String nameWM, String ipWM, int idWM, String typeValue, float value, String timestamp) {
        this.nameWM = nameWM;
        this.ipWM = ipWM;
        this.idWM = idWM;
        this.typeValue = typeValue;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getNameWM() {
        return nameWM;
    }

    public String getIpWM() {
        return ipWM;
    }

    public int getIdWM() {
        return idWM;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public float getValue() {
        return value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "nameWM='" + nameWM + '\'' +
                ", ipWM='" + ipWM + '\'' +
                ", idWM=" + idWM +
                ", typeValue='" + typeValue + '\'' +
                ", value=" + value +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
