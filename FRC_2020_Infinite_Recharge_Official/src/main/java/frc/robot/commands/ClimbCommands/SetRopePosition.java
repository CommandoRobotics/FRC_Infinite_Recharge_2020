/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class SetRopePosition extends CommandBase {

  ClimbSubsystem climbSubsystem;

  public enum SetOrAngle {
    set,
    angle
  }

  SetOrAngle setOrAngle;
  double target;
  boolean finished;

  public SetRopePosition(final SetOrAngle type, double target, ClimbSubsystem climbSubsystem) {
    this.climbSubsystem = climbSubsystem;
    setOrAngle = type;
    this.target = target;
    addRequirements(climbSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch(setOrAngle) {
      case set:
        climbSubsystem.setRopeRelease(target);
        break;
      case angle:
        climbSubsystem.setRopeReleaseAngle(target);
        break;
      default:
        climbSubsystem.setRopeRelease(target);
        break;
    }
    finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
