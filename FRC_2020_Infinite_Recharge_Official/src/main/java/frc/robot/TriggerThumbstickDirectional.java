package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TriggerThumbstickDirectional extends Trigger {

    XboxController controller;
    XboxController.Axis axis;
    double deadZone;
    boolean isLeft;


    public TriggerThumbstickDirectional(XboxController controller, XboxController.Axis axis, boolean isLeft, double deadZone) {
        super();
        this.controller = controller;
        this.axis = axis;
        this.deadZone = deadZone;
        this.isLeft = isLeft;
    }

    @Override
    public boolean get() {
        if(isLeft) {
            return controller.getRawAxis(axis.value) < -deadZone ? true : false;
        } else {
            return controller.getRawAxis(axis.value) > deadZone ? true: false;
        }

    }

   
}