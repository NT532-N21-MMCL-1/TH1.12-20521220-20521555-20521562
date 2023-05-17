package com.example.lab4_iot;

import com.google.gson.annotations.SerializedName;

public class stateWeMos {
    @SerializedName("name")
    public String name;
    @SerializedName("state")
    public String state;
    @SerializedName("timestamp")
    public String timestamp;

    @Override
    public String toString() {
        return "stateWeMos{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
