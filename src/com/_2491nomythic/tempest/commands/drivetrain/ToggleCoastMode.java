package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 * Toggles talons between coast and brake mode
 */
public class ToggleCoastMode extends CommandBase {

	/**
	 * Toggles talons between coast and brake mode
	 */
	public ToggleCoastMode() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drivetrain.enableCoastMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.enableBrakeMode();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
