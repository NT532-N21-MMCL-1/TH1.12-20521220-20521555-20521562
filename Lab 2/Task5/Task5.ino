int pirPin=2;

void setup()
{
  Serial.begin(9600);
  pinMode(pirPin, INPUT);
}

void loop()
{
  int pirValue = digitalRead(pirPin);
  Serial.println(pirValue);
  delay(500);
}