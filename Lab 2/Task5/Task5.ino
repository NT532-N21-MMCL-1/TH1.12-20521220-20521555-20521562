int pirPin=13;

void setup()
{
  Serial.begin(9600);
  pinMode(pirPin, INPUT);
}

void loop()
{
  int pirValue = digitalRead(pirPin);
  Serial.println(pirValue);
}