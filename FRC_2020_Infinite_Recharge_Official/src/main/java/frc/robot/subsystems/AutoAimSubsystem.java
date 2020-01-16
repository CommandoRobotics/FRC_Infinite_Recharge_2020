/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AutoAimSubsystem extends SubsystemBase {
  Spark angler;
  Spark rotator;

  public AutoAimSubsystem() {
    angler = new Spark(Constants.anglerPort);
    rotator = new Spark(Constants.rotaterPort);
  }

  public void setRotator(double speed) {
    rotator.setSpeed(speed);
  }

  public void setAngler(double speed) {
    angler.setSpeed(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
