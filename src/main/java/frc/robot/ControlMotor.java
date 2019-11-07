package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class ControlMotor extends Command {

    public ControlMotor(float speed) {
        Robot.rotatorTalon.set(ControlMode.PercentOutput, speed);
    }


    public boolean isFinished() {
        return true;
    }
}