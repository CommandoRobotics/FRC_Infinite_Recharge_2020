package frc.robot.APIs;

import frc.robot.ConstantsValues;

public class ProjectileMathAPI {

    //This is the class for all the math run to calculate the shooter orientation,
    //velocity, etc if needed

    int FIND_ANGLE_MODE = 0;
    int FIND_VELOCITY_MODE = 1;
    int METRIC_MODE = 0;
    int FEET_MODE = 1;
    int INCH_MODE = 2;
    double yTotal; 
    double vyInitial;
    double vxIntital;

    public ProjectileMathAPI() {

    }

/*NOTE TO SELF
* need to set angles slightly higher than desired as we need the ball to 
* be just coming down from the peak in order to be the most efficient and accurate
*/

    /**
     * This is the code to get both a minimum required velocity and angle for a projectile to fly in order to 
     * hit a certain height. This calcualtes based on a given target negative y velocity in order to ensure
     * that the ball is slightly angled down when it is entering the goal.
     * 
     * @param limelightDistance Distance directly to the target from the limelight or other sensor. This code
     *                          will automatically get the x distance away from the target using this
     * @param targetHeight      The height of the target that you want to shoot at. This should be from where
     *                          the object is *launched* to the desired height, not the total height of the target
     * @return A double array with 3 shots allocated and 2 spots used. Double[0] is the initial required
     *         velocity in and Double[1] is the required launch angle in degrees. The third spot Double[2] is
     *         used for extra scaling with scaleVelocity()
     */
    public double[] calculateInitialVelocityAndAngle(double limelightDistance, double targetHeight) {
        double[] velocityAndAngle = new double[3];

        double Vₒᵧ = calculateRequiredYVelocity(ConstantsValues.negativeTargetVelocity, targetHeight, ConstantsValues.acceleration);
        double timeToTarget = quadSolForLargest((.5*ConstantsValues.acceleration),Vₒᵧ,targetHeight);
        double x = Math.sqrt(Math.pow(limelightDistance,2) - Math.pow(targetHeight,2));
        double Vₒₓ = x/timeToTarget;
        double Vₒ = Math.sqrt(Math.pow(Vₒₓ,2) + Math.pow(Vₒᵧ,2));

        velocityAndAngle[0] = Vₒ;
        velocityAndAngle[1] = (calculateAngleBasedOnVelocity(Vₒₓ, Vₒᵧ) * 180 / Math.PI);
        return velocityAndAngle;
    }

    /**
     * This takes a distance from a target and returns a scaled velocity in the double[] from the one given by 
     * calculateInitialVelocityAndAngle(). This is used to slightly over adjust the speed to ensure proper travel
     * as drag and wheel slow down is not factored into calculateInitialVelocityAndAngle(). Scales differently
     * depending on distance
     * @param velocityAndAngle The double array from calculateInitialVelocityAndAngle()
     * @param distanceToTarget The direct distance to the target read directly from the limelight
     * @param mode METRIC_MODE: Scales assuming meter inputs. FEET_MODE: Scales assuming feet inputs. INCH_MODE
     *             Scales assuming inch inputs
     * @return The double[] inputted but with double[2] now containing the scaled initial required velocity
     */
    public double[] scaleVelocity(double[] velocityAndAngle, double distanceToTarget, double mode) {
        if (mode == METRIC_MODE) {
            if (distanceToTarget < 5) {
                velocityAndAngle[2] = velocityAndAngle[0] + .5;
            } else if (distanceToTarget > 5) {
                velocityAndAngle[2] = velocityAndAngle[0] + 1;
            }           
        } else if (mode == FEET_MODE) {
            if (distanceToTarget < 10) {
                velocityAndAngle[2] = velocityAndAngle[0] + .5;
            } else if (distanceToTarget > 10) {
                velocityAndAngle[2] = velocityAndAngle[0] + 1;
            }
        } else if (mode == INCH_MODE) {
            if (distanceToTarget < 120) {
                velocityAndAngle[2] = velocityAndAngle[0] + .5;
            } else if (distanceToTarget > 120) {
                velocityAndAngle[2] = velocityAndAngle[0] + 1;
            }
        }
        return velocityAndAngle;
    }

    /**
     * Method for calculating the y vector of the initial launch velocity given a target negative velocity to hit 
     * when the projectile hits the target. targetHeight is still the height of the target relative to shooting 
     * position and acceleration should be -9.8 (gravity) but can still be changed
     */
    private double calculateRequiredYVelocity(double targetNegativeVy, double targetHeight, double acceleration) {
        double Δy = (Math.pow(targetNegativeVy,2))/(2*acceleration);
        double yₜ = targetHeight + Math.abs(Δy) ;
        yTotal = yₜ;
        double Vₒᵧ = Math.sqrt(-2*acceleration*yₜ);
        return Vₒᵧ;
    }

    /**
     * Uses inputs of the x and y vectors of initial velocity to find the inital launch angle. Used by 
     * calculateInitialVelocityAndAngle()
     * 
     * @param Vₒₓ Inital x velocity
     * @param Vₒᵧ Initial y velocity
     * @return Minimum theta, or angle required, to hit the target given a certain velocity
     */
    private double calculateAngleBasedOnVelocity(double Vₒₓ, double Vₒᵧ) {
        double θ = Math.atan(Vₒᵧ/Vₒₓ);
        return θ;
    }

    /**
     * This is a simple quadratic formula solver that always returns the highest value found or else reports
     * an error to the console. For reference this is Ax²+Bx+C
     * @param a <b>A</b>x²+Bx+C
     * @param b Ax²+<b>B</b>x+C
     * @param c Ax²+Bx+<b>C</b>
     * @return Largest solution found
     */
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