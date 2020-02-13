/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ConstantsPID;
import frc.robot.ConstantsValues;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LifterSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoTest extends SequentialCommandGroup {

// An example trajectory to follow.  All units in meters.


  // For each path create a trajectory
  Trajectory trajectory;
  Trajectory trajectory2;


  public AutoTest(DriveSubsystem driveSubsystem, LifterSubsystem lifterSubsystem,
                  IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {

    //The Test Path we need to load from the trajectory JSON
    String trajectoryJSON = "paths/TestPathFirst.wpilib.json";
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }

    //The Second Test Path We need to load from the trajectory JSON
    String trajectoryJSON2 = "paths/TestPathSecond.wpilib.json";
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      trajectory2 = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON2, ex.getStackTrace());
    }

    addCommands(
      //Stop all motors and actions and reset encoders gyro etc (kinda like an init loop)
      new ParallelCommandGroup(new StopDrive(driveSubsystem), new SetIntake(intakeSubsystem, 0)),
      new ResetSensorsAuto(driveSubsystem, shooterSubsystem),

      //Drive our first generated auto path
      new CustomRamseteCommand(trajectory, false, driveSubsystem),

      //Raise the lifter and set the intake to its pre-set on speed
      new ParallelCommandGroup(new ToggleLifter(lifterSubsystem), new RunIntake(intakeSubsystem, true)),

      //Run our second generated path from earlier to follow
      new CustomRamseteCommand(trajectory2, false, driveSubsystem),

      //Stop all motors and actions
      new ParallelCommandGroup(new StopDrive(driveSubsystem), new SetIntake(intakeSubsystem, 0))
    );
  }
}
