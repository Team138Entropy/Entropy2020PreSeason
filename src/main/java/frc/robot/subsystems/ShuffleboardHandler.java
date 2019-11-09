package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;

public class ShuffleboardHandler extends Subsystem {

    private NetworkTableEntry rollerSpeedDouble;
    private NetworkTableEntry pistonRotateEnabled;

    public void initDefaultCommand() {

    }

    public void init() {
        pistonRotateEnabled = Robot.main.add("Piston Rotate Enabled", true).getEntry();
    }

    public void execute() {
        
    }
}