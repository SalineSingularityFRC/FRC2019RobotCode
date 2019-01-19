package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
public class HatchMech{
    DoubleSolenoid hatchSolenoid;
/*
the hatch mechanism class allows us to set up a double solenoid valve forward to reverse and off
forward pushes air into piston to extend it
reverse pushes air out of the piston to contract the piston


written by Hayden Clarke
*/
    public Double HatchMech(int forwardgo, int revgo){
        hatchSolenoid = new DoubleSolenoid(forwardgo, revgo);

        public void forgohatch(){
            hatchSolenoid.set(DoubleSolenoid.Value.kfoward)
        }
    }

    public void setReverse(){
        hatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
public void setoff(){
    hatchSolenoid.set(DoubleSolenoid.Value.kOff);
}

}