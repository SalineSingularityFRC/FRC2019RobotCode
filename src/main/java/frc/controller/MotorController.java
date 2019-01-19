package frc.controller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorController {

    public enum ControllerType {
        VICTOR,
        TALON,
        SPARK
    }

    ControllerType currentType;

    VictorSPX victor;
    TalonSRX talon;
    CANSparkMax spark;

    public MotorController(ControllerType currentType, int portNumber) {


        this.currentType = currentType;

        switch(currentType) {

            case VICTOR:
                victor = new VictorSPX(portNumber);
                break;

            case TALON:
                talon = new TalonSRX(portNumber);
                break;

            case SPARK:
                spark = new CANSparkMax(portNumber, MotorType.kBrushless);
                break;
        }
    }

    public void setMotorSpeed(double speed) {

        switch(currentType) {
            case VICTOR:
                victor.set(ControlMode.PercentOutput, speed);
                break;
            case TALON:
                talon.set(ControlMode.PercentOutput, speed);
                break;
            case SPARK:
                spark.set(speed);
                break;
        }

    }


    
}