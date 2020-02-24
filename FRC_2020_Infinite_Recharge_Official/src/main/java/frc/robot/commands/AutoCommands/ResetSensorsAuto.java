/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ResetSensorsAuto extends CommandBase {

  IntakeSubsystem intakeSubsystem;
  DriveSubsystem driveSubsystem;
  ShooterSubsystem shooterSubsystem;

  boolean finished;

  public ResetSensorsAuto(DriveSubsystem m_driveSubsystem, ShooterSubsystem m_shooterSubsystem) {
    addRequirements(m_driveSubsystem, m_shooterSubsystem);
    driveSubsystem = m_driveSubsystem;
    shooterSubsystem = m_shooterSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.resetEncoders();
    driveSubsystem.resetGyro();
    finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
