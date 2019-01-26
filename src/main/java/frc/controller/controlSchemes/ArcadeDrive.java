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

//import java.util.Map;

import edu.wpi.first.wpilibj.Timer;

public class ArcadeDrive implements ControlScheme {

    XboxController controller;
    XboxController armController;

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

    double leftJoyY, rightJoyX;
    

    //Need to be adjusted for our robot
    double Kp = 0.02;
    double min_command = 0.02;
    double driveSpeedConstant = 0.2;

    //Positions for elevator encoder, these are just placeholder values, need to test with robot
    final int elevatorLow = 20;
    final int elevatorMid = 50;
    final int elevatorHigh = 100;

    final int wristStartinPos = 0;
    final int wristHatchPos = 90;
    final int wristCargoPos = 180;

    final double endPosition = 10.0;
    double tx, ta, ty, tv;
    final double driveSpeed = 0.3;
    final double tuningConstant = 50;

    Vision vision;

    

    public ArcadeDrive(int controllerPort, int armControllerPort) {
        controller = new XboxController(controllerPort);
        //armController = new XboxController(armControllerPort);

        fastGear = false;
        buttonDPneuPrevious = false;
        ejectorTimer = new Timer();

        vision = new Vision();
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        leftJoyY = controller.getLS_Y();
        rightJoyX = controller.getRS_Y();
        drive.tankDrive(leftJoyY, rightJoyX, 0.0, false, SpeedMode.FAST);

        SmartDashboard.putNumber("left joystick", leftJoyY);
        SmartDashboard.putNumber("right joystick", rightJoyX);

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

    
    public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, PneumaticEjector ejector, HatchMech hatchMech) {
        double left_command = driveSpeedConstant;
        double right_command = driveSpeedConstant;

        ///*
        this.ta = vision.table.getEntry("ta").getDouble(0.0);
        this.tx = vision.table.getEntry("tx").getDouble(0.0);
        this.ty = vision.table.getEntry("ty").getDouble(0.0);
        this.tv = vision.table.getEntry("tv").getDouble(0.0);


        SmartDashboard.putNumber("tx", tx);
        SmartDashboard.putNumber("ty", ty);
        SmartDashboard.putNumber("ta", ta);
        SmartDashboard.putNumber("tv", tv);
        


        if(controller.getXButton() && vision.table.getEntry("tv").getDouble(0.0) == 1.0) {

            //dPneumatics.setLow();
            //hatchMech.setForward();
            //while(!controller.getYButton() && vision.table.getEntry("ta").getDouble(0.0) < endPosition) {
                

                double heading_error =( this.tx);
                double steering_adjust = 0.0;

                if(tx > 1.0){
                    steering_adjust = Kp * (this.tx) - min_command;
                }
                else if(tx < 1.0){
                    steering_adjust = Kp * (this.tx) + min_command;
                }
                left_command += steering_adjust;
                right_command -= steering_adjust;

                

                drive.tankDrive(left_command, right_command, 0.0, false, SpeedMode.FAST);

              //  drive.drive(driveSpeed, 0, tx/tuningConstant, false, SpeedMode.FAST);
            //} 
            //8ejector.setForward();
            ejectorTimer.reset();
            ejectorTimer.start();
        //*/
        } // end of X button and target
        

        SmartDashboard.putNumber("Left_command", left_command);
        SmartDashboard.putNumber("Right_command", right_command);

        

    }

    public void elevator(Elevator elevator) {

        elevatorButton1Now = armController.getAButton();
        if(elevatorButton1Now && !elevatorButton1Previous) {
            elevator.setElevatorPosition(elevatorLow);
        }

        elevatorButton2Now = armController.getBButton();
        if(elevatorButton2Now && !elevatorButton2Previous) {
            elevator.setElevatorPosition(elevatorMid);
        }

        elevatorButton3Now = armController.getXButton();
        if(elevatorButton3Now && !elevatorButton3Previous) {
            elevator.setElevatorPosition(elevatorHigh);
        }

        elevatorButton1Previous = elevatorButton1Now;
        elevatorButton2Previous = elevatorButton2Now;
        elevatorButton3Previous = elevatorButton3Now;

        }

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

    }
    



    //TODO: finish this method
/*     

    private double clip(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        input = input/(inputMin+inputMax);
        
    }
*/
