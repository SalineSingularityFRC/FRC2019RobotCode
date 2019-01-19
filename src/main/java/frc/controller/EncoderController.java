package frc.controller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DriverStation;

public class EncoderController extends MotorController{

    public EncoderController(ControllerType currentType, int portNumber){
        super(currentType, portNumber);
        if(currentType == ControllerType.VICTOR) {
            DriverStation.reportWarning("You tried to use VICTOR with an encoder. :(", true);
        }

    }

    public void resetEncoder(){

        switch(currentType){

            case TALON:
                talon.getSensorCollection().setQuadraturePosition(0, 10);
                break;

           // case SPARK:
                




        }

    }

}