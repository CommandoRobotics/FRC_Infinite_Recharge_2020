package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TriggerThumbstick extends Trigger {

    XboxController controller;
    XboxController.Axis axis;
    double deadZone;

    public TriggerThumbstick(XboxController controller, XboxController.Axis axis, double deadZone) {
        super();
        this.controller = controller;
        this.axis = axis;
        this.deadZone = deadZone;
    }

    @Override
    public boolean get() {
        return controller.getRawAxis(axis.value) > deadZone ? true : false;
    }

    
}