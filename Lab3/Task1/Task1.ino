#include <ESP8266WiFi.h>
#include <WiFiClient.h>

//ESP Web Server Library to host a web page
#include <ESP8266WebServer.h>

//---------------------------------------------------------------
//Our HTML webpage contents in program memory

//Main
const char MAIN_page[] PROGMEM = R"=====(
<!DOCTYPE html>
<html>
<head>
	<title>Choose right leds</title>
</head>
<body>
	<h1>Choose right leds</h1>
	<p>Random number: <span id="random-number">0</span></p>
	<a href="start"><button onclick="startGame()">Start</button></a>
	<button onclick="selectLed(1)">Led 1</button>
	<button onclick="selectLed(2)">Led 2</button>
	<button onclick="selectLed(3)">Led 3</button>
  <p>Your score: <span id="score">0</span></p>

	<script>
		function startGame() {
			var xmlHttp = new XMLHttpRequest();
      xmlHttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          document.getElementById("random-number").innerHTML = this.responseText;
        }
      };
      xmlHttp.open("GET", "start", true);
      xmlHttp.send();
		}

		// Hàm thực hiện việc chọn LED và hiển thị thông báo kết quả
		function selectLed(ledNumber) {
			var randomNumber = parseInt(document.getElementById("random-number").innerHTML);
			var message;
      var score = parseInt(document.getElementById("score").innerHTML);

			if (ledNumber === 1 && randomNumber === 1) {
				message = "You choose the right LED! +1 point";
        score+=1; 
			} else if (ledNumber === 2 && randomNumber === 2) {
				message = "You choose the right LED!" +1 point;
        score+=1;
			} else if (ledNumber === 3 && randomNumber === 3) {
				message = "You choose the right LED!" +1 point;
        score+=1;
			} else {
				message = "Sorry, wrong choice. -1 point";
        if(score > 0){
          score-=1;
        }
			}
      document.getElementById("score").innerHTML = String(score);
			alert(message);
		}
	</script>
</body>
</html>
)=====";

//---------------------------------------------------------------

//On board LED Connected to GPIO2

//#define LED LED_BUILTIN 
#define led1 D3
#define led2 D4
#define led3 D5

//SSID and Password of your WiFi router
const char* ssid = "UiTiOt-E3.1";
const char* password = "UiTiOtAP";
//Declare a global object variable from the ESP8266WebServer class.
ESP8266WebServer server(80); //Server on port 80

//===============================================================
// This routine is executed when you open its IP in browser
//===============================================================

void handleRoot() {
Serial.println("You called root page");
String html = MAIN_page; //Read HTML contents
server.send(200, "text/html", html); //Send web page
}

void randomNumber(){
  ledHIGH_LtoR();
  ledHIGH_RtoL();
  int number = random(1, 4);
  server.send(200, "text/plane", String(number));
}

void ledHIGH_LtoR(){
  digitalWrite(led1, HIGH);
  delay(200);
  digitalWrite(led1, LOW);
  delay(200);
  digitalWrite(led2, HIGH);
  delay(200);
  digitalWrite(led2, LOW);
  delay(200);
  digitalWrite(led3, HIGH);
  delay(200);
  digitalWrite(led3, LOW);
  delay(200);
}

void ledHIGH_RtoL(){
  digitalWrite(led3, HIGH);
  delay(200);
  digitalWrite(led3, LOW);
  delay(200);
  digitalWrite(led2, HIGH);
  delay(200);
  digitalWrite(led2, LOW);
  delay(200);
  digitalWrite(led1, HIGH);
  delay(200);
  digitalWrite(led1, LOW);
}

//==============================================================
// SETUP
//==============================================================

void setup(){
Serial.begin(115200);
WiFi.begin(ssid, password); //Connect to your WiFi router
Serial.println("");
//Onboard LED port Direction output
//pinMode(LED,OUTPUT); 
pinMode(led1, OUTPUT);
pinMode(led2, OUTPUT);
pinMode(led3, OUTPUT);

//Power on LED state off
//digitalWrite(LED,HIGH);
digitalWrite(led1, LOW);
digitalWrite(led2, LOW);
digitalWrite(led3, LOW);

// Wait for connection
while (WiFi.status() != WL_CONNECTED) {
delay(500);
Serial.print(".");
}

//If connection successful show IP address in serial monitor
Serial.println("");
Serial.print("Connected to ");
Serial.println(ssid);
Serial.print("IP address: ");
Serial.println(WiFi.localIP()); //IP address assigned to your ESP
server.on("/", handleRoot); //Which routine to handle at root location. This is display page
server.on("/start", randomNumber);
server.begin(); //Start server
Serial.println("HTTP server started");
}

//==============================================================
// LOOP
//==============================================================

void loop(){
server.handleClient(); //Handle client requests
}