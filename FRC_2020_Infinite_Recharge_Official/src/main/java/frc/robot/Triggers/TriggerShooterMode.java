package frc.robot.Triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterMode;

public class TriggerShooterMode extends Trigger {

    ShooterMode modeToCheckFor;
    ShooterSubsystem shooterSubsystem;

    public TriggerShooterMode(ShooterMode modeToCheckFor, ShooterSubsystem shooterSubsystem) {
        super();
        this.modeToCheckFor = modeToCheckFor;
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public boolean get() {
        return (modeToCheckFor == shooterSubsystem.getshooterMode());
    }

    
}