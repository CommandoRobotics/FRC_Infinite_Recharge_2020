/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ConstantsValues;
import frc.robot.APIs.ProjectileMathAPI;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootWithCalcVelocity extends CommandBase {

  ShooterSubsystem shooterSubsystem;
  IndexSubsystem indexSubsystem;
  ProjectileMathAPI projectileMath;
  
  public ShootWithCalcVelocity(ShooterSubsystem m_shooterSubsystem, IndexSubsystem m_indexSubsystem) {
    shooterSubsystem = m_shooterSubsystem;
    indexSubsystem = m_indexSubsystem;
    addRequirements(m_shooterSubsystem, m_indexSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooterSubsystem.riserActive) {
      shooterSubsystem.setShooterTarget(
        projectileMath.fromMPerSecToRPM(ConstantsValues.shooterWheelRadius,
                                        projectileMath.calculateInitialVelocityAndAngle(
                                          shooterSubsystem.getLimelightDis(ConstantsValues.targetHeightLifted, false), 
                                          ConstantsValues.targetHeightLifted)[0]
      ));
    } else {
      shooterSubsystem.setShooterTarget(
        projectileMath.fromMPerSecToRPM(ConstantsValues.shooterWheelRadius,
                                        projectileMath.calculateInitialVelocityAndAngle(
                                          shooterSubsystem.getLimelightDis(ConstantsValues.targetHeightLowered, false), 
                                          ConstantsValues.targetHeightLowered)[0]
      ));
    }
    
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
