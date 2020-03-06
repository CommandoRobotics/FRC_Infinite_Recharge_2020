/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.commands.ShooterCommands.*;
import frc.robot.commands.ClimbCommands.*;
import frc.robot.commands.ClimbCommands.SetRopePosition.SetOrAngle;
import frc.robot.commands.DriveCommands.DriveStraightTime;
import frc.robot.commands.DriveCommands.DriveTank;
import frc.robot.commands.IntakeCommands.RunIntake;
import frc.robot.commands.IntakeCommands.SweepIntake;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.TriggerPOV.POVDirection;
import frc.robot.commands.SolenoidSetsAndToggles.CompressIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ReleaseIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ToggleIntake;
import frc.robot.commands.SolenoidSetsAndToggles.ToggleLifterAndPanel;
import frc.robot.commands.SolenoidSetsAndToggles.TogglePanel;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  UsbCamera camera;

  //Speed Switcher
  private double cycleSpeeds = 0;
  private boolean previousBack = false;
  private boolean previousStart = false;
  private boolean isShootWtihLimelight = true;

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
  private final AutoAim autoAim = new AutoAim(autoAimSubsystem);
  private final ShootWithCalcVelocity shootWithCalcVelocity = new ShootWithCalcVelocity(shooterSubsystem, indexSubsystem);

  // Controllers
  private final XboxController driverController = new XboxController(ConstantsOI.driverPort);
  private final XboxController operatorController = new XboxController(ConstantsOI.operatorPort);


 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    camera = CameraServer.getInstance().startAutomaticCapture();

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
    //DRIVER Binds

    //Left Trigger: Sweep
    new TriggerTrigger(driverController, Hand.kLeft, .1)
      .whenActive(new SweepIntake(intakeSubsystem), true)
      .whenInactive(intakeSubsystem::stopIntake, intakeSubsystem);

    //Right Trigger: IntakeIn (Just intake)
    new TriggerTrigger(driverController, Hand.kRight, .1)
      .whenActive(intakeSubsystem::intakeCells, intakeSubsystem)
      .whenInactive(intakeSubsystem::stopIntake, intakeSubsystem);

    //Toggle lifter and panel (driver left bumper)
    new JoystickButton(driverController, Button.kBumperLeft.value)
      .whenActive(new ToggleIntake(lifterSubsystem));

    //Toggle lifter (driver x button)
    new JoystickButton(driverController, Button.kX.value)
      .whenActive(lifterSubsystem::toggleLifter, lifterSubsystem);
     
    //Toggle panel (driver b button)
    new JoystickButton(driverController, Button.kB.value)
      .whenActive(lifterSubsystem::togglePanel, lifterSubsystem);

    //Y: Climb at a set speed TODO
    new JoystickButton(driverController, Button.kY.value)
      .whileHeld(new InstantCommand(() -> driveSubsystem.driveRampedTank(-ConstantsValues.climbDriveSpeed, -ConstantsValues.climbDriveSpeed), driveSubsystem), true)
      .whenReleased(new InstantCommand(() -> driveSubsystem.stopDrive(), driveSubsystem));

    //POV Down: Reset Climb Release (ALL) TODO
    new TriggerPOV(driverController, POVDirection.kDown)
      .whenActive(new ResetClimb(climbSubsystem), true);

    //POV Left: Tgl Rope Servo TODO
    new TriggerPOV(driverController, POVDirection.kLeft)
      .toggleWhenActive(new ToggleRopeServo(climbSubsystem), true);

    //POV Right: Tgl Climb Spring Release TODO
    new TriggerPOV(driverController, POVDirection.kRight)
      .whenActive(climbSubsystem::toggleSpringRelease, climbSubsystem);






    new JoystickButton(driverController, Button.kA.value)
      .whenActive(climbSubsystem::releaseRope, climbSubsystem);

    new JoystickButton(driverController, Button.kBumperRight.value)
      .whenActive(climbSubsystem::resetRopeRelease, climbSubsystem);



    //OPERATOR BINDS

    // //TODO Left Stick X: Manual Pan for target
     new TriggerThumbstickDirectional(operatorController, Axis.kLeftX, false, .15)
       .whenActive(() -> autoAimSubsystem.setPannerOverride(-.3), autoAimSubsystem)
       .whenInactive(autoAimSubsystem::stopPanner, autoAimSubsystem);

       new TriggerThumbstickDirectional(operatorController, Axis.kLeftX, true, .15)
       .whenActive(() -> autoAimSubsystem.setPannerOverride(.3), autoAimSubsystem)
       .whenInactive(autoAimSubsystem::stopPanner, autoAimSubsystem);


    //TODO Right Stick Y: Manual Tilt for target
    new TriggerThumbstickDirectional(operatorController, Axis.kRightY, true, .15)
    .whenActive(() -> autoAimSubsystem.setTiltOverride(-.4), autoAimSubsystem)
    .whenInactive(autoAimSubsystem::stopTilter, autoAimSubsystem);

    new TriggerThumbstickDirectional(operatorController, Axis.kRightY, false, .15)
    .whenActive(() -> autoAimSubsystem.setTiltOverride(.4), autoAimSubsystem)
    .whenInactive(autoAimSubsystem::stopTilter, autoAimSubsystem);

    // Run index in (operator left trigger)
    new TriggerTrigger(operatorController, Hand.kLeft, 0.1)
      .whenActive(indexSubsystem::indexIn)
      .whenInactive(indexSubsystem::stopAllIndexMotors);

    //Right Trigger: Set the Target RPM based on the cycleSpeedSelector or if Auto aim active
    //Based on auto aim
    // new TriggerTrigger(operatorController, Hand.kRight, .1)
    //   .whenActive(new DetermineShooterSource(isShootWtihLimelight, cycleSpeeds, shootWithCalcVelocity, shooterSubsystem))
    //   .whenInactive(new StopShooter(shooterSubsystem)); TODO
    new TriggerTrigger(operatorController, Hand.kRight, .1)
      .whenActive(shooterSubsystem::setShooterCycleSpeeds, shooterSubsystem)
      .whenInactive(shooterSubsystem::stopShooter, shooterSubsystem);

    // Run funnel at a lowerish speed (operator left bumper)
    new JoystickButton(operatorController, Button.kBumperLeft.value)
      .whenActive((() -> indexSubsystem.setFunnel(ConstantsValues.funnelLowSpeed)), indexSubsystem)
      .whenInactive(indexSubsystem::stopFunnel, indexSubsystem);

    //Right Bumper: Cycle between three different target speeds
    new JoystickButton(operatorController, Button.kBumperRight.value)
      .whenActive(shooterSubsystem::cycleSpeeds, shooterSubsystem);

    //A: Run the AutoAim Home command to manually automatically recenter aim
    new JoystickButton(operatorController, Button.kA.value)
      .whenActive(new HomeShooter(autoAimSubsystem), false);

    // Release climb (operator y button)
    new JoystickButton(operatorController, Button.kY.value) 
      .whenActive(new ReleaseClimb(climbSubsystem));

    //TODO X: Lock Climb Pistion (CURRENTLY UNUSED AS THERE IS NO LOCK PISTON)

    //Menu: Tgl Auto Velocity
    //DONE

    //Back: Tgl Auto Aim
    //DONE

    //TODO POV up: Tgl the ctrl panel piston (CURRENTLY UNUSED)

    //TODO POV Left: Run Rotate (Ctrl Panel)

    //TODO POV Right: Run Position (Ctrl Panel)

    //TODO POV Down: Our alt trigger. Dont acutally run a command with this
    //Rather use .and() on a button/trigger with this to activate a command

    //ALT COMMANDS

    //Y ALT: Reset Climb (both piston and servo)
    new TriggerPOV(operatorController, POVDirection.kDown)
      .and(new JoystickButton(operatorController, Button.kY.value))
        .whenActive(new ResetClimb(climbSubsystem));

    //TODO X ALT: Unlock Climb (CURRENTLY UNUSED)

  }

  //Interfacing command for to 
  public void scheduleClimbLock() {
    //new InstantCommand(climbSubsystem::lockClimb, climbSubsystem).schedule();
  }

  // public void makeSureIntakeUp() {
  //   new ParallelCommandGroup(new InstantCommand(lifterSubsystem::retractLifter, lifterSubsystem), new InstantCommand()
  //}


  public void checkAutoToggles() {
    if (operatorController.getRawButton(Button.kBack.value) && (operatorController.getRawButton(Button.kBack.value) != previousBack)) {
      if (autoAim.isScheduled()) {
        autoAim.cancel();
      } else {
        autoAim.schedule(true);
      }
    }
    previousBack = operatorController.getRawButton(Button.kBack.value);

    if (operatorController.getRawButton(Button.kStart.value) && (operatorController.getRawButton(Button.kStart.value) != previousStart)) {
      isShootWtihLimelight = !isShootWtihLimelight;
    }
    previousStart = operatorController.getRawButton(Button.kStart.value);

    System.out.println(isShootWtihLimelight);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new DriveStraightTime(2000, .5, .5, driveSubsystem);
  }
}
