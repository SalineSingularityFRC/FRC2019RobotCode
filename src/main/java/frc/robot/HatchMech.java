package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The hatch mechanism class allows us to set up a double soleoid valve to forward, reverse, and off.
 * setOut pushes air into the bottom of the piston to extend it 
 * setIn pushes air ino the other side of the piston to contract it
 * setOff doesn't set air into any part of the piston
 * 
 * @author Travis Crigger
 */
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


