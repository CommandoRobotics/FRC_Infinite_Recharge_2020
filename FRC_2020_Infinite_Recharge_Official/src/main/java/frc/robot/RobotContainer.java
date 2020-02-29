/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.commands.ShooterCommands.*;
import frc.robot.commands.DriveTank;
import frc.robot.commands.IntakeCommands.RunIntake;
import frc.robot.commands.IntakeCommands.SweepIntake;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.*;
import frc.robot.commands.SolenoidSetsAndToggles.CompressIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ReleaseIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ToggleLifter;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //Limelights and Network Tables
  NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

  //Subsystems
  private final AutoAimSubsystem autoAimSubsystem = new AutoAimSubsystem();
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  private final ColorWheelSubsystem colorWheelSubsystem = new ColorWheelSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final IndexSubsystem indexSubsystem = new IndexSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final LifterSubsystem lifterSubsystem = new LifterSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem(limelight);
  

  // Commands
  // private final TankDriveCommand tankDriveCommand;

  // Controllers
  private final XboxController driverController = new XboxController(ConstantsOI.driverPort);
  private final XboxController operatorController = new XboxController(ConstantsOI.operatorPort);
  TriggerAxis operatorLeftTrigger = new TriggerAxis(operatorController, Hand.kLeft, .1);
  TriggerAxis operatorRightTrigger = new TriggerAxis(operatorController, Hand.kRight, .1);
  TriggerAxis driverLeftTrigger = new TriggerAxis(driverController, Hand.kLeft, .1);
  TriggerAxis driverRightTrigger = new TriggerAxis(driverController, Hand.kRight, .1);

 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    driveSubsystem.setDefaultCommand(new DriveTank(
      () -> driverController.getRawAxis(ConstantsOI.driverRightDriveAxis),
      () -> driverController.getRawAxis(ConstantsOI.driverLeftDriveAxis),
      driveSubsystem));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    operatorLeftTrigger
      .whileActiveContinuous(new InstantCommand(() -> shooterSubsystem.setShooter(1), shooterSubsystem))
      .whenInactive(new InstantCommand(() -> shooterSubsystem.setShooter(0), shooterSubsystem));
    operatorRightTrigger.whileActiveContinuous(new SetShooterRPM(shooterSubsystem, SmartDashboard.getNumber("targetRPM", 10000)), true);
    driverLeftTrigger.whenActive(new RunIntake(intakeSubsystem, true), true);
    driverRightTrigger.whenActive(new SweepIntake(intakeSubsystem), true);
    new JoystickButton(operatorController, Button.kA.value)
      .whenPressed(new InstantCommand(() -> indexSubsystem.setAllIndexMotors(.75), indexSubsystem))
      .whenReleased(new InstantCommand(indexSubsystem::stopAllIndexMotors, indexSubsystem));
    new JoystickButton(operatorController, Button.kBumperLeft.value)
      .whenPressed(new InstantCommand(() -> autoAimSubsystem.setTilter(-.4), autoAimSubsystem))
      .whenReleased(new InstantCommand(autoAimSubsystem::stopTilter, autoAimSubsystem));
      new JoystickButton(operatorController, Button.kBumperRight.value)
      .whenPressed(new InstantCommand(() -> autoAimSubsystem.setTilter(.6), autoAimSubsystem))
      .whenReleased(new InstantCommand(autoAimSubsystem::stopTilter, autoAimSubsystem));
    new JoystickButton(driverController, Button.kBumperRight.value)
      .whenPressed(new ToggleLifter(lifterSubsystem));

    new JoystickButton(driverController, Button.kBumperLeft.value)
      .whenPressed(new CompressIntake(lifterSubsystem));

    new JoystickButton(driverController, Button.kA.value)
      .whenPressed(new ReleaseIntake(lifterSubsystem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
