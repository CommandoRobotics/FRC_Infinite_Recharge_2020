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
  
  //Drives in Tank Drive for direct input control
  public void driveTank(double leftPower, double rightPower) {
    drive.tankDrive(leftPower, rightPower);
  }

  //Drives in Arcade Mode with direct inputs
  public void driveArcade(double power, double rotation) {
    drive.arcadeDrive(power, rotation);   
  }
  
  //Stops the drive motors
  public void stopDrive() {
    drive.tankDrive(0,0);
  }

  //Retrieves the gyro's raw angle
  public double getAngle() {
    return gyro.getAngle();
  }

  //Retrieveies the average drive Encoder Distance
  public double getDistance() {
    return (leftDriveEncoder.getDistance() + rightDriveEncoder.getDistance())/2;
  }

  //Retrieveies the raw drive Encoder values
  public double getRawEncoderVal() {
    return (leftDriveEncoder.getRaw() + rightDriveEncoder.getRaw())/2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
