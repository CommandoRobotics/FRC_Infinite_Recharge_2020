/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ConstantsValues;
import frc.robot.subsystems.AutoAimSubsystem;

public class HomeShooter extends CommandBase {
  
  AutoAimSubsystem autoAimSubsystem;
  boolean isFinished = false;

  public HomeShooter(AutoAimSubsystem sub) {
    autoAimSubsystem = sub;
    addRequirements(sub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    while(!(autoAimSubsystem.isTiltReset() && autoAimSubsystem.isPanReset())) {
      if(!autoAimSubsystem.isTiltReset()) {
        autoAimSubsystem.setTilter(ConstantsValues.tiltHomeSpeed);
      } else {
        autoAimSubsystem.stopTilter();
      }

      if(!autoAimSubsystem.isPanReset()) {
        autoAimSubsystem.setPanner(ConstantsValues.panHomeSpeed);
      } else {
        autoAimSubsystem.stopPanner();
      }
    }

    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    autoAimSubsystem.stopPanner();
    autoAimSubsystem.stopTilter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
