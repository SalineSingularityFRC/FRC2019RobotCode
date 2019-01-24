package frc.controller;

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

public interface ControlScheme {
	
	public void drive(SingDrive drive, DrivePneumatics pneumatics);
	public void controlHatchMech(HatchMech hatchMech, PneumaticEjector ejector);
	public void intake(Intake intake);
	public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, PneumaticEjector ejector, HatchMech hatchMech);
	
}