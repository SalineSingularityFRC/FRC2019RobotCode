package frc.robot;


// The HatchMech class is a basic class to open/close a double solenoid. Being used to push hatch panels on our robot.
//Written by Carter on 1/14/18
import edu.wpi.first.wpilibj.DoubleSolenoid;
public class HatchMech{
    DoubleSolenoid carterSolenoid;

         public HatchMech(int forHatch, int revHatch){
            carterSolenoid = new DoubleSolenoid(forHatch, revHatch);
        }

        //move double solenoid forward
        public void setForward() {
            carterSolenoid.set(DoubleSolenoid.Value.kForward);
        }

        //move double solenoid reverse
        public void setReverse() {
            carterSolenoid.set(DoubleSolenoid.Value.kReverse);
        }

        //turn of double solenoid
        public void setOff() {
            carterSolenoid.set(DoubleSolenoid.Value.kOff);
        }
}