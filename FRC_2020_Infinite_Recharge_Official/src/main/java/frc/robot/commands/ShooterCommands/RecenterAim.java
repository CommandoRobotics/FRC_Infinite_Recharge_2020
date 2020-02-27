/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.ConstantsPID;
import frc.robot.ConstantsValues;
import frc.robot.APIs.ProjectileMathAPI;
import frc.robot.subsystems.AutoAimSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RecenterAim extends CommandBase {

  /*
  * COMPLETE Add in overriding code to stop the shooter so that it doesnt pass a certain angle
  * COMPLETE Add in code to recenter the shooter to 0 when we dont see anything
  * TODO (Maybe) Add in code re-center the shooter using limit switches? ASK MECH LATER
  */

  AutoAimSubsystem autoAimSubsystem;
  PIDController tiltPID;
  ProjectileMathAPI projectileMath;

  //WE BUILD EACH OTHER UP LIKE WE BUILD OUR ROBOT ~ Dorian Head Programmer

  /**
   * Creates a new AutoAim.
   */
  public RecenterAim(AutoAimSubsystem m_AutoAimSubsystem) {
    addRequirements(m_AutoAimSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!ShooterSubsystem.isPanReset()) {
      
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