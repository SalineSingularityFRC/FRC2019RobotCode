package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.controller.MotorController;
import frc.controller.MotorController.ControllerType;


public class Intake {
    MotorController intake;
    


    private final double intakeSpeed = 0.5;
    private final double intakeReverseSpeed = -1.0;
    private final double intakeOffSpeed = 0;

    public Intake(int rotatePort) {
        intake = new MotorController(ControllerType.SPARK, rotatePort);
    }

    public void controlIntake(boolean intakeOn, boolean intakeReverse) {

        if(intakeOn) {
            intake.setMotorSpeed(intakeSpeed);
        }
        else if(intakeReverse) {
            intake.setMotorSpeed(intakeReverseSpeed);
        }
        else {
            intake.setMotorSpeed(intakeOffSpeed);
        }
    }

    public void intakeOn() {
        intake.setMotorSpeed(intakeSpeed);
    }

    public void intakeOff() {
        intake.setMotorSpeed(intakeOffSpeed);
    }

    public void intakeReverse() {
        intake.setMotorSpeed(intakeReverseSpeed);
    }

    public void intakeSpeed(double intakeSpeedInput) {
        intake.setMotorSpeed(intakeSpeedInput);
    }

    //public void intakeOff(boolean intakeOff) {

    //}





}