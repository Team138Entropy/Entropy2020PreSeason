package frc.robot;
/*
 * Constant values used throughout robot code.
 * In C/C++ this would be a header file, but alas, this is Java
 */
public class Constants {

	// System Constants
	public static double commandLoopIterationSeconds = 0.020;

	public static boolean practiceBot = false;

	public final static double CloseLoopJoystickDeadband = 0.2;
	public final static double OperatorJoystickDeadband = 0.5;

	// This is our encoder constant for distance (in METERS) per  encoder pulse
	// 6" Wheels, 15:45 chain drive; 256 encoder counts per drive sprocket rotation
	public final static double MetersPerPulse = Math.PI*6*.0254*15/45/256;
	public final static double SecondsTo100Milliseconds = 0.1;

	// TEST ONLY
	public final static int LeftDriveEncoderPolarity = -1;
	public final static int RightDriveEncoderPolarity = 1;

	// 2019 Drive Train constants
	public final static double FullSpeed = 0.75;
	public final static double ClimbSpeed = 0.75;

	// Controller stuff
	public static final int nykoControllerPort = 0;
	public static final int xboxControllerPort = 1;
	public static final int leftFlightStickPort = 2;
	public static final int rightFlightStickPort = 3;

	// Stuff that was deleted that I had to paste back in from the 2018 code
	public final static int zeroDelay= 60; // Approx 40/sec;
	public final static double highSpeedModeTriggerThreshold = 0.3;

	// Dashboard
	public static final long DASHBOARD_INTERVAL = 50; // In milliseconds
}