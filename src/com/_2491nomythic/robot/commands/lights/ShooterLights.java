package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

/**
 * Sets shooter lights to different patterns based on shooter motor speed
 */
public class ShooterLights extends CommandBase {
	private byte[] lightsPattern;

	/**
	 * Sets shooter lights to different patterns based on shooter motor speed
	 */
	public ShooterLights() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(sickLights);
		lightsPattern = new byte[1];
	}

	// Called just before this Command runs the firt time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Variables.currentShooterRPM > 1) {
			if(Variables.currentShooterRPM <= (Variables.shooterSpeed / 3)) {
				lightsPattern[0] = 2; 
			}
			else if(Variables.currentShooterRPM <= (Variables.shooterSpeed * 2/3)) {
				lightsPattern[0] = 3;
			}
			else if(Variables.currentShooterRPM < (Variables.shooterSpeed)) {
				lightsPattern[0] = 4;
			}
			else {
				lightsPattern[0] = 5;
			}
		}
		else {
			lightsPattern[0] = 1;
		}
		
		sickLights.writeSignal(lightsPattern);
		
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
