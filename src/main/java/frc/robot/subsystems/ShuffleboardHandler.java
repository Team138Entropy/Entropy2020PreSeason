package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;

public class ShuffleboardHandler extends Subsystem {

    //private NetworkTableEntry highWheelNonLinearity; unused because of no FREAKING TRANSMISSION
    private NetworkTableEntry lowWheelNonLinearity;

    public void initDefaultCommand() {

    }

    public void init() {
        lowWheelNonLinearity = Robot.main.add("Wheel Non Linearity Factor", Constants.LowWheelNonLinearity).getEntry();
    }

    public void execute() {
        
    }

    public double getLowWheelNonLinearity() {
        return lowWheelNonLinearity.getDouble(Constants.LowWheelNonLinearity);
    }
}