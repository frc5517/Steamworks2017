#include <Wire.h>
#include <FastLED.h>

#define I2C_ADDRESS 4
#define DATA_PIN 7
#define NUM_LEDS 49
#define MAX_BRIGHTNESS 255

// Colors
//#define COLOR_RED = 0xFF1C1C;
//#define COLOR_BLUE = 0x007BFF;

// Modes
#define ONE_COLOR 1

CRGB leds[NUM_LEDS];

// Values from Roborio
int currentMode;
double currentR;
double currentG;
double currentB;

String byteRead;

void setup() {

  Wire.begin(I2C_ADDRESS);
  Wire.onReceive(receiveEvent);

  FastLED.addLeds<WS2811, DATA_PIN, BRG>(leds, NUM_LEDS);

  Serial.begin(9600);

}

void loop() {

  /*if (Serial.available()) {
    byteRead = Serial.readString();
    Serial.println(byteRead);
  }*/

  /*colorChase(CRGB::Red, 25);
  colorChase(CRGB::Green, 25);
  colorChase(CRGB::Blue, 25);*/
  cylon(CRGB::Green, 40, 1);

  //rainbow(20);
  
  runCurrentMode();
  delay(100);
}

void runCurrentMode() {
  switch(currentMode) {
    case ONE_COLOR:
      allOneColor(currentR, currentG, currentB);
    break;
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
  currentMode = mode;
  currentR = r;
  currentG = g;
  currentB = b;
}

void allOneColor(int r, int g, int b) {
  fill_solid(leds, NUM_LEDS, CRGB(r, g, b));
  FastLED.show();
}

void colorChase(uint32_t color, uint8_t wait)
{
  //clear() turns all LEDs off
  FastLED.clear();
  //The brightness ranges from 0-255
  //Sets brightness for all LEDS at once
  FastLED.setBrightness(MAX_BRIGHTNESS);
  // Move a single led
  for(int led_number = 0; led_number < NUM_LEDS; led_number++)
  {
    // Turn our current led ON, then show the leds
    leds[led_number] = color;

    // Show the leds (only one of which is has a color set, from above
    // Show turns actually turns on the LEDs
    FastLED.show();

    // Wait a little bit
    delay(wait);

    // Turn our current led back to black for the next loop around
    leds[led_number] = CRGB::Black;
  }
  return;
}

//Move an "empty" dot down the strip
void missingDotChase(uint32_t color, uint8_t wait)
{
  //Step thru some brightness levels from max to 10.  led_brightness/=2 is a cryptic shorthand way of saying led_brightness = led_brightness/2
  for (int led_brightness = MAX_BRIGHTNESS; led_brightness > 10; led_brightness/=2)
  {
    FastLED.setBrightness(led_brightness);

    // Start by turning all pixels on:
    //for(int led_number = 0; led_number < NUM_LEDS; led_number++) leds[led_number] = color;
    //https://github.com/FastLED/FastLED/wiki/Controlling-leds
    fill_solid(leds, NUM_LEDS, color);

    // Then display one pixel at a time:
    for(int led_number = 0; led_number < NUM_LEDS; led_number++)
    {
      leds[led_number] =  CRGB::Black; // Set new pixel 'off'
      if( led_number > 0 && led_number < NUM_LEDS)
      {
        leds[led_number-1] = color; // Set previous pixel 'on'
      }
      FastLED.show();
      delay(wait);
    }
  }
  return;
}

//Cylon - LED sweeps back and forth, with the color, delay and number of cycles of your choice 
void cylon(CRGB color, uint16_t wait, uint8_t number_of_cycles)
{
  FastLED.setBrightness(255);
  for (uint8_t times = 0; times<=number_of_cycles; times++)
  {
    // Make it look like one LED is moving in one direction
    for(int led_number = 0; led_number < NUM_LEDS; led_number++)
    {
      //Apply the color that was passed into the function
      leds[led_number] = color;
      //Actually turn on the LED we just set
      FastLED.show();
      // Turn it back off
      leds[led_number] = CRGB::Black;
      // Pause before "going" to next LED
      delay(wait);
    }

    // Now "move" the LED the other direction
    for(int led_number = NUM_LEDS-1; led_number >= 0; led_number--)
    {
      //Apply the color that was passed into the function
      leds[led_number] = color;
      //Actually turn on the LED we just set
      FastLED.show();
      // Turn it back off
      leds[led_number] = CRGB::Black;
      // Pause before "going" to next LED
      delay(wait);
    }
  }
  return;
}

void rainbow(uint8_t wait) 
{

  uint16_t hue;
  FastLED.clear();

  for(hue=10; hue<255*3; hue++) 
  {

    fill_rainbow( &(leds[0]), NUM_LEDS /*led count*/, hue /*starting hue*/);    
    FastLED.show();
    delay(wait);
  }
  return;
}
