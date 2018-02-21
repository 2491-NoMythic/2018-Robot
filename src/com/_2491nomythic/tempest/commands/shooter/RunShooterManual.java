package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 * Runs the shooter as according to input from a controller
 */
public class RunShooterManual extends CommandBase {

	/**
	 * Runs the shooter as according to input from a controller
	 */
	public RunShooterManual() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooter.run(Variables.leftShootSpeed, Variables.rightShootSpeed, Variables.shooterSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
