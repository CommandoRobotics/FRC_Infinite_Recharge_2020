/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class ShooterSubsystem extends SubsystemBase {

  Spark loader;
  Spark shooterTopSpark;
  Spark shooterBottomSpark;

  Encoder shooterTopEnc;
  Encoder shooterBottomEnc;

  NetworkTable limelight;

  double shooterTopMinSpeed;
  double loaderMinSpeed;
  public boolean riserActive = false;
  public boolean seeingTarget = false;

  public int INCH_MODE = 0;
  public int METER_MODE = 1;

  public ShooterSubsystem(NetworkTable m_limelight) {
    loader = new Spark(ConstantsPorts.shooterLoaderPort);
    shooterTopSpark = new Spark(ConstantsPorts.shooterTopID);

    shooterTopEnc = new Encoder(ConstantsPorts.shooterTEncAPort, ConstantsPorts.shooterTEncBPort);
    shooterTopEnc.setDistancePerPulse(ConstantsValues.shooterTMeterDisPerPulse);

    loaderMinSpeed = ConstantsValues.loaderMinSpeed;
    shooterTopMinSpeed = ConstantsValues.shooterTMinSpeed;

    limelight = m_limelight;
  }

  /**Set the shooterTop to a certain inputted speed
   * given that the input speed is greater than the minSpeed
   */
  public void setShooter(double speed) {
    if (speed > shooterTopMinSpeed) {
      shooterTopSpark.setSpeed(speed);
    } else {
      shooterTopSpark.stopMotor();
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
    shooterTopSpark.stopMotor();
  }

  /**Stops the loader/flywheel motors */
  public void stopLoader() {
    loader.stopMotor();
  }

  /**Returns the current set speed of the shooter motors
   * 
   * @return set speed as double from -1 to 1
  */
  public double getShooterSetSpeed() {
    return shooterTopSpark.get();
  }

  /**Returns the raw counts recieved from the shooterTop encoder 
   * 
   * @return Current raw output from the shooterTop encoder 
   *         since the last reset
  */
  public double getShooterEncRaw() {
    return shooterTopEnc.getRaw();
  }

  /**Gets the current rate of the shooterTop encoder.
   * Will be in in/sec in default mode, and m/sec in meter mode
   * 
   * @return The rate at which the shooterTop encoder is spinning
   *         either in in/sec or m/sec
   */
  public double getShooterEncRate() {
    return shooterTopEnc.getRate();
  }

  /**Resets the Shooter encoder back to zero */
  public void resetShooterEnc() {
    shooterTopEnc.reset();
  }

  /**Overrides the distance per pulse of the shooterTop encoder */
  public void setShooterDisPerPulse(double disPerPulse) {
    shooterTopEnc.setDistancePerPulse(disPerPulse);
  }

  /**Sets the disPerPulse to either meters (Physics) or inches (AMERCICA)
   * 
   * @param mode Input METER_MODE for meters. Input INCH_MODE for inches
  */
  public void setShooterEncoderMode(int mode) {
    if (mode == METER_MODE) {
      shooterTopEnc.setDistancePerPulse(ConstantsValues.shooterTMeterDisPerPulse);
    } else if (mode == INCH_MODE) {
      shooterTopEnc.setDistancePerPulse(ConstantsValues.shooterTInDisPerPulse);
    }
  }

  /**Overrides the minimum speed the shooterTop will run */
  public void setMinShooterSpeed(double speed) {
    shooterTopMinSpeed = speed;
  }
  
  /**Overrides the min speed the loader will run */
  public void setMinLoaderSpeed(double speed) {
    loaderMinSpeed = speed;
  }

    //LIMELIGHT THINGS

    public double getLimelightXOffset() {
      return limelight.getEntry("tx").getDouble(0);
    }
  
    public double getLimelightYOffset() {
      return limelight.getEntry("ty").getDouble(0);
    }
  
    /**
     * Gets the distance directly to the target straight from the limlight using
     * right triangles.
     * @param targetHeight The height of the target realtive to the current shooter
     *                     height. This input affects the unit of measurement outputted
     * @param isLifted     Whether or not the the limelight is angled low or high  (this changes 
     *                     the constant angle)
     * @return The distance directly to the target in the same unit of measurement as the
     *         targetHeight
     */
    public double getLimelightDis(double targetHeight, boolean isAngledUp) {
      if (isAngledUp) {
        return targetHeight/Math.sin(ConstantsValues.limlightAngleHigh + limelight.getEntry("ty").getDouble(0));
      } else {
        return targetHeight/Math.sin(ConstantsValues.limlightAngleLow + limelight.getEntry("ty").getDouble(0));
      }
    }
  
    /**
     * Returns the number   
     * @return
     */
    public boolean isTargets() { 
      seeingTarget = (limelight.getEntry("tv").getDouble(0) == 1) ? true : false;
      return seeingTarget;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
