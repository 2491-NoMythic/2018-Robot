package com._2491nomythic.robot.commands.shooter;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

/**
 * Keeps track of the readiness of shooter motors for firing consistently
 */
public class MonitorRPM extends CommandBase {
	private double tolerance, targetRPM;
	private boolean accelerateReady, shootReady;

	/**
	 * Keeps track of the readiness of shooter motors for firing consistently
	 */
	public MonitorRPM() {
		tolerance = 5; //TODO Find a reasonable tolerance value for checking shooter motor RPM
		targetRPM = Variables.shooterSpeed;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (shooter.getAllMotorVelocity() >= targetRPM) {
			if (((shooter.getLeftAccelerateVelocity() + tolerance) > shooter.getRightAccelerateVelocity()) && ((shooter.getLeftAccelerateVelocity() - tolerance) < shooter.getRightAccelerateVelocity())) {
				accelerateReady = true;
			}
			else {
				accelerateReady = false;
			}
		
			if (((shooter.getLeftShootVelocity() + tolerance) > shooter.getRightShootVelocity()) && ((shooter.getLeftShootVelocity() - tolerance) < shooter.getRightShootVelocity())) {
				shootReady = true;
			}
			else {
				shootReady = false;
			}
			
			if (accelerateReady && shootReady) {
				Variables.readyToFire = true;
			}
			else {
				Variables.readyToFire = false;
			}
		}
		else {
			Variables.readyToFire = false;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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
