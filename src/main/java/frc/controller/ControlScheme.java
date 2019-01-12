package frc.controller;

import frc.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.Timer;

/**
 * This interface forces it's subclasses to have all the
 * necessary methods for controlling the robot. These methods 
 * should be called from teleop periodic in robot.java
 */

public interface ControlScheme {
	
	public void drive(SingDrive drive);
	
}