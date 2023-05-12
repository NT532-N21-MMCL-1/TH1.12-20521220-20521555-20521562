#include <ESP8266WiFi.h>
#include <PubSubClient.h>

const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";

const char* mqtt_server = "172.31.9.10";

WiFiClient espClient;

PubSubClient client(espClient);

// Chọn chân kết nối LED
const int ledPin = D5;

// Hàm callback để nhận biết MQTT messages
void callback(char* topic, byte* payload, unsigned int length) {
  // Chuyển dữ liệu từ payload sang string
  String message = "";
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }

  // In ra tin nhắn nhận được ở serial
  Serial.print("Tin nhắn trên topic [");
  Serial.print(topic);
  Serial.print("]: ");
  Serial.println(message);

  // Nếu nhận được tin nhắn "on" thì mở đèn
  if (message == "on") {
    digitalWrite(ledPin, HIGH);
  }
  // Nếu nhận được tin nhắn "off" thì tắt đèn
  else if (message == "off") {
    digitalWrite(ledPin, LOW);
  }
}

void setup() {
  Serial.begin(115200);

  // Kết nối với Wi-Fi
  WiFi.begin(ssid, password);
  Serial.println("Kết nối với Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("kết nối thành công với Wifi");

  // Kết nối với MQTT Broker
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  Serial.println("Đang kết nối với MQTT Broker");
  while (!client.connected()) {
    if (client.connect("Client")) {
      Serial.println("Đã kết nối với MQTT Broker");
      client.subscribe("mmcl/nhom12/led");
    } else {
      Serial.print("Kết nối thất bại");
      Serial.print(client.state());
      Serial.println(" Thử lại sau 10 s");
      delay(10000);
    }
  }

  // Chỉ chế độ chân kết nối LED pin là output
  pinMode(ledPin, OUTPUT);
}

void loop() {
  // Kiểm tra kết nối của MQTT Client tới MQTT Broker
  if (!client.connected()) {
    Serial.println("Kết nối lại với MQTT Broker");
    while (!client.connected()) {
      if (client.connect("Client")) {
        Serial.println("Đã kết nối với MQTT Broker");
        client.subscribe("mmcl/nhom12/led");
      } else {
        Serial.print("Kết nối thất bại");
        Serial.print(client.state());
        Serial.println(" Thử lại sau 10 s");
        delay(10000);
      }
    }
  }

  // Liên tục nhận tin nhắn
  client.loop();
}
