/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LifterSubsystem extends SubsystemBase {

  Solenoid intakeSolenoid;

  public LifterSubsystem() {
    intakeSolenoid = new Solenoid(Constants.intakeLifterPort);
  }

  public void setLifter(boolean set) {
    intakeSolenoid.set(set);
  }

  public void extendLifter() {
    intakeSolenoid.set(true);
  }

  public void retractLifter() {
    intakeSolenoid.set(false);
  }

  public void toggleLifter() {
    intakeSolenoid.set(!intakeSolenoid.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
