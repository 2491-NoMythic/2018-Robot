package com._2491nomythic.robot.commands;

import com._2491nomythic.robot.settings.Constants;
import com._2491nomythic.robot.settings.ControllerMap;
import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Shoots a cube from CubeStorage using driver input
 */
public class SwitchShoot extends CommandBase {
	Timer timer;
	int state;
	boolean wasLowered;

	/**
	 * Shoots a cube from CubeStorage using driver input
	 */
	public SwitchShoot() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(shooter);
		requires(cubeStorage);
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch(state) {
		case 0:
			if(shooter.isRaised()) {
				shooter.lowerShooter();
			}
			else {
				wasLowered = true;
			}
			
			timer.start();
			timer.reset();
			state++;
			break;
		case 1:
			if(wasLowered) {
				shooter.run(Constants.shooterSwitchSpeed);   			
				state++;
			}
			else if (timer.get() > Constants.timeForShooterToRaise) {
				shooter.run(Constants.shooterSwitchSpeed);
			}
			
			break;
		case 2:
			if(Variables.readyToFire && oi.getButton(ControllerMap.driveController, ControllerMap.driverSwitchShootButton)) {
				cubeStorage.run(1);
				state++;
			}
			break;
		default:
			System.out.println("Unexpected state in SwitchShoot. State: " + state);
			break;
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
	}
}
