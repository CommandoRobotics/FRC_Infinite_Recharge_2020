/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.APIs.ProjectileMathAPI;
import frc.robot.subsystems.AutoAimSubsystem;


public class ReadLimelight extends CommandBase {

  private ProjectileMathAPI projectileMath; 

  AutoAimSubsystem autoAimSubsystem;
  public ReadLimelight(AutoAimSubsystem m_autoAimSubsystem) {
    autoAimSubsystem = m_autoAimSubsystem;

    addRequirements(m_autoAimSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    projectileMath = new ProjectileMathAPI();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(projectileMath.calculateInitialVelocityAndAngle(5, 2));
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
