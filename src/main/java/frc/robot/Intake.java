package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.controller.MotorController;
import frc.controller.MotorController.ControllerType;


public class Intake {
    MotorController intake;
    


    private final double intakeSpeed = 0.5;
    private final double intakeReverseSpeed = -0.5;
    private final double intakeOffSpeed = 0;

    public Intake(int rotatePort) {
        intake = new MotorController(ControllerType.SPARK, rotatePort);
    }

    public void controlIntake(boolean intakeOn, boolean intakeOff, boolean intakeReverse) {

        if(intakeOn) {
            intake.setMotorSpeed(intakeSpeed);
        }

        else if(intakeOff) {
            intake.setMotorSpeed(intakeOffSpeed);
        }

        else if(intakeReverse) {
            intake.setMotorSpeed(intakeReverseSpeed);
        }
    }

    public void intakeSpeed(int intakeSpeedInput) {
        intake.setMotorSpeed(intakeSpeedInput);
    }

    //public void intakeOff(boolean intakeOff) {

    //}





}