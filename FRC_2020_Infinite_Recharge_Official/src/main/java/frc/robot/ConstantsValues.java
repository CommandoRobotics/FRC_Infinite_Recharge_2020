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
public final class ConstantsValues {

    //Constants used as variables for things like speeds etc.
    
    //VARIABLES
    //Distance Per Pulses and PPR
    public static final int drivePPR = 2048;
    public static final double driveDisPerPulse = drivePPR/(6*Math.PI);
    public static final double shooterTInDisPerPulse = 0;
    public static final int shooterCPR = 8192;
    public static final double shooterBInDisPerPulse = 0;
    public static final double shooterTMeterDisPerPulse = 0;
    public static final double tiltDisPerPulse = 0;
    public static final double panDisPerPulse = 0;
    public static final double tiltCounterTicks = 179;
    public static final double panEncPPR = 2000;
    public static final double colorMotorDisPerPulse = 0;
    public static final double colorWheelEncPPR = 1;

    //Max and min speeds and angles
    public static final double genericMinSpeed = .1;
    public static final double shooterTMinSpeed = .1;
    public static final double shooterBMinSpeed = .1;
    public static final double loaderMinSpeed = .1;
    public static final double panMinSpeed = .1;
    public static final double tiltMinSpeed = .1;
    public static final double minTiltAngle = 40;
    public static final double maxTiltAngle = 90;
    public static final double minPanAngle = -90;
    public static final double maxPanAngle = 90;
    public static final double tiltDefaultPos = 40;
    
    //Drive Constants
    public static final boolean gyroReversed = false;
    public static final boolean rightEncReversed = false;
    public static final double driveRampRate = .1;

    // Home Speeds
    public static final double tiltHomeSpeed = -0.3;
    public static final double panHomeSpeed = 0.3;

    //Misc.
    public static final double intakeSpeed = 0.7;
    public static final double sweepSpeed = -0.7;
    public static final double indexInSpeed = 0.5;
    public static final double expellSpeed = 0.7;
    public static final double axisThreshold = .08;
    public static final double acceleration = -9.8; 
    public static final double negativeTargetVelocity = -.5;    
    public static final double targetHeightLowered = 2;
    public static final double targetHeightLifted = 1.9;
    public static final double limlightAngleLow = 15;
    public static final double limlightAngleHigh = 15;
    public static final double funnelLowSpeed = 0.5;
    
    public static final double distanceFromCenterOfColorWheelInInches = 0;
    public static final int colorWheelGearboxRatio = 1; // As x:1
    public static final int colorWheelDiameterInInches = 3; // The diameter of the wheel that is touching the control panel, given in inches.
    public static final int colorWheelEncoderCountsPerRevolution =1000;
    public static final double colorWheelRotationsToSpinWhenPositioning = 4;
    public static final double shooterWheelRadius = 1;
    
}
