/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class IndexSubsystem extends SubsystemBase {
  
  Spark sideLiftMotor;
  Spark frontLiftMotor;
  Spark funnelMotor;
  AnalogInput shooterUltrasonic;
  AnalogInput indexUltrasonic;
  SpeedControllerGroup indexMotors;

  int ballsInIndex = 0;

  public IndexSubsystem() {
    sideLiftMotor = new Spark(ConstantsPorts.sideLiftMotorPort);
    frontLiftMotor = new Spark(ConstantsPorts.frontLiftMotorPort);
    frontLiftMotor.setInverted(true);
    funnelMotor = new Spark(ConstantsPorts.funnelMotorPort);

    shooterUltrasonic = new AnalogInput(ConstantsPorts.shooterUltrasonicPort);
    indexUltrasonic = new AnalogInput(ConstantsPorts.indexUltrasonicPort);

    indexMotors = new SpeedControllerGroup(frontLiftMotor, sideLiftMotor);
  }

  /**
   * Sets the speed of the index.
   * @param speed The speed to set the index to, as a double.
   */
  public void setIndex(double speed) {
    indexMotors.set(speed);
  }

  /** Stops the index. */
  public void stopIndex() {
    indexMotors.stopMotor();
  }

  public void stopFunnel() {
    funnelMotor.stopMotor();
  }

  public void stopAllMotors() {
    funnelMotor.stopMotor();
    indexMotors.stopMotor();
  }

  public void expellAll() {
    indexMotors.set(-ConstantsValues.expellSpeed);
    funnelMotor.setSpeed(-ConstantsValues.expellSpeed);
  }

  public void reverseIndex() {
    indexMotors.set(-ConstantsValues.indexInSpeed);
  }

  public void indexIn() {
    indexMotors.set(ConstantsValues.indexInSpeed);
  }

  /**
   * Gets the current value of the shooter ultrasonic sensor.
   * @return The current value of the shooter ultrasonic sensor, as an int.
   */
  public int getShooterUltrasonic() {
    return shooterUltrasonic.getValue();
  }

  /**
   * Gets the current value of the index ultrasonic sensor.
   * @return The current value of the index ultrasonic sensor, as an int.
   */
  public int getIndexUltrasonic() {
    return indexUltrasonic.getValue();
  }

  /** Adds a ball to the current ball count. */
  public void addBall() {
    ballsInIndex++;
  }

  /** Subtracts a ball from the current ball count. */
  public void subtractBall() {
    ballsInIndex--;
  }

  /**
   * Sets the amount of balls currently in the index.
   * @param balls The amount of balls that are currently in the index.
   */
  public void setBalls(int balls) {
    ballsInIndex = balls;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}