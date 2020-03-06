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
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ReleaseAndClimb extends SequentialCommandGroup {
  /**
   * Creates a new ReleaseAndClimb.
   */
  public ReleaseAndClimb(double leftPower, double rightPower, DriveSubsystem driveSubsystem, ClimbSubsystem climbSubsystem) {
    addCommands(
      //Release the climb servo to losen the rope
      new InstantCommand(climbSubsystem::releaseRope, climbSubsystem),

      //Wait a second or two to get it going
      new WaitCommand(1),

      //Finally climb using the drive motors
      new InstantCommand(() -> driveSubsystem.driveTank(leftPower, rightPower), driveSubsystem)
    );
  }
}
