/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.ConstantsPID;
import frc.robot.subsystems.DriveSubsystem;

public class CustomRamseteCommand extends RamseteCommand {
  /**
   * Creates a new CustomRamseteCommand.
   */
  public CustomRamseteCommand(Trajectory trajectory, boolean reversed, DriveSubsystem driveSubsystem) {
    super(
      trajectory,
      driveSubsystem::getPose,
      new RamseteController(ConstantsPID.ramseteB, ConstantsPID.ramseteZeta),
      new SimpleMotorFeedforward(ConstantsPID.ksVolts, 
                                 ConstantsPID.kvVoltSecondsPerMeter, 
                                 ConstantsPID.kaVoltSecondsSquaredPerMeter),
      ConstantsPID.driveKinematics,
      reversed ? driveSubsystem::getReversedWheelSpeeds : driveSubsystem::getWheelSpeeds,
      reversed ? new PIDController(ConstantsPID.kPDriveChar, 0, 0) : new PIDController(ConstantsPID.kPDriveChar, 0, 0),
      reversed ? new PIDController(ConstantsPID.kPDriveChar, 0, 0) : new PIDController(ConstantsPID.kPDriveChar, 0, 0),
      driveSubsystem::tankDriveVolts,
      driveSubsystem
    );
  }
}
