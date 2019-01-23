package frc.singularityDrive;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * SingDrive (short for Singularity Drive) is the base class for all drive trains for 5066. It is
 * abstract, meaning that subclasses are forced to extend the following two methods:
 * 
 * public abstract void arcadeDrive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode)
 * public abstract void tankDrive(double left, double right, boolean squaredInputs, SpeedMode speedMode)
 */
public abstract class SingDrive {


	/**
	 * Declare all motor controllers on the drive train for the year here.
	 * 
	 * Depending on the hardware placed on the robot, use:
	 * 
	 * CANSparkMax (imported from com.revrobotics.CANSparkMax)
	 * TalonSRX (imported from com.ctre.phoenix.motorcontrol.can.TalonSRX)
	 * VictorSPX (imported from com.ctre.phoenix.motorcontrol.can.VictorSPX)
	 * 
	 * Or, add any classes that the team ends up adding.
	 * 
	 * WARNING: These objects will need to be changed if the number, type, or orientation of motor controllers changes
	 */
	protected CANSparkMax m_leftMotor1, m_leftMotor2, m_rightMotor1, m_rightMotor2;

	//When using CANSparkMax motor controllers, use the default motor type to either
	//brushless or brushed motors, depending on hardware.
	private final static MotorType DEFAULT_MOTOR_TYPE = MotorType.kBrushless;


	//The following fields change speed relative to input, and are to be set in the constructor
	private double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;

	//default speed constants to be used when not specified by the constructor:
	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.4;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.8;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;

	//All motor control inputs are multiplied by velocityMultiplier, which is set equal to a speedConstant 
	protected double velocityMultiplier;

	/**
	 * enums can be created from SpeedMode to choose different speed controls, which will be used to determine
	 * which speedConstant velocityMultiplier should be set equal to.
	 * 
	 * To declare an enum of type SpeedMode and initialize/modify its value:
	 * 		SpeedMode exampleSpeedMode;
	 * 		exampleSpeedMode = SpeedMode.NORMAL;
	 * To access the value of exampleSpeedMode, for example, in an 'if' statement:
	 * 		if (exampleSpeedMode == SpeedMode.SLOW) {...}
	 */
	public enum SpeedMode {
        FAST,
        NORMAL,
        SLOW
	}


	//MINIMUM_THRESHOLD limits unintended drift from joystick axes. Any joystick input less than MINIMUM_THRESHOLD
	//will be set to 0 using this.threshold(double velocity). Suggested value: 0.07
	private final static double MINIMUM_THRESHOLD = 0.07;

	//RAMP_RATE is used to limit jerks in motor output. Drive Motors starting at 0 output can ramp up to full power
	//in RAMP_RATE seconds. Suggested value: 0.2
	private final static double DEFAULT_RAMP_RATE = 0.2;
	
    
    
	//All subclasses must implement the following drive methods:
	
	public abstract void arcadeDrive(double vertical, double rotation, double horizontal, boolean squaredInputs, SpeedMode speedMode);

	public abstract void tankDrive(double left, double right, double horizontal, boolean squaredInputs, SpeedMode speedMode);


