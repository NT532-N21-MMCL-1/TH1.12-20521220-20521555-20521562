#define trigPin 3
#define echoPin 2
#define ledPin 13

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(ledPin, OUTPUT);
}

void loop() {
  long duration, distance;
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;

  if (distance > 0 && distance <= 7) {
    ledHIGH(100);
    Serial.println("Led level: 5");
  } else if (distance > 7 && distance <= 14) {
    ledHIGH(200);
    Serial.println("Led level: 4");
  } else if (distance > 14 && distance <= 21) {
    ledHIGH(300);
    Serial.println("Led level: 3");
  } else if (distance > 21 && distance <= 28) {
    ledHIGH(400);
    Serial.println("Led level: 2");
  } else {
    ledHIGH(1000);
    Serial.println("Led level: 1");
  }

  Serial.print(distance);
  Serial.println(" cm");
}

void ledHIGH(int speed){
   digitalWrite(ledPin, HIGH);
   delay(speed);
   digitalWrite(ledPin, LOW);
   delay(speed);
}