    package frc.controller;

    import com.ctre.phoenix.motorcontrol.ControlMode;
    import com.ctre.phoenix.motorcontrol.can.TalonSRX;
    import com.ctre.phoenix.motorcontrol.can.VictorSPX;
    import com.revrobotics.CANEncoder;
    import com.revrobotics.CANPIDController;
    import com.revrobotics.CANSparkMax;

    import edu.wpi.first.wpilibj.DriverStation;
    import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

    public class EncoderController extends MotorController{

        //necessary ancillary objects/constants to work with spark motor controller
        CANEncoder sparkEncoder;
        CANPIDController pidController;
        double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

        public EncoderController(ControllerType currentType, int portNumber){
            super(currentType, portNumber);

            //report warning if trying to use a victor with this class, since it shouldn't be possible
            if(currentType == ControllerType.VICTOR) {
                DriverStation.reportWarning("You tried to use VICTOR with an encoder. :(", true);
            }

            if (currentType == ControllerType.SPARK || currentType == ControllerType.SPARKBRUSHED) {
                
                pidController = super.spark.getPIDController();
                sparkEncoder = super.spark.getEncoder();

                // PID coefficients
                kP = 0.1; 
                kI = 1e-4;
                kD = 1; 
                kIz = 0; 
                kFF = 0; 
                kMaxOutput = 1; 
                kMinOutput = -1;

                // set PID coefficients
                pidController.setP(kP);
                pidController.setI(kI);
                pidController.setD(kD);
                pidController.setIZone(kIz);
                pidController.setFF(kFF);
                pidController.setOutputRange(kMinOutput, kMaxOutput);

                // display PID coefficients on SmartDashboard
                SmartDashboard.putNumber("P Gain", kP);
                SmartDashboard.putNumber("I Gain", kI);
                SmartDashboard.putNumber("D Gain", kD);
                SmartDashboard.putNumber("I Zone", kIz);
                SmartDashboard.putNumber("Feed Forward", kFF);
                SmartDashboard.putNumber("Max Output", kMaxOutput);
                SmartDashboard.putNumber("Min Output", kMinOutput);
                SmartDashboard.putNumber("Set Rotations", 0);
            }

        }

        public void setP(double kP) {
            this.kP = kP;
            pidController.setP(this.kP);
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



        public void resetEncoder(){

            switch(currentType){

                case TALON:
                    talon.getSensorCollection().setQuadraturePosition(0, 10);
                    break;

                //There appears to be no way to reset a Spark Encoder. Nice try
                //case SPARK:
                    
            }

        }

        public void setToPosition(double position) {

            switch(currentType) {

                case TALON:
                    talon.set(ControlMode.Position, position);
                    break;

                case SPARK:
                    
            }

        }

    }