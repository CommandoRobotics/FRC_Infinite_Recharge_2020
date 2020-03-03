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
import frc.robot.commands.ClimbCommands.*;
import frc.robot.commands.ClimbCommands.SetRopePosition.SetOrAngle;
import frc.robot.commands.IntakeCommands.RunIntake;
import frc.robot.commands.IntakeCommands.SweepIntake;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.*;
import frc.robot.commands.SolenoidSetsAndToggles.CompressIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ReleaseIntake;
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
  private final AutoAimSubsystem autoAimSubsystem = new AutoAimSubsystem(limelight);
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
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

    shooterSubsystem.setShooterTarget(0);

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //DRIVER BINDS

    //TODO Drive From left and right Y joysticks

    //TODO Left Trigger: Sweep

    //TODO Right Trigger: IntakeIn (Just intake and possibly funnel?)

    //TODO Left Bumper: Tgl Lifter/Panel

    //TODO X button: Tgl Lifter
     
    //TODO B Button: Tgl Panel

    //TODO POV Down: Reset Climb Release (ALL)

    //TODO POV Left: Tgl Rope Servo

    //TODO POV Right: Tgl Climb Spring Release


    //OPERATOR BINDS

    //TODO Left Stick X: Manual Pan for target

    //TODO Right Stick Y: Manual Tilt for target

    //TODO Left Trigger: Run Index In (not for shooting but rather for when intaking balls)

    //TODO Right Trigger: Set the Target RPM based on the cycleSpeedSelector or if Auto aim active
    //Based on auto aim

    //TODO Left Bumper: Run the funnel only at a lowerish speed

    //TODO Right Bumper: Cycle between three different target RPMs

    //TODO A: Run the AutoAim Home command to manually automatically recenter aim

    //TODO Y: Run the release climb command (runs both servo and pistion in sequence)

    //TODO X: Lock Climb Pistion (CURRENTLY UNUSED AS THERE IS NO LOCK PISTON)

    //TODO Menu: Tgl Auto Velocity

    //TODO Back: Tgl Auto Aim

    //TODO POV up: Tgl the ctrl panel piston (CURRENTLY UNUSED)

    //TODO POV Left: Run Rotate (Ctrl Panel)

    //TODO POV Right: Run Position (Ctrl Panel)

    //TODO POV Down: Our alt trigger. Dont acutally run a command with this
    //Rather use .and() on a button/trigger with this to activate a command

    //ALT COMMANDS

    //TODO Y ALT: Reset Climb (both piston and servo)

    //TODO X ALT: Unlock Climb (CURRENTLY UNUSED)

  }

  //Interfacing command for to 
  public void scheduleClimbLock() {
    new InstantCommand(climbSubsystem::lockClimb, climbSubsystem).schedule();
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
