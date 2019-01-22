package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Elevator {

    CANSparkMax m_motor;
    CANEncoder m_encoder;

    CANPIDController m_pid;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public Elevator(int motorPort, boolean brushlessMotor) {
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
        SmartDashboard.putNumber("Elevator P Gain", kP);
        SmartDashboard.putNumber("Elevator I Gain", kI);
        SmartDashboard.putNumber("Elevator D Gain", kD);
        SmartDashboard.putNumber("Elevator I Zone", kIz);
        SmartDashboard.putNumber("Elevator Feed Forward", kFF);
        SmartDashboard.putNumber("Elevator Max Output", kMaxOutput);
        SmartDashboard.putNumber("Elevator Min Output", kMinOutput);
        //SmartDashboard.putNumber("Elevator Set Rotations", 0);
    }


    public void setElevatorPosition(double position) {

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("Elevator P Gain", 0);
        double i = SmartDashboard.getNumber("Elevator I Gain", 0);
        double d = SmartDashboard.getNumber("Elevator D Gain", 0);
        double iz = SmartDashboard.getNumber("Elevator I Zone", 0);
        double ff = SmartDashboard.getNumber("Elevator Feed Forward", 0);
        double max = SmartDashboard.getNumber("Elevator Max Output", 0);
        double min = SmartDashboard.getNumber("Elevator Min Output", 0);
        //double rotations = SmartDashboard.getNumber("Elevator Set Rotations", 0);

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

    public void elevatorTesting(double speed) {
        m_motor.set(speed);
        SmartDashboard.putNumber("Elevator Encoder Position", m_encoder.getPosition());
    }

}