package frc.robot.APIs;

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

    /* Sets the ratios used to determine what color the color sensor is seeing
    To-do : Change these ratios to fit the actual sensors
    Format is [color one]_TO_[color two]_RATIO_[color on control panel]*/
    public static double RED_TO_GREEN_RATIO_RED = 0; // The ratio of red to green for the red control panel color
    public static double RED_TO_BLUE_RATIO_RED = 0; // The ratio of red to blue for the red control panel color
    public static double BLUE_TO_GREEN_RATIO_RED = 0; // The ratio of blue to green for the red control panel color

    public static double RED_TO_GREEN_RATIO_GREEN = 0; // The ratio of red to green for the green control panel color
    public static double RED_TO_BLUE_RATIO_GREEN = 0; // The ratio of red to blue for the green control panel color
    public static double BLUE_TO_GREEN_RATIO_GREEN = 0; // The ratio of blue to green for the green control panel color

    public static double RED_TO_GREEN_RATIO_BLUE = 0; // The ratio of red to green for the blue control panel color
    public static double RED_TO_BLUE_RATIO_BLUE = 0; // The ratio of red to blue for the blue control panel color
    public static double BLUE_TO_GREEN_RATIO_BLUE = 0; // The ratio of blue to green for the blue control panel color

    public static double RED_TO_GREEN_RATIO_YELLOW = 0; // The ratio of red to green for the yellow control panel color
    public static double RED_TO_BLUE_RATIO_YELLOW = 0; // The ratio of red to blue for the yellow control panel color
    public static double BLUE_TO_GREEN_RATIO_YELLOW = 0; // The ratio of blue to green for the yellow control panel color

    // Set the +/- range that the threshold valuse can have
    static double THRESHOLD_RANGE = 10; // To-do : change this value to the proper value after testing

    double distanceFromCenter = 0; // This takes the distance of the center of the contact from the edge of the control panel wheel
    double motorMaxSpeed = 0; // This is the speed that the motor will spin at to align the wheel
    int gearboxRatio = 1; // The ratio of the gearbox the motor is using as x:1
    double wheelDiameter = 2; // The diameter of the wheel touching the control panel given in inches
    int countsPerRevolution = 1000; // The amount of encoder counts per revolution of the motor

    public ControlPanelAPI(double inputDistanceFromCenter, double inputMotorMaxSpeed, int inputGearboxRatio, double inputWheelDiameter, int inputCountsPerRevolution) {
        distanceFromCenter = inputDistanceFromCenter; // The distance from the center of the control panel to the wheel rotating the control panel given in inches
        motorMaxSpeed = inputMotorMaxSpeed;
        gearboxRatio = inputGearboxRatio;
        wheelDiameter = inputWheelDiameter;
        countsPerRevolution = inputCountsPerRevolution;
    }

    // Check to see if the current colors fit in a ratio
    boolean checkRatio(double inputColor1, double inputColor2, double ratioToCheck) {
        if(inputColor1/inputColor2 > ratioToCheck-THRESHOLD_RANGE && inputColor1/inputColor2 < ratioToCheck+THRESHOLD_RANGE) {
            return true;
        } else {
            return false;
        }
    }

    // Change the color from a char to an int
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

    public int calculateCurrentColor(double red, double green, double blue) {
        int color = 0;
        
        // See if the red/green ratio is true for the red color
        if(checkRatio(red, green, RED_TO_GREEN_RATIO_RED)) {
            // See if the red/blue ratio is true for the red color
            if(checkRatio(red, blue, RED_TO_BLUE_RATIO_RED)) {
                // See if the blue/green ratio is true for the red color
                if(checkRatio(blue, green, BLUE_TO_GREEN_RATIO_RED)) {
                    color = COLOR_RED;
                    return color;
                }
            }
        }

        // See if the red/green ratio is true for the green color
        if(checkRatio(red, green, RED_TO_GREEN_RATIO_GREEN)) {
            // See if the red/blue ratio is true for the green color
            if(checkRatio(red, blue, RED_TO_BLUE_RATIO_GREEN)) {
                // See if the blue/green ratio is true for the green color
                if(checkRatio(blue, green, BLUE_TO_GREEN_RATIO_GREEN)) {
                    color = COLOR_GREEN;
                    return color;
                }
            }
        }

        // See if the red/green ratio is true for the blue color
        if(checkRatio(red, green, RED_TO_GREEN_RATIO_BLUE)) {
            // See if the red/blue ratio is true for the blue color
            if(checkRatio(red, blue, RED_TO_BLUE_RATIO_BLUE)) {
                // See if the blue/green ratio is true for the blue color
                if(checkRatio(blue, green, BLUE_TO_GREEN_RATIO_BLUE)) {
                    color = COLOR_BLUE;
                    return color;
                }
            }
        }

        // See if the red/green ratio is true for the yellow color
        if(checkRatio(red, green, RED_TO_GREEN_RATIO_YELLOW)) {
            // See if the red/blue ratio is true for the yellow color
            if(checkRatio(red, blue, RED_TO_BLUE_RATIO_YELLOW)) {
                // See if the blue/green ratio is true for the yellow color
                if(checkRatio(blue, green, BLUE_TO_GREEN_RATIO_YELLOW)) {
                    color = COLOR_YELLOW;
                    return color;
                }
            }
        }

        // If the color does not match that of any on the control panel, and thus has not been picked yet
        color = COLOR_NONE;
        return color;
    }

    //Calculate method for if we are on our side of the color wheel
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

    // Calculate method for if we are on our enemies side of the color wheel
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