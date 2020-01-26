package frc.robot.APIs;

import frc.robot.ConstantsValues;

public class ProjectileMathAPI {

    //This is the class for all the math run to calculate the shooter orientation,
    //velocity, etc if needed

    int FIND_ANGLE_MODE = 0;
    int FIND_VELOCITY_MODE = 1;
    double yTotal; 
    double vyInitial;
    double vxIntital;

    public ProjectileMathAPI() {

    }

/*NOTE TO SELF
* need to set angles slightly higher than desired as we need the ball to 
* be just coming down from the peak in order to be the most efficient and accurate
*/

    private double calculateRequiredYVelocity(double targetNegativeVy, double targetHeight, double acceleration) {
        double Δy = (Math.pow(targetNegativeVy,2))/(2*acceleration);
        double yₜ = targetHeight + Math.abs(Δy) ;
        yTotal = yₜ;
        double Vₒᵧ = Math.sqrt(-2*acceleration*yₜ);
        return Vₒᵧ;
    }

    public double[] calculateInitialVelocityAndAngle(double limelightDistance, double targetHeight) {
        double[] velocityAndAngle = new double[2];

        double Vₒᵧ = calculateRequiredYVelocity(ConstantsValues.negativeTargetVelocity, targetHeight, ConstantsValues.acceleration);
        double timeToTarget = quadSolForLargest((.5*ConstantsValues.acceleration),Vₒᵧ,targetHeight);
        double x = Math.sqrt(Math.pow(limelightDistance,2) - Math.pow(targetHeight,2));
        double Vₒₓ = x/timeToTarget;
        double Vₒ = Math.sqrt(Math.pow(Vₒₓ,2) + Math.pow(Vₒᵧ,2));

        velocityAndAngle[0] = Vₒ;
        velocityAndAngle[1] = calculateAngleBasedOnVelocity(Vₒₓ, Vₒᵧ);
        return velocityAndAngle;
        
    }

    private double calculateAngleBasedOnVelocity(double Vₒₓ, double Vₒᵧ) {
        double θ = Math.atan(Vₒᵧ/Vₒₓ);
        return θ;
    }

    private double quadSolForLargest(double a, double b, double c) {
        double discriminant = Math.sqrt(Math.pow(b,2) - 4*a*c);
        if (discriminant > 0) {
            double solA = (-b + discriminant)/(2*a);
            double solB = (-b - discriminant)/(2*a);
            if (solA > solB) {
                return solA;
            } else {
                return solB;
            }
        } if (discriminant == 0) {
            return -b/(2*a);
        } else {
            System.out.println("quadSolForLargest ran into impossible/imaginary numbers");
            return 0;
        }
    }

}