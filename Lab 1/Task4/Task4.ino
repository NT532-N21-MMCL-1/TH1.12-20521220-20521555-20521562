int pinA = 5;
int pinB = 6;
int pinC = 7;
int pinD = 8;
int pinE = 9;
int pinF = 10;
int pinG = 11;

int greenLED = 4;
int yellowLED = 3;
int redLED = 2;

int t = 1000;

void setup() {
  // put your setup code here, to run once:
  pinMode(pinA, OUTPUT);
  pinMode(pinB, OUTPUT);
  pinMode(pinC, OUTPUT);
  pinMode(pinD, OUTPUT);
  pinMode(pinE, OUTPUT);
  pinMode(pinF, OUTPUT);
  pinMode(pinG, OUTPUT);
  pinMode(greenLED, OUTPUT);
  pinMode(yellowLED, OUTPUT);
  pinMode(redLED, OUTPUT);
}

void loop() {
  digitalWrite(greenLED, HIGH);
  for(int i = 9; i>=0; i--){
   LED_display(i);
   delay(1000);
  }
  digitalWrite(greenLED, LOW);
  
  digitalWrite(yellowLED, HIGH);
  for(int i = 2; i>=0; i--){
   LED_display(i);
   delay(1000);
  }
  digitalWrite(yellowLED, LOW);
  
  digitalWrite(redLED, HIGH);
  for(int i = 6; i>=0; i--){
   LED_display(i);
   delay(1000);
  }
  digitalWrite(redLED, LOW);
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