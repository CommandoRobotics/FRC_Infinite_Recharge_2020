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

  public ShooterSubsystem() {
    loader = new Spark(ConstantsPorts.shooterLoaderPort);
    shooter = new Spark(ConstantsPorts.shooterPort);

    shooterEnc = new Encoder(ConstantsPorts.shooterEncAPort, ConstantsPorts.shooterEncBPort);

    loaderMinSpeed = ConstantsValues.loaderMinSpeed;
    shooterMinSpeed = ConstantsValues.shooterMinSpeed;
  }

  //Set the shooter to a certain inputted speed, given > minPower
  public void setShooter(double speed) {
    if (speed > shooterMinSpeed) {
      shooter.setSpeed(speed);
    } else {
      shooter.stopMotor();
    }
  }

  //Sets the loader/flywheel to a certain inputted speed, given > minPower
  public void setLoader(double speed) {
    if (speed > loaderMinSpeed) {
      loader.setSpeed(speed);
    } else {
      loader.stopMotor();
    }
  }

  //Stops all action on the shooter motors
  public void stopShooter() {
    shooter.stopMotor();
  }

  //Stops all action on the loader/flywheel motors
  public void stopLoader() {
    loader.stopMotor();
  }

  //Returns the current set speed of the shooter motor
  public double getShooterSetSpeed() {
    return shooter.get();
  }

  //Returns the raw counts recived from the shooter encoder
  public double getShooterEncRaw() {
    return shooterEnc.getRate();
  }

  //Sets the minimum speed the shooter will run
  public void setMinShooterSpeed(double speed) {
    shooterMinSpeed = speed;
  }
  
  //Sets the min speed the loader will run
  public void setMinLoaderSpeed(double speed) {
    loaderMinSpeed = speed;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
