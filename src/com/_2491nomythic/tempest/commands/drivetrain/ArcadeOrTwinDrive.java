package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Allows switching between arcade and twin-stick drive based on controllers present
 */
public class ArcadeOrTwinDrive extends CommandBase {
	private double lastLeftSpeed, currentLeftSpeed, lastRightSpeed, currentRightSpeed, turnSpeed;

	/**
	 * Allows switching between arcade and twin-stick drive based on controllers present
	 */
	public ArcadeOrTwinDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(DriverStation.getInstance().getJoystickName(1) != "Logitech Extreme 3D") {
			turnSpeed = 0.5 * oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveTurnAxis, 0.1);
			
			lastLeftSpeed = currentLeftSpeed;
			lastRightSpeed = currentRightSpeed;
			
			currentLeftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, 0.05) + turnSpeed;
			currentRightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, 0.05) - turnSpeed;
			
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
			
			
			drivetrain.drivePercentOutput(currentLeftSpeed + turnSpeed, currentRightSpeed - turnSpeed);
		}
		else {
			lastLeftSpeed = currentLeftSpeed;
			lastRightSpeed = currentRightSpeed;
			
			currentLeftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveMainAxis, 0.05);
			currentRightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.driveMainAxis, 0.05);
			
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
			
			
			drivetrain.drivePercentOutput(currentLeftSpeed, currentRightSpeed);
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
