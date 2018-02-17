package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 * Runs intake motors for a specified amount of time
 */
public class RunIntakeTime extends CommandBase {
	private double power;
	private double time;
	private Timer timer;

	/**
	 * Runs intake motors for a specified amount of time with a specified power
	 * @param desiredPower The power fed to the intake motors
	 * @param desiredTime The time for which the intake motors will run
	 */
	public RunIntakeTime(double desiredPower, double desiredTime) {
		requires(intake);
		requires(cubeStorage);
		power = desiredPower;
		time = desiredTime;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		intake.run(power);
		cubeStorage.run(power);
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() >= time;
	}

	// Called once after isFinished returns true
	protected void end() {
		intake.stop();
		cubeStorage.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
