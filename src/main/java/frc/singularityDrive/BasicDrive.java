package frc.singularityDrive;

import com.ctre.phoenix.*;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BasicDrive extends SingDrive{
	
	/**
	 * Essential constructor for BasicDrive that takes in ID arrays for all motor types and positions.
	 * @param leftSparkID
	 * @param rightSparkID
	 * @param leftTalonID
	 * @param rightTalonID
	 * @param leftVictorID
	 * @param rightVictorID
	 * @param slowSpeedConstant multiplier for motor output at preferred slow speed
	 * @param normalSpeedConstant multiplier for preferred normal speed
	 * @param fastSpeedConstant multiplier for preffered fast speed
	 * 
	 * @author Cameron Tressler, 1/13/18
	 */
	public BasicDrive(int[] leftSparkID, int[] rightSparkID, int[] leftTalonID, int[] rightTalonID,
	int[] leftVictorID, int[] rightVictorID,
	double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {

		super(leftSparkID, rightSparkID, new int[0], leftTalonID, rightTalonID, new int[0], leftVictorID, rightVictorID, new int[0],
		slowSpeedConstant, normalSpeedConstant, fastSpeedConstant);
		
	}

	/**
	 * Simplified constructor for BasicDrive that only takes in motor controller IDs for REV robotics.
	 * 
	 * @author Cameron Tressler, 1/13/18
	 */
	public BasicDrive(int[] leftSparkID, int[] rightSparkID,
	double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {
		
		super(leftSparkID, rightSparkID, new int[0], new int[0], new int[0], new int[0], new int[0], new int[0], new int[0],
		slowSpeedConstant, normalSpeedConstant, fastSpeedConstant);

	}

	public void arcadeDrive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode) {

		double translationVelocity = vertical, rotationVelocity = rotation;
		
		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(vertical);
			rotationVelocity *= Math.abs(rotation);
		}

		// Guard against illegal values
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));


		m_leftMotor1.set((translationVelocity + rotationVelocity) / maximum);
		m_leftMotor2.set((translationVelocity + rotationVelocity) / maximum);
		m_rightMotor1.set((-translationVelocity + rotationVelocity) / maximum);
		m_rightMotor2.set((-translationVelocity + rotationVelocity) / maximum);

	}

	public void tankDrive(double left, double right, boolean squaredInputs, SpeedMode speedMode) {

	}
	
	
	//TODO gradual acceleration
/*
	public void drive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		double translationVelocity = vertical, rotationVelocity = rotation;
		
		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(vertical);
			rotationVelocity *= Math.abs(rotation);
		}
		
		// Do reverse drive when necessary. There are methods above for different inputs.
		/*if (reverse) {
			translationVelocity = -translationVelocity;
			rotationVelocity = -rotationVelocity;
		}
		
		// Guard against illegal values
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors
		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		
		SmartDashboard.putNumber("rightEncoder", m_rightMiddleMotor.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("leftEncoder", m_leftMiddleMotor.getSensorCollection().getQuadraturePosition());
	}
	
	/**
	 * 
	 * 6 wheel tank drive
	 * @param right
	 * @param squaredInputs
	 * @param speedMode
	 
	public void tankDrive(double left, double right, boolean squaredInputs, SpeedMode speedMode) {
		double leftVelocity = left, rightVelocity = right;
		
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			leftVelocity *= Math.abs(left);
			rightVelocity *= Math.abs(right);
		}
		
		
		// Guard against illegal values
		double leftMaximum = Math.max(1, Math.abs(leftVelocity));
		double rightMaximum = Math.max(1, Math.abs(rightVelocity));

		if (velocityReduceActivated) {
			leftMaximum *= 1 / reducedVelocity;
			rightMaximum *= 1 / reducedVelocity;
		}

		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);

		// Set the motors
		/*
	    m_leftMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((leftVelocity) / leftMaximum));
		m_rightMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((rightVelocity) / rightMaximum));
	
		m_rearLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((leftVelocity) / leftMaximum));
		m_rearRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * -((rightVelocity) / rightMaximum));

		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((leftVelocity) / leftMaximum));
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * -((rightVelocity) / rightMaximum));
		*/
		//SmartDashboard.putNumber("rightEncoder", m_rightMiddleMotor.getSensorCollection().getQuadraturePosition());
		//SmartDashboard.putNumber("leftEncoder", m_leftMiddleMotor.getSensorCollection().getQuadraturePosition());
	//}
}