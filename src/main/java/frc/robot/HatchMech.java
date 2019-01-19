package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.*;


public class HatchMech
 {
  
    DoubleSolenoid hatchSolenoid;

  public HatchMech(int forHatch, int revHatch)
   {
      hatchSolenoid = new DoubleSolenoid(forHatch, revHatch);
    
    public void SetForward()
     {
        hatchSolenoid.set(DoubleSolenoid.Value.kforward);
     }
      
    public void SetReverse()
     {
        hatchSolenoid.set(DoubleSolenoid.Value.kforward);
     }

    public void setOff()
     {
        hatchSolenoid.set(DoubleSolenoid.Value.kforward);
     }


   }

 }