package com.example.lab4_iot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class PredictionBody {
    JSONObject requestBody = new JSONObject();
    Random rand = new Random();

    public PredictionBody(){

        try {
            float min = 20.0f;
            float max = 40.0f;

            float minTemp = random(min, max);
            requestBody.put("MinTemp", minTemp);

            float maxTemp = random(minTemp, max);
            requestBody.put("MaxTemp", maxTemp);

            min = 30.0f;
            max = 100.0f;
            float humidity9am = random(min, max);
            requestBody.put("Humidity9am", humidity9am);

            float humidity3pm = random(min, max);
            requestBody.put("Humidity3pm", humidity3pm);

            float temp9am = random(minTemp, maxTemp);
            requestBody.put("Temp9am", temp9am);

            float temp3pm = random(minTemp, maxTemp);
            requestBody.put("Temp3pm", temp3pm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getRequestBody() {
        return requestBody;
    }

    private float random(float min, float max){
        return min + rand.nextFloat() * (max - min);
    }
}
