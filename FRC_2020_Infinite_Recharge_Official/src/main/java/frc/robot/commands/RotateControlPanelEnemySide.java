/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorWheelSubsystem;
import frc.robot.ConstantsValues;
import frc.robot.APIs.ControlPanelAPI;

public class RotateControlPanelEnemySide extends CommandBase {
  
  ControlPanelAPI controlPanelAPI = new ControlPanelAPI(ConstantsValues.distanceFromCenterOfColorWheelInInches, ConstantsValues.colorWheelGearboxRatio, ConstantsValues.colorWheelDiameterInInches, ConstantsValues.colorWheelEncoderCountsPerRevolution);
  ColorWheelSubsystem colorWheelSubsystem;
  char targetColor;
  boolean isFinished = false;

  public RotateControlPanelEnemySide(char targetColor, ColorWheelSubsystem sub) {
    colorWheelSubsystem = sub;
    this.targetColor = targetColor;
    addRequirements(sub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorWheelSubsystem.enable(); // Enable PID
    colorWheelSubsystem.resetPID(); // Restarts the PID calculations
    colorWheelSubsystem.resetEncoder(); // Reset the current encoder
    colorWheelSubsystem.setSetpoint(controlPanelAPI.calculateEnemySide(targetColor));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (colorWheelSubsystem.atSetpoint()) {
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    colorWheelSubsystem.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
