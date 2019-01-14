package frc.singularityDrive;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorSet {

    protected CANSparkMax[] m_sparks;
    protected TalonSRX[] m_talons;
    protected VictorSPX[] m_victors;

    public MotorSet(int[] sparkID, int[] talonID, int[] victorID) {

        m_sparks = new CANSparkMax[sparkID.length];
		m_talons = new TalonSRX[talonID.length];
        m_victors = new VictorSPX[victorID.length];
        
        for(int i = 0; i < sparkID.length; i++) {
			m_sparks[i] = new CANSparkMax(sparkID[i], MotorType.kBrushless);
			if(i >= 1) {
				m_sparks[i].follow(m_sparks[0], false);
			}
        }
        for(int i = 0; i < talonID.length; i++) {
			m_talons[i] = new TalonSRX(talonID[i]);
			if (i >= 1) {
				m_talons[i].follow(m_talons[0]);
			}
        }
        for(int i = 0; i < victorID.length; i++) {
			m_victors[i] = new VictorSPX(victorID[i]);
			if (i >= 1) {
				m_victors[i].follow(m_victors[0]);
			}
		}
		
    }

}