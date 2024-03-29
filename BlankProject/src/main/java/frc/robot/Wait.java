package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wait extends Command {
	
	private double commandTimeoutSeconds = 0;
	private double _currentCommandTime = 0;
	
	public Wait(double seconds)
	{
		commandTimeoutSeconds = seconds;
	}
	

	protected void initialize() {
		
		_currentCommandTime = 0;
		}

	protected void execute() {
		_currentCommandTime += 0.02;
		SmartDashboard.putNumber("Wait Timer", _currentCommandTime);
	}

	protected boolean isFinished() {
		if (_currentCommandTime >= commandTimeoutSeconds)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	protected void end() {
	
	}

	protected void interrupted() {

	}

}