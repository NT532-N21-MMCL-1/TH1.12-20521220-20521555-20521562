#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <WebSocketsClient.h>
#include <ArduinoJson.h>

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";
const char* url = "http://172.31.11.55:8000/wemos_dht/sqlite/insert";
const char* webSocketServerIP;
const int webSocketServerPort = 80; 

HTTPClient http;
WiFiClient wifiClient;

StaticJsonDocument<200> jsonObj;
//JsonObject dataObj = jsonObj.createNestedObject("data");

WebSocketsClient webSocket;

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
}

void loop() {
  jsonObj["temperature"] = 123.123;
  jsonObj["humidity"] = 456.456;

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
  delay(5000);
}