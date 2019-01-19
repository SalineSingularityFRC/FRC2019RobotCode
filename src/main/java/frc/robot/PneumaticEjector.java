package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PneumaticEjector {

DoubleSolenoid ejector;
    
    public PneumaticEjector(int forwardChannel, int reverseChannel) {
        ejector = new DoubleSolenoid(forwardChannel, reverseChannel);
        }

    public void setForward() {
            ejector.set(DoubleSolenoid.Value.kForward);
        }
    public void setReverse() {
            ejector.set(DoubleSolenoid.Value.kReverse);
        }
    public void setOff() {
            ejector.set(DoubleSolenoid.Value.kOff);
    }



}








