package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TriggerTrigger extends Trigger {

    XboxController controller;
    Hand hand;
    double deadZone;
 

    public TriggerTrigger(XboxController controller, Hand hand, double deadZone) {
        super();
        this.controller = controller;
        this.hand = hand;
        this.deadZone = deadZone;
    }

    @Override
    public boolean get() {
        return controller.getTriggerAxis(hand) > deadZone ? true : false;
    }

    
}