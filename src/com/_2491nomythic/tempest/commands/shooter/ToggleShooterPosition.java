package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 *Lowers shooter if raised, raises if lowered.
 */
public class ToggleShooterPosition extends CommandBase {

	/**
	 * Lowers shooter if raised, raises if lowered.
	 */
	public ToggleShooterPosition() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (shooter.inSwitchPosition() && !intake.armsRetracted()) {
			shooter.setScalePosition();
		}
		else {
			shooter.setSwitchPosition();
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
		end();
	}
}
