package frc.controller;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.DrivePneumatics;
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
	 * @param gyroAngle
	 * @return
	 */
	public double getOffsetHatchAngle(double gyroAngle) {
/* 
		used wrong input angles

		if (gyroAngle >= 16.25 && gyroAngle <= 90) {
			return 61.25;
		}
			
		else if (gyroAngle >= 90 && gyroAngle <= 180) {
			return 118.75;
		}

		else if (gyroAngle >= 180 && gyroAngle <= 270) {
			return 241.25;
		}

		else if (gyroAngle >= 270 && gyroAngle <= 343.75) {
			return 298.75 
		}
		
		return 180; */
		return 1;
	}
	
}