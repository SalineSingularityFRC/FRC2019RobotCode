package frc.controller.controlSchemes;

import frc.controller.*;
import frc.robot.Claw;
import frc.robot.DrivePneumatics;
import frc.robot.Elevator;
import frc.robot.Intake;
import frc.robot.Vision;
import frc.robot.Wrist;
import frc.robot.Elevator.ElevatorPosition;
import frc.robot.Wrist.WristPosition;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;
import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.*;
import com.kauailabs.navx.frc.AHRS;

/**
 * 
 * Main class to control the robot
 * 
 */
public class ArcadeDrive extends ControlScheme {

    // make new XBox driveController objects
    XboxController driveController;
    XboxController armController;

    boolean lowGear;
    SpeedMode speedMode;

    boolean usingVision;

    //Hatch Variables
    //final int grabClawAngle = 120;
    //final int releaseClawAngle = 60;
    final double clawSpeed = 0.5;
    
    WristPosition wristPosition;

    ElevatorPosition elevatorPosition;

    double tx, tv;
    double ultraIn;


    //Need to be adjusted for our robot
    final double driveSpeedConstant = 0.3;
    final double txkP = 0.025;
    final double angleDifferencekP = 0.012;
    final double endDistance = 2.0;

    // Constructor for the ArcadeDrive class

    public ArcadeDrive(int driveControllerPort, int armControllerPort) {
        //Initiates a new Xbox driveController
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);

        lowGear = true;
        speedMode = SpeedMode.SLOW;

        usingVision = false;

