/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.SolenoidSetsAndToggles;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LifterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ReleaseIntake extends SequentialCommandGroup {
  /**
   * Creates a new ReleaseIntake.
   */
  public ReleaseIntake(LifterSubsystem lifterSubsystem) {
    addCommands(
      //First let the intake out
      new InstantCommand(lifterSubsystem::deployLifter, lifterSubsystem),

      //Wait for it to go a bit
      new WaitCommand(.5),

      //Then deploy the pannel out
      new InstantCommand(lifterSubsystem::deployPanel, lifterSubsystem)
    );
  }
}
