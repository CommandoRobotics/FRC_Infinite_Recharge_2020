/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ClimbCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ReleaseClimb extends SequentialCommandGroup {
  /**
   * Creates a new ReleaseClimb.
   */
  public ReleaseClimb(ClimbSubsystem climbSubsystem) {
    addCommands(
      //Release the Velcro
      new InstantCommand(climbSubsystem::releaseRope, climbSubsystem),

      //Time Out a bit to get the velcro into place
      new WaitCommand(1),

      //And Now release the climb spring pistons
      new InstantCommand(climbSubsystem::releaseSpring, climbSubsystem)
    );
  }
}
