/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClimbSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ResetClimb extends SequentialCommandGroup {
  /**
   * Creates a new ResetClimb.
   */
  public ResetClimb(ClimbSubsystem climbSubsystem) {
    addCommands(
      //Start with snapping the spring locks back in
      new InstantCommand(climbSubsystem::lockSpring, climbSubsystem),

      //Now rotate to reset the servos
      new InstantCommand(climbSubsystem::resetRopeRelease, climbSubsystem)
    );
  }
}
