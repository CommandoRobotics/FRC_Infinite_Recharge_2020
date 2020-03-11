/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class ConstantsPID {
    
    //Constants Used for PID and tuning

    //Drive Feedforward and PID
    public static final double kDriveP = .02;
    public static final double kDriveD = 0;
    public static final double kDriveEncTolerance = 2;
    public static final double kDriveVelocityTolerance = 1;
    public static final double ksVolts = 0;
    public static final double kvVoltSecondsPerMeter = 0;
    public static final double kaVoltSecondsSquaredPerMeter = 0;
    public static final double kPDriveChar = 0;
    public static final double maxSpeedMetersPerSecond = 0;
    public static final double maxAccelerationMetersPerSecondSquared = 0;
    public static final double ramseteB = 2; 
    public static final double ramseteZeta = 0.7;
    public static final double trackWidthMeters = 1;
    public static final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(trackWidthMeters);

    //ColorWheel PID
    public static final double colorWheelP = 1;
    public static final double colorWheelI = 1;
    public static final double colorWheelD = 1;
    public static final double colorWheelTolerance = 1;
    
    //Pan PID
    public static final double kPanP = .01;
    public static final double kPanI = 0;
    public static final double kPanD = .001;
    public static final double panTolerance = 3;

    //Tilt PID
    public static final double kTiltP = .001;
    public static final double kTiltI = 0;
    public static final double kTiltD = .0001;
    public static final double tiltTolerance = 1;

    //TODO Calculate These
    //Shooter FeedForward and PID
    public static final double kPTop = 0.181;
    public static final double kITop = 0;
    public static final double kDTop = 0;
    public static final double kIZoneTop = 0;
    public static final double kFFTop = 0;
    public static final double kMaxTop = 1;
    public static final double kMinTop = -1;
    public static final double kSTop = .022;

    public static final double kPBottom = 0.181;
    public static final double kIBottom = 0;
    public static final double kDBottom = 0;
    public static final double kIZoneBottom = 0;
    public static final double kFFBottom = 0;
    public static final double kMaxBottom = 1;
    public static final double kMinBottom = -1;
}