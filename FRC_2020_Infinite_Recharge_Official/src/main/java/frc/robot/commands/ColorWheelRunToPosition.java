/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.ConstantsPID;
import frc.robot.subsystems.ColorWheelSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ColorWheelRunToPosition extends PIDCommand {
  
  ColorWheelSubsystem colorWheelSubsystem;

  /**
   * Creates a new ColorWheelRunToPosition.
   */
  public ColorWheelRunToPosition(double encoderTicksToSpin, ColorWheelSubsystem sub) {
    super(
        // Create the controller
        new PIDController(ConstantsPID.colorWheelP, ConstantsPID.colorWheelI, ConstantsPID.colorWheelD),
        // Give it the value to get the error from
        sub::getRawEncoderValue,
        // Give it the target setpoint
        encoderTicksToSpin,
        // Give it a method that the output will be used on
        output -> sub.setColorMotorSpeed(output),
          // Give it the required subsystem
        sub);

    colorWheelSubsystem = sub;
  }

  // Called when the command in initially sceduled.
  @Override
  public void initialize() {
    // Set PID tolerance
    getController().setTolerance(ConstantsPID.colorWheelTolerance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    colorWheelSubsystem.resetEncoder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