	/**
	 * This is the essential constructor for SingDrive. Its parameters are motor controller ports and the
	 * driving speed constants.
	 * 
	 * The number and position of motor controllers will likely change from year to year, and likely from season to season,
	 * the the first several parameters and corresponding code will need to be edited accordingly.
	 *  
	 * @param leftMotor1 motor controller port # for one motor controller on the left drive train
	 * @param leftMotor2 motor controller port # for one motor controller on the left drive train
	 * @param rightMotor1 motor controller port # for one motor controller on the right drive train
	 * @param rightMotor2 motor controller port # for one motor controller on the right drive train
	 * 
	 * @param slowSpeedConstant suggested values: 0.2 - 0.5
	 * @param normalSpeedConstant suggested values: 0.6 - 1.0
	 * @param fastSpeedConstant suggest value: 1.0
	 * 
	 * WARNING: This method will need to be changed if the number, type, or orientation of motor controllers changes
	 */
	public SingDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2,
	double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {

		m_leftMotor1 = new CANSparkMax(leftMotor1, DEFAULT_MOTOR_TYPE);
		m_leftMotor2 = new CANSparkMax(leftMotor2, DEFAULT_MOTOR_TYPE);
		//setting one motor controller to follow another means that it will automatically set output voltage
		//of the follower controller to the value of the followee motor controller.
		m_leftMotor2.follow(m_leftMotor1);

		m_rightMotor1 = new CANSparkMax(rightMotor1, DEFAULT_MOTOR_TYPE);
		m_rightMotor2 = new CANSparkMax(rightMotor2, DEFAULT_MOTOR_TYPE);
		m_rightMotor2.follow(m_rightMotor1);

		//Set speed constants, and set velocity multiplier to the normalSpeedConstant to begin the match
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
		this.velocityMultiplier = this.normalSpeedConstant;

		//ramp the voltage of the motor output before normal driving (can be changed for auton, special circumstances)
		this.rampDefaultVoltage();
	}


	/**
	 * This is the more basic constructor for SingDrive. Its parameters are only motor controller ports, and they must
	 * correspond to the ports in the above constructor.
	 * 
	 * WARNING: This method will need to be changed if the number, type, or orientation of motor controllers changes
	 */
	public SingDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2) {
		this(leftMotor1, leftMotor2, rightMotor1, rightMotor2, DEFAULT_SLOW_SPEED_CONSTANT, DEFAULT_NORMAL_SPEED_CONSTANT, DEFAULT_FAST_SPEED_CONSTANT);
	}



	/**
	 * @return the velocityMultiplier, used to scale motor speed
	 */
	public double getVelocityMultiplier() {
		return this.velocityMultiplier;
	}

	/**
	 * Set velocityMultiplier, which is used to scale the motor speed, based on speed constants set with the constructor
	 * @param speedMode of an enum of type SpeedMode, set to either SLOW, MEDIUM, or FAST
	 */
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

	/**
	 * Allows other classes to set the velocityMultiplier manually (instead of through the speed constants).
	 * this.clamp (seen just below) is used to ensure a valid multiiplier.
	 * 
	 * @param velocityMultiplier motor speed is always multiplied by velocityMultiplier
	 */
	public void setVelocityMultiplier(double velocityMultiplier) {
		this.velocityMultiplier = this.clamp(velocityMultiplier);
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

	
	/**
	 * Threshold is intended to be used by subclasses to limit the drift on joystick axes
	 * @param joystickInput input any joystick value meant to be used as motor output, before squaring
	 * the input or scaling it with velocityMultiplier
	 * @return an adjusted joystick input
	 */
	public double threshold(double joystickInput) {
		if (Math.abs(joystickInput) <= MINIMUM_THRESHOLD) {
			return 0;
		}
		return joystickInput;
	}


	/**
	 * Used to manually control the rampRate manually. For example, if you are preparing to stop the robot
	 * in autonomous mode, it is recommended you set rampRate to 0.0 to avoid sliding through the intended position.
	 * @param rampRate describes how fast drive motors can ramp from 0 to full power, in seconds
	 * 
	 * WARNING: This method will need to be changed if the number, type, or orientation of motor controllers changes
	 */
	public void rampVoltage(double rampRate) {
		m_leftMotor1.setRampRate(rampRate);
		m_rightMotor1.setRampRate(rampRate);
	}
	/**
	 * Used to return rampRate of motors to the default to avoid wear on motors (recommended for any normal driving).
	 * 
	 * WARNING: This method will need to be changed if the number, type, or orientation of motor controllers changes
	 */
	public void rampDefaultVoltage() {
		m_leftMotor1.setRampRate(DEFAULT_RAMP_RATE);
		m_rightMotor1.setRampRate(DEFAULT_RAMP_RATE);
	}
	
	
}