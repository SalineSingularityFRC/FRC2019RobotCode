    package frc.controller;

    import com.ctre.phoenix.motorcontrol.ControlMode;
    import com.ctre.phoenix.motorcontrol.can.TalonSRX;
    import com.ctre.phoenix.motorcontrol.can.VictorSPX;
    import com.revrobotics.CANEncoder;
    import com.revrobotics.CANPIDController;
    import com.revrobotics.CANSparkMax;

    import edu.wpi.first.wpilibj.DriverStation;
    import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

    public abstract class EncoderController implements MotorController {

        
        double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;


        public void recordP(double kP) {
            this.kP = kP;
            SmartDashboard.putNumber("P Gain", this.kP);
        }

        public void setI(double kI) {
            this.kI = kI;
            pidController.setI(this.kI);
            SmartDashboard.putNumber("I Gain", this.kI);
        }

        public void setD(double kD) {
            this.kD = kD;
            pidController.setD(this.kD);
            SmartDashboard.putNumber("D Gain", this.kD);
        }




        

    }