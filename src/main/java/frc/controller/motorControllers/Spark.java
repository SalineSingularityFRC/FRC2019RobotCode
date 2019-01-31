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

    //PID constants
    double kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut;
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
    double kP, double kI, double KD, double kIZ, double kFF, double kMinOut, double kMaxOut) {

        MotorType type;
        if (brushlessMotor) {
            type = MotorType.kBrushless;
        }
        else {
            type = MotorType.kBrushed;
        }
        
        this.m_motor = new CANSparkMax(portNumber, type);

        this.m_encoder = m_motor.getEncoder();
        this.m_pidController = m_motor.getPIDController();

        this.putConstantsOnDashboard(name, kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
        
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

    public void printEncoderPosition() {
        SmartDashboard.putNumber(name + "encoder value", m_encoder.getPosition());
    }

    
    public void setP(double kP) {
        this.m_pidController.setP(kP);
        this.kP = kP;
    }

    public void setI(double kI) {
        this.m_pidController.setI(kI);
        this.kI = kI;
    }

    public void setD(double kD) {
        this.m_pidController.setD(kD);
        this.kD = kD;
    }

    public void setIz(double kIZ) {
        this.m_pidController.setIZone(kIZ);
        this.kIZ = kIZ;
    }

    public void setFF(double kFF) {
        this.m_pidController.setFF(kFF);
        this.kFF = kFF;
    }

    public void setOutputRange(double kMinOut, double kMaxOut) {
        this.m_pidController.setOutputRange(kMinOut, kMaxOut);
        this.kMinOut = kMinOut;
        this.kMaxOut = kMaxOut;
    }

    public void putConstantsOnDashboard(String name, double kP, double kI, double kD, double kIZ, double kFF, double kMinOut, double kMaxOut) {

        this.setP(kP);
        this.setI(kI);
        this.setD(kD);
        this.setIz(kIZ);
        this.setFF(kFF);
        this.setOutputRange(kMinOut, kMaxOut);
        this.name = name;

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber(name + " P Gain", this.kP);
        SmartDashboard.putNumber(name + " I Gain", this.kI);
        SmartDashboard.putNumber(name + " D Gain", this.kD);
        SmartDashboard.putNumber(name + " I Zone", this.kIZ);
        SmartDashboard.putNumber(name + " Feed Forward", this.kFF);
        SmartDashboard.putNumber(name + " Max Output", this.kMaxOut);
        SmartDashboard.putNumber(name + " Min Output", this.kMinOut);

    }

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
        if ((p != this.kP)) {
            this.setP(kP);
        }
        if((i != this.kI)) {
            this.setI(kI);
        }
        if((d != this.kD)) {
            this.setD(kD);
        }
        if((iz != this.kIZ)) {
            this.setIz(kIZ);
        }
        if((ff != this.kFF)) {
            this.setFF(kFF);
        }
        if((max != this.kMaxOut) || (min != this.kMinOut)) { 
            this.setOutputRange(kMinOut, kMaxOut);
        }
    }

    public boolean isLowerLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    public boolean isUpperLimitPressed(boolean normallyOpen) {
        if (normallyOpen) {
            return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
        }
        return m_motor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed).get();
    }

    public void setInitialPosition() {
        this.initialPosition = this.m_encoder.getPosition();
    }

    public double getCurrentPosition() {
        return this.m_encoder.getPosition() - this.initialPosition;
    }

    public void setToPosition(double position) {


        this.m_pidController.setReference(position, ControlType.kPosition);
    }


}