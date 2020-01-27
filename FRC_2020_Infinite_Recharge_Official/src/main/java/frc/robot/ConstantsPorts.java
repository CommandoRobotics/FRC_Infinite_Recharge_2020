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
public final class ConstantsPorts {
    
    //Constants used to determine ports for constructors

    //PORTS
    //Sparks
    public static final int leftDrivePort = 0; 
    public static final int rightDrivePort = 1;
    public static final int shooterPort = 2;
    public static final int panPort = 3;
    public static final int tiltPort = 4;
    public static final int shooterLoaderPort = 5;
    public static final int colorWheelPort = 6;
    public static final int intakePort = 7;
    public static final int indexMotorOne = 8;
    public static final int indexMotorTwo = 9;
    public static final int indexMotorThree = 10;

    //Solenoids 
    public static final int intakeLifterPort = 0;
    public static final int colorWheelLifterPort = 1;
    public static final int shooterRiserPort = 2;

    //Servos
    public static final int limelightServoPort = 11;

    //Sensors
    public static final int gyroPort = 0;
    public static final int leftDriveEncAPort = 1;
    public static final int leftDriveEncBPort = 2;
    public static final int rightDriveEncAPort = 3;
    public static final int rightDriveEncBPort = 4;
    public static final int tiltEncAPort = 5;
    public static final int tiltEncBPort = 6;
    public static final int panEncAPort = 7;
    public static final int panEncBPort = 8;
    public static final int shooterEncAPort = 9;
    public static final int shooterEncBPort = 10;
}
