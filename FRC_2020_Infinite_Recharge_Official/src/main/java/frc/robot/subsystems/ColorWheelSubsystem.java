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
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.ConstantsPID;
import frc.robot.ConstantsPorts;
import frc.robot.ConstantsValues;
import edu.wpi.first.wpilibj.controller.PIDController;

import frc.robot.APIs.*;

public class ColorWheelSubsystem extends PIDSubsystem {
  
  Spark colorWheelMotor;
  Encoder colorWheelEncoder;
  ColorSensorV3 colorWheelSensor;
  Solenoid colorWheelPiston;
  ControlPanelAPI controlPanelAPI;

  public ColorWheelSubsystem() {
      super(new PIDController(ConstantsPID.colorWheelP, ConstantsPID.colorWheelI, ConstantsPID.colorWheelD));
      colorWheelMotor = new Spark(ConstantsPorts.colorWheelPort);
      colorWheelPiston = new Solenoid(ConstantsPorts.colorWheelPistonPort);
      colorWheelSensor = new ColorSensorV3(ConstantsPorts.colorWheelSensorPort);
      colorWheelEncoder = new Encoder(ConstantsPorts.colorWheelEncAPort, ConstantsPorts.colorWheelEncBPort);
      colorWheelEncoder.setDistancePerPulse(ConstantsValues.colorMotorDisPerPulse);
      controlPanelAPI = new ControlPanelAPI(ConstantsValues.distanceFromCenterOfColorWheelInInches, ConstantsValues.colorWheelGearboxRatio, ConstantsValues.colorWheelDiameterInInches, ConstantsValues.colorWheelEncoderCountsPerRevolution);
    }

  @Override
  public void useOutput(double output, double setpoint) {
    colorWheelMotor.setSpeed(output);
  }

  @Override
  public double getMeasurement() {
    return colorWheelEncoder.getRaw()/ConstantsValues.colorWheelEncPPR;
  }

  /** Retract the piston that holds up the control panel mechanism */
  public void retractColorPiston() {
    colorWheelPiston.set(false);
  }

  /** Deploy the piston that holds up the control panel mechanismm */
  public void deployColorPiston() {
    colorWheelPiston.set(true);
  }

  public void setColorPiston(boolean value) {
    if(value) {
      deployColorPiston();
    } else {
      retractColorPiston();
    }
  }

  /** Toggle the piston that holds up the control panel mechanism */
  public void toggleColorPiston() {
    colorWheelPiston.set(!colorWheelPiston.get());
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

  /** This method resets the color wheel motor encoder. */
  public void resetEncoder() {
    colorWheelEncoder.reset();
  }

  /**
   * This method gets the output of the PID controller.
   * @param currentValue The current position of the motor.
   * @param setPoint The target position of the motor.
   * @return The speed to spin the motor, as a double.
   */
  public double getMotorSpeedPID(double setPoint) {
    return getController().calculate(colorWheelEncoder.getDistance(), setPoint);
  }

  /** This method resets the current PID calculation. */
  public void resetPID() {
    getController().reset();
  }

  /**
   * This method finds if the motor is within its threshold of the setpoint of the motor PID
   * @return If the motor is at its setpoint, as a boolean.
   */
  public boolean atSetpoint() {
    return getController().atSetpoint();
  }

  /** This method gets the encoder travel distance since the last reset (in rotations), and returns it as a double. */
  public double getCurrentEncoderRotations() {
    return colorWheelEncoder.getDistance();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
