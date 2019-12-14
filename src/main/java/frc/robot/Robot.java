package frc.robot;

import java.util.concurrent.CompletableFuture;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.Config.Key;
import frc.robot.events.EventWatcherThread;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.*;

/**
 * This is the development branch.
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory .
 */
public class Robot extends TimedRobot {
    public static final ShuffleboardTab main = Shuffleboard.getTab("SmartDashboard");
    public static final ShuffleboardHandler shuffHandler = new ShuffleboardHandler();
    static Potentiometer pot = new AnalogPotentiometer(0, 360, 0);
    public static final PotPID potHandler = new PotPID(pot);

    // Subsystems
    private static final Compressor compressor = new Compressor();
    // public static final Drivetrain drivetrain = new Drivetrain();
    //public static final DriverVision driverVision = new DriverVision();

    private static double accumulatedHeading = 0.0; // Accumulate heading angle (target)
    public static final OI oi = new OI();
    Preferences prefs = Preferences.getInstance();

    private NetworkTable table;

    private double count = 0;
    private double previousYaw = 0;

    private double initialYaw = 0.0;
    
    static Logger robotLogger = new Logger("robot");
    static Logger visionLogger = new Logger("vision");
    static Logger potLogger = new Logger("pot");
    
    public static WPI_TalonSRX rotatorTalon = new WPI_TalonSRX(1);

    // Global constants
    private static String mode; // "auto" or "teleop"
    public static String gameData;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        Config.getInstance().reload();
        rotatorTalon.set(ControlMode.PercentOutput, 0f);

        robotLogger.log("ROBOT INIT");

        //VisionThread.getInstance().start();
    	// drivetrain.DriveTrainInit();
        compressor.start();
        // map the values on a scale of 0-360 (was 0-1)
        // the final value is the "offset", which is added to each result
    
        Robot.accumulatedHeading = 0;
        //TODO: why is this commented out???
        // Constants.practiceBot = isPracticeRobot();

        EventWatcherThread.getInstance().start();
        shuffHandler.init();

        DashboardThread.getInstance().start();

        // prints an important message to the console after a delay
        new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        DriverStation.reportError("\n\n=======================================================\nHEY YOU! Click the gear in the driver station and enable prints.\n=======================================================\n\n", false);
                    }
                }, 
                1000 
        );
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        Config.getInstance().reload();
        potHandler.disable();
    }

    @Override
    public void disabledPeriodic() {

    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        Config.getInstance().reload();
        robotLogger.log("AUTONOMOUS INIT");
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        potLogger.debug("got pot value " + Double.toString(Math.round(pot.get() * 10) / 10.0));
    }

    @Override
    public void teleopInit() {
        Config.getInstance().reload();
        potHandler.enable();

        robotLogger.log("TELEOP INIT");

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        table = inst.getTable("SmartDashboard");
        
        mode = "teleop";
        //Sensors.resetEncoders();
        // Sensors.gyro.reset();
        Robot.accumulatedHeading = 0;
        // Robot.drivetrain.Relax();

        //Constants.AutoEnable = true;
        //Constants.IntegralError = 0;
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        float potMin = Config.getInstance().getFloat(Key.OI__VISION__POT__MIN);
        float potMax = Config.getInstance().getFloat(Key.OI__VISION__POT__MAX);
        float edgeAvoidance = Config.getInstance().getFloat(Key.OI__VISION__POT__EDGE_AVOIDANCE);

        boolean allowMovement = pot.get() + edgeAvoidance < potMax && pot.get() - edgeAvoidance > potMin;
        potLogger.debug("allow movement " + allowMovement);

//		LiveWindow.run();Scheduler.getInstance().run();
        if(Config.getInstance().getBoolean(Key.OI__VISION__ENABLED)){
            oi.forward.cancel();
            oi.backward.cancel();
            count ++;
            if(count == 2){
                count = 0;

                boolean tapeDetected = table.getEntry("tapeDetected").getBoolean(false);
                double thisYaw = table.getEntry("tapeYaw").getDouble(0.0);

                if(initialYaw == 0){
                    initialYaw = thisYaw;
                }

                // thisYaw = thisYaw - initialYaw;
                //70 is the maximum, -70 is the minimum


                //7 is our "deadband"
                //TODO: add to config
                if(Math.abs(thisYaw) > 7 && tapeDetected){
                    potHandler.enable();
                    previousYaw = thisYaw;
        
                    double setPoint = (thisYaw + 80) / 160;
                    potLogger.verbose("targeting set point " + Double.toString(setPoint));
                    potHandler.setSetpoint(setPoint);

                    visionLogger.verbose("thisYaw " + thisYaw + " tapeDetected " + tapeDetected);
                }else{
                    potHandler.disable();
                    visionLogger.debug("Not getting any output " + Double.toString(thisYaw) + " " + tapeDetected);
                    Robot.rotatorTalon.set(ControlMode.PercentOutput, 0f);
                }
            }
            
        }else{
            visionLogger.debug("Not enabled");
        } 
    }

    private static boolean isPracticeRobot() {
        return (!Sensors.practiceRobotJumperPin.get());
    }

    @Override
    public void testInit(){
        Config.getInstance().reload();
        
        robotLogger.log("TEST INIT");
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        //      LiveWindow.run();
    }
}
