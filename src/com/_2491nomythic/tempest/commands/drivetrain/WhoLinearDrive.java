package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

import edu.wpi.first.wpilibj.Timer;

/**
 * Controls the robot with linear acceleration as according to driver control input
 */
public class WhoLinearDrive extends CommandBase {
	private double turnSpeed, leftSpeed, rightSpeed, rawLeftSpeed, rawRightSpeed, accelerationInterval, time, timeAddition;
	int state;
	Timer timer;

	/**
	 * Controls the robot with linear acceleration as according to driver control input
	 */
	public WhoLinearDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
		timer.start();
		timer.reset();
		accelerationInterval = .6;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		turnSpeed =  0.75 * oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveTurnAxis, 0.1);
		rawLeftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, .05);
		rawRightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, .05);
		
		if (Math.abs(oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, .1)) <= .1) {
			timer.reset();
		}
		
		switch (state) {
		case 0:
			time = timer.get() + timeAddition;
			timeAddition = 0;
			leftSpeed = .2 * rawLeftSpeed;
			rightSpeed = .2 * rawRightSpeed;
			if (time < accelerationInterval) {
				state++;
			}
			if (Math.abs(rawLeftSpeed) < .2 * rawLeftSpeed && Math.abs(rawRightSpeed) < .2 * rawRightSpeed) {
				timer.reset();
			}
			break;
		case 1:
			time = timer.get() + timeAddition;
			timeAddition = 0;
			leftSpeed = .4 * rawLeftSpeed;
			rightSpeed = .4 * rawRightSpeed;
			if (time > (accelerationInterval) && timer.get() < (2*accelerationInterval)) {
				state++;
			}
			if (Math.abs(rawLeftSpeed) < .4 * rawLeftSpeed && Math.abs(rawRightSpeed) < .4 * rawRightSpeed) {
				timer.reset();
				state = 0;
			}
			break;
		case 2:
			time = timer.get() + timeAddition;
			timeAddition = 0;
			leftSpeed = .6 * rawLeftSpeed;
			rightSpeed = .6 * rawRightSpeed;
			if (time > (2*accelerationInterval) && timer.get() < (3*accelerationInterval)) {
				state++;
			}
			if (Math.abs(rawLeftSpeed) < .6 * rawLeftSpeed && Math.abs(rawRightSpeed) < .6 * rawRightSpeed) {
				timer.reset();
				state = 1;
				timeAddition = .5*accelerationInterval;
			}
			break;
		case 3:
			time = timer.get() + timeAddition;
			timeAddition = 0;
			leftSpeed = .8 * rawLeftSpeed;
			rightSpeed = .8 * rawRightSpeed;
		   	if (time > (3*accelerationInterval) && timer.get() < (4*accelerationInterval)) {
		   		state++;
		   	}
			if (Math.abs(rawLeftSpeed) < .8 * rawLeftSpeed && Math.abs(rawRightSpeed) < .8 * rawRightSpeed) {
				timer.reset();
				state = 2;
				timeAddition = accelerationInterval;
			}
			break;
		case 4:
			time = timer.get() + timeAddition;
			timeAddition = 0;
			leftSpeed = rawLeftSpeed;
			rightSpeed = rawRightSpeed;
			if (Math.abs(rawLeftSpeed) < rawLeftSpeed && Math.abs(rawRightSpeed) < rawRightSpeed) {
				timer.reset();
				state = 3;
				timeAddition = 2*accelerationInterval;
			}
			break;
		}
		
		
		drivetrain.drivePercentOutput(leftSpeed + turnSpeed, rightSpeed - turnSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.stop();
		timer.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
