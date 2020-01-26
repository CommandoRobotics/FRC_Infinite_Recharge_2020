#ifndef _ControlPanelAPI_H
#define _ControlPanelAPI_H

#include "ColorSensorAPI.h"

// Declare some constants
static int DIAMETER = 32; // The diameter of the control panel in inches
static double CIRCUMFERENCE = 100.541; // The circumference of the control panel in inches

// Sets names of colors for easy progrmming. DO NOT CHANGE
static int COLOR_BLUE = 1; // Int for the color blue
static int COLOR_GREEN = 2; // Int for the color green
static int COLOR_RED = 3; // Int for the color red
static int COLOR_YELLOW = 4; // Int for the color yellow
static int COLOR_NONE = 5; // Int for no color

// RGB values for red color
static double RED_THRESHOLD_RED = 255; // Red threshold for the control panel color red
static double GREEN_THRESHOLD_RED = 255; // Green threshold for the control panel color red
static double BLUE_THRESHOLD_RED = 255; // Blue threshold for the control panel color red

// RGB values for yellow color
static double RED_THRESHOLD_YELLOW = 255; // Red threshold for the control panel color yellow
static double GREEN_THRESHOLD_YELLOW = 255; // Green threshold for the control panel color yellow
static double BLUE_THRESHOLD_YELLOW = 255; // Blue threshold for the control panel color yellow

// RGB values for blue color
static double RED_THRESHOLD_BLUE = 255; // Red threshold for the control panel color blue
static double GREEN_THRESHOLD_BLUE = 255; // Green threshold for the control panel color blue
static double BLUE_THRESHOLD_BLUE = 255; // Blue threshold for the control panel color blue

// RGB values for green color
static double RED_THRESHOLD_GREEN = 255; // Red threshold for the control panel color green
static double GREEN_THRESHOLD_GREEN = 255; // Green threshold for the control panel color green
static double BLUE_THRESHOLD_GREEN = 255; // Blue threshold for the control panel color green

static double THRESHOLD_RANGE = 5; // The +/- range that the values can vary from their set values

double distanceFromCenterInInches = 0; // This takes the distance of the center of the contact from the edge of the control panel wheel
double motorMaxSpeed = 0; // This is the speed that the motor will spin at to align the wheel
int gearboxRatio = 1; // The ratio of the gearbox the motor is using as x:1
double wheelDiameterInInches = 3; // The diameter of the wheel touching the control panel given in inches
int countsPerRevolution = 1000; // The amount of encoder counts per revolution of the motor

// Convert color as char to an int
int convertColorToInt(char colorAsChar) {
    int colorAsInt = 0;
    if(colorAsChar == 'b') {
        colorAsInt = COLOR_BLUE;
    } else if(colorAsChar == 'g') {
        colorAsInt = COLOR_GREEN;
    } else if(colorAsChar == 'r') {
        colorAsInt = COLOR_RED;
    } else if(colorAsChar == 'y'){
        colorAsInt = COLOR_YELLOW;
    }
    return colorAsInt;
}

