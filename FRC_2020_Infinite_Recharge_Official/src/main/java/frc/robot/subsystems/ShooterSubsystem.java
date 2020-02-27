/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class ShooterSubsystem extends SubsystemBase {

  Spark loader;
  Spark shooter;

  Encoder shooterEnc;

  double shooterMinSpeed;
  double loaderMinSpeed;

  public int INCH_MODE = 0;
  public int METER_MODE = 1;

  public ShooterSubsystem() {
    loader = new Spark(ConstantsPorts.shooterLoaderPort);
    shooter = new Spark(ConstantsPorts.shooterTopMasterID);

    shooterEnc = new Encoder(ConstantsPorts.shooterEncAPort, ConstantsPorts.shooterEncBPort);
    shooterEnc.setDistancePerPulse(ConstantsValues.shooterTInDisPerPulse);

    loaderMinSpeed = ConstantsValues.loaderMinSpeed;
    shooterMinSpeed = ConstantsValues.shooterTMinSpeed;
  }

  /**Set the shooter to a certain inputted speed
   * given that the input speed is greater than the minSpeed
   */
  public void setShooter(double speed) {
    if (speed > shooterMinSpeed) {
      shooter.setSpeed(speed);
    } else {
      shooter.stopMotor();
    }
  }

  /**Sets the loader/flywheel to a certain inputted speed 
   * given that the input speed is greater than the minSpeed
  */
  public void setLoader(double speed) {
    if (speed > loaderMinSpeed) {
      loader.setSpeed(speed);
    } else {
      loader.stopMotor();
    }
  }

  /**Stops the shooter motors */
  public void stopShooter() {
    shooter.stopMotor();
  }

  /**Stops the loader/flywheel motors */
  public void stopLoader() {
    loader.stopMotor();
  }

  /**Returns the current set speed of the shooter motor 
   * 
   * @return set speed as double from -1 to 1
  */
  public double getShooterSetSpeed() {
    return shooter.get();
  }

  /**Returns the raw counts recieved from the shooter encoder 
   * 
   * @return Current raw output from the shooter encoder 
   *         since the last reset
  */
  public double getShooterEncRaw() {
    return shooterEnc.getRaw();
  }

  /**Gets the current rate of the shooter encoder.
   * Will be in in/sec in default mode, and m/sec in meter mode
   * 
   * @return The rate at which the shooter encoder is spinning
   *         either in in/sec or m/sec
   */
  public double getShooterEncRate() {
    return shooterEnc.getRate();
  }

  /**Resets the Shooter encoder back to zero */
  public void resetShooterEnc() {
    shooterEnc.reset();
  }

  /**Overrides the distance per pulse of the shooter encoder */
  public void setShooterDisPerPulse(double disPerPulse) {
    shooterEnc.setDistancePerPulse(disPerPulse);
  }

  /**Sets the disPerPulse to either meters (Physics) or inches (AMERCICA)
   * 
   * @param mode Input METER_MODE for meters. Input INCH_MODE for inches
  */
  public void setShooterEncoderMode(int mode) {
    if (mode == METER_MODE) {
      shooterEnc.setDistancePerPulse(ConstantsValues.shooterTInDisPerPulse);
    } else if (mode == INCH_MODE) {
      shooterEnc.setDistancePerPulse(ConstantsValues.shooterBInDisPerPulse);
    }
  }

  /**Overrides the minimum speed the shooter will run */
  public void setMinShooterSpeed(double speed) {
    shooterMinSpeed = speed;
  }
  
  /**Overrides the min speed the loader will run */
  public void setMinLoaderSpeed(double speed) {
    loaderMinSpeed = speed;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
