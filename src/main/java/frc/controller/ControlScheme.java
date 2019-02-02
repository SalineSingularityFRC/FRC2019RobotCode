package frc.controller;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.DrivePneumatics;
import frc.robot.Elevator;
import frc.robot.HatchMech;
import frc.robot.Intake;
import frc.robot.PneumaticEjector;
import frc.singularityDrive.SingDrive;
import frc.robot.Vision;

/**
 * This interface forces it's subclasses to have all the
 * necessary methods for controlling the robot. These methods 
 * should be called from teleop periodic in robot.java
 */

public abstract class ControlScheme {
	
	public abstract void drive(SingDrive drive, DrivePneumatics pneumatics);
	public abstract void controlHatchMech(HatchMech hatchMech, PneumaticEjector ejector);
	public abstract void intake(Intake intake);
	public abstract void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, AHRS gyro);
	public abstract void elevator(Elevator elevator);
	
	/**
	 * 
	 * @param gyroAngle any gyro value
	 * @return the coterminal angle between 0 and 360.
	 */
	public double smooshGyroAngle(double gyroAngle) {

		if (gyroAngle <= 360 && gyroAngle >= 0) {
			return gyroAngle;
		}

		gyroAngle /= 360;
		gyroAngle -= (int)gyroAngle;
		gyroAngle *= 360;

		if (gyroAngle < 0) {
			gyroAngle += 360;
		}

		return gyroAngle;

	}

	/**
	 * 
	 * @param gyroAngle the current angle our robot is at, between 0 and 360 degrees
	 * @return the alignment angle that we want to drive towards, 0, 90, 270, or 180
	 */
	public double getSquareAngleForPort(double gyroAngle) {

		if (gyroAngle >= 45 && gyroAngle <= 135) {
			return 90;
		}
			
		else if (gyroAngle >= 225 && gyroAngle <= 315) {
			return 270;
		}

		else if (gyroAngle > 315 || gyroAngle < 45) {
			return 0;
		}
		
		return 180;
	}

	/**
	 * 
	 * @param gyroAngle the current angle our robot is at, between 0 and 360 degrees
	 * @return the alignment angle that we want to drive towards, 28.75, 151.25, 208.75, or 331.25
	 */
	public double getOffsetHatchAngle(double gyroAngle) {
 
		

		if (gyroAngle >= 0 && gyroAngle <= 73.75) {
			return 28.75;
		}
			
		else if (gyroAngle >= 106.25 && gyroAngle <= 180) {
			return 151.25;
		}

		else if (gyroAngle >= 180 && gyroAngle <= 253.75) {
			return 208.75;
		}

		else if (gyroAngle >= 286.25 && gyroAngle <= 360) {
			return 331.25; 
		}
		
		return gyroAngle;
		
	}

	
	
}