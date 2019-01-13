package frc.singularityDrive;

import com.ctre.phoenix.*;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BasicDrive extends SingDrive{
	
	
	public BasicDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2) {
		super(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
		
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