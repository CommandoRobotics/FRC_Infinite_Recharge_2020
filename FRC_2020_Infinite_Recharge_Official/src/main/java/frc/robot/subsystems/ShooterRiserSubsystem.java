/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class ShooterRiserSubsystem extends SubsystemBase {


 Solenoid riserSolenoid;

  public ShooterRiserSubsystem() {
    riserSolenoid = new Solenoid(ConstantsPorts.shooterRiserPort);
  }

  /** Sets the riser solenoid to an inputted value 
   * 
   * @param set  True will set the solenoid to on.
   *             False will set the solenoid to off
  */
  public void setRiser(boolean set) {
    riserSolenoid.set(set);
  }

  /** Sets the riser to on/deployed (true) */
  public void deployRiser() {
    riserSolenoid.set(true);
  }

  /**Sets the riser to off/retracted (false) */
  public void retractRiser() {
    riserSolenoid.set(false);
  }

  /**Sets the riser to the opposite value of the current value 
   * Ex. if solenoid is on, this will set it to off
  */
  public void toggleRiser() {
    riserSolenoid.set(!riserSolenoid.get());
  }

  /**Returns the current state of the riser 
   * 
   * @return either true or false
   *         true = on
   *         false = off
  */
  public boolean getCurrentState() { 
    return riserSolenoid.get();
  }

  //Dont forget to run this mechanism into the wall! Because, progress!

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
