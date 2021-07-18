package frc.robot.Triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TriggerPOV extends Trigger {

    public enum POVDirection{
        kUp(0),
        kRight(90),
        kDown(180),
        kLeft(270),
        kNeutral(-1);

        @SuppressWarnings({"MemberName", "PMD.SingularField"})
        public final int value;
    
        POVDirection(int value) {
          this.value = value;
        }
    }
    XboxController controller;
    POVDirection POV;

    public TriggerPOV(XboxController controller,  POVDirection POV) {
        super();
        this.controller = controller;
        this.POV = POV;
    }

    @Override
    public boolean get() {
        if ((controller.getPOV() < (POV.value + 5)) && (controller.getPOV() > (POV.value - 5))) { 
            return true;
        } else {
            return false;
        }
    }

    
}