/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Logger;
import frc.robot.Robot;

public class MoveTurret extends Command {
  boolean forwards;
  static Logger logger = new Logger("turret");
  public MoveTurret(boolean forwards) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    logger.log("MoveTurret: " + forwards);
    this.forwards = forwards;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    logger.log("MoveTurret init: " + forwards);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    logger.log("Moving MoveTurret: " + forwards);
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
    logger.log("Ending MoveTurret " + forwards);
    Robot.rotatorTalon.set(ControlMode.PercentOutput, 0f);
  }


  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    logger.log("Interrupting MoveTurret " + forwards);
    Robot.rotatorTalon.set(ControlMode.PercentOutput, 0f);
  }
}
