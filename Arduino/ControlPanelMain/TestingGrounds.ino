/*This file is intended to offer a testing ground for the ColorSensorAPI and the ControlPanelAPI*/

#include "ColorSensorAPI.h"

void setup() {
    Serial.begin(9600);
    Serial.println("Setting up color sensors");
    setupAllColorSensors();
    Serial.println("Color sensors set up successfully");
}

void loop() {
    Serial.println("Red 1 :");
    Serial.println(getRed(1));
    Serial.println("Green 1 :");
    Serial.println(getGreen(1));
    Serial.println("Blue 1 :");
    Serial.println(getBlue(1));
    Serial.println("-----");
    Serial.println("Red 2 :");
    Serial.println(getRed(2));
    Serial.println("Green 2 :");
    Serial.println(getGreen(2));
    Serial.println("Blue 2 :");
    Serial.println(getBlue(2));
    Serial.println("=====");
    delay(2000);
}
