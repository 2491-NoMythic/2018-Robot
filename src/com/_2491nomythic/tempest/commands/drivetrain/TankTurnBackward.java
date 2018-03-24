package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

/**
 * Drives the robot with linear acceleration as according to input from a driver's controller
 */
public class TankTurnBackward extends CommandBase {
	private double turnSpeed, currentLeftSpeed, currentRightSpeed, lastLeftSpeed, lastRightSpeed;	
	
	/**
	 * Drives the robot with linear acceleration as according to input from a driver's controller
	 */
	public TankTurnBackward() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		turnSpeed = 0.8 * oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveTurnAxis, 0.05);
		
		lastLeftSpeed = currentLeftSpeed;
		lastRightSpeed = currentRightSpeed;
		
		currentLeftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, 0.1) + turnSpeed;
		currentRightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, 0.1) - turnSpeed;
		
		if (Variables.useLinearAcceleration) {
			double leftAcceleration = (currentLeftSpeed - lastLeftSpeed);
			double signOfLeftAcceleration = leftAcceleration / Math.abs(leftAcceleration);
			if (Math.abs(leftAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below accel and is fine
				if (Math.abs(currentLeftSpeed) - Math.abs(lastLeftSpeed) > 0) {
					//System.out.println(currentLeftSpeed + " was too high, setting to " + (lastLeftSpeed + (Variables.accelerationSpeed * signOfLeftAcceleration)));
					currentLeftSpeed = lastLeftSpeed + (Variables.accelerationSpeed * signOfLeftAcceleration);
					
				}
				// if the difference between the numbers is positive it is going up
			}
			double rightAcceleration = (currentRightSpeed - lastRightSpeed);
			double signOfRightAcceleration = rightAcceleration / Math.abs(rightAcceleration);
			if (Math.abs(rightAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below 0.05 accel and is fine
				if (Math.abs(currentRightSpeed) - Math.abs(lastRightSpeed) > 0) {
					//System.out.println(currentRightSpeed + " was too high, setting to " + (lastRightSpeed + (Variables.accelerationSpeed * signOfRightAcceleration)));
					currentRightSpeed = lastRightSpeed + (Variables.accelerationSpeed * signOfRightAcceleration);
				}
				// if the difference between the numbers is positive it is going up
			}
		}
		
		if (turnSpeed > 0) {
			drivetrain.drivePercentOutput(0, -turnSpeed);
		}
		else if (turnSpeed < 0) {
			drivetrain.drivePercentOutput(turnSpeed, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
