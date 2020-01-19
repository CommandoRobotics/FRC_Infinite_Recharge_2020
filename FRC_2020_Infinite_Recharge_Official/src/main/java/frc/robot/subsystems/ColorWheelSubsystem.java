/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorWheelSubsystem extends SubsystemBase {
  
  Spark colorSensorTurner;
  Encoder colorEncoder;
  ColorSensorV3 colorSensor;

  public ColorWheelSubsystem() {
      //FOR ISAAC INGRAM 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