        wristPosition = WristPosition.START;
        
    }

    /**
     * Drives arcade drive
     * 
     */
    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        //Set speed mode based on the dpad on the driveController
        if(driveController.getLB()){
            speedMode = SpeedMode.SLOW;

        }
        else if(driveController.getRB()){
            speedMode = SpeedMode.FAST;
        }
        SmartDashboard.putString("Speed Mode", ""+ speedMode);

        //driving arcade drive based on right joystick on driveController
        //changed boolean poweredInputs from false to true, change back if robot encounters issues
        //ADDED USINGVISION, SO IF THINGS ARE ACTING WEIRD COME BACK TO THIS
        if(driveController.getPOVLeft()) {
            drive.arcadeDrive(0, -0.1, 0.0, false, SpeedMode.FAST);
        }

        else if (driveController.getPOVUp()) {
            drive.arcadeDrive(0.1, 0, 0.0, false, SpeedMode.FAST);
        }

        else if (driveController.getPOVRight()) {
            drive.arcadeDrive(0.0, 0.1, 0.0, false, SpeedMode.FAST);
        }
        
        else if (driveController.getPOVDown()) {
            drive.arcadeDrive(-0.1, 0.0, 0.0, false, SpeedMode.FAST);
        }

        else if (!usingVision) {
            drive.arcadeDrive(driveController.getLS_Y(), driveController.getRS_X(), 0.0, true, speedMode);
        }

        if(driveController.getBackButton()) {
           lowGear = true;
        }
        else if(driveController.getStartButton()) {
            lowGear = false;
        }

        if(lowGear) {
            pneumatics.setLow();
        }
        else {
            pneumatics.setHigh();
        }
    }

    
    public void wrist(Wrist wrist) {

        if(armController.getAButton()){
            wristPosition = WristPosition.START;
        }
        else if (armController.getBButton()){
            wristPosition = WristPosition.CARGO;
        }
        else if (armController.getYButton()){
            wristPosition = WristPosition.HATCH;
        }
        else if (armController.getXButton()){
            wristPosition = WristPosition.INTAKE;
        }
        SmartDashboard.putString("Wrist Intended Position", "" + wristPosition);
        //wrist.setPositionWithEnum(wristPosition, armController.getRS_Y());

        wrist.driveWithFF(0.2 * armController.getRS_Y());

    }



    public void elevator(Elevator elevator) {

        if(wristPosition == WristPosition.HATCH){
            if (armController.getPOVDown()){
                elevatorPosition = ElevatorPosition.HATCH1;
            }
            else if(armController.getPOVLeft()){
                elevatorPosition = ElevatorPosition.HATCH2;
            }
            else if(armController.getPOVUp()){
                elevatorPosition = ElevatorPosition.HATCH3;
            }
        }

        else if(wristPosition == WristPosition.CARGO){
            if (armController.getPOVDown()){
                elevatorPosition = ElevatorPosition.CARGO1;
            }
            else if(armController.getPOVLeft()){
                elevatorPosition = ElevatorPosition.CARGO2;
            }
            else if(armController.getPOVUp()){
                elevatorPosition = ElevatorPosition.CARGO3;
            }
            else if(armController.getPOVRight()){
                elevatorPosition = ElevatorPosition.CARGOSHIP;
            }

        }

        else if(wristPosition == WristPosition.INTAKE){
            elevatorPosition = ElevatorPosition.BOTTOM;
        }

        //WristPosition must be set to Start
        else {
            elevatorPosition = ElevatorPosition.BOTTOM; 
        }

        SmartDashboard.putString("elevator intended position", "" + elevatorPosition);

        double multiplier = 0.4;
        if (armController.getLS_Y() < -0.04) {
            multiplier *= 0.4;
        }

        ///elevator.setPositionWithEnum(elevatorPosition, 0.7 * armController.getLS_Y());\
        elevator.setSpeed(multiplier * armController.getLS_Y() - 0.025);
    }
    

    public void controlClaw(Claw claw) {
        
        
        if(armController.getRB()){

            claw.rightControlClawMotor(clawSpeed);
        }
        else if(armController.getLB()){
            
            claw.leftControlClawMotor(-clawSpeed);
        }
        else if(armController.getTriggerRight() > .5){
            claw.rightControlClawMotor(-clawSpeed);

        }
        else if(armController.getTriggerLeft() > .5){
            claw.leftControlClawMotor(clawSpeed);
        }

        else {
            claw.leftControlClawMotor(0.0);
            claw.rightControlClawMotor(0.0);
        }

        //claw.controlClawMotor(armController.getTriggerRight() - armController.getTriggerLeft());
        
        
    }

    public void intake(Intake intake) {

        if(driveController.getTriggerRight() >= .4) {
            intake.intakeOn();
        }

        else if((driveController.getTriggerLeft() >= .4)) {
            intake.intakeReverse();
        }
        else {
            intake.intakeConstantSpeed();
        }
    }

    /**
     * Method to drive autonomously using limelight and gyro
     * 
     */
    public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, AHRS gyro, Ultrasonic ultra) {
        // Defining tx and tv
        // tx = X coordinate between -27 and 27
        // tv = 0 if no target found, 1 is target found
        tx = vision.tx.getDouble(0.0);
        tv = vision.tv.getDouble(0.0);

        //SmartDashboard.putNumber("Inches", ultraIn);

        // Declaring and instantiating buttons used for enabling vision drive
        boolean squareButton = driveController.getXButton();
        boolean offSetButton = driveController.getYButton();
    
        
        // Defining and Declaring currentAngle as angle from gyro, between 0 and 360 degrees
        double currentAngle = super.smooshGyroAngle(gyro.getAngle());
        SmartDashboard.putNumber("current angle:", currentAngle);

        // Resets gyro value to 0
        if (driveController.getAButton()) {
            gyro.setAngleAdjustment(0);
            gyro.setAngleAdjustment(-super.smooshGyroAngle(gyro.getAngle()));
            
        }

        
        //Starts driving based on vision if the button is pushed and we have a target
        if ((squareButton == true || offSetButton == true) && tv == 1.0/* && ultraIn > endDistance*/) {
            
            //Declaring the left and right command speeds and setting it equal to the driveSpeedConstant
            double left_command = driveSpeedConstant;
            double right_command = driveSpeedConstant;

            //Declares and instaniates steering_adjust, and sets it to txkP * tx
            double steering_adjust = 0.0;
            steering_adjust += txkP * tx;

            // Declare and adjust targetAngle based on currentAngle
            double targetAngle;
            if(squareButton) {
                targetAngle = super.getSquareAngleForPort(currentAngle);
            }
            else {
                targetAngle = super.getOffsetHatchAngle(currentAngle);
            }

            //Declaring the offset angle for turning
            double angleDifference = currentAngle - targetAngle;
            //This is an alternative angleDifference
            double secondAngleDifference = targetAngle - 360 + currentAngle; 
            //This is where we define which one we want to use. 
            //Takes which ever one is closer to 0
            if (Math.abs(secondAngleDifference) < Math.abs(angleDifference)) {
                angleDifference = secondAngleDifference;
            }
            //To remove gyro control, comment out this line:
            steering_adjust += angleDifferencekP * angleDifference;
            
            // Setting vision drive for tank drive
            left_command += steering_adjust;
            right_command -= steering_adjust;
            drive.tankDrive(left_command, right_command, 0.0, false, SpeedMode.FAST);
            

            usingVision = true;
        } // end of if statement

        else {
            usingVision = false;
        }
    }
    public void ledMode( Vision vision ){
        if(driveController.getXButton() || driveController.getYButton()){
            vision.ledMode.setDouble(3.0);
        }
        else{
            vision.ledMode.setDouble(1.0);
        }
    }

}
    