package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Logger;
import frc.robot.Robot;

public class TeleopDrive extends Command {
	static Logger teleopLogger = new Logger("teleop");
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		teleopLogger.silly("TELEOP DRIVE TIME");
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		Robot.drivetrain.drive();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		teleopLogger.silly("teleop command ending");
	}

	protected void interrupted() {
		teleopLogger.silly("teleop command interrupted");
	}

}
