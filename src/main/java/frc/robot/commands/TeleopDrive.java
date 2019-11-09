package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.ShuffleboardHandler;

public class TeleopDrive extends Command {

//           *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		double moveSpeed,rotateSpeed;
		moveSpeed=OI.getMoveSpeed();
		rotateSpeed=OI.getRotateSpeed();

		ourDrive.setLowWheelNonLinearity(Robot.shuffHandler.getLowWheelNonLinearity());

		Robot.drivetrain.drive(ourDrive.cheesyDrive(moveSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
