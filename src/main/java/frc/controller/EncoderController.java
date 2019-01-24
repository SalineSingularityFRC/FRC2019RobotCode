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

        String name;

        public abstract void setP(double kP);
        public void recordP(double kP) {
            this.kP = kP;
            SmartDashboard.putNumber(name + " P Gain", this.kP);
        }

        public abstract void setI(double kI);
        public void recordI(double kI) {
            this.kI = kI;
            SmartDashboard.putNumber(name + " I Gain", this.kI);
        }

        public abstract void setD(double kD);
        public void recordD(double kD) {
            this.kD = kD;
            SmartDashboard.putNumber(name + " D Gain", this.kD);
        }

        public abstract void setIz(double kIz);
        public void recordIz(double kIz) {
            this.kIz = kIz;
            SmartDashboard.putNumber(name + " I Zone", this.kIz);
        }

        public abstract void setFF(double kFF);
        public void recordFF(double kFF) {
            this.kFF = kFF;
            SmartDashboard.putNumber(name + " Feed Forward", this.kFF);
        }


        

    }