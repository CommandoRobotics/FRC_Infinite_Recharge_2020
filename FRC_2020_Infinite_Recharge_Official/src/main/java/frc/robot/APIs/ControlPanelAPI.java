package frc.robot.APIs;

// DO THIS: Find every "To-do" and change those values

import frc.robot.APIs.*;

public class ControlPanelAPI {

    // Declare some constants
    public static final int DIAMETER = 32; // The diameter of the control panel in inches
    public static final double CIRCUMFERENCE = 100.541; // The circumference of the control panel in inches

    // Sets names of colors for easy programming. DO NOT CHANGE
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_RED = 3;
    public static final int COLOR_YELLOW = 4;
    public static final int COLOR_NONE = 5;

    // Declare some error numbers
    public static int ERROR_NOT_ENOUGH_SENSORS = 100; // Error number for when we don't have atleast 3 sensors picking up the color wheel
    public static int ERROR_COLOR_SENSORS_NOT_ADJACENT = 101; // Error number for when color sensors are not adjacent
    public static int ERROR_NO_CALCULABLE_COLOR = 102; // Error number for when cant't determine what color we are seeing

    // RGB values for red color
    public static double RED_THRESHOLD_RED = 255; // To-do : change this value to the proper value after testing
    public static double GREEN_THRESHOLD_RED = 255; // To-do : change this value to the proper value after testing
    public static double BLUE_THRESHOLD_RED = 255; // To-do : change this value to the proper value after testing

    // RGB values for yellow color
    public static double RED_THRESHOLD_YELLOW = 255; // To-do : change this value to the proper value after testing
    public static double GREEN_THRESHOLD_YELLOW = 255; // To-do : change this value to the proper value after testing
    public static double BLUE_THRESHOLD_YELLOW = 255; // To-do : change this value to the proper value after testing

    // RGB values for blue color
    public static double RED_THRESHOLD_BLUE = 255; // To-do : change this value to the proper value after testing
    public static double GREEN_THRESHOLD_BLUE = 255; // To-do : change this value to the proper value after testing
    public static double BLUE_THRESHOLD_BLUE = 255; // To-do : change this value to the proper value after testing

    // RGB values for green color
    public static double RED_THRESHOLD_GREEN = 255; // To-do : change this value to the proper value after testing
    public static double GREEN_THRESHOLD_GREEN = 255; // To-do : change this value to the proper value after testing
    public static double BLUE_THRESHOLD_GREEN = 255; // To-do : change this value to the proper value after testing

    // Set the range of values
    public static double THRESHOLD_RANGE = 10; // To-do : change this value to the proper value after testing

    double distanceFromCenter = 0; // The distance between the center of the control panel and the contact point of the turning mechanism
    int gearboxRatio = 1; // The ratio of the gearbox, as x:1
    double wheelDiameter = 2; // The diameter of the wheel that is spinning the control panel
    int countsPerRevolution = 1000; // The amount of encoder ticks per revolution of the motor (before gearbox ratio)

    /**
     * 
     * @param inputDistanceFromCenter The distance between the center of the control panel and the contact point of the turning mechanism
     * @param inputGearboxRatio The ratio of the gearbox, as x:1
     * @param inputWheelDiameter The diameter of the wheel that is spinning the control panel
     * @param inputCountsPerRevolution The amount of encoder ticks per revolution of the motor (before gearbox ratio)
     */
    public ControlPanelAPI(double inputDistanceFromCenter, double inputMotorMaxSpeed, int inputGearboxRatio, double inputWheelDiameter, int inputCountsPerRevolution) {
        distanceFromCenter = inputDistanceFromCenter; // The distance from the center of the control panel to the wheel rotating the control panel given in inches
        motorMaxSpeed = inputMotorMaxSpeed;
        gearboxRatio = inputGearboxRatio;
        wheelDiameter = inputWheelDiameter;
        countsPerRevolution = inputCountsPerRevolution;
    }

