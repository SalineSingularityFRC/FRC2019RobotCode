package frc.controller.controlSchemes;

import frc.controller.XboxController;
import frc.controller.ControlScheme;
import frc.singularityDrive.SingDrive;
import frc.singularityDrive.SingDrive.SpeedMode;

public class ArcadeDrive implements ControlScheme {

    XboxController controller;

    public ArcadeDrive(int controllerPort) {
        controller = new XboxController(controllerPort);
    }

    public void drive(SingDrive drive) {

        drive.drive(controller.getLS_Y(), 0.0, controller.getLS_X(), true, SpeedMode.FAST);

    }

}