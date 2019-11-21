package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.Logger;
import frc.robot.OI;
import frc.robot.Robot;

public class TeleopDrive extends Command {
	static Logger teleopLogger = new Logger("teleop");

//           *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		teleopLogger.silly("TELEOP DRIVE TIME");
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		double moveSpeed,rotateSpeed;
		moveSpeed=OI.getMoveSpeed();
		rotateSpeed=OI.getRotateSpeed();

		teleopLogger.silly("moveSpeed " + moveSpeed + " rotateSpeed " + rotateSpeed + " OI.isQuickTurn() " + OI.isQuickturn() + " OI.isFullSpeed() " + OI.isFullSpeed());

		Robot.drivetrain.drive(ourDrive.cheesyDrive(moveSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()));
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
