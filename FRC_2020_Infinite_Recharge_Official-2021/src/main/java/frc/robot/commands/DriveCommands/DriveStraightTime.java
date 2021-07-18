
package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveStraightTime extends SequentialCommandGroup {
  /**
   * Creates a new ReleaseIntake.
   */
  public DriveStraightTime(double leftPower, double rightPower, double time, DriveSubsystem driveSubsystem) {
    addCommands(
      //First let the intake out
      new InstantCommand(() -> driveSubsystem.driveTank(leftPower, rightPower), driveSubsystem),

      //Wait for it to go a bit
      new WaitCommand(time),

      //Then deploy the pannel out
      new InstantCommand((driveSubsystem::stopDrive), driveSubsystem)
    );
  }
}