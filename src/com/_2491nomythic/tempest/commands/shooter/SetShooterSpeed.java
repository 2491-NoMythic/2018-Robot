package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

/**
 *Sets the shooter power based on scale height.
 */
public class SetShooterSpeed extends CommandBase {
	public double shooterSpeed;

	/**
	 * Sets the shooter power based on scale height.
	 * @param shooterSpeed the height the scale is at during the time of the launch.
	 */
	public SetShooterSpeed(double shooterSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.shooterSpeed = shooterSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Variables.leftShootSpeed = shooterSpeed;
		Variables.rightShootSpeed = shooterSpeed;
		Variables.shooterSpeed = shooterSpeed;
		if (shooterSpeed == Constants.shooterSwitchSpeed) {
			Variables.shooterRPS = Constants.shooterSwitchRPS;
			Variables.shootFeedForward = Constants.shootFeedForwardSwitch;
		}
		else if (shooterSpeed == Constants.shooterLowScaleSpeed) {
			Variables.shooterRPS = Constants.shooterLowScaleRPS;
			Variables.shootFeedForward = Constants.shootFeedForwardLow;
		}
		else if (shooterSpeed == Constants.shooterMediumScaleSpeed) {
			Variables.shooterRPS = Constants.shooterMediumScaleRPS;
			Variables.shootFeedForward = Constants.shootFeedForwardMed;
		}
		else if (shooterSpeed == Constants.shooterHighScaleSpeed) {
			Variables.shooterRPS = Constants.shooterHighScaleRPS;
			Variables.shootFeedForward = Constants.shootFeedForwardHigh;
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
