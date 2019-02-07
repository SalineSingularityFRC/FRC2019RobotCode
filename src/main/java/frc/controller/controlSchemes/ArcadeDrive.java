package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.robot.DrivePneumatics;
import frc.robot.HatchMech;
import frc.robot.Intake;
import frc.robot.Vision;
import frc.robot.Wrist;
import frc.robot.Elevator;
import frc.robot.PneumaticEjector;
import frc.controller.ControlScheme;
import frc.singularityDrive.BasicDrive;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;
import edu.wpi.first.wpilibj.smartdashboard.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
//import java.util.Map;
import edu.wpi.first.wpilibj.Timer;
/**
 * 
 * Main class to control the robot
 * 
 */
public class ArcadeDrive extends ControlScheme {

    // make new XBox controller objects
    XboxController controller;
    XboxController armController;

    Vision vision;

    //Drive Variales
    boolean fastGear;
    boolean buttonDPneuNow, buttonDPneuPrevious;

    SpeedMode speedMode;

    //Hatch Variables
    boolean hatchMechExtended;
    boolean buttonHatchMechNow, buttonHatchMechPrevious;

    Timer ejectorTimer;
    double ejectorTimerValue;

    boolean ejectorButtonNow, ejectorButtonPrevious;

    boolean elevatorButton1Now, elevatorButton1Previous;
    boolean elevatorButton2Now, elevatorButton2Previous;
    boolean elevatorButton3Now, elevatorButton3Previous;

    boolean wristButton1Now, wristButton1Previous;
    boolean wristButton2Now, wristButton2Previous;


    //Positions for elevator encoder, these are just placeholder values, need to test with robot
    final int elevatorLow = 20;
    final int elevatorMid = 50;
    final int elevatorHigh = 100;

    //Elevator speeed constants for testing
    final double elevatorForwardSpeed = 0.1;
    final double elevatorReverseSpeed = -0.1;

    final int wristStartinPos = 0;
    final int wristHatchPos = 90;
    final int wristCargoPos = 180;

    double tx, tv;

    //Need to be adjusted for our robot
    final double driveSpeedConstant = 0.3;

    final double txkP = 0.025;
    
    final double angleDifferencekP = 0.012;

    // Constructor for the ArcadeDrive class

    public ArcadeDrive(int controllerPort, int armControllerPort) {
        //Initiates a new Xbox controller
        controller = new XboxController(controllerPort);
        //armController = new XboxController(armControllerPort);
        
        // Sets the boolean for fastGear to low (drive train not in fast gear)
        fastGear = false;
        //makes the drive pneumatics only go once instead of many times
        buttonDPneuPrevious = false;
        //instanciating the ejectorTimer is a new timer for the pneumatics to pull the  
        //ejectorTimer = new Timer();

        //instanciating the vision object
        vision = new Vision();

        speedMode = SpeedMode.FAST;

        
    }

    /**
     * Drives arcade drive
     * 
     */
    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        //Set speed mode based on the dpad on the controller
        if(controller.getPOVDown()){
            speedMode = SpeedMode.SLOW;
        }
        else if(controller.getPOVUp()){
            speedMode = SpeedMode.FAST;
        }

