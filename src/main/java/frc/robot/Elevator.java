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

    public enum ElevatorPosition {
        BOTTOM, 
        HATCH1, 
        HATCH2, 
        HATCH3, 
        CARGO1,
        CARGO2,
        CARGO3,
        CARGOSHIP,
    }

    //Positions for elevator
    final double bottomPos = 0.0;
    final double hatch1Pos = 10.0;
    final double hatch2Pos = 20.0;
    final double hatch3Pos = 30.0;
    final double cargo1Pos = 15.0;
    final double cargo2Pos = 25.0;
    final double cargo3Pos = 35.0;
    final double cargoShipPos = 22.0;

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

    public void setPositionWithEnum (ElevatorPosition elevatorPosition, double joystickControl) {

        switch (elevatorPosition) {
            case BOTTOM:
                m_motor.setToPosition(joystickControl, bottomPos);
            
            case HATCH1:
                m_motor.setToPosition(joystickControl, hatch1Pos);

            case HATCH2:
                m_motor.setToPosition(joystickControl, hatch2Pos);

            case HATCH3: 
                m_motor.setToPosition(joystickControl, hatch3Pos);
            
            case CARGO1:
                m_motor.setToPosition(joystickControl, cargo1Pos);

            case CARGO2:
                m_motor.setToPosition(joystickControl, cargo2Pos);

            case CARGO3:
                m_motor.setToPosition(joystickControl, cargo3Pos);

            case CARGOSHIP: 
                m_motor.setToPosition(joystickControl, cargoShipPos);
        }

    }

    //For testing purposes to figure out the correct encoder values, or as a backup, the elevator can manually being controlled using the setSpeed function.
    //It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }

}