// Khai báo các chân kết nối với LED
int ledPin1 = 2;
int ledPin2 = 3;
int ledPin3 = 4;
int ledPin4 = 5;
int ledPin5 = 6;
int ledPin6 = 7;
int ledPin7 = 8;
int ledPin8 = 9;
int ledPin9 = 10;
int ledPin10 = 11;
int ledPin11 = 12;
int ledPin12 = 13;

// Khai báo chân kết nối với biến trở
int potPin = A0;

void setup() {
  Serial.begin(9600);
  // Thiết lập chế độ OUTPUT cho các chân LED
  pinMode(ledPin1, OUTPUT);
  pinMode(ledPin2, OUTPUT);
  pinMode(ledPin3, OUTPUT);
  pinMode(ledPin4, OUTPUT);
  pinMode(ledPin5, OUTPUT);
  pinMode(ledPin6, OUTPUT);
  pinMode(ledPin7, OUTPUT);
  pinMode(ledPin8, OUTPUT);
  pinMode(ledPin9, OUTPUT);
  pinMode(ledPin10, OUTPUT);
  pinMode(ledPin11, OUTPUT);
  pinMode(ledPin12, OUTPUT);
}

void loop() {
  // Đọc giá trị từ biến trở
  int potValue = analogRead(potPin);
  
  // Chuyển đổi giá trị từ khoảng 0-1023 sang khoảng 0-12
  int numLEDs = map(potValue, 0, 1023, 0, 12);
  
  // Bật các LED theo số lượng đã tính toán được
  digitalWrite(ledPin1, numLEDs >= 1);
  digitalWrite(ledPin2, numLEDs >= 2);
  digitalWrite(ledPin3, numLEDs >= 3);
  digitalWrite(ledPin4, numLEDs >= 4);
  digitalWrite(ledPin5, numLEDs >= 5);
  digitalWrite(ledPin6, numLEDs >= 6);
  digitalWrite(ledPin7, numLEDs >= 7);
  digitalWrite(ledPin8, numLEDs >= 8);
  digitalWrite(ledPin9, numLEDs >= 9);
  digitalWrite(ledPin10, numLEDs >=10);
  digitalWrite(ledPin11, numLEDs >=11);
  digitalWrite(ledPin12, numLEDs>=12);
}
