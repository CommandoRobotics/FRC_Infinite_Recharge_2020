/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class DetermineShooterSource extends CommandBase {

  ShooterSubsystem shooterSubsystem;
  ShootWithCalcVelocity shootWithCalcVelocity;
  boolean isAutoAimSource;
  double targetRPM;

  public DetermineShooterSource(boolean isAutoAimSource, double targetRPM, 
                                ShootWithCalcVelocity shootWithCalcVelocity, ShooterSubsystem shooterSubsystem) {
    this.shooterSubsystem = shooterSubsystem;
    this.isAutoAimSource = isAutoAimSource; 
    this.shootWithCalcVelocity = shootWithCalcVelocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (isAutoAimSource) {
      shootWithCalcVelocity.schedule();
    } else {
      shootWithCalcVelocity.cancel();
      new InstantCommand((() -> shooterSubsystem.setShooterTarget(targetRPM)), shooterSubsystem).schedule();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
