#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

#include <NewPing.h>
#include <BH1750.h>
#include <Wire.h>

#include <ArduinoJson.h>

int ledsPin[] = {D3, D4, D5};

#define trigPin D6
#define echoPin D7

BH1750 lightSensor;

NewPing distSensor(trigPin, echoPin, 10);
long distance, duration;

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";
const char* url = "http://172.31.11.55:8000/";
//const int port = 8000;

HTTPClient http;
WiFiClient wifiClient;

StaticJsonDocument<200> jsonObj;
StaticJsonDocument<200> response;

JsonObject sensorData = jsonObj.createNestedObject("data");

void setup() {
  Serial.begin(9600);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  for (int i=0; i<sizeof(ledsPin); i++){
    pinMode(ledsPin[i], OUTPUT);
  }

  Wire.begin();
  lightSensor.begin();

  Serial.println("Connected to WiFi");

  http.begin(wifiClient, url);
  //http.addHeader("Content-Type", "application/json");
}

void loop() {
  for (int i=0; i<sizeof(ledsPin); i++){
    digitalWrite(ledsPin[i], LOW);
  }

  distance = distSensor.ping_cm();

  Serial.print("distance: ");
  Serial.println(distance);

  float lightValue = lightSensor.readLightLevel();
  Serial.print("Light: ");
  Serial.print(lightValue);
  Serial.println(" lx");

  sensorData["light"] = lightValue;
  sensorData["distance"] = distance; 

  String jsonString;
  serializeJson(jsonObj, jsonString);
  Serial.println(jsonString);

  int httpResponseCode = http.POST(jsonString);

  if (httpResponseCode > 0) {
    Serial.println(httpResponseCode);
    String strResponse = http.getString();
    Serial.println(strResponse);

    deserializeJson(response, strResponse);
    int ledsNum = response["ledsNum"];
    Serial.println(String(ledsNum));
    for (int i=0; i<ledsNum; i++){
      digitalWrite(ledsPin[i], HIGH);
    }

  } else {
    Serial.print("Error on HTTP request: ");
    Serial.println(httpResponseCode);
  }
  http.end();
  delay(5000);
}