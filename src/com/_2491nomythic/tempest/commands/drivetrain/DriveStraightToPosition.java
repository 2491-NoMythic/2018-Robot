package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 * Drives the robot straight forward, then stops after a certain distance.
 */
public class DriveStraightToPosition extends CommandBase {
	double speed, distance, initialPosition;

	/**
	 * Drives the robot straight forward, then stops after a certain distance.
	 * @param speed The speed to drive the robot with, in percent voltage.
	 * @param distance The distance to drive the robot forward, in inches.
	 */
	public DriveStraightToPosition(double speed, double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		this.speed = speed;
		this.distance = distance;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		initialPosition = drivetrain.getDistance();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drivetrain.drivePercentOutput(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return drivetrain.getDistance() >= Math.abs(initialPosition + distance);
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
