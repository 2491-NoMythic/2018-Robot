package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *Runs the shooter system for a specified amount of time, and at a specified speed
 */
public class RunShooterTime extends CommandBase {
	private double speed;
	private double time;
	private Timer timer;

	/**
	 *Runs the shooter system for a specified amount of time, and at a specified speed
	 */
	public RunShooterTime(double speed, double time) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(shooter);
		
		this.speed = speed;
		this.time = time;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		timer.reset();
		shooter.run(speed);
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
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
