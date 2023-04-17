int sensorPin = A0;
int sensorValue=0;

int ledPin[] = {13,12,11,10,9,8,7,6,5,4};

int buttonPin = 3;
int preStateButton = LOW;
int count = 0;
void setup()
{
  Serial.begin(9600);
  pinMode(sensorPin, INPUT);
  for(int i=0; i<sizeof(ledPin); i++){
  	pinMode(ledPin[i], OUTPUT);
  }
  pinMode(buttonPin, INPUT);
}

void loop()
{
  sensorValue = analogRead(sensorPin);
  Serial.println(sensorValue);
  delay(500);
  for(int i=0; i<sizeof(ledPin); i++){
  	digitalWrite(ledPin[i], LOW);
  }
  int ledsDisplay = map(sensorValue,679,6,0,sizeof(ledPin));
  for(int i=0; i<ledsDisplay; i++){
    digitalWrite(ledPin[i], HIGH);
  }
  
  int curStateButton = digitalRead(buttonPin);
  if (curStateButton==HIGH && preStateButton==LOW){
    count++;
    Serial.println(count);
  }
  preStateButton=curStateButton;
  
  if (ledsDisplay==6){
  	if(count==1){
    	Serial.println("Led mode: 1");
    	for(int i=0; i<5; i++){
      	digitalWrite(ledPin[i], HIGH);
  		} 
  	}
  
  	if(count==2){
    	Serial.println("Led mode: 2");
    	for(int i=0; i<10; i++){
      	digitalWrite(ledPin[i], HIGH); 
    	}
  	}
  }
  delay(300);
}