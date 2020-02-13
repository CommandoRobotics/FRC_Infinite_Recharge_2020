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
public final class ConstantsPID {
    
    //Constants Used for PID and tuning

    //AutoAim PID
    public static final double kPanP = .1;
    public static final double kPanI = 0;
    public static final double kPanD = 0;
    public static final double kTiltP = .1;
    public static final double kTiltI = 0;
    public static final double kTiltD = 0;
    public static final double tiltTolerance = 5;
    public static final double panTolerance = 5;

    //Shooter PID
    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kIZone = 0;
    public static final double kFF = 0;
    public static final double kMax = 0;
    public static final double kMin = 0;
}
