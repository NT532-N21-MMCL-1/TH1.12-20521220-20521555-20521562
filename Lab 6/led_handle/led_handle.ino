#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <PubSubClient.h>

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";

const char* mqtt_server = "172.31.9.11";
const char* mqttTopic = "mmcl/nhom12/prediction/rainy";

WiFiClient espClient;
PubSubClient client(espClient);

#define led D3

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

  pinMode(led, OUTPUT);
  digitalWrite(led, LOW);

  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {
    if (!client.connected()) {
      reconnect(mqttTopic);
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

  if (message == "rainy") digitalWrite(led, HIGH);
  else digitalWrite(led, LOW);
}

void reconnect(const char* topic) {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "WemosClient-";
    clientId += String(random(0xffff), HEX);
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      client.subscribe(topic);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" retrying in 5 seconds");
      delay(5000);
    }
  }
}
