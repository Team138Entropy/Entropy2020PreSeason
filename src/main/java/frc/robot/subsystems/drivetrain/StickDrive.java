package frc.robot.subsystems.drivetrain;

import frc.robot.DriveSignal;
import frc.robot.OI.FlightStick;

public class StickDrive implements DriveEngine {
    private FlightStick leftStick, rightStick;

    public StickDrive(FlightStick leftStick, FlightStick rightStick) {
        this.leftStick = leftStick;
        this.rightStick = rightStick;
    }

    public DriveSignal drive() {
        return new DriveSignal(leftStick.getRawAxis(FlightStick.yAxis), rightStick.getRawAxis(FlightStick.yAxis));
    }
}