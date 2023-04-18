#include "OneButton.h"

int sensorPin = A0;
int sensorValue = 0;
int buttonPin = A1;

int ledsPin[] = {4,5,6,7,8,9,10,11,12,13};
int ledsNumber;

OneButton button(buttonPin, true);
bool stop=false;

void setup() {
  Serial.begin(9600);
  pinMode(sensorPin, INPUT);
  for (int i=0; i<sizeof(ledsPin); i++){
    pinMode(ledsPin[i], OUTPUT);
  }
  button.attachClick(click_button);
  button.attachDoubleClick(doubleclick_button);
}

void loop() {
    sensorValue = analogRead(sensorPin);
    Serial.println(sensorValue);
    button.tick();
    delay(10);

    for(int i=0; i<10; i++){
      digitalWrite(ledsPin[i], LOW);
    }

    if(stop){
      ledsNumber = map(sensorValue, 0, 1023, 0, 5);
    }

    if(!stop){
      ledsNumber = map(sensorValue, 0, 1023, 0, 10);
    }


    for(int i=0; i<ledsNumber; i++){
      digitalWrite(ledsPin[i], HIGH);
    }
}


void click_button(){
  stop = true;
  Serial.println("1 click");
}

void doubleclick_button(){
  stop = false;
  Serial.println("double click");
}
