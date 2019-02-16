package frc.robot;

import com.revrobotics.CANEncoder;

import frc.controller.motorControllers.Spark;

// The wrist class is very similar to the Elevator class, so refer to that if you need it
public class Wrist {

    // Declaring new motor using a spark motor controller and our Spark class
    Spark m_motor;

    // PID values used by the encoder on the Spark motor controller, these still need to be adjusted for our robot.
    // These for the moment are just placeholder values, they need to be adjusted for our robot.
    public final double kP = 0.1, kI = 1e-4, kD = 0.1, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
    // Rate that the motor speeds up atv
    public final double rampRate = 0.2;


    // enum WristPosition allows us to set the wrist to one of 4 desired positions for doing stuff.
    // See below setPositionWithEnum method for more information
    public enum WristPosition {
        START,
        HATCH,
        CARGO,
        INTAKE
    }

    // Constants for the encoder positions for each of our enum values.
    // These at the moment are just placeholder values, need to be tested for our robot.
    public final double startPosition = 0.0;
    public final double hatchPosition = 90.0;
    public final double cargoPosition = 180.0;
    public final double intakePosition = 225.0;

    // Constants for Feed Forward control to counteract gravity
    // PID control is not the best for holding a position against gravity
    private final double mass = 6.818;// kg FIND THIS VALUE
    private final double gravity = 9.80665;// m/s^2
    private final double distanceToCM = 0.0;// m FIND THIS VALUE
    private final double kTorque = .216667; //N*m
    private final double gearRatio = 34.22;

    private final double kVoltage = this.mass * this.distanceToCM / (this.kTorque * gearRatio);

    // Constants for converting position to angle and vice versa
    private final double positionScalar = 0.0;// FIND THIS VALUE
    private final double positionTranslator = 60.0;// FIND THIS VALUE

    //Constructor for Wrist class, takes in the port the motor is plugged in to and whether the motor is brushless or not, along with the PID values
    //This also sets coast mode to false (therefor to brake), so the wrist stays in place when not being moved
    public Wrist(int motorPort, boolean brushlessMotor) {
        m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Wrist", kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
    }

    // Basic function using the setToPosition function in the Spark class to move the motor using the encoder to a specified point (position).
    // This also takes in a joystick value, and as defined in Spark if the motor hasn't moved yet it will use joystick control until it hits the bottom endstop
    public void setPosition(double position, double joystickControl) {
        
        m_motor.setToPosition(joystickControl, position, this.getFeedForward());
    }

    // Using the WristPosition enum & constants, we can set the wrist to one of 4 positions for doing our 4 different tasks using the setPositionWithEnum method
    // Works using the constants defined above and setToPosition from Spark, see above setPosition for more info
    // Start is what it is when starting the match, hatch is to deliver hatch panels, cargo is to deliver cargo, and intake is to pickup cargo.
    public void setPositionWithEnum(WristPosition wristPosition, double joystickControl) {

        switch (wristPosition) {

            case START:
                m_motor.setToPosition(joystickControl, startPosition, this.getFeedForward());
                break;
            case HATCH:
                m_motor.setToPosition(joystickControl, hatchPosition, this.getFeedForward());
                break;
            case CARGO:
                m_motor.setToPosition(joystickControl, cargoPosition, this.getFeedForward());
                break;
            case INTAKE:
                m_motor.setToPosition(joystickControl, intakePosition, this.getFeedForward());
                break;
        }


    }

    // For testing purposes to figure out the correct encoder values, or as a backup, the wrist can manually being controlled using the setSpeed function.
    // It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }

    private double getAngle() {
        return this.m_motor.getCurrentPosition() * this.positionScalar - this.positionTranslator;
    }

    private double getFeedForward() {

        if (m_motor.isLowerLimitPressed(true)) {
            return 0.0;
        }

        //Option to try to incorporate acceleration of elevator
        double acceleration = this.gravity;

        return Math.cos(this.getAngle()) * acceleration * this.kVoltage;
    }

}