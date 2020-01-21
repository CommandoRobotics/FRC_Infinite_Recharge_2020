/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class TankDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private DriveSubsystem driveSubsystem;
  DoubleSupplier leftPower;
  DoubleSupplier rightPower;

  
  public TankDriveCommand (DoubleSupplier m_leftPower, DoubleSupplier m_rightPower, DriveSubsystem m_driveSubsystem) {
    driveSubsystem = m_driveSubsystem;
    leftPower = m_leftPower;
    rightPower = m_rightPower;
    System.out.println(leftPower.getAsDouble());
    System.out.println(rightPower.getAsDouble());
    //Subsystem Requirements
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.driveTank(leftPower.getAsDouble(), rightPower.getAsDouble());
    System.out.println(leftPower.getAsDouble());
    System.out.println(rightPower.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
