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

    XboxController controller;
    XboxController armController;
    Vision vision;

    Timer ejectorTimer;
    double ejectorTimerValue;

    boolean fastGear;
    boolean buttonDPneuNow, buttonDPneuPrevious;

    boolean hatchMechExtended;
    boolean buttonHatchMechNow, buttonHatchMechPrevious;

    boolean ejectorButtonNow, ejectorButtonPrevious;

    boolean elevatorButton1Now, elevatorButton1Previous;
    boolean elevatorButton2Now, elevatorButton2Previous;
    boolean elevatorButton3Now, elevatorButton3Previous;

    boolean wristButton1Now, wristButton1Previous;
    boolean wristButton2Now, wristButton2Previous;

    double joyY, joyX;
    

    //Need to be adjusted for our robot
    final double txkP = 0.025;
    double txAdjust;
    final double driveSpeedConstant = 0.3;
    final double txPower = 1;

    final double angleDifferencekP = 0.012;
    final double angleDifferencePower = 1;

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

    final double endPosition = 10.0;
    double tx, ta, ty, tv;
    final double driveSpeed = 0.3;
    final double tuningConstant = 50;

    SpeedMode speedMode;

 

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
        ejectorTimer = new Timer();

        //instanciating the vision object
        vision = new Vision();

        speedMode = SpeedMode.FAST;

        
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        joyY = controller.getRS_Y();
        joyX = controller.getRS_X();

        if(controller.getPOVDown()){
            speedMode = SpeedMode.SLOW;

        }
        else if(controller.getPOVUp()){
            speedMode = SpeedMode.FAST;
        }
        drive.arcadeDrive(joyY, joyX, 0.0, false, speedMode);







        
        SmartDashboard.putNumber("left joystick", joyY);
        SmartDashboard.putNumber("right joystick", joyX);

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

    
    public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, AHRS gyro) {

        ta = vision.ta.getDouble(0.0);
        tx = vision.tx.getDouble(0.0);
        ty = vision.ty.getDouble(0.0);
        tv = vision.tv.getDouble(0.0);
 

        boolean squareButton = controller.getXButton();
        boolean offSetButton = controller.getYButton();

        SmartDashboard.putNumber("tx", tx);
        SmartDashboard.putNumber("ty", ty);
        SmartDashboard.putNumber("ta", ta);
        SmartDashboard.putNumber("tv", tv);

        SmartDashboard.putBoolean("Square button", squareButton);
        SmartDashboard.putBoolean("Offset button", offSetButton);
        

        
        double currentAngle = super.smooshGyroAngle(gyro.getAngle());
        SmartDashboard.putNumber("current angle:", currentAngle);

        SmartDashboard.putBoolean("A button", controller.getAButton());
        if(controller.getAButton()) {
            gyro.setAngleAdjustment(0);
            gyro.setAngleAdjustment(-super.smooshGyroAngle(gyro.getAngle()));
            
        }
        

        if((squareButton == true || offSetButton == true) && tv == 1.0) {

            double left_command = driveSpeedConstant;
            double right_command = driveSpeedConstant;

            double steering_adjust = 0.0;
            
            txAdjust = txkP * Math.pow(Math.abs(tx), txPower);
            if (tx < 0) {
                txAdjust *= -1;
            }

            
            double targetAngle;

            if(squareButton) {
                targetAngle = super.getSquareAngleForPort(currentAngle);
            }

            else {
                targetAngle = super.getOffsetHatchAngle(currentAngle);
            }


            double angleDifference = currentAngle - targetAngle;
            double secondAngleDifference = targetAngle - 360 + currentAngle;

            if (Math.abs(secondAngleDifference) < Math.abs(angleDifference)) {
                angleDifference = secondAngleDifference;
            }
            

            //To remove gyro control, comment out this line:
            steering_adjust += txAdjust;
            steering_adjust += angleDifferencekP * angleDifference;
            

            left_command += steering_adjust;
            right_command -= steering_adjust;

                

            drive.tankDrive(left_command, right_command, 0.0, false, SpeedMode.FAST);
            
            SmartDashboard.putNumber("Left_command", left_command);
            SmartDashboard.putNumber("Right_command", right_command);
        } // end of X button and target
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
}
    



    //TODO: finish this method
/*     

    private double clip(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        input = input/(inputMin+inputMax);
        
    }
*/