        //driving arcade drive based on right joystick on controller
        //changed boolean poweredInputs from false to true, change back if robot encounters issues
        drive.arcadeDrive(controller.getRS_Y(), controller.getRS_X(), 0.0, true, speedMode);

/*
        buttonDPneuNow = controller.getRB();

        if (buttonDPneuNow && !buttonDPneuPrevious) {
            fastGear = !fastGear;
        }
  // 20190126 PNEUMATICS NOT ON ROBOT AT THIS TIME       
  //      if (fastGear) {
  //          pneumatics.setHigh();
  //      }
  //      else {
  //          pneumatics.setLow();
  //      }

        buttonDPneuPrevious = buttonDPneuNow;
    */
    }
    

    public void controlHatchMech(HatchMech hatchMech, PneumaticEjector ejector) {

        buttonHatchMechNow = controller.getAButton();
        ejectorButtonNow = controller.getBButton();

        if (buttonHatchMechNow && !buttonHatchMechPrevious) {
            hatchMechExtended = !hatchMechExtended;
        }

        if (hatchMechExtended) {
            hatchMech.setForward();
        }
        else {
            hatchMech.setReverse();
        }

        if (ejectorButtonNow && !ejectorButtonPrevious) {
            ejector.setForward();
            ejectorTimer.reset();
            ejectorTimer.start();           
        }

        ejectorTimerValue = ejectorTimer.get();

        if(ejectorTimerValue > 1) {
            ejectorTimer.stop();
            ejector.setReverse();
        }

        buttonHatchMechPrevious = buttonHatchMechNow;
        ejectorButtonPrevious = ejectorButtonNow;
        

    }

    public void intake(Intake intake) {

        intake.controlIntake(controller.getPOVDown(), controller.getPOVUp());

    }

    public void elevator(Elevator elevator) {

        //Elevator code set to three poitions with encoders, not using right now
        /*
        elevatorButton1Now = armController.getAButton();
        if(elevatorButton1Now && !elevatorButton1Previous) {
            elevator.setPosition(elevatorLow);
        }

        elevatorButton2Now = armController.getBButton();
        if(elevatorButton2Now && !elevatorButton2Previous) {
            elevator.setPosition(elevatorMid);
        }

        elevatorButton3Now = armController.getXButton();
        if(elevatorButton3Now && !elevatorButton3Previous) {
            elevator.setPosition(elevatorHigh);
        }

        elevatorButton1Previous = elevatorButton1Now;
        elevatorButton2Previous = elevatorButton2Now;
        elevatorButton3Previous = elevatorButton3Now;
        */

        //Test code to move elevator motor with d-pad
        if(controller.getPOVUp()) {
            elevator.setSpeed(this.elevatorForwardSpeed);
        }

        else if(controller.getPOVDown()) {
            elevator.setSpeed(this.elevatorReverseSpeed);
        }

        SmartDashboard.putBoolean("D-Pad Up", controller.getPOVUp());
        SmartDashboard.putBoolean("D-Pad Down", controller.getPOVDown());
        }
/*
    public void wrist(Wrist wrist) {

        wristButton1Now = armController.getPOVDown();
        if(wristButton1Now && !wristButton1Previous)  {
            wrist.setWristPosition(wristCargoPos);
        }
        
        wristButton2Now = armController.getPOVUp();
        if(wristButton2Now && !wristButton2Previous) {
            wrist.setWristPosition(wristHatchPos);
        }

        wristButton1Previous = wristButton1Now;
        wristButton2Previous = wristButton2Now;



    }
*/


    /**
     * Method to drive autonomously using limelight and gyro
     * 
     */
    public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, AHRS gyro) {

        // Defining tx and tv
        // tx = X coordinate between -27 and 27
        // tv = 0 if no target found, 1 is target found
        tx = vision.tx.getDouble(0.0);
        tv = vision.tv.getDouble(0.0);

        // Pastes values into smart dashboard
        SmartDashboard.putNumber("tx", tx);
        SmartDashboard.putNumber("tv", tv);

        // Declaring and instantiating buttons used for enabling vision drive
        boolean squareButton = controller.getXButton();
        boolean offSetButton = controller.getYButton();

        
        // Defining and Declaring currentAngle as angle from gyro, between 0 and 360 degrees
        double currentAngle = super.smooshGyroAngle(gyro.getAngle());
        SmartDashboard.putNumber("current angle:", currentAngle);

        // Resets gyro value to 0
        if(controller.getAButton()) {
            gyro.setAngleAdjustment(0);
            gyro.setAngleAdjustment(-super.smooshGyroAngle(gyro.getAngle()));
            
        }

        
        //Starts driving based on vision if the button is pushed and we have a target
        if((squareButton == true || offSetButton == true) && tv == 1.0) {
            
            //Declaring the left and right command speeds and setting it equal to the driveSpeedConstant
            double left_command = driveSpeedConstant;
            double right_command = driveSpeedConstant;

            //Declares and instaniates steering_adjust, and sets it to txkP * tx
            double steering_adjust = 0.0;
            steering_adjust += txkP * tx;

            // Declare and adjust tarteAngle based on currentAngle
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
            
            SmartDashboard.putNumber("Left_command", left_command);
            SmartDashboard.putNumber("Right_command", right_command);
        } // end of if statement
    }

}
    