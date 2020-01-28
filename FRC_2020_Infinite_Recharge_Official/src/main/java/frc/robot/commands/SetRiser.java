/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RiserSubsystem;

public class SetRiser extends CommandBase {

  RiserSubsystem riserSubsystem;
  boolean setRiser;
  boolean finished = false;

  public SetRiser(boolean setValue, RiserSubsystem m_RiserSubsystem) {
    riserSubsystem = m_RiserSubsystem;
    setRiser = setValue;
    addRequirements(m_RiserSubsystem);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    riserSubsystem.setRiser(setRiser);
    finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    riserSubsystem.retractRiser();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
