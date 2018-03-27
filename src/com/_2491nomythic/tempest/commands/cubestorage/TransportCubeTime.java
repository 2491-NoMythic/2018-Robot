package com._2491nomythic.tempest.commands.cubestorage;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *Runs cube storage system for specified period of time at specified speed.
 */
public class TransportCubeTime extends CommandBase {
	private double speed;
	private double time;
	Timer timer;

	/**
	 * Runs cube storage system for specified period of time at specified speed.
	 * @param speed the speed at which the cube storage system runs.
	 * @param time the time for which the cube storage system runs.
	 */
	public TransportCubeTime(double speed, double time) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(cubeStorage);
		this.speed = speed;
		this.time = time;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		cubeStorage.runWithoutRoller(speed);
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() > time;
	}

	// Called once after isFinished returns true
	protected void end() {
		cubeStorage.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		cubeStorage.stop();
	}
}
