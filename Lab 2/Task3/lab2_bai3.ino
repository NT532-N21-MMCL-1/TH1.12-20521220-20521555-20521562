#include <DHT.h>

#define DHTPIN 2          // Chân kết nối với DHT11
#define DHTTYPE DHT11     // Loại cảm biến DHT

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  // Đọc giá trị nhiệt độ và độ ẩm từ cảm biến
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();

  // In giá trị nhiệt độ và độ ẩm ra serial kèm theo đơn vị
  Serial.print("Nhiệt độ: ");
  Serial.print(temperature);
  Serial.print(" °C, Độ ẩm: ");
  Serial.print(humidity);
  Serial.println(" %");

  delay(2000); // chờ 2 giây trước khi đọc giá trị mới
}