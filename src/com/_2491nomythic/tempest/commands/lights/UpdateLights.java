package com._2491nomythic.tempest.commands.lights;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Updates the pattern sent to the lights during Teleop and Autonomous
 */
public class UpdateLights extends CommandBase {
	private Timer timer;
	private boolean newCube;

	/**
	 * Updates the pattern sent to the lights during Teleop and Autonomous
	 */
	public UpdateLights() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(sickLights);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		newCube = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(shooter.getAllMotorVelocity() >= 0.05) {
			if(Variables.readyToFire) {
				sickLights.writePattern(3);
				timer.stop();
				timer.reset();
			}
			else {
				sickLights.writePattern(2);
			}
		}
		else if(cubeStorage.isHeld() && newCube) {
			timer.start();
			sickLights.writePattern(4);
		}
		else {
			sickLights.writePattern(1);
		}
		
		if(timer.get() < 3) {
			newCube = true;
		}
		else {
			newCube = false;
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
