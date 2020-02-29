/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class ClimbSubsystem extends SubsystemBase {

  Servo rightRopeRelease;
  Servo leftRopeRelease;

  DoubleSolenoid climbRelease;
  DoubleSolenoid climbLock;

  public ClimbSubsystem() {
    rightRopeRelease = new Servo(ConstantsPorts.rightRopeReleasePort);
    leftRopeRelease = new Servo(ConstantsPorts.leftRopeReleasePort);

    climbRelease = new DoubleSolenoid(ConstantsPorts.releasePortA, ConstantsPorts.releasePortB);
    climbLock = new DoubleSolenoid(ConstantsPorts.lockPortA, ConstantsPorts.lockPortB);
  }

  /** Sets the ClimbRelease solenoid to an inputted value 
   * 
   * @param set  True will set the solenoid to on.
   *             False will set the solenoid to off
  */
  public void setSpringRelease(Value set) {
    climbRelease.set(set);
  }

  /** Sets the ClimbRelease to on/deployed (true) */
  public void releaseSpring() {
    climbRelease.set(Value.kReverse);
  }

  /**Sets the ClimbRelease to off/retracted (false) */
  public void lockSpring() {
    climbRelease.set(Value.kForward);
  }

  /**Sets the ClimbRelease to the opposite value of the current value 
   * Ex. if solenoid is on, this will set it to off
  */
  public void toggleSpringRelease() {
    if (climbRelease.get() == Value.kOff) {
      climbRelease.set(Value.kForward);
    } else if (climbRelease.get() == Value.kReverse) {
      climbRelease.set(Value.kForward);
    } else if (climbRelease.get() == Value.kForward) {
      climbRelease.set(Value.kReverse);
    }
  }

  /**Returns the current state of the ClimbRelease 
   * 
   * @return State of the DoubleSolenoid as a Value enum.
   *         kOff, kReversed, or kForward;
  */
  public Value getSpringReleaseState() { 
    return climbRelease.get();
  }

  /** Sets the climbLock solenoid to an inputted value 
   * 
   * @param set  True will set the solenoid to on.
   *             False will set the solenoid to off
  */
  public void setClimbLock(Value set) {
    climbLock.set(set);
  }

  /** Sets the climbLock to on/deployed (true) */
  public void lockClimb() {
    climbLock.set(Value.kForward);
  }

  /**Sets the climbLock to off/retracted (false) */
  public void unlockClimb() {
    climbLock.set(Value.kReverse);
  }

  /**Sets the climbLock to the opposite value of the current value 
   * Ex. if solenoid is on, this will set it to off
  */
  public void toggleClimbLock() {
    if (climbLock.get() == Value.kOff) {
      climbLock.set(Value.kForward);
    } else if (climbLock.get() == Value.kReverse) {
      climbLock.set(Value.kForward);
    } else if (climbLock.get() == Value.kForward) {
      climbLock.set(Value.kReverse);
    }
  }

  /**Returns the current state of the climbLock 
   * 
   * @return State of the DoubleSolenoid as a Value enum.
   *         kOff, kReversed, or kForward;
  */
  public Value getLockState() { 
    return climbLock.get();
  }

  /**Release the Velcro that is on the servo hook for the climb motor*/
  public void releaseRope() {
    leftRopeRelease.set(1);
    rightRopeRelease.set(1);
  }
  /**Reset the servos for the velcro release back to their starting positions*/
  public void resetRopeRelease() {
    leftRopeRelease.set(0);
    rightRopeRelease.set(0);
  }

  /**
   * Set the Velcro release servos to a specific value using the "angle" parameter of 
   * the Servo Object
   * 
   * @param setVal The angle in degrees to set the servo
   */
  public void setRopeRelease(double setVal) {
    leftRopeRelease.set(setVal);
    rightRopeRelease.set(setVal);
  }

  /**
   * Set the Velcro release servos to a specific value using the "set" parameter of 
   * the Servo Object
   * 
   * @param angle The value from  where the servo should be positioned
   */
  public void setRopeReleaseAngle(double angle) {
    leftRopeRelease.setAngle(angle);
    rightRopeRelease.setAngle(angle);
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
