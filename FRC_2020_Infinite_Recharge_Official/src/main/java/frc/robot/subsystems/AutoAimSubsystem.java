/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.naming.LimitExceededException;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ConstantsPorts;

public class AutoAimSubsystem extends SubsystemBase {
  Spark tilt;
  Spark pan;

  Encoder tiltEncoder;
  Encoder panEncoder;

  public AutoAimSubsystem() {
    tilt = new Spark(ConstantsPorts.tiltPort);
    pan = new Spark(ConstantsPorts.panPort);

    boolean reverseDirection = false;
    tiltEncoder = new Encoder(ConstantsPorts.tiltEncAPort, ConstantsPorts.tiltEncBPort);
    panEncoder = new Encoder(ConstantsPorts.panEncAPort, ConstantsPorts.panEncBPort, reverseDirection);   
  }

  public void setRotator(double speed) {
    tilt.setSpeed(speed);
  }

  public void setAngler(double speed) {
    pan.setSpeed(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
