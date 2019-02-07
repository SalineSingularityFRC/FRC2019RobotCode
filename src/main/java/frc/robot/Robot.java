/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.controller.ControlScheme;
import frc.singularityDrive.BasicDrive;
import frc.singularityDrive.SingDrive;
import frc.controller.controlSchemes.*;
import frc.controller.controlSchemes.ArcadeDrive;
import frc.robot.Vision;


import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.*;
import com.revrobotics.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //stores the motor controller IDs
  int driveLeft1, driveLeft2, driveLeft3, driveRight1, driveRight2, driveRight3;
  int drivePneuHigh, drivePneuLow;
  int ejectorPneuPush, ejectorPneuHold;
  int hatchMechDown, hatchMechUp;
  int intakeMotor;
  int elevatorMotor;

  //Declaration of our driving scheme, which can be initialized to
  //any ControlScheme in robotInit()
  ControlScheme currentScheme;

  //Declaration of mechanisms
  SingDrive drive;
  DrivePneumatics drivePneumatics;
  Intake intake;
  Claw hatchMech;
  PneumaticEjector ejectorPneu;
  Vision vision;
  Elevator elevator;

  //Create a gyro
  AHRS gyro;
  boolean gyroResetAtTeleop;


  //default ports of certain joysticks in DriverStation
  final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
    
    
    //initialize motor controller ports IDs
    setDefaultProperties();

    //initialize our driving scheme to a basic arcade drive
    currentScheme = new ArcadeDrive(XBOX_PORT, XBOX_PORT +1);
    
    //initialize mechanisms
    drive = new BasicDrive(driveLeft1, driveLeft2, driveLeft3, driveRight1, driveRight2, driveRight3);
    //drivePneumatics = new DrivePneumatics(0, 1);
    //elevator = new Elevator(elevatorMotor, true);
    /*intake = new Intake(intakeMotor);
    claw = new Claw(hatchMechDown, hatchMechUp);
    ejectorPneu = new PneumaticEjector(ejectorPneuPush, ejectorPneuHold);
    */
    vision = new Vision();
    //DO NOT REMOVE PLZ
    //CameraServer.getInstance().startAutomaticCapture();
    //CameraServer.getInstance().startAutomaticCapture();

    gyro = new AHRS(SPI.Port.kMXP);
    gyroResetAtTeleop = true;
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

    gyro.setAngleAdjustment(-gyro.getAngle());
    gyroResetAtTeleop = false;
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    
    if (gyroResetAtTeleop) {
      gyro.setAngleAdjustment(-gyro.getAngle());
    } 
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    //Allow driver control based on current scheme
    //(we shouldn't need to change this too often)
    currentScheme.drive(drive, drivePneumatics);
    // partial autonomy via vision
    //currentScheme.visionDrive(vision, drive, drivePneumatics, gyro);
    //currentScheme.elevator(elevator);
    
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  
  /**
   * Assigning port numbers to components
   * 
   * To be run at beginning of robotInit 
   * 
   * @author Max P.
   */
  private void setDefaultProperties() {
    
    //Motors
    driveLeft1 = 1;
    driveLeft2 = 2;
    driveLeft3 = 3;
    driveRight1 = 3;
    driveRight2 = 4;
    driveRight3 = 6;
    intakeMotor = 8;
    elevatorMotor = 7;
    
    //Pneumatics
    drivePneuHigh = 1;
    drivePneuLow = 2;
    ejectorPneuPush = 3;
    ejectorPneuHold = 4;
    hatchMechDown = 5;
    hatchMechUp = 6;
    

  }



}





