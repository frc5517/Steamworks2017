#include <Wire.h>
#include <FastLED.h>

#define I2C_ADDRESS 4
#define DATA_PIN 6
#define LED_COUNT 60

// Colors
//#define COLOR_RED = 0xFF1C1C;
//#define COLOR_BLUE = 0x007BFF;

// Modes
#define ALL_COLOR 1

CRGB leds[LED_COUNT];

void setup() {

  Wire.begin(I2C_ADDRESS);
  Wire.onReceive(receiveEvent);

  FastLED.addLeds<WS2811, DATA_PIN, RGB>(leds, LED_COUNT);

  Serial.begin(9600);

}

void loop() {
  // Every other LED, green and blue
  for(int i = 0; i < LED_COUNT; i++) {
    if(i % 2 == 0) {
      leds[i] = CRGB::Green;
    }
    else {
      leds[i] = CRGB::Blue;
    }
  }
  FastLED.show();
  delay(1000);
}

void allOneColor(int r, int g, int b) {
  for(int i = 0; i < LED_COUNT; i++) {
    leds[i].setRGB(r, g, b);
  }
  FastLED.show();
}

// executes whenever data is received from roboRIO
void receiveEvent(int howMany) {
  /*while (1 < Wire.available()) { // loop through all but the last
    char c = Wire.read(); // receive byte as a character
    Serial.print(c);         // print the character
  }
  int x = Wire.read();    // receive byte as an integer
  Serial.println(x);         // print the integer
  */
  
  // get first byte, which will be the mode..
  // I think this is how this works, not sure though
  // Will find out later
  int mode = Wire.read();
  // get next three bytes for rgb
  int r = Wire.read();
  int g = Wire.read();
  int b = Wire.read();
  switch(mode) {
    case ALL_COLOR:
      allOneColor(r, g, b);
    break;
  }
}
