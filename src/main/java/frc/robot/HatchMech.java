package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*
The hatch mechanism class allows us to set a double soleniod valve to forward, reverse, and off
Forward pushes air into the piston to extend it
Reverse pushes air out of the piston to contract the piston

Author BranAms
*/
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