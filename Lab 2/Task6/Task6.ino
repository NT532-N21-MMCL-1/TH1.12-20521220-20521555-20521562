int sensorPin = 2;
int ledPinA = 3;
int ledPinB = 4;

void setup()
{
  Serial.begin(9600);
  pinMode(sensorPin, INPUT);
  pinMode(ledPinA, OUTPUT);
  pinMode(ledPinB, OUTPUT);
}

void loop()
{
  int sensorValue = digitalRead(sensorPin);
  if (sensorValue == HIGH){
    Serial.println("Detected motion!");
    digitalWrite(ledPinA, HIGH);
    digitalWrite(ledPinB, HIGH);
    
    delay(200);
    
    digitalWrite(ledPinA, LOW);
    digitalWrite(ledPinB, LOW);
  }else{
  	Serial.println("No motion");
  }
  delay(500);
}