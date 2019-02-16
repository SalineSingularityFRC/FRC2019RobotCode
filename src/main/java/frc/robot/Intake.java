package frc.robot;


import frc.controller.MotorController;
import frc.controller.motorControllers.Spark;
import frc.singularityDrive.SingDrive;


public class Intake {
    
    
    MotorController intake;
    


    private final double intakeSpeed = 0.5;
    private final double intakeReverseSpeed = -1.0;
    private final double intakeOffSpeed = 0;

    public static boolean haveBall;

    public Intake(int rotatePort) {
        intake = new Spark(rotatePort, false, SingDrive.DEFAULT_RAMP_RATE);
        intake.setCoastMode(false);
    }

    public void controlIntake(boolean intakeOn, boolean intakeReverse) {

        if(intakeOn) {
            intake.setSpeed(intakeSpeed);
        }
        else if(intakeReverse) {
            intake.setSpeed(intakeReverseSpeed);
        }
        else {
            intake.setSpeed(intakeOffSpeed);
        }
    }

    public void intakeOn() {
        intake.setSpeed(intakeSpeed);
    }

    public void intakeOff() {
        intake.setSpeed(intakeOffSpeed);
    }

    public void intakeReverse() {
        intake.setSpeed(intakeReverseSpeed);
    }

    public void intakeSpeed(double intakeSpeedInput) {
        intake.setSpeed(intakeSpeedInput);
    }

    //public void intakeOff(boolean intakeOff) {

    //}





}