    /**
     * Set the distance between the center of the control panel and the contact point of the turning mechanism
     * @param inputDistanceFromCenter The distance between the center of the control panel and the contact point of the turning mechanism in inches
     */
    public void setDistanceFromCenter(double inputDistanceFromCenter) {
        distanceFromCenter = inputDistanceFromCenter;
    }

    /** 
     * This method is used to convert a color, given as a character, to an integer.
     * @param colorAsChar This is the color you want to convert, given as a character.
     * @return An integer representing a color.
    */
    public static int convertColorToInt(char colorAsChar) {
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

    /**
     * This method determines what color we are seeing, based on RGB values and previously established thresholds.
     * @param red The red value (from RGB).
     * @param green The green value (from RGB).
     * @param blue The blue value (from RGB).
     * @return The color we are seeing, represented by an integer.
     */
    public static int calculateColor(double red, double green, double blue) {
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
        
        // See if the RGB values match those of the color YELLOW
        if(red >= (RED_THRESHOLD_YELLOW-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
            if(green >= (GREEN_THRESHOLD_YELLOW-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
                if(blue >= (BLUE_THRESHOLD_YELLOW-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_YELLOW+THRESHOLD_RANGE)) {
                    color = COLOR_YELLOW;
                    return color;
                }
            }
        }
        
        // See if the RGB values match those of the color BLUE
        if(red >= (RED_THRESHOLD_BLUE-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
            if(green >= (GREEN_THRESHOLD_BLUE-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
                if(blue >= (BLUE_THRESHOLD_BLUE-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_BLUE+THRESHOLD_RANGE)) {
                    color = COLOR_BLUE;
                    return color;
                }
            }
        }

        // See if the RGB values match those of the color GREEN
        if(red >= (RED_THRESHOLD_GREEN-THRESHOLD_RANGE) && red <= (RED_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
            if(green >= (GREEN_THRESHOLD_GREEN-THRESHOLD_RANGE) && green <= (GREEN_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
                if(blue >= (BLUE_THRESHOLD_GREEN-THRESHOLD_RANGE) && blue <= (BLUE_THRESHOLD_GREEN+THRESHOLD_RANGE)) {
                    color = COLOR_GREEN;
                    return color;
                }
            }
        }

        color = COLOR_NONE;
        return color;
    }

    /**
     * This method determines what color is centered, and therefore what color the robot is detecting
     * @return The color the robot is detecting, as an integer
     */
    public int calculateCurrentColor() {
        int color = 0; // The 
        // Calculate what color each sensor is seeing, if they are seeing a color
        int colorSensor1Color = calculateColor(); // To-do : add methods for get red, get green, and get blue
        int colorSensor2Color = calculateColor(); // To-do : add methods for get red, get green, and get blue
        int colorSensor3Color = calculateColor(); // To-do : add methods for get red, get green, and get blue
        int colorSensor4Color = calculateColor(); // To-do : add methods for get red, get green, and get blue
        int colorSensor5Color = calculateColor(); // To-do : add methods for get red, get green, and get blue

        boolean canColorSensor1SeeControlPanel = false; // True if color sensor 1 sees a control panel color
        boolean canColorSensor2SeeControlPanel = false; // True if color sensor 2 sees a control panel color
        boolean canColorSensor3SeeControlPanel = false; // True if color sensor 3 sees a control panel color
        boolean canColorSensor4SeeControlPanel = false; // True if color sensor 4 sees a control panel color
        boolean canColorSensor5SeeControlPanel = false; // True if color sensor 5 sees a control panel color

        // Calculate how many sensors are seeing the control panel
        int howManySensorsSeeTheControlPanel = 0;
        if(colorSensor1Color > 0 && colorSensor1Color < 5) {
            howManySensorsSeeTheControlPanel++;
            canColorSensor1SeeControlPanel = true;
        }
        if(colorSensor2Color > 0 && colorSensor2Color < 5) {
            howManySensorsSeeTheControlPanel++;
            canColorSensor2SeeControlPanel = true;
        }
        if(colorSensor3Color > 0 && colorSensor3Color < 5) {
            howManySensorsSeeTheControlPanel++;
            canColorSensor3SeeControlPanel = true;
        }
        if(colorSensor4Color > 0 && colorSensor4Color < 5) {
            howManySensorsSeeTheControlPanel++;
            canColorSensor4SeeControlPanel = true;
        }
        if(colorSensor5Color > 0 && colorSensor5Color < 5) {
            howManySensorsSeeTheControlPanel++;
            canColorSensor5SeeControlPanel = true;
        }

        // Exit program with error 100 if we can not see the control panel with at least 3 sensors
        if(howManySensorsSeeTheControlPanel < 3) {
            return ERROR_NOT_ENOUGH_SENSORS;
        }

        boolean areColorSensorsAdjacent = false; // True if there are atleast 3 adjacent color sensors detecting a color on the control panel
        int adjacentColorSensorStart = 0; // What sensor number the row of adjacent sensors begins from
        int adjacentColorSensorEnd = 0; // What sensor number the row of adjacent sensors ends at

        // Determine if color sensors that are seeing values are adjacent by making sure that there are at least 3 color sensors with a color reading in a row, without interruption by a "no-color" reading.
        if(canColorSensor1SeeControlPanel) {
            if(canColorSensor2SeeControlPanel) {
                if(canColorSensor3SeeControlPanel) {
                    areColorSensorsAdjacent = true;
                    // Check to see if 5 can see the control panel. If it can't, we already have 3 in a row, so we are all clear. If we can, we need to check and make sure that 4 is visible, as well.
                    if(canColorSensor5SeeControlPanel) {
                        if(canColorSensor4SeeControlPanel) {
                            areColorSensorsAdjacent = true;
                        } else {
                            areColorSensorsAdjacent = false;
                        }
                    }
                }
            }
        // Since we only checked for sensor 1, we need to check for sensor 2 given sensor 1 is not activated
        } else if(canColorSensor2SeeControlPanel) {
            if(canColorSensor3SeeControlPanel) {
                if(canColorSensor4SeeControlPanel) {
                    // We can determine that we have 3 color sensors adjacent to one another since the last one that we need to check is 5, and it can't interrupt any line of 3
                    areColorSensorsAdjacent = true;
                }
            }
        // Since we checked for both sensor 1 and sensor 2, we now need to check for sensor 3 given, both 1 and 2 are not activated
        } else if(canColorSensor3SeeControlPanel) {
            if(canColorSensor4SeeControlPanel) {
                if(canColorSensor5SeeControlPanel) {
                    areColorSensorsAdjacent = true;
                }
            }
        }

        // If sensors are not next to each other, exit program with error 101 (color sensors are not adjacent)
        if(areColorSensorsAdjacent == false) {
            return ERROR_COLOR_SENSORS_NOT_ADJACENT;
        }

        // Determine what color sensor is the first in the array
        if(canColorSensor1SeeControlPanel) {
            adjacentColorSensorStart = 1;
        } else if(canColorSensor2SeeControlPanel) {
            adjacentColorSensorStart = 2;
        } else if(canColorSensor3SeeControlPanel) {
            adjacentColorSensorStart = 3;
        }

        // Determine what color sensor is last in the array
        if(canColorSensor5SeeControlPanel) {
            adjacentColorSensorEnd = 5;
        } else if(canColorSensor4SeeControlPanel) {
            adjacentColorSensorEnd = 4;
        } else if(canColorSensor3SeeControlPanel) {
            adjacentColorSensorEnd = 3;
        }

        // See if we are too far to the right. If we are, see if there is a color that is the same for 2 sensors furthest to the left (1 and 2). 
        if(adjacentColorSensorStart == 1 && adjacentColorSensorEnd == 3) {
            if(colorSensor1Color == colorSensor2Color) {
                return colorSensor1Color;
            }
        }

        // See if we are to far to the left. If we are, see if there is a color that is the same for 2 sensors furthest to the right (4 and 5).
        if(adjacentColorSensorStart == 3 && adjacentColorSensorEnd == 5) {
            if(colorSensor4Color == colorSensor45Color) {
                return colorSensor4Color;
            }
        }

        // See if we are in the center of the color wheel. If we are, see what color is in the center
        if(adjacentColorSensorStart == 2 && adjacentColorSensorEnd == 4) {
            return colorSensor3Color;
        }

        // If we have not yet determiend that we are seeing a color, exit with error 102 (no calculable color)
        return ERROR_NO_CALCULABLE_COLOR;
    }


    /**
     * This method calculates the amount of ticks the motor needs to spin to land on the right color, assuming we are on our side of the field.
     * @param targetColorAsChar The color we are trying to spin the wheel to, as a character (can be a raw value from the FMS).
     * @return The amount of ticks to spin the motor, as a double.
     */
    public double calculateOurSide(char targetColorAsChar) {
        double outputMotorSpeed = 0;
        double compensatedSpinDistance = 0;
        double motorSpinDistanceInEncoders = 0; // The amount of ticks the motor should spin
        int targetColor = convertColorToInt(targetColorAsChar);        
        int currentRobotColor = calculateCurrentColor(255, 0, 0); // Add methods to get the real RGB values
        int currentWheelColor = 0;
        double redDistance = 0;
        double yellowDistance = 0;
        double blueDistance = 0;
        double greenDistance = 0;
        double motorSpinDistanceInRotations = 0;

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
            compensatedSpinDistance = (redDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_YELLOW) {
            compensatedSpinDistance = (yellowDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_BLUE) {
            compensatedSpinDistance = (blueDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_GREEN) {
            compensatedSpinDistance = (greenDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        }

        motorSpinDistanceInRotations = motorSpinDistanceInRotations/countsPerRevolution;

        if(motorSpinDistanceInRotations > 0){
            outputMotorSpeed = motorMaxSpeed;
        } else if(motorSpinDistanceInRotations < 0){
            outputMotorSpeed = -motorMaxSpeed;
        } else {
            outputMotorSpeed = 0;
        }

        return outputMotorSpeed;
    }

    /**
     * This method calculates the amount of ticks the motor needs to spin to land on the right color, assuming we are on our side of the field.
     * @param targetColorAsChar The color we are trying to spin the wheel to, as a character (can be a raw value from the FMS).
     * @return The amount of ticks to spin the motor, as a double.
     */
    public double calculateEnemySide(char targetColorAsChar) {
        double outputMotorSpeed = 0;
        double compensatedSpinDistance = 0;
        double motorSpinDistanceInEncoders = 0; // The amount of ticks the motor should spin
        int targetColor = convertColorToInt(targetColorAsChar);        
        int currentRobotColor = calculateCurrentColor(255, 0, 0); // Add methods to get the real RGB values
        int currentWheelColor = 0;
        double redDistance = 0;
        double yellowDistance = 0;
        double blueDistance = 0;
        double greenDistance = 0;
        double motorSpinDistanceInRotations =0;

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
            compensatedSpinDistance = (redDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_YELLOW) {
            compensatedSpinDistance = (yellowDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_BLUE) {
            compensatedSpinDistance = (blueDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        } else if(targetColor == COLOR_GREEN) {
            compensatedSpinDistance = (greenDistance*distanceFromCenter)/16;
            motorSpinDistanceInEncoders = ((compensatedSpinDistance/wheelDiameter)*gearboxRatio)*countsPerRevolution;
        }

        motorSpinDistanceInRotations = motorSpinDistanceInRotations/countsPerRevolution;

        if(motorSpinDistanceInRotations > 0){
            outputMotorSpeed = motorMaxSpeed;
        } else if(motorSpinDistanceInRotations < 0){
            outputMotorSpeed = -motorMaxSpeed;
        } else {
            outputMotorSpeed = 0;
        }

        return outputMotorSpeed;
    }

}