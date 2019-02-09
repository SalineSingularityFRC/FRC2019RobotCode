package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.robot.DrivePneumatics;
import frc.robot.Claw;
import frc.robot.Intake;
import frc.robot.Vision;
import frc.robot.Wrist;
import frc.robot.Wrist.WristPosition;
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
    boolean clawExtended;
    boolean buttonClawNow, buttonClawPrevious;

    Timer ejectorTimer;
    double ejectorTimerValue;

    boolean ejectorButtonNow, ejectorButtonPrevious;

    
    WristPosition wristPosition;


    double tx, tv;

    //Need to be adjusted for our robot
    final double driveSpeedConstant = 0.3;

    final double txkP = 0.025;
    
    final double angleDifferencekP = 0.012;

    // Constructor for the ArcadeDrive class

    public ArcadeDrive(int controllerPort, int armControllerPort) {
        //Initiates a new Xbox controller
        controller = new XboxController(controllerPort);
        armController = new XboxController(armControllerPort);
        
        // Sets the boolean for fastGear to low (drive train not in fast gear)
        fastGear = false;
        //makes the drive pneumatics only go once instead of many times
        buttonDPneuPrevious = false;
        buttonClawPrevious = false;
        //instanciating the ejectorTimer is a new timer for the pneumatics to pull the  
        //ejectorTimer = new Timer();

        //instanciating the vision object
        vision = new Vision();

        speedMode = SpeedMode.FAST;

        wristPosition = WristPosition.START;
        
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

        wrist.setPositionWithEnum(wristPosition, armController.getRS_Y());


    }


    public void elevator(Elevator elevator) {

    }
    

    public void controlClaw(Claw claw, PneumaticEjector ejector) {

        buttonClawNow = controller.getAButton();
        ejectorButtonNow = controller.getBButton();

        if (buttonClawNow && !buttonClawPrevious) {
            clawExtended = !clawExtended;
        }
/*
        if (clawExtended) {
            claw.setForward();
        }
        else {
            claw.setReverse();
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

        buttonclawPrevious = buttonclawNow;
        ejectorButtonPrevious = ejectorButtonNow;
        */

    }

    public void intake(Intake intake) {

        intake.controlIntake(controller.getPOVDown(), controller.getPOVUp());

    }

  

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
    