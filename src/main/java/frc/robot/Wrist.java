package frc.robot;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.controller.motorControllers.Spark;

// The wrist class is very similar to the Elevator class, so refer to that if you need it
public class Wrist {

    // Declaring new motor using a spark motor controller and our Spark class
    Spark m_motor;

    // PID values used by the encoder on the Spark motor controller, these still need to be adjusted for our robot.
    // These for the moment are just placeholder values, they need to be adjusted for our robot.
    public final double kP = 0.01, kI = 0.0, kD = 0.0, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
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
    public final double hatchPosition = 3.0;
    public final double cargoPosition = 9.476;
    public final double intakePosition = 9.476;

    // Constants for Feed Forward control to counteract gravity
    // PID control is not the best for holding a position against gravity
    private final double mass = 7.48;// kg
    private final double gravity = 9.80665;// m/s^2
    private final double distanceToCM = .368;// m
    private final double kT = .216667;// N*m
    private final double gearRatio = 32.1;

    private final double kTorque = 1 / (this.kT * gearRatio);// m*N

    // Constants for converting position to angle and vice versa
    private final double positionScalar = -152/9.476;// FIND THIS VALUE
    private final double positionTranslator = 152;// FIND THIS VALUE

    private final double hatchMassAddition = 1.04;
    private final double hatchCMAddition = 0.03;

    private Claw claw;

    //Constructor for Wrist class, takes in the port the motor is plugged in to and whether the motor is brushless or not, along with the PID values
    //This also sets coast mode to false (therefor to brake), so the wrist stays in place when not being moved
    public Wrist(int motorPort, boolean brushlessMotor, Claw claw) {
        this.m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Wrist", true, true, kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);

        this.claw = claw;
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

        double position = 0.0;

        switch (wristPosition) {

            case START:
                position = startPosition;
                break;
            
            case HATCH:
                position = hatchPosition;
                break;

            case CARGO:
                position = cargoPosition;
                break;

            case INTAKE:
                position = intakePosition;
                break;
        }

        this.m_motor.setToPosition(joystickControl, position, 0 /*- this.getFeedForward()*/);

    }

    // For testing purposes to figure out the correct encoder values, or as a backup, the wrist can manually being controlled using the setSpeed function.
    // It also adds the current encoder position to the smart dashboard using the printEncoderPosition() function from the Spark class.
    public void setSpeed(double speed) {
        //m_motor.setSpeed(speed);
        //m_motor.printEncoderPosition();
        m_motor.watchEncoderWithJoystick(speed);
    }

    private double getAngle() {
        return this.m_motor.getCurrentPosition() * this.positionScalar + this.positionTranslator;
    }

    private double getFeedForward() {

        if (m_motor.isUpperLimitPressed(true)) {
            return 0.0;
        }

        //Option to try to incorporate acceleration of elevator
        double acceleration = this.gravity;

        double currentMass = mass;
        double currentCM = distanceToCM;
        if (claw.hasHatch()) {
            currentMass += hatchMassAddition;
            currentCM += hatchCMAddition;
        }

        return Math.cos(this.getAngle()) * acceleration * this.kTorque * currentMass * currentCM;
    }

    public void driveWithFF(double speed) {

        double ff = this.getFeedForward() / 10;
        double spd = -0.5 * speed;

        if (Math.abs(spd + ff) > 1) {
            this.m_motor.watchEncoderWithJoystick(speed);
        }
        else {
            this.m_motor.watchEncoderWithJoystick(speed - ff);
        }

        //this.m_motor.setSpeed(speed - this.getFeedForward());

        SmartDashboard.putNumber("wrist FF", ff);
        SmartDashboard.putNumber("wrist Output", speed + ff);
    }

}