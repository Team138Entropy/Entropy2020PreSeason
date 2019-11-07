/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveTurret extends Command {
  boolean forwards;
  public MoveTurret(boolean forwards) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    System.out.println("MoveTurret: " + forwards);
    this.forwards = forwards;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("MoveTurret init: " + forwards);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println("MoveTurret: " + forwards);
    Robot.rotatorTalon.set(ControlMode.PercentOutput, this.forwards ? 0.11f : -0.11f);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
