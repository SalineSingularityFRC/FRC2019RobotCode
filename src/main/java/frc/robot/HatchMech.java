package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.*;
public class HatchMech{
    DoubleSolenoid carterSolenoid;

         public HatchMech(int forHatch, int revHatch){
            carterSolenoid = new DoubleSolenoid(forHatch, revHatch);
        }

        public void setForward() {
            carterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        public void setReverse() {
            carterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }

        public void setOff() {
            carterSolenoid.set(DoubleSolenoid.Value.kOff);
        }
}