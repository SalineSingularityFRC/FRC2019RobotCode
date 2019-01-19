package frc.controller;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;

public class EncoderController extends MotorController{

    public EncoderController(ControllerType currentType, int portNumber){
        super(currentType, portNumber);
    }


}