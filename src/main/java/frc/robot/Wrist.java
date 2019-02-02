package frc.robot;

import frc.controller.motorControllers.Spark;

public class Wrist {

    Spark m_motor;

    public final double kP = 0.1, kI = 1e-4, kD = 0.1, kIZ = 0, kFF = 0, kMaxOut = 1, kMinOut = -1;
    public final double rampRate = 0.2;

    public Wrist(int motorPort, boolean brushlessMotor) {
        m_motor = new Spark(motorPort, brushlessMotor, this.rampRate, "Wrist", kP, kI, kD, kIZ, kFF, kMinOut, kMaxOut);
    }

    public void setPosition(double position, double joystickControl) {
        m_motor.setToPosition(joystickControl, position);
    }

    public void setSpeed(double speed) {
        m_motor.setSpeed(speed);
        m_motor.printEncoderPosition();
    }

}