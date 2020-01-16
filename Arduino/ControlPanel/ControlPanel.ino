#include <Wire.h>

const int colorSensorAddress = 0x52;
const int redRegisterValue = 0x15;
const int greenRegisterValue = 0x0F;
const int blueRegisterValue = 0x12;
const int mainControlRegisterValue = 0x00;

void setup() {
    // Start listening to the I2C bus
    Wire.begin();
    Serial.begin(9600);

    // Enable the color sensor
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(mainControlRegisterValue);
    Wire.write(0b00000010); // Set the light sensor enable bit
    Wire.endTransmission();

}

void loop() {

    // Form a request for the red color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(redRegisterValue); // Request the red value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte redOnesPlace = Wire.read();
    byte redTensPlace = Wire.read();
    byte redHundredsPlace = Wire.read();
    int redTotal = int(redHundredsPlace) << 16 + int(redTensPlace) << 8 + int(redOnesPlace);

    // Form a request for the green color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(greenRegisterValue); // Request the green value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte greenOnesPlace = Wire.read();
    byte greenTensPlace = Wire.read();
    byte greenHundredsPlace = Wire.read();
    int greenTotal = int(greenHundredsPlace) << 16 + int(greenTensPlace) << 8 + int(greenOnesPlace);

    // Form a request for the blue color values
    Wire.beginTransmission(colorSensorAddress);
    Wire.write(blueRegisterValue); // Request the blue value
    Wire.endTransmission(); // Send the transmission

    // Read the message
    Wire.requestFrom(colorSensorAddress, 3);
    byte blueOnesPlace = Wire.read();
    byte blueTensPlace = Wire.read();
    byte blueHundredsPlace = Wire.read();
    int blueTotal = int(blueHundredsPlace) << 16 + int(blueTensPlace) << 8 + int(blueOnesPlace);

    Serial.println("Red value : ");
    Serial.print(redTotal);
    Serial.println("Green value : ");
    Serial.print(greenTotal);
    Serial.println("Blue value : ");
    Serial.print(blueTotal);
    delay(1000);
}
