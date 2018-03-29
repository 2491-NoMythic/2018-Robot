package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Keeps track of and adjusts shooter motors for firing consistently
 * @deprecated
 */
public class MonitorRPS extends CommandBase {
	private double tolerance, targetRPS, delayTime, threshold;
	private Timer delay;
	
	/**
	 * Keeps track of and adjusts shooter motors for firing consistently
	 * @deprecated
	 */
	public MonitorRPS() {
		setRunWhenDisabled(true);
		tolerance = .75;
		delayTime = .5;
		threshold = .1;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		delay = new Timer();
		setRunWhenDisabled(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Variables.useMonitorRPS) {
		targetRPS = Variables.shooterRPS;
		if (shooter.getShootVelocity() >= targetRPS - tolerance) {
			if (delay.get() > delayTime) {
				if (shooter.getLeftShootVelocity() < targetRPS - tolerance) {
					Variables.leftShootSpeed *= 1.15;
					if (Variables.leftShootSpeed > Variables.shooterSpeed) {
						Variables.leftShootSpeed = Variables.shooterSpeed + threshold;
					}
					else if (Variables.leftShootSpeed < Variables.shooterSpeed) {
						Variables.leftShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
				if (shooter.getRightShootVelocity() < targetRPS - tolerance) {
					Variables.rightShootSpeed *= 1.1;
					if (Variables.rightShootSpeed > Variables.shooterSpeed) {
						Variables.rightShootSpeed = Variables.shooterSpeed + threshold;
					}
					else if (Variables.rightShootSpeed < Variables.shooterSpeed) {
						Variables.rightShootSpeed = Variables.shooterSpeed;
					}
					delay.reset();
				}
			}
			if (delay.get() > delayTime) {
				if (shooter.getLeftShootVelocity() > targetRPS + tolerance) {
					Variables.leftShootSpeed *= .95;
					if (Variables.leftShootSpeed < Variables.shooterSpeed - threshold) {
						Variables.leftShootSpeed = Variables.shooterSpeed - (threshold / 2);
					}
					delay.reset();
				}
				if (shooter.getRightShootVelocity() > targetRPS + tolerance) {
					Variables.rightShootSpeed *= .95;
					if (Variables.rightShootSpeed < Variables.shooterSpeed - threshold) {
						Variables.rightShootSpeed = Variables.shooterSpeed - (threshold / 2);
					}
					delay.reset();
				}
			}
		}
			if (Math.abs(shooter.getRightShootVelocity() - shooter.getLeftShootVelocity()) <= 0 + tolerance && shooter.getLeftShootVelocity() + shooter.getRightShootVelocity() > 2 * (targetRPS - tolerance)) {
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
