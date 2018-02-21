package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 * Keeps track of the readiness of shooter motors for firing consistently
 */
public class MonitorRPS extends CommandBase {
	private double tolerance, targetRPS;

	/**
	 * Keeps track of the readiness of shooter motors for firing consistently
	 */
	public MonitorRPS() {
		tolerance = 0.8;
		targetRPS = Variables.shooterRPS;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (shooter.getShootVelocity() >= targetRPS - tolerance) {
			System.out.println("Hey, MonitorRPS is running");
			if (shooter.getLeftShootVelocity() < shooter.getRightShootVelocity() - tolerance) {
				Variables.leftShootSpeed = (Variables.leftShootSpeed + 1) * (shooter.getLeftShootVelocity() / shooter.getRightShootVelocity());
				System.out.println("Hey, leftShootSpeed should have just changed");
			}
			if (shooter.getRightShootVelocity() < shooter.getRightShootVelocity() - tolerance) {
				Variables.rightShootSpeed = (Variables.rightShootSpeed + 1) * (shooter.getRightShootVelocity() / shooter.getLeftShootVelocity());
			}
			if (shooter.getLeftShootVelocity() > shooter.getRightShootVelocity() + tolerance) {
				Variables.leftShootSpeed = Variables.leftShootSpeed * (shooter.getRightShootVelocity() / shooter.getLeftShootVelocity());
			}
			if (shooter.getRightShootVelocity() > shooter.getLeftShootVelocity() + tolerance) {
				Variables.rightShootSpeed = Variables.rightShootSpeed * (shooter.getLeftShootVelocity() / shooter.getRightShootVelocity());
			}
			if (Math.abs(shooter.getRightShootVelocity() - shooter.getLeftShootVelocity()) <= 0 + tolerance) {
				Variables.readyToFire = true;
			}
			else {
				Variables.readyToFire = false;
			}
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
