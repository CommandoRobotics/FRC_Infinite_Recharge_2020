/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ConstantsPID;
import frc.robot.ConstantsValues;
import frc.robot.subsystems.AutoAimSubsystem;

public class AutoPan extends CommandBase {

  AutoAimSubsystem autoAimSubsystem;
  PIDController pidController;
  
  public AutoPan(AutoAimSubsystem autoAimSubsystem) {
    addRequirements(autoAimSubsystem);
    this.autoAimSubsystem = autoAimSubsystem;
    pidController = new PIDController(ConstantsPID.kPanP, ConstantsPID.kPanI, ConstantsPID.kPanD);
    pidController.setTolerance(ConstantsPID.panTolerance);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    autoAimSubsystem.setLights(true);
    System.out.println("Starting AutoPan");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    autoAimSubsystem.setPannerOverride(pidController.calculate(autoAimSubsystem.getLimelightXOffset()));
    SmartDashboard.putNumber("PAN PID OUTPUT", pidController.calculate(autoAimSubsystem.getLimelightXOffset()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    autoAimSubsystem.setLights(false);
    autoAimSubsystem.stopPanner();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
