package frc.controller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorController {

    public enum ControllerType {
        
        SPARK,
        SPARKBRUSHED,
        VICTOR,
        TALON
    }

    ControllerType currentType;

    CANSparkMax spark;
    VictorSPX victor;
    TalonSRX talon;
    

    public MotorController(ControllerType currentType, int portNumber) {


        this.currentType = currentType;

        switch(currentType) {

            case SPARK:
                spark = new CANSparkMax(portNumber, MotorType.kBrushless);
                break;
            case SPARKBRUSHED:
                spark = new CANSparkMax(portNumber, MotorType.kBrushed);
                break;

            case VICTOR:
                victor = new VictorSPX(portNumber);
                break;
            case TALON:
                talon = new TalonSRX(portNumber);
                break;
        }
    }

    public void setMotorSpeed(double speed) {

        switch(currentType) {

            case SPARK:
                spark.set(speed);
                break;
            case SPARKBRUSHED:
                spark.set(speed);
                break;
                
            case VICTOR:
                victor.set(ControlMode.PercentOutput, speed);
                break;
            case TALON:
                talon.set(ControlMode.PercentOutput, speed);
                break;
            
        }

    }


    
}