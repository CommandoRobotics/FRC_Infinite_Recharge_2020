/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.SparkMax;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class AutoAimSubsystem extends SubsystemBase {

  VictorSPX tilt;
  VictorSPX pan;

  Counter tiltCounter;
  Encoder panEnc;

  double panMinSpeed = ConstantsValues.panMinSpeed;
  double tiltMinSpeed = ConstantsValues.tiltMinSpeed;

  NetworkTable limelight;

  DigitalInput panResetSwitch;
  DigitalInput tiltResetSwitch;

  public boolean riserActive = false;
  public boolean seeingTarget = false;
  private double currentCounts = 0;

  public AutoAimSubsystem(NetworkTable m_limelight) {
    tilt = new VictorSPX(ConstantsPorts.tiltID);
    pan = new VictorSPX(ConstantsPorts.panID);
    tilt.setInverted(true);

    tiltCounter = new Counter(ConstantsPorts.tiltCounterPort);
    panEnc = new Encoder(ConstantsPorts.panEncAPort, ConstantsPorts.panEncBPort);
    panEnc.setDistancePerPulse(ConstantsValues.panDisPerPulse);
    panEnc.setReverseDirection(true);

    panResetSwitch = new DigitalInput(ConstantsPorts.shooterPanLimitSwitch);
    tiltResetSwitch = new DigitalInput(ConstantsPorts.shooterTiltLimitSwitch);

    limelight = m_limelight;
  }

  /**
   * Sets the tilter to a given speed for a given range. If the tilter goes above or below
   * a certain range, and wants to continue moving past the min/max, then the motor is stopped.
   * Min speed is also checked for.
  */
  public void setTilter(double speed) {
    if (getTiltAngle() > ConstantsValues.maxTiltAngle && speed > 0) {
      tilt.set(ControlMode.PercentOutput, 0);
    } else if (getTiltAngle() < ConstantsValues.minTiltAngle && speed < 0) {
      tilt.set(ControlMode.PercentOutput, 0);
    } else {
      if ((speed > 0 && speed >= tiltMinSpeed) || (speed < 0 && speed <= -tiltMinSpeed)) {
        tilt.set(ControlMode.PercentOutput, speed);
      } else {
        tilt.set(ControlMode.PercentOutput, 0);
      }
    }
  }



  /**Sets the Panner to a given speed */
  public void setPanner(double speed) {
    if (getPanAngle() > ConstantsValues.maxPanAngle && speed > 0) {
      pan.set(ControlMode.PercentOutput, 0);
      System.out.println("PAN LIMIT TOP REACHED");
    } else if (getPanAngle() < ConstantsValues.minPanAngle && speed < 0) {
      pan.set(ControlMode.PercentOutput, 0);
      System.out.println("PAN LIMIT BOTTOM REACHED");
    } else {
      if ((speed > 0 && speed >= panMinSpeed) || (speed < 0 && speed <= -panMinSpeed)) {
        pan.set(ControlMode.PercentOutput, speed);
      } else {
        pan.set(ControlMode.PercentOutput, 0);
      }
    }
  }

  public void setPannerOverride(double speed) {
    pan.set(ControlMode.PercentOutput, speed);
  }

  public void setTiltOverride(double speed) {
    tilt.set(ControlMode.PercentOutput, speed);
  }

  /**Stops the Tilter motor */
  public void stopTilter() {
    tilt.set(ControlMode.PercentOutput, 0);
  }

  /**Stops the Panner motor */
  public void stopPanner() {
    pan.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Returns the current set speed of the tilt motor 
   * 
   * @return set speed as double from -1 to 1
  */
  public double getTilterSetSpeed() {
    return tilt.getMotorOutputPercent();
  }

  /**
   * Returns the current set speed of the pan motor 
   * 
   * @return set speed as double from -1 to 1
  */
  public double getPannerSetSpeed() {
    return pan.getMotorOutputPercent();
  }

  /**
   * Returns the raw counts recieved from the tilt encoder 
   * 
   * @return Current raw output from the tilt encoder since the last reset
  */
  public double getTilterTicks() {
    return currentCounts;
  }

  public double getTilterCounter() {
    return tiltCounter.get();
  }

  public double getTiltAngle() {
    //TODO get exact starting angle
    return ((tiltCounter.get()/ConstantsValues.tiltCounterTicks) * 360) + ConstantsValues.minTiltAngle;
  }

  /**Resets the Tilter encoder back to zero */
  public void resetTilterCounter() {
    tiltCounter.reset();
  }

  public void resetTilterTotalTicks() {
    currentCounts = 0;
  }

  /**
   * Returns the raw counts recieved from the pan encoder 
   * 
   * @return Current raw output from the pan encoder since the last reset
  */
  public double getPannerEncRaw() {
    return panEnc.getRaw();
  }

  /**Returns the pan encoder's distance in inches */
  public double getPannerDistance() {
    return panEnc.getDistance();
  }

  /**
   * This takes the current raw enc value for the pan encoder and gets the 
   * angle by finding the proportion of rotations and then converting to degrees
   * 
   * @return The angle at which the motor has rotated from the last start or reset
   */
  public double getPanAngle() {                      //Gear Reduction
    return ((panEnc.getRaw()/ConstantsValues.panEncPPR)/8.26666666) * 360;
  }

  /**Resets the Panner encoder back to zero */
  public void resetPannerEnc() {
    panEnc.reset();
  }

  //LIMELIGHT THINGS

  public double getLimelightXOffset() {
    SmartDashboard.putNumber("LimelightX", limelight.getEntry("tx").getDouble(0));
    return limelight.getEntry("tx").getDouble(0);
  }

  public double getLimelightYOffset() {
    SmartDashboard.putNumber("LimelightY", limelight.getEntry("ty").getDouble(0));
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
   * Uses isTarget()'s seeingTarget boolean to determine whether to output/use limelight as the 
   * meausurement source (if seeing target) or to use the encoder to reset to 0 (if not seeing target)
   * 
   * @return The offset from 0 of either proper aiming or resetting to zero
   */
  public double getPanTarget() {
    if (isTargets()) {
      return getLimelightXOffset();
    } else {
      return getPanAngle();
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

  //LIMIT SWITCH COMMANDS

  /**
   * Returns whether the pan is reset to its zero position by using a limit switch
   * and seeing when it is enabled
   * @return whether or not the pan limit switch is active
   */
  public boolean isPanReset() {
    return panResetSwitch.get();
  }

  /**
   * Returns whether the tilt is reset to its zero position by using a limit switch
   * and seeing when it is enabled
   * @return whether or not the tilt limit switch is active
   */
  public boolean isTiltReset() {
    return panResetSwitch.get();
  }

  public void setLights(boolean lightsOn) {
    if (lightsOn) {
      limelight.getEntry("pipeline").setNumber(1);
    } else {
      limelight.getEntry("pipeline").setNumber(0);
    }
  }

  public void toggleLimelightLights() {
    if (limelight.getEntry("pipeline").getDouble(0) == 1) {
      limelight.getEntry("pipeline").setNumber(0);
    } else if (limelight.getEntry("pipeline").getDouble(0) == 0) {
      limelight.getEntry("pipeline").setNumber(1);
    }
  }

  public void setLimeCameraMode(boolean isVision) {
    if (isVision) {
      limelight.getEntry("pipeline").setNumber(0);
    } else {
      limelight.getEntry("pipeline").setNumber(1);
    }
  }


  @Override
  public void periodic() {
    if (tilt.getMotorOutputPercent() > 0) {
      currentCounts += tiltCounter.get();
   } else if (tilt.getMotorOutputPercent() < 0) {
      currentCounts -= tiltCounter.get();
   }
   tiltCounter.reset();
   SmartDashboard.putNumber("tiltENC Angle", getTiltAngle());
   SmartDashboard.putNumber("LimelightY", limelight.getEntry("ty").getDouble(0));
   SmartDashboard.putNumber("LimelightX", limelight.getEntry("tx").getDouble(0));
   SmartDashboard.putBoolean("Bottom Limit switch", tiltResetSwitch.get());
   SmartDashboard.putBoolean("Pan Limit switch", panResetSwitch.get());
  }
}