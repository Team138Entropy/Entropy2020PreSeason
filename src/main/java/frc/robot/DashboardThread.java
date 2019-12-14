package frc.robot;

import frc.robot.config.Config;
import frc.robot.config.Config.Key;

/**
 * Singleton thread for updating the SmartDashboard on an interval of {@value Constants#DASHBOARD_INTERVAL} milliseconds.
 */
public class DashboardThread extends Thread {
    static Logger dashLogger = new Logger("dashboardThread");

    private static DashboardThread thread = new DashboardThread();

    public static DashboardThread getInstance() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            Sensors.updateSmartDashboard();
            dashLogger.debug("updating smart dashboard");
            try {
                Thread.sleep(Config.getInstance().getInt(Key.DASHBOARD_INTERVAL));
            } catch (InterruptedException e) {
                dashLogger.warn("Dashboard thread interrupted, dashboard values will not be correct");
                e.printStackTrace();
            }
        }
    }
}