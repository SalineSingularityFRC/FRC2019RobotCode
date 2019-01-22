package frc.singularityDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public abstract class SingDrive {


	protected CANSparkMax m_leftMotor1, m_leftMotor2, m_rightMotor1, m_rightMotor2;


	public double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;

	protected double velocityMultiplier;

	private final static double DEFAULT_MINIMUM_THRESHOLD = 0.07;

	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.4;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.8;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;
	
	private final static double RAMP_RATE = 0.2;
	
    
    
    public enum SpeedMode {
        FAST,
        NORMAL,
        SLOW
	}
	
	public abstract void drive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode);
	public abstract void tankDrive(double left, double right, boolean squaredInputs, SpeedMode speedMode);



	public SingDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2,
	double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {

		m_leftMotor1 = new CANSparkMax(leftMotor1, MotorType.kBrushless);
		m_leftMotor2 = new CANSparkMax(leftMotor2, MotorType.kBrushless);
		m_leftMotor2.follow(m_leftMotor1);

		m_rightMotor1 = new CANSparkMax(rightMotor1, MotorType.kBrushless);
		m_rightMotor2 = new CANSparkMax(rightMotor2, MotorType.kBrushless);
		m_rightMotor2.follow(m_rightMotor1);

		this.velocityMultiplier = normalSpeedConstant;
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
	}

	public SingDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2) {
		this(leftMotor1, leftMotor2, rightMotor1, rightMotor2, DEFAULT_SLOW_SPEED_CONSTANT, DEFAULT_NORMAL_SPEED_CONSTANT, DEFAULT_FAST_SPEED_CONSTANT);
	}


	
	private double clamp(double velocityMultiplier) {
		if (velocityMultiplier > 1.0) {
			return 1.0;
		} else if (velocityMultiplier < -1.0) {
			return -1.0;
		} else {
			return velocityMultiplier;
		}
	}

	public void setVelocityMultiplier(double velocityMultiplier) {
		this.velocityMultiplier = this.clamp(velocityMultiplier);
	}

	public double getVelocityMultiplier() {
		return this.velocityMultiplier;
	}


	public double threshold(double velocity) {
		if (Math.abs(velocity) <= DEFAULT_MINIMUM_THRESHOLD) {
			return 0;
		}
		return velocity;
	}

	public void rampVoltage(double rampRate) {
		m_leftMotor1.setRampRate(rampRate);
		m_rightMotor1.setRampRate(rampRate);
	}
	public void rampDefaultVoltage() {
		m_leftMotor1.setRampRate(RAMP_RATE);
		m_rightMotor1.setRampRate(RAMP_RATE);
	}
	
	protected void setVelocityMultiplierBasedOnSpeedMode(SpeedMode speedMode) {
		
		switch(speedMode) {
		case SLOW:
			velocityMultiplier = this.slowSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using slow speed constant");
			break;
		case NORMAL:
			velocityMultiplier = this.normalSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using normal speed constant");
			break;
		case FAST:
			velocityMultiplier = this.fastSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using fast speed constant");
			break;
		}
	}

}