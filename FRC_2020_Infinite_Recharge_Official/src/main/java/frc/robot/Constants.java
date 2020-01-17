/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    //Constants used to determine ports for constructors or other constants for setting variables

    //PORTS
    //Sparks
    public static final int leftDrivePort = 0; 
    public static final int rightDrivePort = 1;
    public static final int shooterPort = 2;
    public static final int rotaterPort = 3;
    public static final int anglerPort = 4;
    public static final int shooterLoaderPort = 4;
    public static final int colorWheelPort = 5;
    public static final int intakePort = 6;
    public static final int indexMotorOne = 7;
    public static final int indexMotorTwo = 8;
    public static final int indexMotorThree = 9;

    //Solenoids 
    public static final int intakeLifterPort = 0;
    public static final int colorWheelLifterPort = 1;

    //Sensors
    public static final int gyroPort = 0;
    public static final int leftDriveEncAPort = 0;
    public static final int leftDriveEncBPort = 0;
    public static final int rightDriveEncAPort = 0;
    public static final int rightDriveEncBPort = 0;


    //VARIABLES
    public static final int driveDisPerPulse = 0;
    
}
