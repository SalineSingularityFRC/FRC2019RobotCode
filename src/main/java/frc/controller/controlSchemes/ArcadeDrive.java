package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.robot.DrivePneumatics;
import frc.robot.HatchMech;
import frc.robot.Intake;
import frc.robot.PneumaticEjector;
import frc.controller.ControlScheme;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;

public class ArcadeDrive implements ControlScheme {

    XboxController controller;

    boolean fastGear;
    boolean buttonDPneuNow, buttonDPneuPrevious;

    boolean hatchMechExtended;
    boolean buttonHatchMechNow, buttonHatchMechPrevious;

    public ArcadeDrive(int controllerPort) {
        controller = new XboxController(controllerPort);

        fastGear = false;
        buttonDPneuPrevious = false;
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

        if (buttonHatchMechNow && !buttonHatchMechPrevious) {
            hatchMechExtended = !hatchMechExtended;
        }

        if (hatchMechExtended) {
            hatchMech.setForward();
        }
        else {
            hatchMech.setReverse();
        }

        buttonHatchMechPrevious = buttonHatchMechNow;

    }

    public void intake(Intake intake) {

    }

}