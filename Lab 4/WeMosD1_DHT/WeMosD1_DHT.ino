#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>
#include <DHT.h>
#include <PubSubClient.h>

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";
const char* url = "http://172.31.11.55:8000/sqlite/insert/wemos_dht";

const char* mqtt_server = "172.31.9.11";
const char* mqttTopic1 = "mmcl/nhom12/led/n1";
const char* mqttTopic2 = "mmcl/nhom12/led/n2";

HTTPClient http;
WiFiClient wifiClient;

StaticJsonDocument<200> jsonObj;
//JsonObject dataObj = jsonObj.createNestedObject("data");

#define DHTPIN D3
#define led1 D4
#define led2 D5

const int DHTTYPE = DHT11;  
DHT dht(DHTPIN, DHTTYPE);
WiFiClient espClient;
PubSubClient client(espClient);
unsigned long oldtime = 0;

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);

  digitalWrite(led1, LOW);
  digitalWrite(led2, LOW);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("Connected to WiFi");
  Serial.print("IP: ");
  Serial.println(WiFi.localIP());

  http.begin(wifiClient, url);
  dht.begin();

  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {
 int delayTimebyMillis = 5000;

  unsigned long currentMillis = millis();
  if ((unsigned long) (millis() - oldtime) > delayTimebyMillis) {
    float h = dht.readHumidity();    
    float t = dht.readTemperature(); 
    Serial.println("temp: ");
    Serial.print(t);
    Serial.print(" hum: ");
    Serial.print(h);
  
    if(!isnan(t) && !isnan(h)){
      jsonObj["dht_wemosIP"] = WiFi.localIP().toString();
      jsonObj["temperature"] = t;
      jsonObj["humidity"] = h;

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
    oldtime = millis();  
    Serial.println("5s roi nhe");  
  }

  if (!client.connected()) {
      reconnect();
    }
    client.loop();
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Received message on topic: ");
  Serial.println(topic);

  String message = "";
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }
  Serial.print("Message: ");
  Serial.println(message);

  int led1State = digitalRead(led1);
  int led2State = digitalRead(led2);

  if(strcmp(topic, mqttTopic1) == 0){
    if(led1State == LOW) digitalWrite(led1, HIGH);
    else digitalWrite(led1, LOW);
  }
  
  if(strcmp(topic, mqttTopic2) == 0){
    if(led2State == LOW) digitalWrite(led2, HIGH);
    else digitalWrite(led2, LOW);
  }
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "WemosClient-";
    clientId += String(random(0xffff), HEX);
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      client.subscribe("mmcl/nhom12/led/n1");
      client.subscribe("mmcl/nhom12/led/n2");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" retrying in 5 seconds");
      delay(5000);
    }
  }
}