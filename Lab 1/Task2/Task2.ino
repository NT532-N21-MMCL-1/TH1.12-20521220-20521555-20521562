// Khai báo các chân được sử dụng cho các đèn LED và biến trở
int ledPin1 = 2;
int ledPin2 = 3;
int ledPin3 = 4;
int potPin = A0;

// Khai báo biến cho giá trị của biến trở
int potValue = 0;

// Khai báo biến cho tốc độ của đèn LED
int speed = 0;

void setup() {
  Serial.begin(9600);
  // Thiết lập chân của biến trở là đầu vào
  pinMode(potPin, INPUT);

  // Thiết lập chân của các đèn LED là đầu ra
  pinMode(ledPin1, OUTPUT);
  pinMode(ledPin2, OUTPUT);
  pinMode(ledPin3, OUTPUT);
}

void loop(){
// Đọc giá trị của biến trở
potValue = analogRead(potPin);

// Chia giá trị của biến trở thành 3 mức
if (potValue < 341) {
  speed = 200;
  Serial.println("Slow");
} else if (potValue >= 341 && potValue < 682) {
  speed = 100;
  Serial.println("Medium");
} else {
  speed = 50;
  Serial.println("Fast");
}

// Đèn LED sáng từ trái sang phải
for (int i = 0; i < 3; i++) {
  digitalWrite(ledPin1+i, HIGH);
  delay(speed);
  digitalWrite(ledPin1+i, LOW);
}

// Đèn LED sáng từ phải sang trái
for (int i = 2; i >= 0; i--) {
  digitalWrite(ledPin1+i, HIGH);
  delay(speed);
  digitalWrite(ledPin1+i, LOW);
}

Serial.print("Potentiometer value: ");
Serial.println(potValue); 
}