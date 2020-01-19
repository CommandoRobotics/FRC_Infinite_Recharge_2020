#include "ColorSensorAPI.h"

void setup() {
    Serial.begin(9600);
    setupColorSensor();
}

void loop() {
    Serial.println("Red :");
    Serial.println(getRed());
    Serial.println("Green :");
    Serial.println(getGreen());
    Serial.println("Blue :");
    Serial.println(getBlue());
    delay(1000);
}
