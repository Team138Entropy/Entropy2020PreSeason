/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class PotPID extends PIDSubsystem {
  /**
   * Add your docs here.
   */
  Potentiometer pot;
  public PotPID(Potentiometer pot) {
    // Intert a subsystem name and PID values here
    super("PotPID", 1, 5, 0.2);
    this.pot = pot;
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  protected double returnPIDInput() {
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    return this.pot.get() / 360;
  }

  @Override
  protected void usePIDOutput(double output) {
    System.out.println("pid out " + output);
    Robot.rotatorTalon.set(ControlMode.PercentOutput, output / 10);
  }
}
