package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.DriveSignal;
import frc.robot.OI.FlightStick;
import frc.robot.Logger;

public class StickDrive implements DriveEngine {
    private Joystick leftStick, rightStick;

    public StickDrive(Joystick leftStick, Joystick rightStick) {
        this.leftStick = leftStick;
        this.rightStick = rightStick;

        Logger logger = new Logger("StickDrive");
        logger.info("Using StickDrive");
    }

    public DriveSignal drive() {
        return new DriveSignal(
            scaleInput(leftStick.getRawAxis(FlightStick.yAxis)),
            scaleInput(rightStick.getRawAxis(FlightStick.yAxis))
        );
    }

    private static double scaleInput(double input) {
        return (2 * Math.asin(input)) / (Math.PI);
    }
}