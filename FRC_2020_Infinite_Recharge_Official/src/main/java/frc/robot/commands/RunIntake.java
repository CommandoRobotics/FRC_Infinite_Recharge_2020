/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ConstantsValues;
import frc.robot.subsystems.IntakeSubsystem;

public class RunIntake extends CommandBase {

  IntakeSubsystem intakeSubsystem;
  private boolean finished = false;
  BooleanSupplier controllerButton = null;
  DoubleSupplier controllerAxis = null;
  private Boolean setIntake = null;

  public RunIntake(IntakeSubsystem sub, BooleanSupplier ctrlInput) {
    intakeSubsystem = sub;
    controllerButton = ctrlInput;
    addRequirements(sub);
  }

  public RunIntake(IntakeSubsystem sub, DoubleSupplier ctrlInput) {
    intakeSubsystem = sub;
    controllerAxis = ctrlInput;
    addRequirements(sub);
  }

  public RunIntake(IntakeSubsystem sub, boolean intakeOn) {
    intakeSubsystem = sub;
    setIntake = intakeOn;
    addRequirements(sub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (controllerButton != null) {
      if (controllerButton.getAsBoolean()) {
        intakeSubsystem.intakeCells();
      } else {
        intakeSubsystem.stopIntake();
      }
    } else if (controllerAxis != null) {
      if (controllerAxis.getAsDouble() >= ConstantsValues.axisThreshold) {
        intakeSubsystem.intakeCells();
      } else {
        intakeSubsystem.stopIntake();
      }
      finished = false;
    } else if (setIntake != null) {
      if (setIntake) {
        intakeSubsystem.intakeCells();
      } else {
        intakeSubsystem.stopIntake();
      }
      finished = true;
    } else {
      System.out.println("Error in executing RunIntake");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
