package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

import frc.robot.Constants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public final class OI {

    public static class NykoController extends Joystick {
        // Buttons
        // These are package-private because we should only use them in OI to map them to commands
        static final int button1 = 1;
        static final int button2 = 2;
        static final int button3 = 3;
        static final int button4 = 4;
        static final int leftBumper = 5;
        static final int rightBumper = 6;
        static final int leftTrigger = 7;
        static final int rightTrigger = 8;
        static final int middle9 = 9;
        static final int middle10 = 10;
        static final int middle11 = 11;
        static final int leftStick = 12;
        static final int rightStick = 13;

        // Axes
        // These are public because they need to be accessible to events
        public static final int leftXAxis = 0;        // X Axis on Driver Station
        public static final int leftYAxis = 1;        // Y Axis on Driver Station
        public static final int rightYAxis = 2;    // Z Axis on Driver Station
        public static final int rightXAxis = 3;    // Rotate Axis on Driver Station

        public NykoController(int port) {
            super(port);
        }

    }

    public static class XboxController extends Joystick {
        // Buttons
        // These are package-private because we should only use them in OI to map them to commands
        static final int a = 1;
        static final int b = 2;
        static final int x = 3;
        static final int y = 4;
        static final int leftBumper = 5;
        static final int rightBumper = 6;
        static final int leftStick = 7;
        static final int rightStick = 8;
        static final int menu = 9;
        static final int view = 10;
        static final int home = 11;
        static final int dPadUp = 12;
        static final int dPadDown = 13;
        static final int dPadLeft = 14;
        static final int dPadRigt = 15;

        // Axes
        // These are public because they need to be accessible to events
        public static final int leftXAxis = 0;
        public static final int leftYAxis = 1;
        public static final int leftTriggerAxis = 2;
        public static final int rightTriggerAxis = 3;
        public static final int rightXAxis = 4;
        public static final int rightYAxis = 5;

        public XboxController(int port) {
            super(port);
        }
    }

    public static class FlightStick extends Joystick {
        // Flight Stick-specific constants
        static final double deadband = 0.1;

        // Buttons
        // These are package-private because we should only use them in OI to map them to commands
        static final int trigger = 0;
        static final int lowerThumb = 1;
        static final int topMiddle = 2;
        static final int topLeft = 3;
        static final int topRight = 4;
        static final int farLeft = 5;
        static final int nearLeft = 6;
        static final int middleLeft = 7;
        static final int middleRight = 8;
        static final int nearRight = 9;
        static final int farRight = 10;

        // Axes
        // These are public because they need to be accessible to events
        public static final int xAxis = 0;
        public static final int yAxis = 1;
        public static final int bottomWheel = 2;

        public FlightStick(int port) {
            super(port);
        }
    }

    public static FlightStick leftDriveStick = new FlightStick(Constants.leftFlightStickPort);
    public static FlightStick rightDriveStick = new FlightStick(Constants.rightFlightStickPort);
    public static NykoController operatorStick = new NykoController(Constants.nykoControllerPort);

    static double lastX = 0;
    static double LastY = 0;

    // Buttons are private because we should only use them once to map them to commands.
    // Driver
    private static Button climbPistonButton  = new JoystickButton(rightDriveStick, FlightStick.topMiddle);
} // :D)))

