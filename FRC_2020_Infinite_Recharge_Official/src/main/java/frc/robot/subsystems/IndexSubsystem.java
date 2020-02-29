/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class IndexSubsystem extends SubsystemBase {
  
  Spark funnelMotor;
  Spark sideLiftMotor;
  Spark frontLiftMotor;
  SpeedControllerGroup indexMotors;

  public IndexSubsystem() {
    funnelMotor = new Spark(ConstantsPorts.funnelMotorPort);
    sideLiftMotor = new Spark(ConstantsPorts.indexRightPort);
    frontLiftMotor = new Spark(ConstantsPorts.indexLeftPort);
    frontLiftMotor.setInverted(true);

    indexMotors = new SpeedControllerGroup(frontLiftMotor, sideLiftMotor);

  }

  /** This method stops all of the motors in the index system. */
  public void stopAllIndexMotors() {
    funnelMotor.setSpeed(0);
    indexMotors.set(0);
  }
  
  /** This method stops the lift motors. */
  public void stopLift() {
    indexMotors.set(0);
  }

  /** This method stops the funnel motor. */
  public void stopFunnel() {
    funnelMotor.setSpeed(0);
  }

  /**
   * This method sets the speed of all of the motors in the index system.
   * @param speed The speed to set the motors to.
   */
  public void setAllIndexMotor(double speed) {
    funnelMotor.setSpeed(speed);
    indexMotors.set(speed);
  }

  /**
   * This method sets the speed of the lift motors.
   * @param speed The speed to set the motors to.
   */
  public void setLiftSpeed(double speed) {
    indexMotors.set(speed);
  }

  /**
   * This method sets the speed of the funnel motor.
   * @param speed The speed to set the motor to.
   */
  public void setFunnelSpeed(double speed) {
    funnelMotor.setSpeed(speed);
  }

  /**
   * 
   */
  public void loadToShooter() {
    funnelMotor.setSpeed(.5);
    indexMotors.set(.8);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
