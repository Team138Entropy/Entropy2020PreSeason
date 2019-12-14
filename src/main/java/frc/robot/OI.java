package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.events.EventWatcherThread;
import frc.robot.Config.Key;
import frc.robot.commands.*;
import frc.robot.Constants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public final class OI {
    static float closeLoopJoystickDeadband = Config.getInstance().getFloat(Key.OI__CONTROL__CLOSED_LOOP_JOYSTICK_DEADBAND);

    private static OI instance;

    public static OI getInstance() {
        if (instance == null) instance = new OI();
        return instance;
    }

    public static enum DriveInterface {
        CLASSIC, STICK
    }

    private DriveInterface driveInterface;
    public DriveInterface getDriveInterface() { return driveInterface; }

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
        static final int dPadRight = 15;

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

    // Flight stick controls
    public Optional<Joystick> leftDriveStick = Optional.empty(), rightDriveStick = Optional.empty();

    // Classic controls
    public Optional<Joystick> driverStick = Optional.empty();

    // Nyko controller; this doesn't care about which driver control scheme we're using
    public NykoController operatorStick = new NykoController(Constants.nykoControllerPort);

    private OI() {
        Joystick port0 = new Joystick(0);
        switch (port0.getType()) {
            case kHIDJoystick:
                driveInterface = DriveInterface.STICK;
                leftDriveStick = Optional.of(port0);
                rightDriveStick = Optional.of(new Joystick(Constants.rightFlightStickPort));
                break;
            case kXInputGamepad:
                driveInterface = DriveInterface.CLASSIC;
                driverStick = Optional.of(port0);
                break;
            default:
                throw new RuntimeException("Controller on port 0 did not report a supported device type");
        }
    }

    /*
    // Flight stick controls
    public static FlightStick leftDriveStick = new FlightStick(Constants.leftFlightStickPort);
    public static FlightStick rightDriveStick = new FlightStick(Constants.rightFlightStickPort);

    // Classic controls
    public static Joystick driverStick = new Joystick(Constants.xboxControllerPort);
    public static NykoController operatorStick = new NykoController(Constants.nykoControllerPort);
    */

    static double lastX = 0;
    static double LastY = 0;

} // :D)))
