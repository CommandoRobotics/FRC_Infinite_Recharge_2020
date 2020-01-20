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

public class IntakeSubsystem extends SubsystemBase {

  Spark intakeMotor;

  public IntakeSubsystem() {
    intakeMotor = new Spark(ConstantsPorts.intakePort);
  }

  public void setIntakeMotor(double speed) {
    intakeMotor.setSpeed(speed);
  }

  public void stopIntake() {
    intakeMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
