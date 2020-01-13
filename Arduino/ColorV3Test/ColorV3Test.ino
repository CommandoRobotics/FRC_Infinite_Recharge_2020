// Rev Color V3 Tester
// This program is designed to test the Rev Color sensor V3.
// If this program does not work, check that you are using a V3 sensor, not a V2 sensor.

// You may need to download the following library. If so, go to Sketch->Include Library->Manage Libraries... and search for it in the list.
#include <Wire.h> // Includes the I2C libraries. 

const int color_sensor_v3_address = 0x52; // Address (in Hex) of the RevV3.

void setup() {
  Wire.begin(); // Begin paying attention to the  I2C bus, as the master

  // Verify we are talking to the V3 sensor by sending a request message for basic info.
  
  // Form the request for data.
  Wire.beginTransmission(color_sensor_v3_address);
  byte part_id_register_number = 0x06; // Location of the Rev V3 Part ID #
  Wire.write(part_id_register_number);
  Wire.endTransmission(); // Send the transmission
  
  // Read the response (1 byte long)
  Wire.requestFrom(color_sensor_v3_address, 1);
  byte part_id_response = Wire.read();

  Serial.print("Part ID: ");
  Serial.println(part_id_response);
}

byte x = 0;

void loop() {
  // Form a message to the sensor
  Wire.beginTransmission(color_sensor_v3_address);
  byte green_register = 0x0D;
  Wire.write(green_register); // Request the green value.
  Wire.endTransmission(); // Send the transmission

  // Read the response (should be three bytes)
  Wire.requestFrom(color_sensor_v3_address, 3);
  byte least_significant_byte = Wire.read();
  byte intermediate_byte = Wire.read();
  byte most_significant_byte = Wire.read();

  // The following just displays on the Arduino console for debugging.
  Serial.print("\nGreen MSB: ");
  Serial.print(most_significant_byte);
  Serial.print("Green INT: ");
  Serial.print(intermediate_byte);
  Serial.print("Green LSB: ");
  Serial.print(least_significant_byte);
  int single_value = int(most_significant_byte) << 16 + int(most_significant_byte) << 8 + least_significant_byte;
  Serial.print("Green Total: ");
  Serial.print(single_value);
    
  delay(500);
}
