package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.robot.DrivePneumatics;
import frc.robot.HatchMech;
import frc.robot.Intake;
import frc.robot.PneumaticEjector;
import frc.controller.ControlScheme;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;
import edu.wpi.first.wpilibj.Timer;

public class ArcadeDrive implements ControlScheme {

    XboxController controller;

    Timer ejectorTimer;
    double ejectorTimerValue;

    boolean fastGear;
    boolean buttonDPneuNow, buttonDPneuPrevious;

    boolean hatchMechExtended;
    boolean buttonHatchMechNow, buttonHatchMechPrevious;

    boolean ejectorButtonNow, ejectorButtonPrevious;

    public ArcadeDrive(int controllerPort) {
        controller = new XboxController(controllerPort);

        fastGear = false;
        buttonDPneuPrevious = false;
        ejectorTimer = new Timer();
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        drive.drive(controller.getLS_Y(), 0.0, controller.getLS_X(), true, SpeedMode.FAST);

        buttonDPneuNow = controller.getRB();

        if (buttonDPneuNow && !buttonDPneuPrevious) {
            fastGear = !fastGear;
        }
        
        if (fastGear) {
            pneumatics.setForward();
        }
        else {
            pneumatics.setReverse();
        }

        buttonDPneuPrevious = buttonDPneuNow;

    }

    public void controlHatchMech(HatchMech hatchMech, PneumaticEjector ejector) {

        buttonHatchMechNow = controller.getAButton();
        ejectorButtonNow = controller.getBButton();

        if (buttonHatchMechNow && !buttonHatchMechPrevious) {
            hatchMechExtended = !hatchMechExtended;
        }

        if (hatchMechExtended) {
            hatchMech.setForward();
        }
        else {
            hatchMech.setReverse();
        }

        if (ejectorButtonNow && !ejectorButtonPrevious) {
            ejector.setForward();
            ejectorTimer.reset();
            ejectorTimer.start();           
        }

        ejectorTimerValue = ejectorTimer.get();

        if(ejectorTimerValue > 5) {
            ejectorTimer.stop();
            ejector.setReverse();
        }

        buttonHatchMechPrevious = buttonHatchMechNow;
        ejectorButtonPrevious = ejectorButtonNow;
        




    }

    public void intake(Intake intake) {

    }

}