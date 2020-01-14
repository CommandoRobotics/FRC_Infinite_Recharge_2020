/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  Spark leftFrontMotor;
  Spark leftRearMotor;
  Spark rightFrontMotor;
  Spark rightRearMotor;

  DifferentialDrive chassis;

  Encoder leftDriveEncoder;
  Encoder rightDriveEncoder;

  Gyro gyro;


  public DriveSubsystem() {
    
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
