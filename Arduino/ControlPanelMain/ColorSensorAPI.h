#ifndef _ColorSensorAPI_H
#define _ColorsSensorAPI_H

#include <Wire.h>

const int colorSensorAddress = 0x52;
const int redRegisterValue = 0x13;
const int greenRegisterValue = 0x0D;
const int blueRegisterValue = 0x10;
const int mainControlRegisterValue = 0x00;
const int startingRegisterValue = 0x0D;
const int colorSwitcherRegisterValue = 0x19;
const int multiplexerAddress = 0x70;

// Switch which color sensor we are talking to
static void selectColorSensor(int sensorSelected) {
    // Make sure that the queried sensor actually existed
    if(sensorSelected > 7) {
        return;
    }
    // Send the multiplexer a message saying which color senor to use
    Wire.beginTransmission(multiplexerAddress);
    Wire.write(1 << sensorSelected);
    Wire.endTransmission();
}

// Set up the color senor
static void setupColorSensor() {
    // Start listening to the I2C bus
    Wire.begin();

    // Enable the color sensor
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(mainControlRegisterValue);
    Wire.write(0b00000110); // Set the light sensor enable bit
    Wire.endTransmission();
}

static void setupAllColorSensors() {
    // Select color sensor 1
    selectColorSensor(0);
    
    // Setup sensor 1
    setupColorSensor();

    // Select color sensor 2
    selectColorSensor(1);

    // Setup sensor 2
    setupColorSensor();

    // Select color sensor 3
    selectColorSensor(2);

    // Setup sensor 3
    setupColorSensor();

    // Select color sensor 4
    selectColorSensor(3);

    // Setup sensor 4
    setupColorSensor();

    // Select color sensor 5
    selectColorSensor(4);

    // Setup sensor 5
    setupColorSensor();
}

static int getRed(int colorSensorToReadFrom) {

    //Select what color sensor to use
    selectColorSensor(colorSensorToReadFrom);

    // Switch color to red
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(colorSwitcherRegisterValue);
    Wire.write(0b00100000);
    Wire.endTransmission();

    // Form a request for the red color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(redRegisterValue); // Request the red value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte redLeastSignificantByte = Wire.read();
    byte redIntermediateByte = Wire.read();
    byte redMostSignificantByte = Wire.read();
    int redTotal = int(redMostSignificantByte)*255*255+int(redIntermediateByte)*255+redLeastSignificantByte;
    return redTotal;
}

static int getGreen(int colorSensorToReadFrom) {

    // Select which color sensor to use
    selectColorSensor(colorSensorToReadFrom);

    // Switch color to green
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(colorSwitcherRegisterValue);
    Wire.write(0b00010000);
    Wire.endTransmission();

    // Form a request for the green color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(greenRegisterValue); // Request the green value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte greenLeastSignificantByte = Wire.read();
    byte greenIntermediateByte = Wire.read();
    byte greenMostSignificantByte = Wire.read();
    int greenTotal = int(greenMostSignificantByte)*255*255+int(greenIntermediateByte)*255+greenLeastSignificantByte;
    return greenTotal;
}

static int getBlue(int colorSensorToReadFrom) {

    // Select which color sensor to use
    selectColorSensor(colorSensorToReadFrom);

    // Switch color to blue
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(colorSwitcherRegisterValue);
    Wire.write(0b00110000);
    Wire.endTransmission();

    // Form a request for the blue color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(blueRegisterValue); // Request the blue value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte blueLeastSignificantByte = Wire.read();
    byte blueIntermediateByte = Wire.read();
    byte blueMostSignificantByte = Wire.read();
    int blueTotal = int(blueMostSignificantByte)*255*255+int(blueIntermediateByte)*255+blueLeastSignificantByte;
    return blueTotal;
}

#endif
