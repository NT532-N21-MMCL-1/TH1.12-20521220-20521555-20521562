int ledPin[] = {A3,A2,A1,A0,2,3,4,5,6,7,8,9,10,11,12,13};
int score = 0;
int speed = 400;
int buttonPin = A4;
int buttonState = LOW;

void setup(){
  pinMode(buttonPin, INPUT);
  for(int i = 0; i<16; i++){
  	pinMode(ledPin[i],OUTPUT);
  }
  Serial.begin(9600);
  Serial.println("Game Started! \n");
  Serial.print("Your score: ");
  Serial.println(score);
}

void loop(){
  for(int i=0; i < 16; i++){
    twoTimes_ledHIGH(ledPin[i], speed);
    buttonState = digitalRead(buttonPin);
    if(buttonState == HIGH){
      if(i==7){ 
       Serial.print("\nRight led!");
        switch(speed){
        	case 400:
          		score+=100;
          		Serial.println(" +100 points");
          		break;
            case 300:
          		score+=200;
          		Serial.println(" +200 points");
              break;
				break;
          	case 200:
          		score+=300;
          		Serial.println(" +300 points");
          		break;
        }
       Serial.print("Your score: ");
       Serial.println(score);
       if(speed-100 >=200) speed-=100;
       Serial.print("Current speed: ");
       Serial.print(speed);
       Serial.print("\n");
       i=-1;
       sixTimes_ledHIGH();
      }
      else{
        Serial.print("\nWrong led!");
        switch(speed){
        	case 400:
          		score-=100;
          		Serial.println(" -100 points");
          		break;
            case 300:
          		score-=200;
          		Serial.println(" -200 points");
               break;
				break;
          	case 200:
          		score-=300;
          		Serial.println(" -300 points");
          		break;
        }
        if(score<0){
        	score=0;
          	Serial.print("Your score: ");
        	Serial.println(score);
        }
        else{
        	Serial.print("Your score: ");
        	Serial.println(score);
        }
        speed=400;
        Serial.print("Current speed: ");
        Serial.print(speed);
        Serial.print("\n");
        resetGame(i);
        i=-1;
      }
    }
  }
}

void twoTimes_ledHIGH(int ledPin, int speed){
  digitalWrite(ledPin,HIGH); 
  delay(speed);
  digitalWrite(ledPin,LOW);
  delay(speed);
  digitalWrite(ledPin,HIGH); 
  delay(speed);
  digitalWrite(ledPin,LOW);
  delay(speed);
}

void sixTimes_ledHIGH(){
  for (int i=0; i<6; i++){
    for (int j=0; j<16; j++){
    	digitalWrite(ledPin[j],HIGH); 
    }
    delay(200);
    for (int j=0; j<16; j++){
     	digitalWrite(ledPin[j],LOW); 
    }
    delay(200);
  }
}

void resetGame(int i){
  if(i<7){
   for (int j=i+1; j<=7; j++){
          digitalWrite(ledPin[j],HIGH);
          delay(300);
          digitalWrite(ledPin[j],LOW);
          delay(300);
        } 
  }
  
  if(i>7){
   for (int j=i-1; j>=7; j--){
          digitalWrite(ledPin[j],HIGH);
          delay(300);
          digitalWrite(ledPin[j],LOW);
          delay(300);
        } 
  }
}