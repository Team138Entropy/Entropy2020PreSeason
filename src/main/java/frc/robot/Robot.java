package frc.robot;

import java.util.concurrent.CompletableFuture;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	// Interface with players
    public static final ShuffleboardTab main = Shuffleboard.getTab("SmartDashboard");
    public static final ShuffleboardHandler shuffHandler = new ShuffleboardHandler();

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
    
    public static WPI_TalonSRX rotatorTalon = new WPI_TalonSRX(1);

    // Global constants
    private static String mode; // "auto" or "teleop"
    public static String gameData;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Config.getInstance().reload();
        rotatorTalon.set(ControlMode.PercentOutput, 0f);


        robotLogger.log("ROBOT INIT");
        //VisionThread.getInstance().start();
    	// drivetrain.DriveTrainInit();
    	compressor.start();	
        Robot.accumulatedHeading = 0;
        //TODO: why is this commented out???
        // Constants.practiceBot = isPracticeRobot();

        EventWatcherThread.getInstance().start();
        shuffHandler.init();

        DashboardThread.getInstance().start();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {
        Config.getInstance().reload();
    }

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
    public void autonomousInit() {
        Config.getInstance().reload();
        robotLogger.log("AUTONOMOUS INIT");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    public void teleopInit() {
        Config.getInstance().reload();

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
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
//		LiveWindow.run();Scheduler.getInstance().run();
        if(Config.getInstance().getBoolean(Key.OI__VISION__ENABLED)){
            oi.forward.cancel();
            oi.backward.cancel();
            count ++;
            if(count == 6){
                count = 0;

                boolean tapeDetected = table.getEntry("tapeDetected").getBoolean(false);
                double thisYaw = table.getEntry("tapeYaw").getDouble(0.0);

                if(initialYaw == 0){
                    initialYaw = thisYaw;
                }

                thisYaw = thisYaw - initialYaw;

                if(Math.abs(thisYaw) > 1 && tapeDetected/* && Math.abs(previousYaw - thisYaw) < 7*/){
                    float direction = thisYaw < 0 ? 0.11f : -0.11f;
                    Robot.rotatorTalon.set(ControlMode.PercentOutput, direction);
                    visionLogger.debug(Float.toString(direction));
                    previousYaw = thisYaw;
        
                    visionLogger.verbose("thisYaw " + thisYaw + " tapeDetected " + tapeDetected);
                }else{
                    visionLogger.debug("Not getting any output " + Double.toString(thisYaw) + " " + tapeDetected);
                    Robot.rotatorTalon.set(ControlMode.PercentOutput, 0f);
                }
            }
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
    public void testPeriodic() {
        //      LiveWindow.run();
    }
}
