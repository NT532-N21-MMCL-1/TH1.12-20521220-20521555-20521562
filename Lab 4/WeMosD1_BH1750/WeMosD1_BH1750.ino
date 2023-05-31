#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>

#include <BH1750.h>
#include <Wire.h>

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";
const char* url = "http://172.31.11.55:8000/sqlite/insert/wemos_bh1750";

HTTPClient http;
WiFiClient wifiClient;

StaticJsonDocument<200> jsonObj;

BH1750 lightSensor;

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("Connected to WiFi");
  Serial.print("IP: ");
  Serial.println(WiFi.localIP());

  http.begin(wifiClient, url);

  Wire.begin();
  lightSensor.begin();
}

void loop() {
  float l = lightSensor.readLightLevel();

  if(l!=-2){
    jsonObj["bh1750_wemosIP"] = WiFi.localIP().toString();
  jsonObj["light"] = l;

  String jsonString;
  serializeJson(jsonObj, jsonString);
  Serial.println(jsonString);

  int httpResponseCode = http.POST(jsonString);

  if (httpResponseCode > 0) {
    Serial.println(httpResponseCode);
    String strResponse = http.getString();
    Serial.println(strResponse);
  } else {
    Serial.print("Error on HTTP request: ");
    Serial.println(httpResponseCode);

    String errorDescription = http.errorToString(httpResponseCode);
    Serial.print("Error description: ");
    Serial.println(errorDescription);
  }
  http.end();
  }
  delay(5000);
}
