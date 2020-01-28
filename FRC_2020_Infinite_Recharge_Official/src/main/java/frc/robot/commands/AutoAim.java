/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.ConstantsPID;
import frc.robot.ConstantsValues;
import frc.robot.APIs.ProjectileMathAPI;
import frc.robot.subsystems.AutoAimSubsystem;

public class AutoAim extends PIDCommand {

  /*TODO Add in overriding code in case we see more than one object
  * TODO Add in overriding code to stop the shooter so that it doesnt pass a certain angle
  * TODO Add in code to recenter the shooter to 0 when we dont see anything
  * TODO (Maybe) Add in code re-center the shooter using limit switches?
  */
  
  AutoAimSubsystem autoAimSubsystem;
  PIDController tiltPID;
  ProjectileMathAPI projectileMath;

  /**
   * Creates a new AutoAim.
   */
  public AutoAim(AutoAimSubsystem m_AutoAimSubsystem) {
    super(
        //Create the controller
        new PIDController(ConstantsPID.kPanP, ConstantsPID.kPanI, ConstantsPID.kPanD),
        //Give it the current value to get error from
        m_AutoAimSubsystem::getLimelightXOffset,
        //Give it the target setpoin
        0,
        //Give it the method that the output will be used on
        output -> m_AutoAimSubsystem.setPanner(output),
        //And add the required subsystem/s
        m_AutoAimSubsystem);

    //Creating the second PID Controller for the tilter
    tiltPID = new PIDController(ConstantsPID.kTiltP, ConstantsPID.kTiltI, ConstantsPID.kTiltD);

    //Create the math API and add the subsytem integration
    projectileMath = new ProjectileMathAPI();
    autoAimSubsystem = m_AutoAimSubsystem;
    addRequirements(m_AutoAimSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Set both of th tolerances for the PID loops saved in ConstantsValues (probably pretty small)
    tiltPID.setTolerance(ConstantsPID.tiltTolerance);
    getController().setTolerance(ConstantsPID.panTolerance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //Detect if the riser is up or down
    if (autoAimSubsystem.riserActive) {
      /*
      * CODE FOR WHEN RISER IS UP
      *
      * Calculate the set angle the tilter should adjust to. This will look confusing, but
      * basically all it's doing is calcualting the the velocity and angle using the limelight
      * distance and the current target height (changes due to lifting pistions)
      */
      tiltPID.setSetpoint(
        projectileMath.calculateInitialVelocityAndAngle(
          autoAimSubsystem.getLimelightDis(ConstantsValues.targetHeightLifted), 
          ConstantsValues.targetHeightLifted)[1]
          );

      //Set the tilter to the calculated PID value using encoder
      autoAimSubsystem.setTilter(tiltPID.calculate(autoAimSubsystem.getTiltAngle()));
    } else {
      /*
      * CODE FOR WHEN RISER IS DOWN
      *
      * Calculate the set angle the tilter should adjust to. This will look confusing, but
      * basically all it's doing is calcualting the the velocity and angle using the limelight
      * distance and the current target height (changes due to lifting pistions)
      */
      tiltPID.setSetpoint(
        projectileMath.calculateInitialVelocityAndAngle(
          autoAimSubsystem.getLimelightDis(ConstantsValues.targetHeightLowered), 
          ConstantsValues.targetHeightLowered)[1]
          );

      //Set the tilter to the calculated PID value using encoder
      autoAimSubsystem.setTilter(tiltPID.calculate(autoAimSubsystem.getTiltAngle()));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
