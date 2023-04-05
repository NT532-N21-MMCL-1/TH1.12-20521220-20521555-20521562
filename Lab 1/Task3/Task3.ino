// Khai báo chân của LED và nút bấm
int led1 = 2;
int led2 = 3;
int led3 = 4;
int led4 = 5;
int led5 = 6;
int led6 = 7;
int button = 13;

void setup() {
  // Khai báo chân của LED và nút bấm là OUTPUT và INPUT
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(led4, OUTPUT);
  pinMode(led5, OUTPUT);
  pinMode(led6, OUTPUT);
  pinMode(button, INPUT);
  Serial.begin(9600);
}

void loop() {
  // Chờ đợi cho đến khi người dùng bấm nút
  while (digitalRead(button) == HIGH) {
    delay(50);
  }
  
  digitalWrite(led1, HIGH);
	digitalWrite(led2, HIGH);
	digitalWrite(led3, HIGH);
	digitalWrite(led4, HIGH);
	digitalWrite(led5, HIGH);	
	digitalWrite(led6, HIGH);
  	delay(500);
  digitalWrite(led1, LOW);
	digitalWrite(led2, LOW);
	digitalWrite(led3, LOW);
	digitalWrite(led4, LOW);
	digitalWrite(led5, LOW);
	digitalWrite(led6, LOW);
  	delay(500);
  // Xử lý khi người dùng bấm nút
  int num = random(1, 7); // Tạo ngẫu nhiên số từ 1 đến 6
  Serial.println(num);
  for (int i = 0; i < num; i++) {
    // Hiển thị số trên các đèn LED
  	digitalWrite(led1+i, HIGH); 
  }

  delay(1000); // Dừng lại 1 giây để người dùng theo dõi
  digitalWrite(led1, LOW);
	digitalWrite(led2, LOW);
	digitalWrite(led3, LOW);
	digitalWrite(led4, LOW);
	digitalWrite(led5, LOW);
	digitalWrite(led6, LOW);
  delay(250);

  for (int i = 1; i <= 6; i++) {
    digitalWrite(led1, HIGH);
	digitalWrite(led2, HIGH);
	digitalWrite(led3, HIGH);
	digitalWrite(led4, HIGH);
	digitalWrite(led5, HIGH);	
	digitalWrite(led6, HIGH);
	delay(200);
	digitalWrite(led1, LOW);
	digitalWrite(led2, LOW);
	digitalWrite(led3, LOW);
	digitalWrite(led4, LOW);
	digitalWrite(led5, LOW);
	digitalWrite(led6, LOW);
	delay(200);
  }
}