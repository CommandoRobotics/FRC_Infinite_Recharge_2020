/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.ConstantsPID;
import frc.robot.subsystems.DriveSubsystem;

public class DriveWithEnc extends PIDCommand {
  
DriveSubsystem driveSubsystem;
double targetAngle = 0;

  public DriveWithEnc(DriveSubsystem driveSubsystem, double targetDistance) {
    super(
      //Feed it the PIDController with correct PID Values
      new PIDController(ConstantsPID.kDriveP, 0, ConstantsPID.kDriveD),

      //Feed it the method to get the error from
      driveSubsystem::getAveDistance,

      //Then set the target distance to travel (or the target to hit)
      targetDistance,

      //And last give it the method to set the power with 
      //(in this case we use both the encoder error and the rotation correction PID)
      output -> driveSubsystem.driveStraightWithPIDCorrection(output, output),

      //And set the requirement
      driveSubsystem
    );
    //Set the tolerance of the controller
    getController().setTolerance(ConstantsPID.kDriveEncTolerance, ConstantsPID.kDriveVelocityTolerance);
    this.driveSubsystem = driveSubsystem;
  }

  @Override
  public void initialize() {
    driveSubsystem.resetEncoders();
    driveSubsystem.setTargetStraightAngle(driveSubsystem.getAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
