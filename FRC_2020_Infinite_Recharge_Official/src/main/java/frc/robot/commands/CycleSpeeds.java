/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CycleSpeeds extends CommandBase {
  
  double currentTargetSpeed; 

  public CycleSpeeds(double currentTargetSpeed) {
    this.currentTargetSpeed = currentTargetSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (currentTargetSpeed == 0) {
      currentTargetSpeed = 4000;
    } else if (currentTargetSpeed == 4000) {
      currentTargetSpeed = 6000;
    } else if (currentTargetSpeed == 6000) {
      currentTargetSpeed = 7500;
    } else if (currentTargetSpeed == 7500) {
      currentTargetSpeed = 10000;
    } else if (currentTargetSpeed == 10000) {
      currentTargetSpeed = 0;
    }

    SmartDashboard.putNumber("Current Cycle Speed", currentTargetSpeed);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
