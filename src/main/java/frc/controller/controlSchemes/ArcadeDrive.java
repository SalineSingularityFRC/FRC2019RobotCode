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
    boolean buttonNow, buttonPrevious;

    public ArcadeDrive(int controllerPort) {
        controller = new XboxController(controllerPort);

        fastGear = false;
        buttonPrevious = false;
    }

    public void drive(SingDrive drive, DrivePneumatics pneumatics) {

        drive.drive(controller.getLS_Y(), 0.0, controller.getLS_X(), true, SpeedMode.FAST);

        buttonNow = controller.getRB();

        if (buttonNow && !buttonPrevious) {
            fastGear = !fastGear;
        }
        
        if (fastGear) {
            pneumatics.setForward();
        }
        else {
            pneumatics.setReverse();
        }

        buttonPrevious = buttonNow;

    }

    public void controlHatchMech(HatchMech hatchMech, PneumaticEjector ejector) {

    }

    public void intake(Intake intake) {

    }

}