package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	public static ADXRS450_Gyro gyro; 
	
    static Joystick driverStick = new Joystick(0);
	
	public static SensorCollection leftSensorCollection;
	public static SensorCollection rightSensorCollection;
	
	public static double gyroBias=0;

	public static DigitalInput practiceRobotJumperPin;

	public static boolean isCargoDetectionEnabled = false;
	private static boolean isCargoDetected = false;
	private static boolean isOverCurrentDetected = false;
	
	static {
		Robot.drivetrain.backLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		Robot.drivetrain.backRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
	   
		practiceRobotJumperPin = new DigitalInput(RobotMap.PRACTICE_ROBOT_JUMPER);
	}
	
	public static double getLeftDistance() {
		// In METERS
		return -Robot.drivetrain.backLeftTalon.getSelectedSensorPosition(0) * Constants.MetersPerPulse;
	}

	public static double getRightDistance() {
		// In METERS
		return -Robot.drivetrain.backRightTalon.getSelectedSensorPosition(0) * Constants.MetersPerPulse;
	}

	public static void resetEncoders() {
		Robot.drivetrain.backLeftTalon.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.backRightTalon.setSelectedSensorPosition(0, 0, 0);
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Practice Bot", isPracticeBot());
	// 	SmartDashboard.putNumber("Left Pos(M)", getLeftDistance());
	// 	SmartDashboard.putNumber("Right Pos(M)", getRightDistance());
	// 	SmartDashboard.putNumber("Elev Position", Robot.elevator._elevatorMotor.getSelectedSensorPosition(0));     
	//	SmartDashboard.putNumber("Elev Velocity", Robot.elevator._elevatorMotor.getSelectedSensorVelocity(0));
		
	// 	SmartDashboard.putNumber("Target Heading", Robot.accumulatedHeading);		
	// 	SmartDashboard.putNumber("Robot Heading", gyro.getAngle());
	// 	SmartDashboard.putNumber("Left Velocity",-Robot.drivetrain.frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	// 	SmartDashboard.putNumber("Right Velocity",-Robot.drivetrain.frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);

		double totalCurrent = 0;

		totalCurrent += Robot.drivetrain.backLeftTalon.getOutputCurrent();
		totalCurrent += Robot.drivetrain.backRightTalon.getOutputCurrent();
		totalCurrent += Robot.drivetrain.frontLeftTalon.getOutputCurrent();
		totalCurrent += Robot.drivetrain.frontRightTalon.getOutputCurrent();

		SmartDashboard.putNumber("MOTOR Current", totalCurrent);
	 }
	 
	 public static boolean isPracticeBot() {
		return !practiceRobotJumperPin.get();
	}
}
