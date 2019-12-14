package org.usfirst.frc.team138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team138.robot.commands.TeleopDrive;
import org.usfirst.frc.team138.robot.Utility;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team138.robot.RobotMap;
import org.usfirst.frc.team138.robot.Constants;
import org.usfirst.frc.team138.robot.Robot;


public class Drivetrain extends Subsystem{
	public double lastSpeed=0;
	double _speedFactor = 1;
	double _rotateFactor = 1;

	// Servo Loop Gains
	double Drive_Kf = 1.7;
	double Drive_Kp = 5;
	double Drive_Ki = 0.02; //
	double Drive_Kd = 30;

	// Filter state for joystick movements
	double _lastMoveSpeed = 0;
	double lastRotateSpeed=0;

	int counter=0;

	public WPI_TalonSRX frontLeftTalon = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_FRONT);
	WPI_TalonSRX backLeftTalon = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_BACK);
	public WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_FRONT);
	WPI_TalonSRX backRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_BACK);

	protected void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}


	public void DriveTrainInit()
	{
		/* choose the sensor and sensor direction */
		frontLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontLeftTalon.setSensorPhase(true);
		frontLeftTalon.setInverted(true);
		backLeftTalon.setInverted(true);
		/* set the peak and nominal outputs, 12V means full */
		frontLeftTalon.configNominalOutputForward(0.,0);
		frontLeftTalon.configNominalOutputReverse(0.,0);
		frontLeftTalon.configPeakOutputForward(1,0);
		frontLeftTalon.configPeakOutputReverse(-1,0);
		frontLeftTalon.setNeutralMode(NeutralMode.Coast);
		backLeftTalon.setNeutralMode(NeutralMode.Coast);

		/* choose the sensor and sensor direction */
		frontRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontRightTalon.setSensorPhase(true);
		frontRightTalon.configNominalOutputForward(0.,0);
		frontRightTalon.configNominalOutputReverse(-0.,0);
		frontRightTalon.configPeakOutputForward(1,0);
		frontRightTalon.configPeakOutputReverse(-1,0);
		frontRightTalon.setNeutralMode(NeutralMode.Coast);
		backRightTalon.setNeutralMode(NeutralMode.Coast);

		// Configure Talon gains
		frontLeftTalon.config_kF(0, Drive_Kf,0);
		frontLeftTalon.config_kP(0, Drive_Kp,0);
		frontLeftTalon.config_kI(0, Drive_Ki,0);
		frontLeftTalon.config_kD(0, Drive_Kd,0);
		frontRightTalon.config_kF(0, Drive_Kf,0);
		frontRightTalon.config_kP(0, Drive_Kp,0);
		frontRightTalon.config_kI(0, Drive_Ki,0);
		frontRightTalon.config_kD(0, Drive_Kd,0);

		// Configure slave Talons to follow masters
		backLeftTalon.follow(frontLeftTalon);
		backRightTalon.follow(frontRightTalon);
	}

	public double limitDriveAccel(double moveSpeed)
	{
		// Limit rate of change of move and rotate in order to control acceleration
		lastSpeed = Utility.limitValue(moveSpeed, lastSpeed - Constants.MaxSpeedChange,
				lastSpeed + Constants.MaxSpeedChange);
		return lastSpeed;
	}

	public double limitRotateAccel(double rotateSpeed)
	{
		// Limit rate of change of move and rotate in order to control acceleration
		lastRotateSpeed = Utility.limitValue(rotateSpeed, lastRotateSpeed - Constants.MaxRotateSpeedChange,
				lastRotateSpeed + Constants.MaxRotateSpeedChange);
		return lastRotateSpeed;
	}

	public void drive(double moveSpeed, double rotateSpeed)
	{
		if (Constants.useClosedLoopDrivetrain)
		{
			Robot.drivetrain.driveCloseLoopControl(moveSpeed, rotateSpeed);

		}
		else
		{
			Robot.drivetrain.driveWithTable(moveSpeed, rotateSpeed);
		}	
	}

	public void driveCloseLoopControl(double moveSpeed, double rotateSpeed)
	{
		/*
		 * moveSpeed and rotateSpeed in Meters/second.
		 */
		double left  = 0;
		double right = 0;
		/*
		 * Robot motors move opposite to joystick and autonomous directions
		 */
		moveSpeed=-moveSpeed;
		rotateSpeed=-rotateSpeed;
		// Case where commands are exactly NULL
		if (moveSpeed==0 && rotateSpeed==0)
		{
			Relax();
		}
		else {
			left = moveSpeed - rotateSpeed; 
			right = moveSpeed + rotateSpeed;

			// Convert Meters / seconds to Encoder Counts per 100 milliseconds
			frontLeftTalon.set(ControlMode.Velocity, left * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
			frontRightTalon.set(ControlMode.Velocity, right * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
		}


		SmartDashboard.putNumber("L PWM", -frontLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -frontRightTalon.getMotorOutputPercent());

		SmartDashboard.putNumber("L Talon Vel (M/S)", -frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
		SmartDashboard.putNumber("R Talon Vel (M/S)", -frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);

	}

	public void Relax(){
		frontLeftTalon.set(ControlMode.PercentOutput, 0);
		frontRightTalon.set(ControlMode.PercentOutput, 0);
		SmartDashboard.putNumber("L PWM", -frontLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -frontRightTalon.getMotorOutputPercent());
	}


	public void driveWithTable(double moveSpeed, double rotateSpeed)
	{
		/* 
		 *  moveSpeed and rotateSpeed are +/- 1 where 1=full voltage
		 */
		double leftMotorSpeed  = getLeftMotorSpeed(moveSpeed, rotateSpeed);
		double rightMotorSpeed = getRightMotorSpeed(moveSpeed, rotateSpeed);
		frontLeftTalon.set(ControlMode.PercentOutput, leftMotorSpeed);
		frontRightTalon.set(ControlMode.PercentOutput, rightMotorSpeed);
	}

	double getLeftMotorSpeed(double moveSpeed, double rotateSpeed)
	{
		int[] indices = {16, 16};

		indices = getIndex(moveSpeed, rotateSpeed);

		return DriveTable.Drive_Matrix_2017[indices[1]][indices[0]];
	}

	double getRightMotorSpeed(double moveSpeed, double rotateSpeed)
	{
		int[] indices = {16, 16};

		indices = getIndex(moveSpeed, rotateSpeed);
		indices[0] = 32 - indices[0];

		return DriveTable.Drive_Matrix_2017[indices[1]][indices[0]];
	}

	int[] getIndex(double moveSpeed, double rotateSpeed)
	{		
		double diff1 = 0;
		double diff2 = 0;
		// [0] is x, [1] is y
		int[] returnIndex = {0, 0};

		double[] arrayPtr = DriveTable.Drive_Lookup_X;
		int arrayLength = DriveTable.Drive_Lookup_X.length;

		double rotateValue = Utility.limitValue(rotateSpeed, arrayPtr[0], arrayPtr[arrayLength-1]);

		for(int i = 0; i < arrayLength; i++) 
		{
			if(i+1 >= arrayLength || inRange(rotateValue, arrayPtr[i], arrayPtr[i+1]))
			{
				//Assume match found
				if((i + 1) >= arrayLength)
				{
					returnIndex[0] = i;	
				}
				else
				{
					diff1 = Math.abs(rotateValue - arrayPtr[i]);
					diff2 = Math.abs(rotateValue - arrayPtr[i+1]);

					if(diff1 < diff2)
					{
						returnIndex[0] = i;
					}
					else
					{
						returnIndex[0] = i + 1;
					}
				}
				break;
			}
		}

		arrayPtr = DriveTable.Drive_Lookup_Y;
		arrayLength = DriveTable.Drive_Lookup_Y.length;
		double moveValue = Utility.limitValue(moveSpeed, arrayPtr[0], arrayPtr[arrayLength - 1]);

		for( int i = 0; i < arrayLength; i++) 
		{
			if(i+1 >= arrayLength || inRange(moveValue, arrayPtr[i], arrayPtr[i+1]))
			{
				//Assume match found
				if((i + 1) >= arrayLength)
				{
					returnIndex[1] = i;	
				}
				else
				{
					diff1 = Math.abs(moveValue - arrayPtr[i]);
					diff2 = Math.abs(moveValue - arrayPtr[i+1]);

					if(diff1 < diff2)
					{
						returnIndex[1] = i;
					}
					else
					{
						returnIndex[1] = i + 1;
					}
				}
				break;
			}
		}

		return returnIndex;
	}

	boolean inRange(double testValue, double bound1, double bound2) 
	{  
		return (((bound1 <= testValue) && (testValue <= bound2)) ||
				((bound1 >= testValue) && (testValue >= bound2)));
	}







}
