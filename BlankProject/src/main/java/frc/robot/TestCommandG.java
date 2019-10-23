package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.ControlMotor;

public class TestCommandG extends CommandGroup {

    public TestCommandG() {
        System.out.println("bruh mode");
        addSequential(new ControlMotor(1f));
        addSequential(new Wait(1f));
        addSequential(new ControlMotor(0f));
        System.out.println("end of bruh mode");
    }

}