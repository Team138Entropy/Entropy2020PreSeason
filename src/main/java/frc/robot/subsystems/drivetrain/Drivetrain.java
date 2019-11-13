package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;
import frc.robot.commands.TeleopDrive;

public class Drivetrain extends Subsystem {
	private double lastSpeed = 0;
	private double _speedFactor = Constants.FullSpeed;
	private double _rotateFactor = 1;

	// Servo Loop Gains
	private static final double Drive_Kf = 1.7;
	private static final double Drive_Kp = 5;
	private static final double Drive_Ki = 0.02; //
	private static final double Drive_Kd = 30;

	// Filter state for joystick movements
	double _lastMoveSpeed = 0;
	double lastRotateSpeed=0;

	int zeroCounter=0;

	public WPI_TalonSRX frontLeftTalon  = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_FRONT);
	public WPI_TalonSRX backLeftTalon   = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_BACK);
	public WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_FRONT);
	public WPI_TalonSRX backRightTalon  = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_BACK);

	private DriveEngine driveEngine;

	public DriveEngine getDriveEngine() {
		return driveEngine;
	}

	public void setDriveEngine(DriveEngine driveEngine) {
		this.driveEngine = driveEngine;
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}

	// NOTE: Look at turning this into a constructor
	public void init() {
		/* choose the sensor and sensor direction */
		configFeedbackSensor(backLeftTalon);
		backLeftTalon.setSensorPhase(true);

		/* set the peak and nominal outputs, 12V means full */
		backLeftTalon.configNominalOutputForward(0.,0);
		backLeftTalon.configNominalOutputReverse(0.,0);
		backLeftTalon.configPeakOutputForward(1,0);
		backLeftTalon.configPeakOutputReverse(-1,0);
		backLeftTalon.setNeutralMode(NeutralMode.Brake);
		frontLeftTalon.setNeutralMode(NeutralMode.Brake);

		configFeedbackSensor(backRightTalon);
		backRightTalon.setSensorPhase(true);
		backRightTalon.configNominalOutputForward(0.,0);
		backRightTalon.configNominalOutputReverse(-0.,0);
		backRightTalon.configPeakOutputForward(1,0);
		backRightTalon.configPeakOutputReverse(-1,0);
		backRightTalon.setNeutralMode(NeutralMode.Brake);
		frontRightTalon.setNeutralMode(NeutralMode.Brake);

		// Configure Talon gains
		backLeftTalon.config_kF(0, Drive_Kf,0);
		backLeftTalon.config_kP(0, Drive_Kp,0);
		backLeftTalon.config_kI(0, Drive_Ki,0);
		backLeftTalon.config_kD(0, Drive_Kd,0);
		backRightTalon.config_kF(0, Drive_Kf,0);
		backRightTalon.config_kP(0, Drive_Kp,0);
		backRightTalon.config_kI(0, Drive_Ki,0);
		backRightTalon.config_kD(0, Drive_Kd,0);

		// Configure slave Talons to follow masters
		frontLeftTalon.follow(backLeftTalon);
		frontRightTalon.follow(backRightTalon);
	}

    public void drive() {
		DriveSignal signal = driveEngine.drive();

        backLeftTalon.set(ControlMode.PercentOutput, signal.getLeft() * _speedFactor * -1);
		backRightTalon.set(ControlMode.PercentOutput, signal.getRight() * _speedFactor);
    }

	public void Relax(){
		backLeftTalon.set(ControlMode.PercentOutput, 0);
		backRightTalon.set(ControlMode.PercentOutput, 0);
		SmartDashboard.putNumber("L PWM", -backLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -backRightTalon.getMotorOutputPercent());
	}

	public void setDriveSpeed(double newDriveSpeed){
		_speedFactor = newDriveSpeed;
	}

	private void configFeedbackSensor(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
	}

}
