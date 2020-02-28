/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class LifterSubsystem extends SubsystemBase {

  Solenoid intakeSolenoid;
  Solenoid pannelSolenoid;

  public LifterSubsystem() {
    intakeSolenoid = new Solenoid(ConstantsPorts.intakeLifterPort);
    pannelSolenoid = new Solenoid(ConstantsPorts.pannelPort);
  }

  //LIFTER METHODS

  /** Sets the lifter solenoid to an inputted value 
   * 
   * @param set  True will set the solenoid to on.
   *             False will set the solenoid to off
  */
  public void setLifter(boolean set) {
    intakeSolenoid.set(set);
  }

  /** Sets the lifter to on/deployed (true) */
  public void deployLifter() {
    intakeSolenoid.set(true);
  }

  /**Sets the lifter to off/retracted (false) */
  public void retractLifter() {
    intakeSolenoid.set(false);
  }

  /**Sets the lifter to the opposite value of the current value 
   * Ex. if solenoid is on, this will set it to off
  */
  public void toggleLifter() {
    intakeSolenoid.set(!intakeSolenoid.get());
  }

  /**Returns the current state of the lifter 
   * 
   * @return either true or false
   *         true = on
   *         false = off
  */
  public boolean getIntakeCurrentState() { 
    return intakeSolenoid.get();
  }

  //PANNEL METHODS

    /** Sets the pannel solenoid to an inputted value 
   * 
   * @param set  True will set the solenoid to on.
   *             False will set the solenoid to off
  */
  public void setPannel(boolean set) {
    pannelSolenoid.set(set);
  }

  /** Sets the pannel to on/deployed (true) */
  public void deployPannel() {
    pannelSolenoid.set(false);
  }

  /**Sets the pannel to off/retracted (false) */
  public void retractPannel() {
    pannelSolenoid.set(true);
  }

  /**Sets the pannel to the opposite value of the current value 
   * Ex. if solenoid is on, this will set it to off
  */
  public void togglePannel() {
    pannelSolenoid.set(!pannelSolenoid.get());
  }

  /**Returns the current state of the pannel 
   * 
   * @return either true or false
   *         true = on
   *         false = off
  */
  public boolean getPannelCurrentState() { 
    return pannelSolenoid.get();
  }

  //Dont forget to run this mechanism into the wall! Because, progress!

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
