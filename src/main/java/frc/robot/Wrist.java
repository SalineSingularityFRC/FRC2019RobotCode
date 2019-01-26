package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Wrist {
    CANSparkMax m_motor;
    CANEncoder m_encoder;

    CANPIDController m_pid;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public Wrist(int motorPort, boolean brushlessMotor) {
        if (brushlessMotor) {
            m_motor = new CANSparkMax(motorPort, MotorType.kBrushless);
        }
        else {
            m_motor = new CANSparkMax(motorPort, MotorType.kBrushed);
        }

        m_pid = m_motor.getPIDController();
        m_encoder = m_motor.getEncoder();

        // PID coefficients
        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        m_pid.setD(kD);
        m_pid.setI(kI);
        m_pid.setP(kP);
        m_pid.setIZone(kIz);
        m_pid.setFF(kFF);
        m_pid.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("Wrist P Gain", kP);
        SmartDashboard.putNumber("Wrist I Gain", kI);
        SmartDashboard.putNumber("Wrist D Gain", kD);
        SmartDashboard.putNumber("Wrist I Zone", kIz);
        SmartDashboard.putNumber("Wrist Feed Forward", kFF);
        SmartDashboard.putNumber("Wrist Max Output", kMaxOutput);
        SmartDashboard.putNumber("Wrist Min Output", kMinOutput);
        //SmartDashboard.putNumber("Wrist Set Rotations", 0);
    }


    public void setWristPosition(double position) {

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("Wrist P Gain", 0);
        double i = SmartDashboard.getNumber("Wrist I Gain", 0);
        double d = SmartDashboard.getNumber("Wrist D Gain", 0);
        double iz = SmartDashboard.getNumber("Wrist I Zone", 0);
        double ff = SmartDashboard.getNumber("Wrist Feed Forward", 0);
        double max = SmartDashboard.getNumber("Wrist Max Output", 0);
        double min = SmartDashboard.getNumber("Wrist Min Output", 0);
        //double rotations = SmartDashboard.getNumber("Wrist Set Rotations", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if ((p != kP)) {
            m_pid.setP(p);
            kP = p;
        }
        if((i != kI)) {
            m_pid.setI(i);
            kI = i;
        }
        if((d != kD)) {
            m_pid.setD(d);
            kD = d;
        }
        if((iz != kIz)) {
            m_pid.setIZone(iz);
            kIz = iz;
        }
        if((ff != kFF)) {
            m_pid.setFF(ff);
            kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_pid.setOutputRange(min, max); 
            kMinOutput = min;
            kMaxOutput = max; 
        }

        m_pid.setReference(position, ControlType.kPosition);

        SmartDashboard.putNumber("SetPoint", position);
        SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
    }

    public void WristTesting(double speed) {
        m_motor.set(speed);
        SmartDashboard.putNumber("Wrist Encoder Position", m_encoder.getPosition());
    }
}