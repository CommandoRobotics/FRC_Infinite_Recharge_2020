/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class ShooterSubsystem extends SubsystemBase {
  Spark loader;
  Spark shooter;

  public ShooterSubsystem() {
    loader = new Spark(ConstantsPorts.shooterLoaderPort);
    shooter = new Spark(ConstantsPorts.shooterPort);
  }

  public void setShooter(double speed) {
    shooter.setSpeed(speed);
  }

  public void setLoader(double speed) {
    loader.setSpeed(speed);
  }

  public void stopShooter() {
    shooter.stopMotor();
  }

  public void stopLoader() {
    loader.stopMotor();
  }

  public void getShooterSetSpeed() {
    shooter.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
