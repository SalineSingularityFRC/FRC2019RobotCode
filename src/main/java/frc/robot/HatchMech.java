package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class HatchMech{
  DoubleSolenoid hatchSolenoid;  

    public HatchMech(int forHatch, int revHatch){
        hatchSolenoid = new DoubleSolenoid(forHatch, revHatch);
    }

    public void setForward(){
        hatchSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void setReverse(){
        hatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setOff(){
        hatchSolenoid.set(DoubleSolenoid.Value.kOff);
    }
    
}