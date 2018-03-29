package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Shoots a cube from CubeStorage using driver input
 */
public class SwitchShoot extends CommandBase {
	TransportCubeTime transportCube;
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
		transportCube = new TransportCubeTime(1, 0.5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch(state) {
		case 0:
			if(!shooter.inSwitchPosition()) {
				shooter.setSwitchPosition();
				wasLowered = false;
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
				state++;
			}
			
			break;
		case 2:
			if(Variables.readyToFire && oi.getButton(ControllerMap.driveController, ControllerMap.driverSwitchShootButton)) {
				transportCube.start();
				state++;
			}
			break;
		case 3:	
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
		transportCube.cancel();
		cubeStorage.stop();
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
