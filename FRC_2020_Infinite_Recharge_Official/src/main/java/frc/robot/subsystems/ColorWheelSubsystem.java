/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;

import frc.robot.APIs.*;

public class ColorWheelSubsystem extends SubsystemBase {
  
  Spark colorWheelMotor;
  Encoder colorWheelEncoder;
  ColorSensorV3 colorWheelSensor;
  Solenoid colorWheelPiston;
  ControlPanelAPI controlPanelAPI;

  public ColorWheelSubsystem() {
      colorWheelMotor = new Spark(ConstantsPorts.colorWheelPort);
      colorWheelPiston = new Solenoid(ConstantsPorts.colorWheelPistonPort);
      colorWheelSensor = new ColorSensorV3(ConstantsPorts.colorWheelSensorPort);
      colorWheelEncoder = new Encoder(ConstantsPorts.colorWheelEncAPort, ConstantsPorts.colorWheelEncBPort);
      colorWheelEncoder.setDistancePerPulse(ConstantsValues.colorMotorDisPerPulse);
      controlPanelAPI = new ControlPanelAPI(ConstantsValues.distanceFromCenterOfColorWheelInInches, ConstantsValues.colorWheelGearboxRatio, ConstantsValues.colorWheelDiameterInInches, ConstantsValues.colorWheelEncoderCountsPerRevolution);
    }

  /** Retract the piston that holds up the control panel mechanism */
  public void retractColorPiston() {
    colorWheelPiston.set(false);
  }

  /** Deploy the piston that holds up the control panel mechanismm */
  public void deployColorPiston() {
    colorWheelPiston.set(true);
  }

  /** Toggle the piston that holds up the control panel mechanism */
  public void toggleColorPiston() {
    if(colorWheelPiston.get()) {
      colorWheelPiston.set(false);
    } else {
      colorWheelPiston.set(true);
    }
  }

  /**
   * This method gets the red RGB value from the color wheel color sensor.
   * @return The color sensor's red RGB value as an integer.
   */
  public int getColorSensorRed() {
    return colorWheelSensor.getRed();
  }

  /**
   * This method gets the green RGB value from the color wheel color sensor.
   * @return The color sensor's green RGB value as an integer.
   */
  public int getColorSensorGreen() {
    return colorWheelSensor.getGreen();
  }

  /**
   * This method gets the blue RGB value from the color wheel color sensor.
   * @return The color sensor's blue RGB value as an integer.
   */
  public int getColorSensorBlue() {
    return colorWheelSensor.getBlue();
  }

  /**
   * This method gets the IR value from the color wheel color sensor.
   * @return The color sensor's IR value as an integer.
   */
  public int getColorSensorIR() {
    return colorWheelSensor.getIR();
  }

  /**
   * This method gets the proximity from the color wheel solor sensor.
   * @return The color sensor's proximity as an integer.
   */
  public int getColorSensorProximity() {
    return colorWheelSensor.getProximity();
  }

  /**
   * This method sets the speed of the color wheel motor.
   * @param speed The speed to set the motor to, as a double.
   */
  public void setColorMotorSpeed(double speed) {
    colorWheelMotor.setSpeed(speed);
  }

  /** This method stops the color wheel motor */
  public void stopColorMotor() {
    colorWheelMotor.setSpeed(0);
  }

  /**
   * This method gets the raw encoder value of the over the motor controlling the control panel spinner.
   * @return The raw rotation of the encoder, as an integer.
   */
  public int getRawEncoderValue() {
    return colorWheelEncoder.getRaw();
  }

  /**
   * This method spins the motor to a specified encoder value.
   * @param positionToSpinTo The encoder value to spin to, as an integer.
   */
  public void spinMotorToPosition(int positionToSpinTo) {

  }

  /**
   * This method spins the motor a specificed number of rotations.
   * @param rotationsToSpin The amount of rotations to spin, as a double.
   */
  public void spinMotorByRotations(double rotationsToSpin) {

  }

  /**
   * This method calculates the distance to spin the motor to acheive the target color, assuming we are on our side of the field.
   * @param targetColor The color we are trying to spin to (raw from the FMS), as a character.
   * @return The amount of encoder ticks to spin, as an integer.
   */
  public int calculateSpinDistanceOurSide(char targetColor) {
    return controlPanelAPI.calculateOurSide(targetColor);
  }

  public int calculateSpinDistanceEnemySide(char targetColor) {
    return controlPanelAPI.calculateEnemySide(targetColor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
