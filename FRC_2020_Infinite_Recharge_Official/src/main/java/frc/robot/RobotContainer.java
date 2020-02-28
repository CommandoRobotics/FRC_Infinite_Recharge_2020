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
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import frc.robot.commands.DriveTank;
import frc.robot.commands.ClimbCommands.*;
import frc.robot.commands.ClimbCommands.SetRopePosition.SetOrAngle;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  //Limelights and Network Tables
  NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

  //Subsystems
  private final AutoAimSubsystem autoAimSubsystem = new AutoAimSubsystem();
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
  private final ColorWheelSubsystem colorWheelSubsystem = new ColorWheelSubsystem();
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final IndexSubsystem indexSubsystem = new IndexSubsystem();
  private final IntakeSubsystem intakeSubsystem =  new IntakeSubsystem();
  private final LifterSubsystem lifterSubsystem = new LifterSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  

  //Commands
  //private final TankDriveCommand tankDriveCommand;

  //Controllers
  private final XboxController driverController = new XboxController(ConstantsOI.driverPort);
  private final XboxController operatorController = new XboxController(ConstantsOI.operatorPort);

 
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    driveSubsystem.setDefaultCommand(new DriveTank(
      () -> driverController.getRawAxis(ConstantsOI.driverRightDriveAxis), 
      () -> driverController.getRawAxis(ConstantsOI.driverLeftDriveAxis),
      driveSubsystem));

    configureButtonBindings();  
  }

  /** 
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Lock the Climb
    new JoystickButton(operatorController, Button.kA.value)
      .whenPressed(new InstantCommand(climbSubsystem::lockClimb, climbSubsystem));
    
    //Release the climb logic
    new JoystickButton(operatorController, Button.kY.value)
      .whenPressed(new ReleaseClimb(climbSubsystem));

    //Test Code for testing if we should use set or setAngle
    //Set
    new JoystickButton(operatorController, Button.kX.value) 
      .whenPressed(new SetRopePosition(SetOrAngle.set, 1, climbSubsystem));

    //Angle
    new JoystickButton(operatorController, Button.kB.value)
      .whenPressed(new SetRopePosition(SetOrAngle.angle, 180, climbSubsystem));

    //Command for reseting the climb after matches
    new JoystickButton(operatorController, Button.kStart.value)
      .whenPressed(new ResetClimb(climbSubsystem));

    //Command for unlocking the climb lock pistons
    new JoystickButton(operatorController, Button.kBack.value)
      .whenPressed(new InstantCommand(climbSubsystem::unlockClimb, climbSubsystem));
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
