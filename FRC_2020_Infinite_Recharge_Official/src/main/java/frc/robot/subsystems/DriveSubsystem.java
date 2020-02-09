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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class DriveSubsystem extends SubsystemBase {

  //Make a drive with voltage subsystem

  Spark leftMotors;
  Spark rightMotors;

  DifferentialDrive drive;

  Encoder leftDriveEncoder;
  Encoder rightDriveEncoder;

  AnalogGyro gyro;


  public DriveSubsystem() {
    leftMotors = new Spark(ConstantsPorts.leftDrivePort);
    rightMotors = new Spark(ConstantsPorts.rightDrivePort);
    drive = new DifferentialDrive(leftMotors, rightMotors);

    leftDriveEncoder = new Encoder(ConstantsPorts.leftDriveEncAPort, ConstantsPorts.leftDriveEncBPort, true);
    rightDriveEncoder =  new Encoder(ConstantsPorts.rightDriveEncAPort, ConstantsPorts.rightDriveEncBPort);
    leftDriveEncoder.setDistancePerPulse(ConstantsValues.driveDisPerPulse);
    rightDriveEncoder.setDistancePerPulse(ConstantsValues.driveDisPerPulse);

    gyro = new AnalogGyro(ConstantsPorts.gyroPort);

  }
  /**Drives in Tank Drive with direct input controls for left and right power */
  public void driveTank(double leftPower, double rightPower) {
    drive.tankDrive(leftPower, rightPower);
  }

  /**Drives in Arcade Mode with direct inputs */
  public void driveArcade(double power, double rotation) {
    drive.arcadeDrive(power, rotation);   
  }
  
  //There might be coffee btw

  /**Stops all the drive motors */
  public void stopDrive() {
    drive.tankDrive(0,0);
  }

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

  /**Retrieves the average distance driven from the drive encoders 
   * 
   * @return the distance in inches from each left and right encoders added together
   *         and then divided by 2 to achieve an average distance
  */
  public double getDistance() {
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

  /**Resets the drive encoders back to 0 */
  public void resetEncoders() {
    leftDriveEncoder.reset();
    rightDriveEncoder.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
