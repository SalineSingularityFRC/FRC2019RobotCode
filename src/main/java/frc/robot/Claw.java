package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.controller.motorControllers.Spark;
import frc.singularityDrive.SingDrive;

public class Claw {

    //Declaring new motor using a spark motor controller and the Spark class
    /* NOT USING SPARK OR SERVOS
    Spark m_motor;
    Servo servo1;
    Servo servo2;
    */
    VictorSPX clawMotor1;
    /* Commented out stuff for motor since we are using servo instead
    //PID values used by the encoder on the Spark motor controller, these still need to be adjusted for our robot.
    public final double kP = 0.1, kI = 1e-4, kD = 0.1, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
    //Rate that the motor speeds up at
    public final double rampRate = 0.2;

    //Constructor for Claw Class, takes in the port the motor is plugged in to and whether the motor is brushless or not, along with the PID values
    //This also sets coast mode to false (therefor to brake), so the Claw stays in place when not being moved
    public Claw(int motorPort, boolean brushlessMotor) {
        m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Claw", kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        m_motor.setCoastMode(false);
    }
    */

    boolean hatchAcquired;

    public Claw(int claw1Port) {
        clawMotor1 = new VictorSPX(claw1Port);
        hatchAcquired = false;    }

    public void controlClawMotor(double power) {
        clawMotor1.set(ControlMode.PercentOutput, power);

        if (power > .25) {
            hatchAcquired = true;
        }
        else if (power < .25) {
            hatchAcquired = false;
        }
        SmartDashboard.putBoolean("hatch acquired`", hatchAcquired);
    }

    public boolean haveHatch() {
        return haveHatch();
    }


    /* Servo Control
    public void controlServo(int angle) {
        SmartDashboard.putNumber("Servo Position", angle);
        int servo2Angle = 170 - angle;
        servo1.setAngle(angle);
        servo2.setAngle(servo2Angle);
    }
    */
   
    /* Also commented out since we are not using a motor
    //Basic function using the setToPosition function in the Spark class to move the encoder to a specified point (position).
    //This also takes in a joystick value, and as defined in Spark if the motor hasn't moved yet it will use joystick control until it hits the bottom endstop
    public void setPosition(double position, double joystickControl) {
        m_motor.setToPosition(joystickControl, position);
    }

    //For testing purposes to figure out the correct encoder values, or as a backup, the Claw can manually being controlled using the setSpeed function.
    //It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }
    */

}