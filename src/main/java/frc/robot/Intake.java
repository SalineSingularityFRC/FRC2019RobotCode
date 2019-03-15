package frc.robot;


import frc.controller.MotorController;
import frc.controller.motorControllers.Spark;


public class Intake {
    
    
    MotorController intake;
    


    private final double intakeSpeed = -0.5;
    private final double intakeReverseSpeed = 1.0;
    private final double constantSpeed = -0.1;

    public static boolean haveBall;

    public Intake(int rotatePort) {
        intake = new Spark(rotatePort, true, 0.00);
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
            intake.setSpeed(constantSpeed);
        }
    }

    public void intakeOn() {
        intake.setSpeed(intakeSpeed);
    }

    public void intakeConstantSpeed() {
        intake.setSpeed(constantSpeed);
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