// Calculate the current color visible using RGB inputs
int calculateCurrentColor(double red, double green, double blue) {
    int color = 0;
    // See if the RGB values match those of the color RED
    if(red >= (RED_THRESHOLD_RED-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_RED+THRESHOLD_RANGE)) {
        if(green >= (GREEN_THRESHOLD_RED-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_RED+THRESHOLD_RANGE)) {
            if(blue >= (BLUE_THRESHOLD_RED-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_RED+THRESHOLD_RANGE)) {
                color = COLOR_RED;
                return color;
            }
        }
    }

    //See if the RGB values match those of the color YELLOW
    if(red >= (RED_THRESHOLD_YELLOW-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
        if(green >= (GREEN_THRESHOLD_YELLOW-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
            if(blue >= (BLUE_THRESHOLD_YELLOW-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
                color = COLOR_YELLOW;
                return color;
            }
        }
    }

    //See if the RGB values match those of the color BLUE
    if(red >= (RED_THRESHOLD_BLUE-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
        if(green >= (GREEN_THRESHOLD_BLUE-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
            if(blue >= (BLUE_THRESHOLD_BLUE-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
                color = COLOR_BLUE;
                return color;
            }
        }
    }

    //See if the RGB values match those of the color GREEN
    if(red >= (RED_THRESHOLD_GREEN-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
        if(green >= (GREEN_THRESHOLD_GREEN-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
            if(blue >= (BLUE_THRESHOLD_GREEN-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
                color = COLOR_GREEN;
                return color;
            }
        }
    }
    // Since we are not seeing a color that's actually on the color wheel, we can set the color to none
    color = COLOR_NONE;
    return color;
}

// Calculate distance to spin the motor if we are on our side of the field
double calculateOurSide(char targetColorAsChar) {
    double outputMotorSpeed = 0; // The speed that is actually outputed to the motor
    double compensatedSpinDistance = 0; // Distance to spin after we account for the mechanism's distance from the center of the control panel
    double motorSpinDistanceInEncoders = 0; // The amount of ticks the motor should spin
    int targetColor = convertColorToInt(targetColorAsChar); // The color we are trying to spin to     
    int currentRobotColor = calculateCurrentColor(getRed(), getGreen(), getBlue()); // The current color the robot's sensor sees
    int currentWheelColor = 0; // The current color that the FMS sensor sees
    double redDistance = 0; // The distance that we need to spin to put the red color underneath the FMS sensor
    double yellowDistance = 0; // The distance that we need to spin to put the yellow color underneath the FMS sensor
    double blueDistance = 0; // The distance that we need to spin to put the blue color underneath the FMS sensor
    double greenDistance = 0; // The distance that we need to spin to put the green color underneath the FMS sensor
    double motorSpinDistanceInRotations = 0; // The distance that the motor needs to spin in rotations

    // Sets the distances of each color relative to the wheel's current location
    if(currentRobotColor == COLOR_RED) {
        currentWheelColor = COLOR_BLUE;
        redDistance = -0.25*CIRCUMFERENCE;
        yellowDistance = -0.125*CIRCUMFERENCE;
        blueDistance = 0*CIRCUMFERENCE;
        greenDistance = 0.125*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_YELLOW) {
        currentWheelColor = COLOR_GREEN;
        redDistance = 0.125*CIRCUMFERENCE;
        yellowDistance = -0.25*CIRCUMFERENCE;
        blueDistance = -0.125*CIRCUMFERENCE;
        greenDistance = 0*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_BLUE) {
        currentWheelColor = COLOR_RED;
        redDistance = 0*CIRCUMFERENCE;
        yellowDistance = 0.125*CIRCUMFERENCE;
        blueDistance = -0.25*CIRCUMFERENCE;
        greenDistance = -0.125*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_GREEN) {
        currentWheelColor = COLOR_YELLOW;
        redDistance = -0.125*CIRCUMFERENCE;
        yellowDistance = 0*CIRCUMFERENCE;
        blueDistance = 0.125*CIRCUMFERENCE;
        greenDistance = -0.25*CIRCUMFERENCE;
    }

    //  Calculate distances based on the target color
    if(targetColor == COLOR_RED) {
        compensatedSpinDistance = (redDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_YELLOW) {
        compensatedSpinDistance = (yellowDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_BLUE) {
        compensatedSpinDistance = (blueDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_GREEN) {
        compensatedSpinDistance = (greenDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    }

    motorSpinDistanceInRotations = motorSpinDistanceInRotations/countsPerRevolution;

    return motorSpinDistanceInRotations;
}

// Calculate distance to spin the motor if we are on our enemie's side of the field
double calculateEnemySide(char targetColorAsChar) {
    double outputMotorSpeed = 0; // The speed that is actually outputed to the motor
    double compensatedSpinDistance = 0; // Distance to spin after we account for the mechanism's distance from the center of the control panel
    double motorSpinDistanceInEncoders = 0; // The amount of ticks the motor should spin
    int targetColor = convertColorToInt(targetColorAsChar); // The color we are trying to spin to     
    int currentRobotColor = calculateCurrentColor(getRed(), getGreen(), getBlue()); // The current color the robot's sensor sees
    int currentWheelColor = 0; // The current color that the FMS sensor sees
    double redDistance = 0; // The distance that we need to spin to put the red color underneath the FMS sensor
    double yellowDistance = 0; // The distance that we need to spin to put the yellow color underneath the FMS sensor
    double blueDistance = 0; // The distance that we need to spin to put the blue color underneath the FMS sensor
    double greenDistance = 0; // The distance that we need to spin to put the green color underneath the FMS sensor
    double motorSpinDistanceInRotations = 0; // The distance that the motor needs to spin in rotations

    // Sets the distances of each color relative to the wheel's current location
    if(currentRobotColor == COLOR_RED) {
        currentWheelColor = COLOR_BLUE;
        redDistance = 0.25*CIRCUMFERENCE;
        yellowDistance = 0.125*CIRCUMFERENCE;
        blueDistance = 0*CIRCUMFERENCE;
        greenDistance = -0.125*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_YELLOW) {
        currentWheelColor = COLOR_GREEN;
        redDistance = -0.125*CIRCUMFERENCE;
        yellowDistance = 0.25*CIRCUMFERENCE;
        blueDistance = 0.125*CIRCUMFERENCE;
        greenDistance = 0*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_BLUE) {
        currentWheelColor = COLOR_RED;
        redDistance = 0*CIRCUMFERENCE;
        yellowDistance = -0.125*CIRCUMFERENCE;
        blueDistance = 0.25*CIRCUMFERENCE;
        greenDistance = 0.125*CIRCUMFERENCE;
    } else if(currentRobotColor == COLOR_GREEN) {
        currentWheelColor = COLOR_YELLOW;
        redDistance = 0.125*CIRCUMFERENCE;
        yellowDistance = 0*CIRCUMFERENCE;
        blueDistance = -0.125*CIRCUMFERENCE;
        greenDistance = 0.25*CIRCUMFERENCE;
    }

    //  Calculate distances based on the target color
    if(targetColor == COLOR_RED) {
        compensatedSpinDistance = (redDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_YELLOW) {
        compensatedSpinDistance = (yellowDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_BLUE) {
        compensatedSpinDistance = (blueDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    } else if(targetColor == COLOR_GREEN) {
        compensatedSpinDistance = (greenDistance*distanceFromCenterInInches)/16;
        motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameterInInches)*gearboxRatio)*countsPerRevolution;
    }

    motorSpinDistanceInRotations = motorSpinDistanceInRotations/countsPerRevolution;

    return motorSpinDistanceInRotations;
}

#endif