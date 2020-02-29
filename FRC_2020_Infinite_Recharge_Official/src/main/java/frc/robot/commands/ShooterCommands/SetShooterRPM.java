/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.APIs.ProjectileMathAPI;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class SetShooterRPM extends CommandBase {
  
  ShooterSubsystem shooterSubsystem;
  ProjectileMathAPI projectileMath;
  double targetRPM;
  boolean finished;
  
  public SetShooterRPM(ShooterSubsystem m_shooterSubsystem, double targetRPM) {
    shooterSubsystem = m_shooterSubsystem;
    this.targetRPM = targetRPM;
    addRequirements(m_shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooterSubsystem.setShooterTarget(targetRPM);
    finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      shooterSubsystem.stopShooter();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
