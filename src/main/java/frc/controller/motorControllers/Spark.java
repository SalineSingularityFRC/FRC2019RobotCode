package frc.controller.motorControllers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

        this.m_encoder = m_motor.getEncoder();
        this.m_pidController = m_motor.getPIDController();
        
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

    public CANEncoder getEncoder() {
        return this.m_encoder;
    }

    /*
    public void setP(double kP) {
        this.m_pidController.setP(kP);
        super.recordP(kP);
    }

    public void setI(double kI) {
        this.m_pidController.setI(kI);
        super.recordI(kI);
    }

    public void setD(double kD) {
        this.m_pidController.setD(kD);
        super.recordD(kD);
    }
*/

}