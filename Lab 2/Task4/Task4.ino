#include <DHT.h>

#define DHTPIN 2      // Chân kết nối của DHT11 với Arduino
#define DHTTYPE DHT11 // Loại cảm biến DHT11

#define LED_RED 3     // Chân kết nối của đèn LED màu đỏ với Arduino
#define LED_YELLOW 4  // Chân kết nối của đèn LED màu vàng với Arduino
#define LED_GREEN 5   // Chân kết nối của đèn LED màu xanh lá với Arduino

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();

  pinMode(LED_RED, OUTPUT);
  pinMode(LED_YELLOW, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
}

void loop() {
  // Đọc giá trị nhiệt độ và độ ẩm từ cảm biến DHT11
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();

  // Hiển thị giá trị nhiệt độ và độ ẩm trên Serial Monitor
  Serial.print("Temperature: ");
  Serial.print(temperature);
  Serial.print(" °C\tHumidity: ");
  Serial.print(humidity);
  Serial.println(" %");

  // Điều khiển đèn LED tương ứng với giá trị nhiệt độ trong DataCenter
  if (temperature >= 30) {
    digitalWrite(LED_RED, HIGH);
    digitalWrite(LED_YELLOW, LOW);
    digitalWrite(LED_GREEN, LOW);
  } else if (temperature >= 20) {
    digitalWrite(LED_RED, LOW);
    digitalWrite(LED_YELLOW, HIGH);
    digitalWrite(LED_GREEN, LOW);
  } else {
    digitalWrite(LED_RED, LOW);
    digitalWrite(LED_YELLOW, LOW);
    digitalWrite(LED_GREEN, HIGH);
  }

  delay(2000); // Thời gian chờ trước khi đọc lại giá trị từ cảm biến
}