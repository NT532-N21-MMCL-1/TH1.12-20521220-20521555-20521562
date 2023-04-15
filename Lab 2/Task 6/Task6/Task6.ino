int pirPin = 3;
int ledPinA = 5;
int ledPinB = 6;

void setup()
{
  Serial.begin(9600);
  pinMode(pirPin, INPUT);
  pinMode(ledPinA, OUTPUT);
  pinMode(ledPinB, OUTPUT);
}

void loop()
{
  int pirValue = digitalRead(pirPin);
  if (pirValue == HIGH){
    Serial.println("Detected motion!");
    digitalWrite(ledPinA, HIGH);
    digitalWrite(ledPinB, HIGH);
    
    delay(300);
    
    digitalWrite(ledPinA, LOW);
    digitalWrite(ledPinB, LOW);
  }else{
  	Serial.println("No motion");
  }
  delay(500);
}