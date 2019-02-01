package frc.controller.motorControllers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.controller.EncoderController;
import frc.controller.MotorController;


/**
 * Class written by 5066 to control Spark motor controllers.
 */
public class Spark implements MotorController {

    // Declare a single CANSparkMax motor controller.
    private CANSparkMax m_motor;

    private CANEncoder m_encoder;
    private CANPIDController m_pidController;

    //name of the mechanism
    String name;

    double currentPosition;
    double initialPosition;

    /**
     * Constructor for Spark class.
     * 
     * @param portNumber pass the CAN network ID for this motor controller
     * @param brushlessMotor pass true if using a neo brushless motor
     */
    public Spark(int portNumber, boolean brushlessMotor) {

        MotorType type;
        if (brushlessMotor) {
            type = MotorType.kBrushless;
        }
        else {
            type = MotorType.kBrushed;
        }
        
        this.m_motor = new CANSparkMax(portNumber, type);
    }

    /**
     * Constructor for Spark class.
     * 
     * @param portNumber pass the CAN network ID for this motor controller
     * @param brushlessMotor pass true if using a neo brushless motor
     */
    public Spark(int portNumber, boolean brushlessMotor, String name,
    double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {
        this(portNumber, brushlessMotor);

        this.m_encoder = m_motor.getEncoder();
        this.m_pidController = m_motor.getPIDController();

        this.putConstantsOnDashboard(name, kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);

        //If intitialPosition = -100, lower limit switch has not been pressed.
        double initialPosition = -100;
        
    }

    /**
     * 
     * @return the object's instance of the motorController, m_motor
     */
    public CANSparkMax getMotorController() {
        return this.m_motor;
    }

    
    public void follow(MotorController baseController, boolean invert) {

        // Because a Spark object can only follow another Spark object, we must cast the parameter
        // baseController to be a Spark.
        this.m_motor.follow(((Spark)baseController).getMotorController(), invert);
    }

    public void setSpeed(double percentOutput) {
        
        this.m_motor.set(percentOutput);
    }

    public void setRampRate(double rampRate) {

        if (!this.m_motor.isFollower()) {
            this.m_motor.setRampRate(rampRate);
        }
    }

    public void setCoastMode(boolean coast) {

        if (coast) {
            this.m_motor.setIdleMode(IdleMode.kCoast);
        }
        else {
            this.m_motor.setIdleMode(IdleMode.kBrake);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //Encoder methods:

    public void printEncoderPosition() {
        SmartDashboard.putNumber(name + "encoder value", m_encoder.getPosition());
    }


    /**
     * Puts constants on the dashboard, possibly so they can be edited later with getConstantsFromDashboard()
     * 
     * @param name name of the mechanism we are controlling
     * @param kP constant of proportional control
     * @param kI constant of integral control
     * @param kD constant of derivative control
     * @param kIZ constant of IZone (whatever that is)
     * @param kFF constant of FeedvForward
     * @param kMinOut constant of minimum output for the motor in percent voltage (never lower than -1)
     * @param kMaxOut constant of maximum output for the motor in percent voltage (never more that +1)
     */
    public void putConstantsOnDashboard(String name, double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {

        this.m_pidController.setP(kP);
        this.m_pidController.setI(kI);
        this.m_pidController.setD(kD);
        this.m_pidController.setIZone(kIZ);
        this.m_pidController.setFF(kFF);
        this.m_pidController.setOutputRange(kMinOut, kMaxOut);
        

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber(name + " P Gain", kP);
        SmartDashboard.putNumber(name + " I Gain", kI);
        SmartDashboard.putNumber(name + " D Gain", kD);
        SmartDashboard.putNumber(name + " I Zone", kIZ);
        SmartDashboard.putNumber(name + " Feed Forward", kFF);
        SmartDashboard.putNumber(name + " Max Output", kMaxOut);
        SmartDashboard.putNumber(name + " Min Output", kMinOut);

    }

    
    /**
     * Update PID constants from the dashboard (primarily for testing purposes)
     */
    public void getConstantsFromDashboard() {

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber(this.name + " P Gain", 0);
        double i = SmartDashboard.getNumber(this.name + " I Gain", 0);
        double d = SmartDashboard.getNumber(this.name + " D Gain", 0);
        double iz = SmartDashboard.getNumber(this.name + " I Zone", 0);
        double ff = SmartDashboard.getNumber(this.name + " Feed Forward", 0);
        double max = SmartDashboard.getNumber(this.name + " Max Output", 0);
        double min = SmartDashboard.getNumber(this.name + " Min Output", 0);
        //double rotations = SmartDashboard.getNumber("Elevator Set Rotations", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if ((p != this.m_pidController.getP())) {
            this.m_pidController.setP(p);
        }
        if((i != this.m_pidController.getI())) {
            this.m_pidController.setI(i);
        }
        if((d != this.m_pidController.getD())) {
            this.m_pidController.setD(d);
        }
        if((iz != this.m_pidController.getIZone())) {
            this.m_pidController.setIZone(iz);
        }
        if((ff != this.m_pidController.getFF())) {
            this.m_pidController.setFF(ff);
        }
        if((max != this.m_pidController.getOutputMax()) || (min != this.m_pidController.getOutputMin())) { 
            this.m_pidController.setOutputRange(min, max);
        }
    }

    

    /**
     * returns state of limit switch (may need to be made more reusable)
     */
    public boolean isLowerLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    /**
     * returns state of second limit switch (may need to be made more reusable)
     * @param normallyOpen true if limit switch often is not pressed (I think)
     * @return state of limit switch (true if in unexpected state (maybe))
     */
    public boolean isUpperLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    /**
     * sets initial position to m_encoder.getPosition()
     * Call this method when a limit switch is pressed, because then we know where the mechanism is
     */
    public void setInitialPosition() {
        this.initialPosition = this.m_encoder.getPosition();
    }

    public double getCurrentPosition() {
        return this.m_encoder.getPosition() - this.initialPosition;
    }

    public void setToPosition(double joystickControl, double position) {

        if (this.initialPosition != -100) {
            this.m_pidController.setReference(position, ControlType.kPosition);
            return;
        }


    }

    /**
     * sets velocity of the motor (not likely to be used in 2019)
     * @param velocity in RPM
     */
    public void setVelocity(double velocity) {

        this.m_pidController.setReference(velocity, ControlType.kVelocity);
    }
}