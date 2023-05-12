#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>

#define WIFI_SSID "UiTiOt-E3.1"
#define WIFI_PASSWORD "UiTiOtAP"
#define MQTT_SERVER "172.31.9.10"
#define MQTT_PORT 1883
#define MQTT_USER "20521562"
#define MQTT_PASSWORD "password"
#define MQTT_TOPIC "mmcl/nhom12/dht/value"
#define LED_PIN D6
#define DHT_PIN D7
#define DHT_TYPE DHT22

WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);
DHT dht(DHT_PIN, DHT_TYPE);
unsigned long lastMsg = 0;

void setup() {
  pinMode(LED_PIN, OUTPUT);
  digitalWrite(LED_PIN, LOW);
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.println("Connecting to WiFi...");
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT);
  mqttClient.subscribe("mmcl/nhom12/dht/detected");
  mqttClient.setCallback(alert);
  dht.begin();
}

void loop() {
  if (!mqttClient.connected()) {
    reconnect();
  }
  mqttClient.loop();

  unsigned long now = millis();
  if (now - lastMsg > 5000) {
    lastMsg = now;
    float temperature = dht.readTemperature();
    float humidity = dht.readHumidity();
    Serial.print("Temperature: ");
    Serial.print(temperature);
    Serial.print(" °C");
    Serial.print(" Humidity: ");
    Serial.print(humidity);
    Serial.println(" %");
    String payload = String(temperature) + ";" + String(humidity);
    mqttClient.publish(MQTT_TOPIC, payload.c_str());
    mqttClient.subscribe("mmcl/nhom12/dht/detected");
  }
}

void alert(char* topic, byte* payload, unsigned int length) {
  
  // Chuyển dữ liệu từ payload sang string
  String message = "";
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }
  Serial.print("Tin nhắn trên topic [");
  Serial.print(topic);
  Serial.print("]: ");
  Serial.println(message);
  if (message == "Temperature or humidity is abnormal!") {
      digitalWrite(LED_PIN, HIGH);
      delay(1000);
      digitalWrite(LED_PIN, LOW);
      delay(1000);
      digitalWrite(LED_PIN, HIGH);
      delay(1000);
      digitalWrite(LED_PIN, LOW);
      delay(1000);
    }
    else {
      digitalWrite(LED_PIN, LOW);
    }
}

void reconnect() {
  while (!mqttClient.connected()) {
    Serial.println("Connecting to MQTT...");
    if (mqttClient.connect("ESP8266Client", MQTT_USER, MQTT_PASSWORD)) {
      Serial.println("Connected to MQTT");
    }
    else {
      Serial.print("MQTT failed");
      Serial.print(mqttClient.state());
      Serial.println(" try again in 5 seconds");
      delay(2000);
    }
  }
}