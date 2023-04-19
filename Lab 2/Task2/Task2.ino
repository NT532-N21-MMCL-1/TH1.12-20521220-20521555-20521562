int pinTrig = 4;
int pinEcho = 3;
int pinA = 5;
int pinB = 6;
int pinC = 7;
int pinD = 8;
int pinE = 9;
int pinF = 10;
int pinG = 11;

int t = 1000;

void setup() {
  Serial.begin(9600);
  pinMode(pinTrig, OUTPUT);
  pinMode(pinEcho, INPUT);
  pinMode(pinA, OUTPUT);
  pinMode(pinB, OUTPUT);
  pinMode(pinC, OUTPUT);
  pinMode(pinD, OUTPUT);
  pinMode(pinE, OUTPUT);
  pinMode(pinF, OUTPUT);
  pinMode(pinG, OUTPUT);
}

void loop() {
  int distanceDisplay = 0;
  long duration, distance;
  digitalWrite(pinTrig, LOW); 
  delayMicroseconds(2); 
  digitalWrite(pinTrig, HIGH);
  delayMicroseconds(10); 
  digitalWrite(pinTrig, LOW);
  duration = pulseIn(pinEcho, HIGH);
  distance = (duration/2) / 29.1;

  Serial.print(distance);
  Serial.println(" cm");
  
  if(distance>=0 && distance<=9){
    LED_display(distance);
  }else{
    Serial.println("Out of range!");
    LED_display(0);
  }
  delay(500);
}

void LED_display(int number){
	if(number==0 || number==2 || number==3 || number==5 || number==6 || number==7 || number==8 || number==9){
    	digitalWrite(pinA , LOW);
    }else digitalWrite(pinA , HIGH);
  
  	if(number==0 || number==1 || number==2 || number==3 || number==4 || number==7 || number==8 || number==9){
   		digitalWrite(pinB, LOW);	 
  	}else digitalWrite(pinB , HIGH);
  
  	if(number==0 || number==1 || number==3 || number==4 || number==5 || number==6 || number==7 || number==8 || number==9){
   		digitalWrite(pinC, LOW);	 
  	}else digitalWrite(pinC , HIGH);
  
  	if(number==0 || number==2 || number==3 || number==5 || number==6 || number==8 || number==9){
   		digitalWrite(pinD, LOW);	 
  	}else digitalWrite(pinD , HIGH);
  
  	if(number==0 || number==2 || number==6 || number==8){
   		digitalWrite(pinE, LOW);	 
  	}else digitalWrite(pinE , HIGH);
  
  	if(number==0 || number==4 || number==5 || number==6 || number==8 || number==9){
   		digitalWrite(pinF, LOW);	 
  	}else digitalWrite(pinF , HIGH);
  
  	if(number==2 || number==3 || number==4 || number==5 || number==6 || number==8 || number ==9){
   		digitalWrite(pinG, LOW);	 
  	}else digitalWrite(pinG , HIGH);
}