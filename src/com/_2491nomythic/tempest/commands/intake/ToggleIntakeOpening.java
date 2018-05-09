package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 * Toggles between the open and closed states of the intake arms
 */
public class ToggleIntakeOpening extends CommandBase {

	/**
	 * Toggles between the open and closed states of the intake arms
	 */
	public ToggleIntakeOpening() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if(intake.fingersOpened()) {
			intake.retractFingers();
		}
		else {
			intake.openFingers();
		}
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
