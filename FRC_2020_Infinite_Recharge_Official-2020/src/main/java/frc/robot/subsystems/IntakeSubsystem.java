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
import frc.robot.ConstantsValues;

public class IntakeSubsystem extends SubsystemBase {
  
  Spark intakeMotor;

  public IntakeSubsystem() {
    intakeMotor = new Spark(ConstantsPorts.intakePort);
  }

  /**Sets the speed of the intake motor from an inputted speed */
  public void setIntake(double speed) {
    intakeMotor.setSpeed(speed);
  }

  /**Sets the intake motor to a set speed for intaking, which is pre-determined and set in ConstantsValues */
  public void intakeCells() {
    intakeMotor.setSpeed(ConstantsValues.intakeSpeed);
  }

  public void sweep() {
    intakeMotor.setSpeed(ConstantsValues.sweepSpeed);
  }

  /**In case you were confused, this stops the intake motor
   * 
   * Cosmic Brownies are not as good as you think they are
   */
  public void stopIntake() {
    intakeMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}