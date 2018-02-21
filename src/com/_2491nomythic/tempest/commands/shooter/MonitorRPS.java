package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Keeps track of the readiness of shooter motors for firing consistently
 */
public class MonitorRPS extends CommandBase {
	private double tolerance, targetRPS, delayTime, threshold;
	private Timer delay;

	/**
	 * Keeps track of the readiness of shooter motors for firing consistently
	 */
	public MonitorRPS() {
		setRunWhenDisabled(true);
		tolerance = 0.8;
		delayTime = 1;
		threshold = .1;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		delay = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		delay.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		targetRPS = Variables.shooterRPS;
		if (shooter.getShootVelocity() >= targetRPS - tolerance) {
			if (delay.get() > delayTime) {
				if (shooter.getLeftShootVelocity() < targetRPS - tolerance) {
					Variables.leftShootSpeed *= (shooter.getLeftShootVelocity() / targetRPS) + 1;
					if (Variables.leftShootSpeed > Variables.shooterSpeed) {
						Variables.leftShootSpeed += threshold;
					}
					else if (Variables.leftShootSpeed < Variables.shooterSpeed) {
						Variables.leftShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
				if (shooter.getRightShootVelocity() < targetRPS - tolerance) {
					Variables.rightShootSpeed *= (shooter.getRightShootVelocity() / targetRPS) +1;
					if (Variables.rightShootSpeed > Variables.shooterSpeed) {
						Variables.rightShootSpeed += threshold;
					}
					else if (Variables.rightShootSpeed < Variables.shooterSpeed) {
						Variables.rightShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
			}
			if (delay.get() > delayTime) {
				if (shooter.getLeftShootVelocity() > targetRPS + tolerance) {
					Variables.leftShootSpeed *= (targetRPS / shooter.getLeftShootVelocity());
					if (Variables.leftShootSpeed < Variables.shooterSpeed) {
						Variables.leftShootSpeed -= threshold;
					}
					else if (Variables.leftShootSpeed > Variables.shooterSpeed) {
						Variables.leftShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
				if (shooter.getRightShootVelocity() > targetRPS + tolerance) {
					Variables.rightShootSpeed *= (targetRPS / shooter.getRightShootVelocity());
					if (Variables.rightShootSpeed < Variables.shooterSpeed) {
						Variables.rightShootSpeed -= threshold;
					}
					else if (Variables.rightShootSpeed > Variables.shooterSpeed) {
						Variables.rightShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
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
