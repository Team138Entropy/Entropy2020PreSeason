/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
class Gains {
	public final double kP;
	public final double kI;
	public final double kD;
	public final double kF;
	public final int kIzone;
	public final double kPeakOutput;
	
	public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
		kP = _kP;
		kI = _kI;
		kD = _kD;
		kF = _kF;
		kIzone = _kIzone;
		kPeakOutput = _kPeakOutput;
	}
}
class Constants {
	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;

	/**
	 * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
    static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);
}
/**
 * Add your docs here.
 */
public class PIDControl {
    static private PIDControl instance;
    private WPI_TalonSRX talon;

    private PIDControl(){
    }

    public void init(WPI_TalonSRX talon){
        this.talon = talon;
        talon.configFactoryDefault();
        talon.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, Constants.kPIDLoopIdx,
        Constants.kTimeoutMs);

        /* set deadband to super small 0.001 (0.1 %).
            The default deadband is 0.04 (4 %) */
        talon.configNeutralDeadband(0.001, Constants.kTimeoutMs);

        /**
         * Configure Talon SRX Output and Sesnor direction accordingly Invert Motor to
         * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
         * sensor to have positive increment when driving Talon Forward (Green LED)
         */
        talon.setSensorPhase(false);
        talon.setInverted(false);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

        /* Set the peak and nominal outputs */
        talon.configNominalOutputForward(0, Constants.kTimeoutMs);
        talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
        talon.configPeakOutputForward(1, Constants.kTimeoutMs);
        talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

        /* Set Motion Magic gains in slot0 - see documentation */
        talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
        talon.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
        talon.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
        talon.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
        talon.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

        /* Set acceleration and vcruise velocity - see documentation */
        talon.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
        talon.configMotionAcceleration(6000, Constants.kTimeoutMs);

        /* Zero the sensor once on robot boot up */
        talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

    public void periodic(){
        double targetPos = 0.5 * 4096 * 10.0;
        talon.set(ControlMode.MotionMagic, targetPos);
    }

	public static PIDControl getInstance(){
		if(instance == null){
			instance = new PIDControl();
        }
		return instance;
	}

}
