/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class DriveStraightTime extends CommandBase {

  private DriveSubsystem driveSubsystem;
  double startTime; 
  double time;
  double rightPower;
  double leftPower;
  boolean finished = false;

  
  public DriveStraightTime(double m_time, double m_leftPower, double m_rightPower, DriveSubsystem m_driveSubsystem) {
    driveSubsystem = m_driveSubsystem;
    time = m_time;
    leftPower = m_leftPower;
    rightPower = m_rightPower;
    //Subsystem Requirements
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.nanoTime();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if((System.nanoTime() - startTime) <= time) {
      driveSubsystem.driveTank(leftPower, rightPower);
      finished = false;
    } else {
      finished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
