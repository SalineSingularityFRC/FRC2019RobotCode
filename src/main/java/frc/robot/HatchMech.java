package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
public class HatchMech{
    DoubleSolenoid hatchSolenoid;

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