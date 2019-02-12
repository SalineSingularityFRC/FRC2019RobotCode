package frc.robot;

import frc.controller.motorControllers.Spark;

//The wrist class is very similar to the Elevator class, so refer to that if you need it
public class Wrist {

    //Declaring new motor using a spark motor controller and our Spark class
    Spark m_motor;

    //PID values used by the encoder on the Spark motor controller, these still need to be adjusted for our robot.
    //These for the moment are just placeholder values, they need to be adjusted for our robot.
    public final double kP = 0.1, kI = 1e-4, kD = 0.1, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
    //Rate that the motor speeds up atv
    public final double rampRate = 0.2;


    public enum WristPosition {
        START,
        HATCH,
        CARGO,
        INTAKE
    }

    public final double startPosition = 0.0;
    public final double hatchPosition = 90.0;
    public final double cargoPosition = 180.0;
    public final double intakePosition = 225.0;

    //Constructor for Wrist class, takes in the port the motor is plugged in to and whether the motor is brushless or not, along with the PID values
    //This also sets coast mode to false (therefor to brake), so the wrist stays in place when not being moved
    public Wrist(int motorPort, boolean brushlessMotor) {
        m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Wrist", kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
    }

    //Basic function using the setToPosition function in the Spark class to move the motor using the encoder to a specified point (position).
    //This also takes in a joystick value, and as defined in Spark if the motor hasn't moved yet it will use joystick control until it hits the bottom endstop
    public void setPosition(double position, double joystickControl) {
        m_motor.setToPosition(joystickControl, position);
    }

    public void setPositionWithEnum(WristPosition wristPosition, double joystickControl) {
        switch (wristPosition) {

            case START:
                m_motor.setToPosition(joystickControl, startPosition);
                break;
            case HATCH:
                m_motor.setToPosition(joystickControl, hatchPosition);
                break;
            case CARGO:
                m_motor.setToPosition(joystickControl, cargoPosition);
                break;
            case INTAKE:
                m_motor.setToPosition(joystickControl, intakePosition);
                break;
        }


    }

    //For testing purposes to figure out the correct encoder values, or as a backup, the wrist can manually being controlled using the setSpeed function.
    //It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }

}