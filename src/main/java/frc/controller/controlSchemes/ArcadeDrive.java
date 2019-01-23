package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.robot.DrivePneumatics;
import frc.robot.HatchMech;
import frc.robot.Intake;
import frc.robot.Vision;
import frc.robot.Elevator;
import frc.robot.PneumaticEjector;
import frc.controller.ControlScheme;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;

import java.util.Map;

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

    //Need to be adjusted for our robot
    double Kp = -0.1;
    double min_command = 0.05;
    double driveSpeedConstant = 0.2;

    final int elevatorLow = 20;
    final int elevatorMid = 50;
    final int elevatorHigh = 100;
    final double endPosition = 10.0;
    double tx, ta, ty;
    final double driveSpeed = 0.3;
    final double tuningConstant = 50;

    Vision vision;

    

    public ArcadeDrive(int controllerPort, int armControllerPort) {
        controller = new XboxController(controllerPort);
        armController = new XboxController(armControllerPort);

        fastGear = false;
        buttonDPneuPrevious = false;
        ejectorTimer = new Timer();

        vision = new Vision();
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        drive.arcadeDrive(controller.getLS_Y(), controller.getLS_X(), 0.0, true, SpeedMode.FAST);

        buttonDPneuNow = controller.getRB();

        if (buttonDPneuNow && !buttonDPneuPrevious) {
            fastGear = !fastGear;
        }
        
        if (fastGear) {
            pneumatics.setHigh();
        }
        else {
            pneumatics.setLow();
        }

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


        ///*
        if(controller.getXButton() && vision.table.getEntry("tv").getDouble(0.0) == 1.0) {

            //dPneumatics.setLow();
            //hatchMech.setForward();

            


            while(!controller.getYButton() && vision.table.getEntry("ta").getDouble(0.0) < endPosition) {
                this.ta = vision.table.getEntry("ta").getDouble(0.0);
                this.tx = vision.table.getEntry("tx").getDouble(0.0);
                this.ty = vision.table.getEntry("ty").getDouble(0.0);

                double heading_error =( this.tx * -1 );
                double steering_adjust = 0.0;

                double left_comand = driveSpeedConstant;
                double right_comand = driveSpeedConstant;

                if(tx > 1.0){
                    steering_adjust = Kp*heading_error - min_command;
                }
                else if(tx < 1.0){
                    steering_adjust = Kp*heading_error + min_command;
                }
                left_comand -= steering_adjust;
                right_comand += steering_adjust;

                drive.tankDrive(left_comand, right_comand, 0.0, false, SpeedMode.FAST);

              //  drive.drive(driveSpeed, 0, tx/tuningConstant, false, SpeedMode.FAST);
            } 
            //8ejector.setForward();
            ejectorTimer.reset();
            ejectorTimer.start();
        //*/
            


        } 
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

    }
    //TODO: finish this method
/*     

    private double clip(double input, double inputMin, double inputMax, double outputMin, double outputMax) {
        input = input/(inputMin+inputMax);
        
    }
*/
