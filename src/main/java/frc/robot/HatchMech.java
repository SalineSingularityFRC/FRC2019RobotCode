package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class HatchMech{
    DoubleSolonoid swatterSolenoid;

    public HatchMech(int forwardDrive, reverseDrive){
        swatterSolenoid = new DoubleSolenoid(forwardDrive, reverseDrive);
    } 

    public void setOut(){
        swatterSolenoid.set(DoubleSolonoid.Value.kforward);
    }

    public void setIn(){
        swatterSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setOff(){
        swatterSolenoid.set(DoubleSolenoid.Value.kOff);
    }
}


