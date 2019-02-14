package frc.controller.controlSchemes;

import frc.controller.*;
import frc.robot.*;
import frc.singularityDrive.*;

import edu.wpi.first.wpilibj.Ultrasonic;
import com.kauailabs.navx.frc.AHRS;

public class Test extends ControlScheme{
    XboxController driveController;
    XboxController armController;

    final int grabClawAngle = 120;
    final int releaseClawAngle = 60;

    public Test(int driveControllerPort, int armControllerPort){
        driveController = new XboxController(driveControllerPort);
        armController = new XboxController(armControllerPort);
    }
    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

    }
	public void controlClaw(Claw claw, PneumaticEjector ejector) {
        if(driveController.getRB()){
            claw.controlServo(grabClawAngle);
        }
        else if(driveController.getLB()){
            claw.controlServo(releaseClawAngle);
        }
        
    }
	public void intake(Intake intake){

    }
	public void visionDrive(Vision vision, SingDrive drive, DrivePneumatics dPneumatics, AHRS gyro, Ultrasonic ultra){

    }
    public void elevator(Elevator elevator){
        elevator.setSpeed(armController.getRS_Y());
    }
    public void wrist(Wrist wrist){
        wrist.setSpeed(armController.getLS_Y());
    }
}