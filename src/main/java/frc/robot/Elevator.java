package frc.robot;

import frc.controller.motorControllers.Spark;

public class Elevator {

    //Declaring new motor using a spark motor controller and our Spark class
    Spark m_motor;

    //PID values used by the encoder on the Spark motor controller, these still need to be adjusted for our robot.
    //These for the moment are just placeholder values, they need to be adjusted for our robot.
    public final double kP = 0.1, kI = 1e-4, kD = 0.1, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
    //Rate that the motor speeds up at
    public final double rampRate = 0.2;

    //Constructor for Elevator Class, takes in the port the motor is plugged in to and whether the motor is brushless or not, along with the PID values
    //This also sets coast mode to false (therefor to brake), so the elevator stays in place when not being moved
    public Elevator(int motorPort, boolean brushlessMotor) {
        m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Elevator", kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        m_motor.setCoastMode(false);
    }

    //Basic function using the setToPosition function in the Spark class to move the encoder to a specified point (position).
    //This also takes in a joystick value, and as defined in Spark if the motor hasn't moved yet it will use joystick control until it hits the bottom endstop
    public void setPosition(double position, double joystickControl) {
        m_motor.setToPosition(joystickControl, position);
    }

    //For testing purposes to figure out the correct encoder values, or as a backup, the elevator can manually being controlled using the setSpeed function.
    //It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }

}