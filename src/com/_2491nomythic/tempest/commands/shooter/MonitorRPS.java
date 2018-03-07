package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;
import com._2491nomythic.util.ShooterController;

/**
 * Keeps track of the readiness of shooter motors for firing consistently
 */
public class MonitorRPS extends CommandBase {
	private ShooterController shootControl;
	/**
	 * Keeps track of the readiness of shooter motors for firing consistently
	 */
	public MonitorRPS() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		setRunWhenDisabled(true);
		shootControl = new ShooterController(shooter, .015, Variables.rightShootFeedForward);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shootControl.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Variables.useMonitorRPS) {
			shootControl.setF(Variables.rightShootFeedForward);
			Variables.leftShootSpeed = shootControl.leftOut;
			Variables.rightShootSpeed = shootControl.rightOut;
			Variables.readyToFire = shootControl.onTarget();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		shootControl.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
