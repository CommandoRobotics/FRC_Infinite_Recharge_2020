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
  boolean isTiltFinished = false;
  boolean isPanFinished = false;
  int panDirection = 1;

  public HomeShooter(AutoAimSubsystem sub) {
    autoAimSubsystem = sub;
    addRequirements(sub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(autoAimSubsystem.getPannerEncRaw() > 2) {
      panDirection = 1;
    } else if(autoAimSubsystem.getPannerEncRaw() < -2) {
      panDirection = -1;
    } else {
      panDirection = 0;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(!isTiltFinished) {
      if(autoAimSubsystem.isTiltReset()) {
        isTiltFinished = true;
        autoAimSubsystem.stopTilter();
      } else {
        autoAimSubsystem.setTilter(ConstantsValues.tiltHomeSpeed);
      }
    }

    if(!isPanFinished || panDirection == 0) {
      if(autoAimSubsystem.isPanReset()) {
        isPanFinished = true;
        autoAimSubsystem.stopPanner();
      } else {
        autoAimSubsystem.setPanner(ConstantsValues.panHomeSpeed*panDirection);
      }
    }

    if(isPanFinished && isTiltFinished) {
      isFinished = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    autoAimSubsystem.stopPanner();
    autoAimSubsystem.stopTilter();
    autoAimSubsystem.resetPannerEnc();
    autoAimSubsystem.resetTilterCounter();
    autoAimSubsystem.resetTilterTotalTicks();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
