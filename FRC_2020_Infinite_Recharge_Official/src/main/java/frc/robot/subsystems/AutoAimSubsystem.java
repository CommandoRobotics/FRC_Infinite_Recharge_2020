/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

public class AutoAimSubsystem extends SubsystemBase {
  VictorSPX tilt;
  VictorSPX pan;

  Encoder tiltEnc;
  Encoder panEnc;

  double panMinSpeed = ConstantsValues.panMinSpeed;
  double tiltMinSpeed = ConstantsValues.tiltMinSpeed;

  public AutoAimSubsystem() {
    tilt = new VictorSPX(ConstantsPorts.tiltID);
    pan = new VictorSPX(ConstantsPorts.panID);

    tiltEnc = new Encoder(ConstantsPorts.tiltEncAPort, ConstantsPorts.tiltEncBPort);
    tiltEnc.setDistancePerPulse(ConstantsValues.tiltDisPerPulse);
    panEnc = new Encoder(ConstantsPorts.panEncAPort, ConstantsPorts.panEncBPort);
    panEnc.setDistancePerPulse(ConstantsValues.panDisPerPulse);

  }

  /**Sets the tilter to a given speed */
  public void setTilter(double speed) {
      tilt.set(ControlMode.PercentOutput, speed);

  }

  /**Sets the Panner to a given speed */
  public void setPanner(double speed) {
      pan.set(ControlMode.PercentOutput, speed); 
  }

  /**Stops the Tilter motor */
  public void stopTilter() {
    tilt.set(ControlMode.PercentOutput, 0);
  }

  /**Stops the Panner motor */
  public void stopPanner() {
    pan.set(ControlMode.PercentOutput, 0);
  }

  // /**Returns the current set speed of the tilt motor 
  //  * 
  //  * @return set speed as double from -1 to 1
  // */
  // public double getTilterSetSpeed() {
  //   return tilt.get();
  // }

  // /**Returns the current set speed of the pan motor 
  //  * 
  //  * @return set speed as double from -1 to 1
  // */
  // public double getPannerSetSpeed() {
  //   return pan.get();
  // }

  // /**Returns the raw counts recieved from the tilt encoder 
  //  * 
  //  * @return Current raw output from the tilt encoder since the last reset
  // */
  // public double getTilterEncRaw() {
  //   return tiltEnc.getRaw();
  // }

  // /**Returns the tilt encoder's distance in inches */
  // public double getTilterDistance() {
  //   return tiltEnc.getDistance();
  // }

  // /**Resets the Tilter encoder back to zero */
  // public void resetTilterEnc() {
  //   tiltEnc.reset();
  // }

  // /**Returns the raw counts recieved from the pan encoder 
  //  * 
  //  * @return Current raw output from the pan encoder since the last reset
  // */
  // public double getPannerEncRaw() {
  //   return panEnc.getRaw();
  // }

  // /**Returns the pan encoder's distance in inches */
  // public double getPannerDistance() {
  //   return panEnc.getDistance();
  // }

  // /**Resets the Panner encoder back to zero */
  // public void resetPannerEnc() {
  //   panEnc.reset();
  // }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
