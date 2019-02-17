/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import frc.controller.*;
import frc.singularityDrive.*;
import frc.singularityDrive.SingDrive;
import frc.controller.controlSchemes.ArcadeDrive;
import frc.controller.controlSchemes.Test;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
  int drivePneu1, drivePneu2;

  //int ejectorPneuPush, ejectorPneuHold;
  //int hatchMechDown, hatchMechUp;

  int intakeMotor;
  int elevatorMotor;
  int wristMotor;
  int clawLeftMotor, clawRightMotor;
  

  //Declaration of our driving scheme, which can be initialized to
  //any ControlScheme in robotInit()
  ControlScheme currentScheme;

  //Declaration of mechanisms
  SingDrive drive;
  DrivePneumatics drivePneumatics;

  Intake intake;
  //PneumaticEjector ejectorPneu;
  Elevator elevator;
  Wrist wrist;
  Claw claw;

  Vision vision;

  //Create a gyro
  AHRS gyro;
  boolean gyroResetAtTeleop;

  //create ultrasonics
  Ultrasonic ultra;
  final int ultraInput = 1;
  final int ultraOutput = 2;

  Compressor compressor;

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
    currentScheme = new Test(XBOX_PORT, XBOX_PORT +1);
    
    //initialize mechanisms
    drive = new BasicDrive(driveLeft1, driveLeft2, driveLeft3, driveRight1, driveRight2, driveRight3);
    drivePneumatics = new DrivePneumatics(drivePneu1, drivePneu2);

    //intake = new Intake(intakeMotor);
    //claw = new Claw(clawMotor1);

    //elevator = new Elevator(elevatorMotor, true);
    //wrist = new Wrist(wristMotor, true);
    
    //ejectorPneu = new PneumaticEjector(ejectorPneuPush, ejectorPneuHold);
    
    //vision = new Vision();
    //DO NOT REMOVE PLZ
    //CameraServer.getInstance().startAutomaticCapture();
    //CameraServer.getInstance().startAutomaticCapture();

    gyro = new AHRS(SPI.Port.kMXP);
    gyroResetAtTeleop = true;
    
    
    //ultra = new Ultrasonic(ultraInput, ultraOutput);
    //ultra.setAutomaticMode(true);
    
    
    compressor = new Compressor();
    

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

    //compressor.start();

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

    // Allow driver control based on current scheme
    // (we shouldn't need to change this too often- other than commenting)
    currentScheme.drive(drive, drivePneumatics);
    // partial autonomy via vision
    //currentScheme.visionDrive(vision, drive, drivePneumatics, gyro, ultra);
    //currentScheme.elevator(elevator);
    //currentScheme.wrist(wrist);
    //currentScheme.controlClaw(claw);
    compressor.start();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

    compressor.start();
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
    driveRight1 = 4;
    driveRight2 = 5;
    driveRight3 = 6;
    elevatorMotor = 7;
    wristMotor = 8;
    intakeMotor = 9;
    clawLeftMotor = 10; //don't known this yet
    clawRightMotor = 11;

    //Pneumatics
    
    drivePneu1 = 1;
    drivePneu2 = 2;
    //ejectorPneuPush = 3;
    //ejectorPneuHold = 4;
    //hatchMechDown = 5;
    //hatchMechUp = 6;*/

    

  }



}





