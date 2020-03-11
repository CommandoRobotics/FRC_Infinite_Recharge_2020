/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.SolenoidSetsAndToggles;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LifterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class CompressIntake extends SequentialCommandGroup {
  /**
   * Creates a new CompressIntake.
   */
  public CompressIntake(LifterSubsystem lifterSubsystem) {
    addCommands(
      //Retract the pannel first
      new InstantCommand(lifterSubsystem::retractPanel, lifterSubsystem),

      //Wait for it to go a bit
      //new WaitCommand(.5),

      //Then retract the intake
      new InstantCommand(lifterSubsystem::retractLifter, lifterSubsystem)
    );
  }
}
