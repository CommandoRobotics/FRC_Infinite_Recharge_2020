/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class DriveSubsystem extends SubsystemBase {

  //TODO Decide on what motor types to use
  Spark leftMotors;
  Spark rightMotors;

  DifferentialDrive drive;
  DifferentialDriveOdometry dOdometry;

  Encoder leftDriveEncoder;
  Encoder rightDriveEncoder;

  //TODO Create a gyro properly and decide on which one we need
  AnalogGyro gyro;


  public DriveSubsystem() {
    leftDriveEncoder = new Encoder(ConstantsPorts.leftDriveEncAPort, ConstantsPorts.leftDriveEncBPort, true);
    rightDriveEncoder =  new Encoder(ConstantsPorts.rightDriveEncAPort, ConstantsPorts.rightDriveEncBPort);
    leftDriveEncoder.setDistancePerPulse(ConstantsValues.driveDisPerPulse);
    rightDriveEncoder.setDistancePerPulse(ConstantsValues.driveDisPerPulse);

    gyro = new AnalogGyro(ConstantsPorts.gyroPort);

    leftMotors = new Spark(ConstantsPorts.leftDrivePort);
    rightMotors = new Spark(ConstantsPorts.rightDrivePort);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    dOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
  }

  //BASIC DRIVE METHODS

  /**Drives in Tank Drive with direct input controls for left and right power */
  public void driveTank(double leftPower, double rightPower) {
    drive.tankDrive(leftPower, rightPower);
  }

  /**Drives in Arcade Mode with direct inputs */
  public void driveArcade(double power, double rotation) {
    drive.arcadeDrive(power, rotation);   
  }

   /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(-rightVolts);
    drive.feed();
  }

  /**
   * Overrides the max speed of the drive motors to the set input
   * 
   * @param speed The max speed that will be outputted to the drive motors
   */
  public void setMaxSpeed(double speed) {
    drive.setMaxOutput(speed);
  }
  
  //There might be coffee btw

  /**Stops all the drive motors */
  public void stopDrive() {
    drive.tankDrive(0,0);
  }

  //GYRO METHODS

  /**Returns the the raw angle from the drive trains's gyro 
   * 
   * @return the current gyro value in degrees. This is continuous and 
   *         and will go past 360
  */
  public double getAngle() {
    return gyro.getAngle();
  }

  /**Resets the gyro back to 0 degrees (basically re-centers it) */
  public void resetGyro() {
    gyro.reset();
  }

  /**
   * Returns the vurrent heading of the robot
   * 
   * @return the heading of the robot from -180 to 180 degrees
   */
  public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle(), 360) * (ConstantsValues.gyroReversed ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return gyro.getRate() * (ConstantsValues.gyroReversed ? -1.0 : 1.0);
  }

  //ENCODER METHODS

  /**Retrieves the average distance driven from the drive encoders 
   * 
   * @return the distance in inches from each left and right encoders added together
   *         and then divided by 2 to achieve an average distance
  */
  public double getAveDistance() {
    return (leftDriveEncoder.getDistance() + rightDriveEncoder.getDistance())/2;
  }

  /** Retrieves the average raw counts from the drive encoders 
   * 
   * @return the raw output (PPR) from each left and right encoders added together
   *         and then divided by 2 to achieve an average distance
  */
  public double getRawEncoderVal() {
    return (leftDriveEncoder.getRaw() + rightDriveEncoder.getRaw())/2;
  }

  /**
   * Returns the the speed of the wheel at that moment as a DifferentialDriveWheelSpeeds object
   *  
   * @return the speed of the wheels as a DifferentialDriveWheelSpeeds
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftDriveEncoder.getRate(), rightDriveEncoder.getRate());
  }

  /**Resets the drive encoders back to 0 */
  public void resetEncoders() {
    leftDriveEncoder.reset();
    rightDriveEncoder.reset();
  }

  //PID/PathWeaver METHODS

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return dOdometry.getPoseMeters();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    dOdometry.update(Rotation2d.fromDegrees(getHeading()), leftDriveEncoder.getDistance(),
                     rightDriveEncoder.getDistance());
  }
}
