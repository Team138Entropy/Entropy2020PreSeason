package frc.robot;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

public final class OI {

    public static class CustomController extends Joystick {

        // Buttons
        public static final int button1 = 1;
        public static final int button2 = 2;
        public static final int button3 = 3;
        public static final int button4 = 4;
        public static final int button5 = 5;
        public static final int button6 = 6;
        public static final int button7 = 7;
        public static final int button8 = 8;
        public static final int button9 = 9;

        // Axes
        public static final int leftXAxis = 0;        // X Axis on Driver Station
        public static final int leftYAxis = 1;        // Y Axis on Driver Station
        public static final int rightYAxis = 2;    // Z Axis on Driver Station
        public static final int rightXAxis = 3;    // Rotate Axis on Driver Station

        public CustomController(int port) {
            super(port);
        }

    }

    // controller port is 0
    public static Joystick customController = new Joystick(0);

    static Button button1 = new JoystickButton(customController, CustomController.button1);
    static Button button2 = new JoystickButton(customController, CustomController.button2);
    static Button button3 = new JoystickButton(customController, CustomController.button3);
    static Button button4 = new JoystickButton(customController, CustomController.button4);
    static Button button5 = new JoystickButton(customController, CustomController.button5);
    static Button button6 = new JoystickButton(customController, CustomController.button6);
    static Button button7 = new JoystickButton(customController, CustomController.button7);
    static Button button8 = new JoystickButton(customController, CustomController.button8);
    static Button button9 = new JoystickButton(customController, CustomController.button9);

	// 	double moveSpeed = driverStick.getRawAxis(XboxController.leftYAxis);

    public OI(){
        button1.whileHeld(new MoveTurret(true));
    }
    
    public static boolean getButton1Pressed(){
        return OI.customController.getRawButtonPressed(OI.CustomController.button1);
    }
    
    public static boolean getButton1(){
        return OI.customController.getRawButton(OI.CustomController.button1);
    }
    
	// public static double getMoveSpeed()
	// {
	// 	// joystick values are opposite to robot directions
	// 	double moveSpeed = driverStick.getRawAxis(XboxController.leftYAxis);
	// 	// Apply thresholds to joystick positions to eliminate
	// 	// creep motion due to non-zero joystick value when joysticks are 
	// 	// "centered"
	// 	if (Math.abs(moveSpeed) < Constants.CloseLoopJoystickDeadband)
	// 		moveSpeed=0;
	// 	return moveSpeed;
	// }
	
	// public static double getRotateSpeed()
	// {
    //     double rotateSpeed;
        
    //     if (Constants.practiceBot) {
    //         rotateSpeed = driverStick.getRawAxis(XboxController.rightXAxis);
    //     }
    //     else {
    //         rotateSpeed = -1 * driverStick.getRawAxis(XboxController.rightXAxis);
    //     }
        
	// 	if (Math.abs(rotateSpeed) < Constants.CloseLoopJoystickDeadband)
	// 		rotateSpeed=0;
	// 	return rotateSpeed;
	// }
	
	// public static boolean isReverse() {
	// 	return driverStick.getRawButton(XboxController.b);
	// }
	
	// public static boolean isFullSpeed() {
	// 	// We don't use a freaking transmission, so just return false
	// 	return false;

    //     // But if we did...
    //     // return driverStick.getRawAxis(xboxRightTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
    // }

    // public static boolean isQuickturn() {
    //     return driverStick.getRawAxis(XboxController.leftTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
    // }
    
} // :D)))
